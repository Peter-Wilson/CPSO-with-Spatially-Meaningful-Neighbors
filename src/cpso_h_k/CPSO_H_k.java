/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso_h_k;

import cpso.*;
import javax.swing.JTextArea;
/**
 *
 * @author Peter
 */
public class CPSO_H_k extends CPSO {    
    
    private Swarm pso_swarm;
    double PSO_C1 = 0.5;
    double PSO_C2 = 0.3;
    double PSO_INTERTIA = 0.3;

    public CPSO_H_k(int dimensionSize, int maxLoops, int swarmSize, double Inertia, double c1, double c2, int k, boolean DT, int function)
    {
        super(dimensionSize, maxLoops, swarmSize, Inertia, c1, c2, k, DT, function);
        pso_swarm = new Swarm(dimensionSize, PSO_C1, PSO_C2, PSO_INTERTIA, min, dimensionSize, function);
        InitializeSwarms(false);
    }
    
    public CPSO_H_k(int dimensionSize, int maxLoops, int swarmSize, double Inertia, double c1, double c2, int k, boolean DT, int function, JTextArea op)
    {
        super(dimensionSize, maxLoops, swarmSize, Inertia, c1, c2, k, DT, function, op);
        pso_swarm = new Swarm(dimensionSize, PSO_C1, PSO_C2, PSO_INTERTIA, min, dimensionSize, function);
        InitializeSwarms(false);
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
                //perform the delaunay triangulation
                if(Delaunay)
                {
                    try{ swarms[s].CalculateDelaunayTriangulation(); }
                    catch(Exception e) {System.out.println("error creating delaunay");}
                }
                
                // calculate delaunay neighbours
                for(Particle p : swarms[s].getParticles()){ //for each particle

                    double fitness = CalculateFitness(s, p.getPosition(), numSwarms); //calculate the new fitness
                    UpdateBests(fitness, p, swarms[s]);   
                    if(Delaunay) 
                    {
                        Particle neighbour = swarms[s].chooseBestNeighbour(p);
                        if(neighbour != null)
                            p.setpBest(neighbour.getpBest());
                    }
                }
                
                for (Particle p : swarms[s].getParticles()) //move the particles
                {
                    swarms[s].UpdateVelocity(p);
                    swarms[s].UpdatePosition(p);
                }                       
            }
            // </editor-fold>

            UpdateSolution();

            //transfer knowledge from CPSO to PSO
            if(swarms[0].getGlobalBest() != null && swarms[0].getGlobalBest().getVelocity() != null)
            {
                int count = 0;
                double[] velocity = new double[dimensionSize];
                for(int s = 0; s < numSwarms; s++)
                {
                    for(int j = 0; j < swarms[s].getParticles()[0].getPosition().length; j++)
                    {
                        velocity[count] = swarms[s].getGlobalBest().getVelocity()[j];
                        count++;
                    }
                }
                pso_swarm.setRandomParticle(super.getSolution(), velocity);
            }
            
            for(Particle p : pso_swarm.getParticles()){ //for each particle

                double fitness = CalculateFitness(0, p.getPosition(), 1); //calculate the new fitness
                UpdateBests(fitness, p, pso_swarm);                    
            }

            for (Particle p : pso_swarm.getParticles()) //move the particles
            {
                pso_swarm.UpdateVelocity(p);
                pso_swarm.UpdatePosition(p);
            }     
            // </editor-fold>

            //Transfer knowledge from PSO to CPSO
            if(pso_swarm.getGlobalBest() != null)
            {
                //for each swarm
                for(int s = 0; s < swarms.length; s++)
                {
                    //select a random particle to replace with the pso global best
                    int size = swarms[s].getParticles()[0].getPosition().length;
                    double[] value = new double[size];
                    double[] velocity = new double[size];
                    for(int j = 0; j < size; j++)
                    {
                        value[j] = pso_swarm.getGlobalBest().getPosition()[(s*size)+j];
                        velocity[j] = pso_swarm.getGlobalBest().getVelocity()[(s*size)+j];
                    }
                    swarms[s].setRandomParticle(value, velocity);
                }
            }

        }

        for(int i = 0; i < solution.length; i++) //loop to print off solution
        {
            writeOutput("Solution "+(i+1)+": "+ solution[i]);
        }
        writeOutput("The final fitness value is: "+ CalculateFinalFitness(solution));
    }

}
