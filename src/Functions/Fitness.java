/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Functions;

/**
 *
 * @author pw12nb
 */
public class Fitness {
    public static double SumOfLogs(double[] values, int dimensionSize)
    {
        double fitness = 0;
        for(int i = 0; i < dimensionSize; i++)
        {
            fitness += Math.log(values[i]);
        }
        return fitness;
    }
    
    public static double Schaffer(double[] values)
    {
        double fitness = 0.5;
        fitness += ((Math.pow(Math.sin(Math.pow(values[0],2)-Math.pow(values[1],2)),2)-0.5)/
                    Math.pow(1+0.001*(Math.pow(values[0],2)+Math.pow(values[1],2)),2));
        return fitness;
    }
    
    public static double Rastrigin(double[] values, int dimensionSize)
    {
        double fitness = 0;
        for(int i = 0; i < dimensionSize; i++)
        {
            fitness += (Math.pow(values[i],2) + 10 - 10*Math.cos(2*Math.PI*values[i]));
        }
        return fitness;
    }
    
    public static double Rosenbrock(double[] values, int dimensionSize)
    {
        double fitness = 0;
        for(int i = 0; i < dimensionSize-1; i++)
        {
            fitness += 100*Math.pow(values[i+1] - Math.pow(values[i], 2),2)+
                    Math.pow(values[i] - 1,2);
        }
        return fitness;
    }
    
    public static double Griewanck(double[] values, int dimensionSize)
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
    
    public static double Ackley(double[] values, int dimensionSize)
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

        fitness = 20 + Math.E - 20*Math.pow(Math.E, (-0.2*Math.sqrt(a/dimensionSize))) -
                Math.pow(Math.E, b/dimensionSize);

       return fitness;
    }
}
