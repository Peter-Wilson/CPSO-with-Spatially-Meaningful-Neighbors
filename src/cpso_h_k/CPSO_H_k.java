/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso_h_k;
/**
 *
 * @author Peter
 */
public class CPSO_H_k {    
    
        int loops;
        boolean min = true;
        private Swarm[] swarms; 
        private Swarm pso_swarm;
        private double[] solution;
        int dimensionSize;
        int swarmSize;
        double C1 = 0.5;
        double C2 = 0.3;
        double INERTIA = 0.3;
        double PSO_C1 = 0.5;
        double PSO_C2 = 0.3;
        double PSO_INTERTIA = 0.3;
        int maxLoops;
        int k = 0;
        
        public CPSO_H_k(int dimensionSize, int maxLoops, int swarmSize, double Inertia, double c1, double c2, int k)
        {
            this.dimensionSize = dimensionSize;
            this.maxLoops = maxLoops;
            this.swarmSize = swarmSize;
            this.INERTIA = Inertia;
            this.C1 = c1;
            this.C2 = c2;
            this.k = k;
            InitializeSwarms();
        }
        
        public void InitializeSwarms()
        {
            swarms = new Swarm[dimensionSize/k];
            pso_swarm = new Swarm(swarmSize, PSO_C1, PSO_C2, PSO_INTERTIA, min, dimensionSize);
            solution = new double[dimensionSize];
            for(int i = 0; i < dimensionSize/k; i++)
            {
                swarms[i] = new Swarm(swarmSize, C1, C2, INERTIA, min, k);
                
                for(int j = 0; j < k; j++)
                {
                    solution[(i*k)+j] = swarms[i].getParticles()[0].getPosition()[j];
                }
            }
        }
        
        //calculate the fitness of the PSO
        public void start()
        {
            for(int i = 0; i < maxLoops; i++)
            {
                
                // <editor-fold desc="CPSO swarm">
                    /////////////////////////////////////////
                    /////    Update the CPSO Swarms    //////
                    /////////////////////////////////////////
                for (int s = 0; s < swarms.length; s++) //iterate through swarms
                {                    
                    for(Particle p : swarms[s].getParticles()){ //for each particle
                        
                        double fitness = CalculateFitness(s, k, p.getPosition()); //calculate the new fitness
                        UpdateBests(fitness, p, swarms[s]);   
                    }
                    
                    for (Particle p : swarms[s].getParticles()) //move the particles
                    {
                        swarms[s].UpdateVelocity(p);
                        swarms[s].UpdatePosition(p);
                    }                       
                }
                // </editor-fold>
                
                // <editor-fold desc="PSO swarm"> 
                    /////////////////////////////////////////
                    /////     Update the PSO Swarm     //////
                    /////////////////////////////////////////                               
                for(Particle p : pso_swarm.getParticles()){ //for each particle

                    double fitness = CalculateFitness(0, dimensionSize, p.getPosition()); //calculate the new fitness
                    UpdateBests(fitness, p, pso_swarm);                    
                }

                for (Particle p : pso_swarm.getParticles()) //move the particles
                {
                    pso_swarm.UpdateVelocity(p);
                    pso_swarm.UpdatePosition(p);
                }     
                // </editor-fold>
                
                //Transfer knowledge from PSO to CPSO
                
            }
            
            for(int i = 0; i < solution.length; i++) //loop to print off solution
            {
                writeOutput("Solution "+(i+1)+": "+ solution[i]);
            }
            writeOutput("The final fitness value is: "+ CalculateFinalFitness());
        }

        /**
         * Takes the new fitness and particle and determine if it is the new 
         * global best and or personal best
         * @param fitness the new fitness value
         * @param p the particle that is being updated
         * @param swarm the swarm that particle is from
         */
        private void UpdateBests(double fitness, Particle p, Swarm swarm)
        {
            if (p.UpdatePersonalBest(fitness, p.getPosition(), min))  //update the personal best
                writeOutput("New Personal best for " + p + ": x=" + p.getPosition());

            if ((swarm.getGlobalBest() == null) ||
                (p.getFitness() < swarm.getGlobalBest().getFitness() && min) ||
                (swarm.getGlobalBest().getFitness() < p.getFitness() && !min))      //update the global best
            {
                swarm.setGlobalBest(p);
                writeOutput("New Global Best for Swarm " + swarm + ": x=" + p.getPosition());
            }
        }
        
        /**
         * Calculate the fitness of the current swarm
         * @param position the current position
         * @return 
         */
        public double CalculateFitness(int index, int size, double[] position)
        {
            double fitness = 0;
            for(int i = 0; i < (getSolution().length/size); i++)
            {
                if(i == index)
                    for(int j = 0; j < size; j++)
                        fitness += Math.log(position[j]);
                else
                    for(int j = 0; j < size; j++)
                        fitness += Math.log(getSolution()[(i*k)+j]);
            }
            return fitness;   
        }
        
        /**
         * Returns the final fitness based on the solution function
         * @return fitness
         */
        public double CalculateFinalFitness()
        {
            double fitness = 0;
            for(int i = 0; i < (getSolution().length/k); i++)
            {
                fitness += Math.log(getSolution()[i]);
            }
            return fitness; 
        }

        private void writeOutput(String output)
        {
            System.out.println(output);
        }

        /**
         * @return the swarms
         */
        public Swarm[] getSwarms() {
            return swarms;
        }

    /**
     * @return the solution
     */
    public double[] getSolution() {
        return solution;
    }

    /**
     * @param solution the solution to set
     */
    public void setSolution(double[] solution) throws Exception {
        if(solution.length == dimensionSize)
        {
            this.solution = solution;
        }
        else throw new Exception("Incorrect solution size");
    }
}
