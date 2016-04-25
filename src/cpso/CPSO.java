/*
 * The skeleton CPSO program
 * This is extended by all the variants
 */
package cpso;

import javax.swing.JTextArea;

/**
 *
 * @author Peter
 */
public class CPSO {
    
    public int loops;
    public boolean min = true;
    public Swarm[] swarms; 
    public double[] solution;
    public double[] testSolution;
    public int dimensionSize;
    public int swarmSize;
    public double C1 = 0.5;
    public double C2 = 0.3;
    public double INERTIA = 0.3;
    public int maxLoops;
    public int numSwarms;
    public boolean Delaunay;
    public int function;
    JTextArea screen;
    
    public CPSO(int dimensionSize, int maxLoops, int swarmSize, double Inertia, double c1, double c2, int numSwarms, boolean Delaunay, int function)
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
        solution = new double[dimensionSize];
        testSolution = new double[dimensionSize];
        screen = null;
    }
    
    public CPSO(int dimensionSize, int maxLoops, int swarmSize, double Inertia, double c1, double c2, int numSwarms, boolean Delaunay, int function, JTextArea op)
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
        solution = new double[dimensionSize];
        testSolution = new double[dimensionSize];
        screen = op;
    }

    /**
     * Creates the swarms
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
                if(i%numSwarms == 0)
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
                solution[count++] = swarms[i].getParticles()[0].getPosition()[j];
            }
        }
    }

    /**
     * Takes the new fitness and particle and determine if it is the new 
     * global best and or personal best
     * @param fitness the new fitness value
     * @param p the particle that is being updated
     * @param swarm the swarm that particle is from
     */
    public void UpdateBests(double fitness, Particle p, Swarm swarm)
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
    public void UpdateSolution()
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
    public double CalculateFitness(int index, double[] position, int numSwarms)
    {
        double fitness = 0;
        int count = 0;
        if(numSwarms == 1)
        {
            for(int i = 0; i < position.length; i++)
            {
                testSolution[i] = position[i];
            }
        }
        else
        {
            for(int i = 0; i < numSwarms; i++)
            {
                if(i == index)
                    for(int j = 0; j < swarms[i].getParticles()[0].getPosition().length; j++)
                    {
                        testSolution[count] = position[j];
                        count++;
                    }
                else
                    for(int j = 0; j < swarms[i].getParticles()[0].getPosition().length; j++)
                    {
                        testSolution[count] = solution[count];
                        count++;
                    }
            }
        }
        return CalculateFinalFitness(testSolution);   
    }

    /**
     * Returns the final fitness based on the solution function
     * @return fitness
     */
    public double CalculateFinalFitness(double[] values)
    {
        switch(function)
        {
            case 0: //sum of logs
            {
                return SumOfLogs(values);
            }
            case 1: //Schaffer
            {
                return Schaffer(values);
            }
            case 2: //Rastrigin
            {
                 return Rastrigin(values);
            }
            case 3: //Rosenbrock
            {
                return Rosenbrock(values);
            }
            case 4: //Griewanck
            {
                return Griewanck(values);
            }
            case 5: //Ackley
            {
                return Ackley(values);
            }
            default:
                return 0;     
        }
    }
    
    public double SumOfLogs(double[] values)
    {
        double fitness = 0;
        for(int i = 0; i < dimensionSize; i++)
        {
            fitness += Math.log(values[i]);
        }
        return fitness;
    }
    
    public double Schaffer(double[] values)
    {
        double fitness = 0.5;
        fitness += ((Math.pow(Math.sin(Math.pow(values[0],2)+Math.pow(values[1],2)),2)-0.5)/
                    Math.pow(1+0.001*(Math.pow(values[0],2)+Math.pow(values[1],2)),2));
        return fitness;
    }
    
    public double Rastrigin(double[] values)
    {
        double fitness = 0;
        for(int i = 0; i < dimensionSize; i++)
        {
            fitness += Math.pow(values[i],2) + 10 - 10*Math.cos(2*Math.PI*values[i]);
        }
        return fitness;
    }
    
    public double Rosenbrock(double[] values)
    {
        double fitness = 0;
        for(int i = 0; i < dimensionSize-1; i++)
        {
            fitness += 100*Math.pow(values[i+1] - Math.pow(values[i], 2),2)+
                    Math.pow(values[i] - 1,2);
        }
        return fitness;
    }
    
    public double Griewanck(double[] values)
    {
        double fitness = 0;
        double summation = 0;
        double multiplication = 1;

        for(int i = 0; i < dimensionSize; i++)
        {
            summation += Math.pow(values[i],2);
        }

        for(int i = 0; i < dimensionSize; i++)
        {
            multiplication *= Math.cos(values[i]/Math.sqrt(i+1));
        }
        fitness = 1+ (summation/4000)-multiplication;
        return fitness;
    }
    
    public double Ackley(double[] values)
    {
        double fitness = 0;
        double a = 0;
        double b = 0;

        for(int i = 0; i < dimensionSize; i++)
        {
            a += Math.pow(values[i],2);
        }

        for(int i = 0; i < dimensionSize; i++)
        {
            b += Math.cos(2*Math.PI*values[i]);
        }

        fitness = 20 + Math.E - 20*Math.pow(Math.E, -0.2*Math.sqrt(a/dimensionSize)) -
                Math.pow(Math.E, b/dimensionSize);

       return fitness;
    }

    public void writeOutput(String output)
    {
        if(screen == null)
            System.out.println(output);
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
