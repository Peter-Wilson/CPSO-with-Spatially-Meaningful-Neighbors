/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso_s;
import cpso.*;
import javax.swing.JTextArea;

/**
 *
 * @author Peter
 */
public class CPSO_S extends CPSO {    
    
        public CPSO_S(int dimensionSize, int maxLoops, int swarmSize, double Inertia, double c1, double c2, boolean DT, int function)
        {
            super(dimensionSize, maxLoops, swarmSize, Inertia, c1, c2, dimensionSize, DT, function);
            InitializeSwarms(false);
        }
        
        public CPSO_S(int dimensionSize, int maxLoops, int swarmSize, double Inertia, double c1, double c2, boolean DT, int function, JTextArea op)
        {
            super(dimensionSize, maxLoops, swarmSize, Inertia, c1, c2, dimensionSize, DT, function, op);
            InitializeSwarms(false);
        }
        
        //calculate the fitness of the PSO
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
                        try{ swarms[s].CalculateDelaunayTriangulation(); }
                        catch(Exception e) {System.out.println("error creating delaunay");}
                    }
                    
                    for(Particle p : swarms[s].getParticles()){ //for each particle
                        
                        double fitness = CalculateFitness(s, p.getPosition(), numSwarms); //calculate the new fitness
                        UpdateBests(fitness, p, swarms[s]); 
                    }
                    
                    if(Delaunay) 
                    {
                        for(Particle p: swarms[s].getParticles())
                        {
                                Particle neighbour = swarms[s].chooseBestNeighbour(p);
                                if(neighbour != null)
                                    p.setSocialNeighbour(neighbour);
                        }
                    }
                    
                    for (Particle p : swarms[s].getParticles()) //move the particles
                    {
                        swarms[s].UpdateVelocity(p, i/(double)maxLoops);
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
                    solution = this.testSolution;
                    break;
                }
            }
            
            this.getSolutionFitness();
            for(int i = 0; i < testSolution.length; i++) //loop to print off solution
            {
                writeOutput("Solution "+(i+1)+": "+ testSolution[i]);
            }
            writeOutput("The final fitness value is: "+ CalculateFinalFitness(testSolution));
            return result;
        }
}
