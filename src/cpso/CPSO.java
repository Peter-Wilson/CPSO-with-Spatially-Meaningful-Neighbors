/*
 * The skeleton CPSO program
 * This is extended by all the variants
 */
package cpso;

import Functions.Fitness;
import Functions.Triangulation;
import java.util.Random;
import javax.swing.JTextArea;

/**
 *
 * @author Peter
 */
public class CPSO {
    
    public int loops;
    public boolean min;
    public Swarm[] swarms; 
    public double[] startSolution;
    public int dimensionSize;
    public int swarmSize;
    public double C1 = 0.5;
    public double C2 = 0.3;
    public int successfulDTs;
    public int unsuccessfulDTs;
    public double INERTIA = 0.3;
    public int maxLoops;
    public int numSwarms;
    public boolean Delaunay;
    public int function;
    public double criterion;
    JTextArea screen;
    
    /**
     * Create a CPSO
     * @param dimensionSize the overall dimension size
     * @param maxLoops the max loops to perform
     * @param swarmSize the number of particles in each swarm
     * @param Inertia the effect the current velocity has on its next (0-1)
     * @param c1 the effect the personal best has on the next velocity
     * @param c2 the effect that the global (or network) best has on the future velocity
     * @param numSwarms the number of swarms to create
     * @param Delaunay determines if you are using delaunay triangulation or not
     * @param function determines which function it is optimizing
     * @param min determines if it is a minimum or maximum problem (true is minimum)
     * @param op the output to push the i/o to
     */
    public CPSO(int dimensionSize, int maxLoops, int swarmSize, double Inertia, double c1, double c2, int numSwarms, boolean Delaunay, int function, boolean min,  JTextArea op)
    {
        this.dimensionSize = dimensionSize;
        this.maxLoops = maxLoops;
        this.swarmSize = swarmSize;
        this.INERTIA = Inertia;
        this.C1 = c1;
        this.C2 = c2;
        if(numSwarms > dimensionSize) numSwarms = dimensionSize;
        this.numSwarms = numSwarms;  
        this.Delaunay = Delaunay;          
        this.function = function;  
        criterion = getCriterion(function);
        this.min = min;
        startSolution = new double[dimensionSize];
        screen = op;
    }
    
    /**
     * Create a CPSO
     * @param dimensionSize the overall dimension size
     * @param maxLoops the max loops to perform
     * @param swarmSize the number of particles in each swarm
     * @param Inertia the effect the current velocity has on its next (0-1)
     * @param c1 the effect the personal best has on the next velocity
     * @param c2 the effect that the global (or network) best has on the future velocity
     * @param numSwarms the number of swarms to create
     * @param Delaunay determines if you are using delaunay triangulation or not
     * @param min determines if it is a minimum or maximum problem (true is minimum)
     * @param function determines which function it is optimizing
     */
    public CPSO(int dimensionSize, int maxLoops, int swarmSize, double Inertia, double c1, double c2, int numSwarms, boolean Delaunay, int function, boolean min)
    {
        this(dimensionSize, maxLoops, swarmSize, Inertia, c1, c2, numSwarms, Delaunay, function, min, null);
    }
       

    /**
     * Creates the swarms
     * @param random notes if the swarm should be built with random sizes or 
     * with identical swarm sizes
     */
    public void InitializeSwarms(boolean random)
    {
        int remaining = 0;
        int count = 0;
        swarms = new Swarm[numSwarms];
        for(int i = 0; i < numSwarms; i++)
        {
            int width = 0;
            
            if(!random)
            {
                if(i < dimensionSize%numSwarms)
                    width = (int)(Math.ceil(((double)dimensionSize/numSwarms)));
                else
                    width = (int)(Math.floor(((double)dimensionSize/numSwarms)));
            }
            else
            {
                if(i == numSwarms-1)
                    width = (int)(Math.ceil(((double)dimensionSize/numSwarms)))+remaining;
                else
                {
                    //select the random grouping into k subgroups
                    int randomValue = (int)((Math.random())*(((double)dimensionSize/numSwarms)))+1;                
                    width = remaining + randomValue;
                    remaining = (int)(Math.ceil(((double)dimensionSize/numSwarms)-randomValue));
                }
            }
            
            swarms[i] = new Swarm(swarmSize, C1, C2, INERTIA, min, width, function);

            for(int j = 0; j < width; j++)
            {
                startSolution[count++] = swarms[i].getParticles()[0].getPosition()[j];
            }
        }
    }
    
    public void UpdateBests(Particle p, int index)
    {
        UpdateBests(p, swarms[index], index);
    }
    
    public void UpdateBests(Particle p, Swarm s)
    {
        UpdateBests(p, s, 0);
    }

    /**
     * Takes the new fitness and particle and determine if it is the new 
     * global best and or personal best
     * @param p the particle that is being updated
     * @param index the index the swarm appears in the swarm array
     */
    public void UpdateBests(Particle p, Swarm swarm, int index)
    {
        double pBestFitness = CalculateFitness(index, p.getpBest());
        double particleFitness = CalculateFitness(index, p.getPosition());
        
        //update the personal best of the particle
        if (p.getpBest() == null || 
                (particleFitness < pBestFitness && min) ||
                (particleFitness > pBestFitness && !min)) 
        {
            p.setpBest(p.getPosition());
            pBestFitness = particleFitness;
            writeOutput("New Personal best for " + p + ": old:"+ pBestFitness + "..... new:" + particleFitness + " Position: "+p.getPosition()[0]);
        }

        if ((swarm.getGlobalBest() == null) ||
            (min && pBestFitness < CalculateFitness(index, swarm.getGlobalBest().getpBest())) ||
            (!min && CalculateFitness(index, swarm.getGlobalBest().getpBest()) < pBestFitness))      //update the global best
        {
            swarm.setGlobalBest(p);
            if(swarm == swarms[index]) UpdateSolution(); //update the solution if it is the main cpso (not the PSO)
            writeOutput("New Global Best for Swarm " + swarm + ": x=" + particleFitness);
        }
    }
    
    /**
     * Adds the best solutions into temp and calculate the fitness
     * @return the fitness of the global best values
     */
    public double getSolutionFitness()
    {
        return this.CalculateFinalFitness(this.startSolution);
    }

    /**
     * Calculate the fitness of the current swarm
     * @param position the current position
     * @param index the index the position starts from
     * @param numSwarms the number of swarms in the cpso
     * @return the fitness of after the new value
     */
    public double CalculateFitness(int index, double[] position)
    {
        double fitness = 0;
        int count = 0;
        
        double[] tempSolution = new double[startSolution.length];
        if(swarms.length == 1)
        {
            for(int i = 0; i < position.length; i++)
            {
                tempSolution[i] = position[i];
            }
        }
        else
        {
            for(int i = 0; i < swarms.length; i++)
            {
                if(i == index)
                    for(int j = 0; j < swarms[i].getParticles()[0].getPosition().length; j++)
                    {
                        tempSolution[count++] = position[j];
                    }
                else
                    for(int j = 0; j < swarms[i].getParticles()[0].getPosition().length; j++)
                    {
                        tempSolution[count] = startSolution[count];
                        count++;
                    }
            }
        }
        return CalculateFinalFitness(tempSolution);   
    }

    /**
     * Returns the final fitness based on the startSolution function
     * @param values the values to pass into the fitness function
     * @return fitness
     */
    public double CalculateFinalFitness(double[] values)
    {
        switch(function)
        {
            case 0: //sum of logs
            {
                return Fitness.SumOfLogs(values, dimensionSize);
            }
            case 1: //Schaffer
            {
                return Fitness.Schaffer(values);
            }
            case 2: //Rastrigin
            {
                 return Fitness.Rastrigin(values, dimensionSize);
            }
            case 3: //Rosenbrock
            {
                return Fitness.Rosenbrock(values, dimensionSize);
            }
            case 4: //Griewanck
            {
                return Fitness.Griewanck(values, dimensionSize);
            }
            case 5: //Ackley
            {
                return Fitness.Ackley(values, dimensionSize);
            }
            default:
                return 0;     
        }
    }
    
    
    /**
     * Outputs the value to either the console or the ui depending on if
     * a screen output has been entered
     * @param output the value to output
     */
    public void writeOutput(String output)
    {
        if(screen == null)
            //System.out.println(output);
            return;
        else
            screen.append("\n "+output);
    }
       
    /**
     * @return the swarms
     */
    public Swarm[] getSwarms() {
        return swarms;
    }

    /**
     * @return the startSolution
     */
    public double[] getSolution() {
        return startSolution;
    }

    /**
     * Sets the default startSolution
     * @param solution the startSolution to set
     * @throws java.lang.Exception incorrect startSolution size
     */
    public void setSolution(double[] solution) throws Exception {
        if(solution.length == dimensionSize)
        {
            this.startSolution = solution;
        }
        else throw new Exception("Incorrect solution size");
    }
    
    /**
     * Updates the overall solution (which is used in fitness calculations) 
     * to have the global best values from each swarm
     */
    public void UpdateSolution()
    {
        int count = 0;

        for(int i = 0; i < swarms.length; i++)
        {
            //pull from the global best only if it exists
            //otherwise pull from the first
            Particle best = (swarms[i].getGlobalBest() != null)? swarms[i].getGlobalBest() : swarms[i].getParticles()[0];
            
            
            for(int j = 0; j < best.getpBest().length; j++)
            {
                startSolution[count] = best.getpBest()[j];
                count++;
            }
        }
    }

    /**
     * Returns the criterion dependent on the function testing
     * @param function the function being run
     * @return the criterion
     */
    private double getCriterion(int function) {
        switch(function)
        {
            case 0: return 0.5; //sum of logs                 
            case 1: return 0.00001; //Schaffer                
            case 2: return 0.01; //Rastrigin                 
            case 3: return 100; //Rosenbrock                
            case 4: return 0.05; //Griewanck                
            case 5: return 0.01; //Ackley
                
            default: return 0;
                     
        }
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
                    if(swarms[s].isDTNull()) 
                        this.unsuccessfulDTs++;
                    else
                        this.successfulDTs++;
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
                        if(neighbour != null && Triangulation.working_together(Triangulation.convertParticletoPoint(p), 
                                Triangulation.convertParticletoPoint(neighbour), swarms[s].getParticles()))
                            p.setSocialNeighbour(neighbour);
                        else
                            p.setSocialNeighbour(null);
                    }
                }

                for (Particle p : swarms[s].getParticles()) //move the particles
                {
                    swarms[s].UpdateVelocity(p,i/(double)maxLoops);
                    swarms[s].UpdatePosition(p);
                }                       
            }
            
            result.globalBestPerIteration.add(this.getSolutionFitness());
            if(result.globalBestPerIteration.get(result.globalBestPerIteration.size()-1) <= this.criterion)
            {
                writeOutput("Criterion Met after "+i+" iterations");
                result.solved = true;
                result.successfulDTs = this.successfulDTs;
                result.unsuccessfulDTs = this.unsuccessfulDTs;
                result.iterationsToSolve = i+1;
                result.finalFitness = result.globalBestPerIteration.get(result.globalBestPerIteration.size()-1);
                break;
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
