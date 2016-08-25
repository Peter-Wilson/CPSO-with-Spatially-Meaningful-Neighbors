/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso_h_k;

import Functions.Triangulation;
import cpso.*;
import javax.swing.JTextArea;
/**
 * CPSO Variant:
 * solves the multi-dimensional problem by dividing the problem evenly into k 
 * subswarms and solving them alongside a standard PSO to allow them to work
 * together to get the best result
 * @author Peter
 */
public class CPSO_H_k extends CPSO {    
    
    private Swarm pso_swarm;
    double PSO_C1 = 0.5;
    double PSO_C2 = 0.3;
    double PSO_INTERTIA = 0.3;

    /**
     * Create a CPSO_H_k
     * @param dimensionSize the overall dimension size
     * @param maxLoops the max loops to perform
     * @param swarmSize the number of particles in each swarm
     * @param Inertia the effect the current velocity has on its next (0-1)
     * @param c1 the effect the personal best has on the next velocity
     * @param c2 the effect that the global (or network) best has on the future velocity
     * @param k the number of swarms
     * @param DT determines if you are using delaunay triangulation or not
     * @param min determines if it is a minimum or maximum problem (true is minimum)
     * @param function determines which function it is optimizing
     */
    public CPSO_H_k(int dimensionSize, int maxLoops, int swarmSize, double Inertia, double c1, double c2, int k, boolean DT, int function, boolean min)
    {
        this(dimensionSize, maxLoops, swarmSize, Inertia, c1, c2, k, DT, function, min, null);
    }
    
    /**
     * Create a CPSO_H_k with output
     * @param dimensionSize the overall dimension size
     * @param maxLoops the max loops to perform
     * @param swarmSize the number of particles in each swarm
     * @param Inertia the effect the current velocity has on its next (0-1)
     * @param c1 the effect the personal best has on the next velocity
     * @param c2 the effect that the global (or network) best has on the future velocity
     * @param k the number of swarms
     * @param DT determines if you are using delaunay triangulation or not
     * @param function determines which function it is optimizing
     * @param min determines if it is a minimum or maximum problem (true is minimum)
     * @param op the text area to output the i/o
     */
    public CPSO_H_k(int dimensionSize, int maxLoops, int swarmSize, double Inertia, double c1, double c2, int k, boolean DT, int function, boolean min, JTextArea op)
    {
        super(dimensionSize, maxLoops, swarmSize/2, Inertia, c1, c2, k, DT, function, min, op);
        pso_swarm = new Swarm(swarmSize/2, PSO_C1, PSO_C2, PSO_INTERTIA, min, dimensionSize, function);
        InitializeSwarms(false);
    }

    /**
     * Begin calculating the CPSO
     * @return the final best result
     */
    public Result start()
    {
        //TODO: see why not converging as successfully as others
        Result result = new Result();
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
                    swarms[s].CalculateDelaunayTriangulation();
                    if(swarms[s].isDTNull()) 
                        this.unsuccessfulDTs++;
                    else
                        this.successfulDTs++;
                }
                
                // calculate delaunay neighbours
                for(Particle p : swarms[s].getParticles()){ //for each particle
                    UpdateBests(p, s); 
                }
                
                //update the closest social neighbor
                if(Delaunay) 
                {
                    for(Particle p: swarms[s].getParticles())
                    {
                           Particle neighbour = swarms[s].chooseBestNeighbour(p, this, s);
                           //TODO:check if you should be able to swap with those not working together
                           if(neighbour != null && Triangulation.working_together(Triangulation.convertParticletoPoint(p), 
                                    Triangulation.convertParticletoPoint(neighbour), swarms[s].getParticles()))
                                p.setSocialNeighbour(neighbour);
                            else
                                p.setSocialNeighbour(null);
                    }
                }
                
                for (Particle p : swarms[s].getParticles()) //move the particles
                {
                    swarms[s].UpdateVelocity(p, i/(double)maxLoops);
                    swarms[s].UpdatePosition(p);
                }                       
            }
            // </editor-fold>
            
            result.globalBestPerIteration.add(this.getSolutionFitness());
            if(result.globalBestPerIteration.get(result.globalBestPerIteration.size()-1) < this.criterion)
            {
                writeOutput("Criterion Met after "+i+" iterations");
                result.solved = true;
                result.iterationsToSolve = i+1;
                result.successfulDTs = this.successfulDTs;
                result.unsuccessfulDTs = this.unsuccessfulDTs;
                result.finalFitness = result.globalBestPerIteration.get(result.globalBestPerIteration.size()-1);
                break;
            }

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
                pso_swarm.setRandomParticle(startSolution, velocity);
            }
            
            for(Particle p : pso_swarm.getParticles()){ //for each particle
                UpdateBests(p, pso_swarm);                    
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
                int count = 0;
                for(int s = 0; s < swarms.length; s++)
                {
                    //select a random particle to replace with the pso global best
                    int size = swarms[s].getParticles()[0].getPosition().length;
                    double[] value = new double[size];
                    double[] velocity = new double[size];
                    for(int j = 0; j < size; j++)
                    {
                        value[j] = pso_swarm.getGlobalBest().getpBest()[count];
                        velocity[j] = pso_swarm.getGlobalBest().getVelocity()[count++];
                    }
                    
                    swarms[s].setRandomParticle(value, velocity);
                }
            }

        }

        for(int i = 0; i < startSolution.length; i++) //loop to print off startSolution
        {
            writeOutput("Solution "+(i+1)+": "+ startSolution[i]);
        }
        writeOutput("The final fitness value is: "+ CalculateFinalFitness(startSolution));
        return result;
    }

}
