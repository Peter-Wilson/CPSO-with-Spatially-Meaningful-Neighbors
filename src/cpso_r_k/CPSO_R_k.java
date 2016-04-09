/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso_r_k;

import cpso.*;


/**
 *
 * @author Peter
 */
public class CPSO_R_k extends CPSO {    
    
    int loops;
    boolean min = true;
    private Swarm[] swarms; 
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

    /**
     * Create a CPSO_R_k
     * @param dimensionSize the overall dimension size
     * @param maxLoops the max loops to perform
     * @param swarmSize the number of particles in each swarm
     * @param Inertia
     * @param c1
     * @param c2
     * @param k the number of swarms
     */
    public CPSO_R_k(int dimensionSize, int maxLoops, int swarmSize, double Inertia, double c1, double c2, int k)
    {
        super(dimensionSize, maxLoops, swarmSize, Inertia, c1, c2, k);
        this.InitializeSwarms();
    }

    @Override
    public void InitializeSwarms()
    {
        int remaining = 0;
        int count = 0;
        swarms = new Swarm[k];
        solution = new double[dimensionSize];
        for(int i = 0; i < k; i++)
        {
            //select the random grouping into k subgroups
            int randomValue = (int)((Math.random())*((dimensionSize/k)-1))+1;                
            int randomWidth = remaining + randomValue;
            remaining = (int)(Math.ceil((dimensionSize/k)-randomValue));
            swarms[i] = new Swarm(swarmSize, C1, C2, INERTIA, min, randomWidth);

            for(int j = 0; j < randomWidth; j++)
            {
                solution[count++] = swarms[i].getParticles()[0].getPosition()[j];
            }
        }
    }

    //calculate the fitness of the PSO
    @Override
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

                    double fitness = CalculateFitness(s, p.getPosition()); //calculate the new fitness
                    UpdateBests(fitness, p, swarms[s]);   
                }

                for (Particle p : swarms[s].getParticles()) //move the particles
                {
                    swarms[s].UpdateVelocity(p);
                    swarms[s].UpdatePosition(p);
                }                       
            }
            // </editor-fold>

            UpdateSolution();

        }

        for(int i = 0; i < solution.length; i++) //loop to print off solution
        {
            writeOutput("Solution "+(i+1)+": "+ solution[i]);
        }
        writeOutput("The final fitness value is: "+ CalculateFinalFitness());
    }
}
