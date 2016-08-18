/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso_s_k;
import cpso.*;
import javax.swing.JTextArea;

/**
 * A CPSO variant:
 * solves the multi-dimensional problem by dividing it evenly into k subswarms 
 * and solving those separately
 * @author Peter
 */
public class CPSO_S_k extends CPSO {    

    /**
     * Create a CPSO_S_k
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
     */
    public CPSO_S_k(int dimensionSize, int maxLoops, int swarmSize, double Inertia, double c1, double c2, int k, boolean DT, int function, boolean min)
    {
        this(dimensionSize, maxLoops, swarmSize, Inertia, c1, c2, k, DT, function, min, null);
    }
    
    /**
     * Create a CPSO_S_k with output
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
    public CPSO_S_k(int dimensionSize, int maxLoops, int swarmSize, double Inertia, double c1, double c2, int k, boolean DT, int function, boolean min, JTextArea op)
    {
        super(dimensionSize, maxLoops, swarmSize, Inertia, c1, c2, k, DT, function, min, op);
        super.InitializeSwarms(false);
    }

    /**
     * Begin calculating the CPSO
     * @return the final best result
     */
    public Result start()
    {
        Result result = new Result();
        for(int i = 0; i < maxLoops; i++)
        {
            for (int s = 0; s < swarms.length; s++) //iterate through swarms
            {
                //perform the delaunay triangulation
                if(Delaunay)
                {
                    swarms[s].CalculateDelaunayTriangulation(); 
                }
                
                for(Particle p : swarms[s].getParticles()){ //for each particle
                    UpdateBests(p, s);  
                }
                
                //update the closest social neighbor
                if(Delaunay) 
                {
                    for(Particle p: swarms[s].getParticles())
                    {
                            Particle neighbour = swarms[s].chooseBestNeighbour(p, this, s);
                            p.setSocialNeighbour(neighbour);
                    }
                }

                for (Particle p : swarms[s].getParticles()) //move the particles
                {
                    swarms[s].UpdateVelocity(p,i/(double)maxLoops);
                    swarms[s].UpdatePosition(p);
                }                       
            }
            
            result.globalBestPerIteration.add(this.getSolutionFitness());
            if(result.globalBestPerIteration.get(result.globalBestPerIteration.size()-1) < this.criterion)
            {
                writeOutput("Criterion Met after "+i+" iterations");
                result.solved = true;
                result.iterationsToSolve = i+1;
                result.finalFitness = result.globalBestPerIteration.get(result.globalBestPerIteration.size()-1);
                break;
            }
        }

        double[] bestSolution = getGlobalBestSolution();
        for(int i = 0; i < bestSolution.length; i++) //loop to print off startSolution
        {
            writeOutput("Solution "+(i+1)+": "+ bestSolution[i]);
        }
        writeOutput("The final fitness value is: "+ CalculateFinalFitness(bestSolution));
        return result;
    }
}
