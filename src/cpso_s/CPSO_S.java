/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso_s;
import cpso.*;

/**
 *
 * @author Peter
 */
public class CPSO_S extends CPSO {    
    
        int loops;
        boolean min = true;
        private Swarm[] swarms; 
        private double[] solution;
        int dimensionSize;
        int swarmSize;
        double C1 = 0.5;
        double C2 = 0.3;
        double INERTIA = 0.3;
        int maxLoops;
        int k;
        
        public CPSO_S(int dimensionSize, int maxLoops, int swarmSize, double Inertia, double c1, double c2, int k)
        {
            super();
            this.dimensionSize = dimensionSize;
            this.maxLoops = maxLoops;
            this.swarmSize = swarmSize;
            this.INERTIA = Inertia;
            this.C1 = c1;
            this.C2 = c2;
            if(k > dimensionSize) k = dimensionSize;
            this.k = k;            
            solution = new double[dimensionSize];
            swarms = InitializeSwarms(k, getSolution(), dimensionSize, swarmSize,
                            C1, C2, Inertia, min);
        }
        
        //calculate the fitness of the PSO
        @Override
        public void start()
        {
            for(int i = 0; i < maxLoops; i++)
            {
                for (int s = 0; s < swarms.length; s++) //iterate through swarms
                {
                    for(Particle p : swarms[s].getParticles()){ //for each particle
                        
                        double fitness = CalculateFitness(s, p.getPosition(), k, swarms, getSolution()); //calculate the new fitness
                        UpdateBests(fitness, p, swarms[s], min); 
                    }
                    
                    for (Particle p : swarms[s].getParticles()) //move the particles
                    {
                        swarms[s].UpdateVelocity(p);
                        swarms[s].UpdatePosition(p);
                    } 
                    
                }
                    UpdateSolution(swarms, solution);
            }
            
            for(int i = 0; i < solution.length; i++) //loop to print off solution
            {
                writeOutput("Solution "+(i+1)+": "+ solution[i]);
            }
            writeOutput("The final fitness value is: "+ CalculateFinalFitness(dimensionSize, getSolution()));
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
     * @throws exception incorrect solution size
     */
    public void setSolution(double[] solution) throws Exception {
        if(solution.length == dimensionSize)
        {
            this.solution = solution;
        }
        else throw new Exception("Incorrect solution size");
    }
}
