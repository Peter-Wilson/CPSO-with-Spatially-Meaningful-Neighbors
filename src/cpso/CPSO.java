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
public class CPSO {    
    
        int loops;
        boolean problemSatisfied = false;
        boolean min = true;
        private Swarm[] swarms; 
        int dimensionSize;
        int swarmSize;
        double C1 = 1;
        double C2 = 1;
        double INERTIA = 1;
        
        public CPSO(int dimensionSize)
        {
            this.dimensionSize = dimensionSize;
            InitializeSwarms();
        }
        
        private void InitializeSwarms()
        {
            swarms = new Swarm[dimensionSize];
            for(int i = 0; i < dimensionSize; i++)
            {
                swarms[i] = new Swarm(swarmSize, C1, C2, INERTIA, min);
            }
        }
        
        //calculate the fitness of the PSO
        public void PSO()
        {
            int loopsSinceNoNewGlobalBest = 0;
            while (!problemSatisfied)
            {
                for (Swarm s : getSwarms())
                {
                    for(Particle i : s.getParticles()){
                        double fitness = CalculateFitness(i.getPosition());
                        if (i.UpdatePersonalBest(fitness, i.getPosition(), min))
                            writeOutput("New Particle " + i + " best: x=" + i.getPosition());
                        if ((s.getGlobalBest() == null) ||
                            (i.getFitness() < s.getGlobalBest().getFitness() && min) ||
                            (s.getGlobalBest().getFitness() < i.getFitness() && !min))
                        {

                            s.setGlobalBest(i);
                            loopsSinceNoNewGlobalBest = 0;
                            writeOutput("New Particle " + i + " best: x=" + i.getPosition());
                        }
                    }
                    
                    for (Particle i : s.getParticles())
                    {
                        s.UpdateVelocity(i);
                        s.UpdatePosition(i);
                    }
                }
                

                if (++loopsSinceNoNewGlobalBest == 5) 
                    problemSatisfied = true;
            }
           // writeOutput("Final Best X Value is...."+s..getPosition());
        }

        /**
         * Calculate the fitness of the current swarm
         * @param position the current position
         * @return 
         */
        public double CalculateFitness(double position)
        {
            return (3 - Math.pow(position, 2));
                
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
         * @param swarms the swarms to set
         */
        public void setSwarms(Swarm[] swarms) {
            this.swarms = swarms;
        }
}
