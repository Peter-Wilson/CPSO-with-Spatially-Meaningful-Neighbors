/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso;

import cpso.Classes.Particle;
import cpso.Classes.Swarm;

/**
 *
 * @author Peter
 */
public class CPSO_S {    
    
        int loops;
        boolean problemSatisfied = false;
        boolean min = true;
        private Swarm[] swarms; 
        private double[] solution;
        int dimensionSize;
        int swarmSize;
        double C1 = 1;
        double C2 = 1;
        double INERTIA = 1;
        int maxLoops;
        
        public CPSO_S(int dimensionSize, int maxLoops, int swarmSize)
        {
            this.dimensionSize = dimensionSize;
            this.maxLoops = maxLoops;
            this.swarmSize = swarmSize;
            InitializeSwarms();
        }
        
        public void InitializeSwarms()
        {
            swarms = new Swarm[dimensionSize];
            solution = new double[dimensionSize];
            for(int i = 0; i < dimensionSize; i++)
            {
                swarms[i] = new Swarm(swarmSize, C1, C2, INERTIA, min);
                
                solution[i] = swarms[i].getParticles()[0].getPosition();
            }
        }
        
        //calculate the fitness of the PSO
        public void start()
        {
            for(int i = 0; i < maxLoops; i++)
            {
                for (int s = 0; s < swarms.length; s++)
                {
                    for(Particle p : swarms[s].getParticles()){
                        double fitness = CalculateFitness(s, p.getPosition());
                        if (p.UpdatePersonalBest(fitness, p.getPosition(), min))
                            writeOutput("New Particle " + p + " best: x=" + p.getPosition());
                        if ((swarms[s].getGlobalBest() == null) ||
                            (p.getFitness() < swarms[s].getGlobalBest().getFitness() && min) ||
                            (swarms[s].getGlobalBest().getFitness() < p.getFitness() && !min))
                        {

                            swarms[s].setGlobalBest(p);
                            writeOutput("New Particle " + p + " best: x=" + p.getPosition());
                        }
                    }
                    
                    for (Particle p : swarms[s].getParticles())
                    {
                        swarms[s].UpdateVelocity(p);
                        swarms[s].UpdatePosition(p);
                    }
                    
                    if(swarms[s].getGlobalBest() != null) 
                        writeOutput("Final Best X Value is...."+swarms[s].getGlobalBest().getPosition());                        
                }
            }
        }

        /**
         * Calculate the fitness of the current swarm
         * @param position the current position
         * @return 
         */
        public double CalculateFitness(int index, double position)
        {
            double fitness = 0;
            for(int i = 0; i < getSolution().length; i++)
            {
                if(i == index)
                    fitness += Math.log(position);
                else
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
