/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso;

/**
 *
 * @author Peter
 */
public abstract class CPSO {
    
    public CPSO(){}

    public Swarm[] InitializeSwarms(int k, double[] solution,
            int dimensionSize, int swarmSize, double C1, double C2, double INERTIA, boolean min)
    {
        int remaining = 0;
        int count = 0;
        Swarm[] swarms = new Swarm[k];
        for(int i = 0; i < k; i++)
        {
            //select the random grouping into k subgroups
            int width = (int)(Math.ceil((dimensionSize/k)));
            swarms[i] = new Swarm(swarmSize, C1, C2, INERTIA, min, width);

            for(int j = 0; j < width; j++)
            {
                solution[count++] = swarms[i].getParticles()[0].getPosition()[j];
            }
        }
        return swarms;
    }

    //calculate the fitness of the PSO
    public abstract void start();

    /**
     * Takes the new fitness and particle and determine if it is the new 
     * global best and or personal best
     * @param fitness the new fitness value
     * @param p the particle that is being updated
     * @param swarm the swarm that particle is from
     */
    public void UpdateBests(double fitness, Particle p, Swarm swarm, boolean min)
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
     * Update to the best current solution by taking the global best values
     */
    public void UpdateSolution(Swarm[] swarms, double[] solution)
    {
        int index = 0;
        for(int i = 0; i < swarms.length; i++)
        {
            Particle best = swarms[i].getGlobalBest();
            if(best == null){
                index+= swarms[i].getParticles()[0].getPosition().length;
                continue;
            }
            else{
                for(int j = 0; j < best.getPosition().length; j++)
                {
                    solution[index++] = best.getPosition()[j];
                }
            }
        }
    }

    /**
     * Calculate the fitness of the current swarm
     * @param position the current position
     * @return 
     */
    public double CalculateFitness(int index, double[] position, int k, Swarm[] swarms, double[] solution)
    {
        double fitness = 0;
        int count = 0;
        for(int i = 0; i < k; i++)
        {
            if(i == index)
                for(int j = 0; j < swarms[i].getParticles()[0].getPosition().length; j++)
                {
                    fitness += Math.log(position[j]);
                    count++;
                }
            else
                for(int j = 0; j < swarms[i].getParticles()[0].getPosition().length; j++)
                    fitness += Math.log(solution[count++]);
        }
        return fitness;   
    }

    /**
     * Returns the final fitness based on the solution function
     * @return fitness
     */
    public double CalculateFinalFitness(int dimensionSize, double[] solution)
    {
        double fitness = 0;
        for(int i = 0; i < dimensionSize; i++)
        {
            fitness += Math.log(solution[i]);
        }
        return fitness; 
    }

    public void writeOutput(String output)
    {
        System.out.println(output);
    }
}
