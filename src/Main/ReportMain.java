/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import cpso.CPSO;
import cpso.Result;
import cpso_h_k.CPSO_H_k;
import cpso_r_k.CPSO_R_k;
import cpso_s.CPSO_S;
import cpso_s_k.CPSO_S_k;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 *
 * @author Peter
 */
public class ReportMain {
    
    static BufferedWriter writer;
    
    public static void getAverageSolveRate()
    {
        try {
            
            Date d = new Date();
            File file = new File("Runtime_Reports/reportOutput_"+d.getTime()+".csv");

            // if file doesnt exists, then create it
            if (!file.exists()) {
                    file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            writer = new BufferedWriter(fw);

        System.out.println("");
        System.out.println("-----------------------------------------------");
        System.out.println("------------------ S=125  N=50 ------------------");
        System.out.println("-----------------------------------------------");
        testRastrigin(125, 25, 50); // dt
            System.out.println("\b");
        testRosenbrock(125, 25, 50); // no dt      
            System.out.println("\b");
        testGriewanck(125, 25, 50); // dt
            System.out.println("\b");
        testAckley(125, 25, 50);
        
        System.out.println("");
        System.out.println("-----------------------------------------------");
        System.out.println("------------------ S=187  N=50 ------------------");
        System.out.println("-----------------------------------------------");
        testRastrigin(187, 25, 50); // dt
            System.out.println("\b");
        testRosenbrock(187, 25, 50); // no dt      
            System.out.println("\b");
        testGriewanck(187, 25, 50); // dt
            System.out.println("\b");
        testAckley(187, 25, 50);
        
        System.out.println("");
        System.out.println("-----------------------------------------------");
        System.out.println("------------------ S=250  N=50 ------------------");
        System.out.println("-----------------------------------------------");
        
        testRastrigin(250, 25, 50); // dt
            System.out.println("\b");
        testRosenbrock(250, 25, 50);  // no dt      
            System.out.println("\b");
        testGriewanck(250, 25, 50); // dt
            System.out.println("\b");
        testAckley(250, 25, 50); 
            writer.close();
    
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
    public static void runTest(int numParticles, int Dimensions, boolean dt, int function, int numSwarms, int type)
    {
        int completed = 0;
            int iteration = 0;
            boolean worked = false;
            int successDT = 0;
            int unsuccessDT = 0;
            int numIterations = 10000;
            int loops = 50;
            double[] averageBest = new double[numIterations];
            double[] finalResult = new double[loops];
            double averageResult = 0;
            
            for(int i = 0; i < loops; i++)
            {
                //System.out.println("Starting"+(i+1));
                worked = false;
                try{
                        CPSO cpso;
                        switch(type)
                        {
                            case 0:
                                //PSO
                                cpso = new CPSO_S_k(Dimensions, numIterations, numParticles, 1.0, 1.49618, 1.49618, 1, dt, function, true);
                                break;
                            case 2:
                                cpso = new CPSO_S_k(Dimensions, numIterations, numParticles, 1.0, 1.49618, 1.49618, numSwarms, dt, function, true);
                                break;
                            case 3:
                                cpso = new CPSO_H_k(Dimensions, numIterations, numParticles, 1.0, 1.49618, 1.49618, numSwarms, dt, function, true);
                                break;
                            case 4:
                                cpso = new CPSO_R_k(Dimensions, numIterations, numParticles, 1.0, 1.49618, 1.49618, numSwarms, dt, function, true);
                                break;
                            default:
                                cpso = new CPSO_S(Dimensions, numIterations, numParticles, 1.0, 1.49618, 1.49618, dt, function, true);
                                break;
                        }
                        Result r = cpso.start();
                        if(r.solved)
                        {
                            completed++;
                        }
                        iteration += r.iterationsToSolve;
                        successDT += r.successfulDTs;
                        unsuccessDT = r.unsuccessfulDTs;
                        finalResult[i] = r.globalBestPerIteration.get(r.globalBestPerIteration.size() - 1);
                        averageResult += r.globalBestPerIteration.get(r.globalBestPerIteration.size() - 1);
                        
                        for(int j = 0; j < r.globalBestPerIteration.size(); j++)
                        {
                            averageBest[j] += r.globalBestPerIteration.get(j);
                        }
                        worked = true;
                    }
                    catch(StackOverflowError soe)
                    {
                        System.out.println("Restarting...");
                        worked = false;
                    }
            }
            
            averageResult = averageResult/loops;
            double stDev = getStdDev(averageResult, finalResult);
            
            int successAverage = (successDT+unsuccessDT <= 0)?0:(successDT/(successDT+unsuccessDT))*100;
            System.out.print("Function?"+ function + " type?"+type+" DT?"+dt+":\t\tCompleted: "+completed/((double)loops/100)+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed))+"\tDT Success rate:"+successAverage+"%");
            System.out.println("\tConfidence Interval = "+ (averageResult - ((getTValue(loops)*stDev)/(Math.sqrt(loops)))) + " - " + (averageResult + ((getTValue(loops)*stDev)/(Math.sqrt(loops)))));
            try{
            if(writer != null)
            {
                writer.write("S="+numParticles+"-N="+Dimensions+"-Type="+getPSOType(type)+"-Function"+getFunctionName(function)+",");
                for(int i = 0; i < averageBest.length; i++)
                {
                    writer.write(averageBest[i]/loops + ",");
                }
                writer.newLine();
                writer.flush();
            }
            }catch(IOException io)
            {
                
            }
    }
    
    private static double getTValue(int n)
    {
        switch(n)
        {
            case 10: return 2.228;
            case 15: return 2.131;
            case 20: return 2.086;
            case 25: return 2.060;
            case 30: return 2.042;
            case 40: return 2.021;
            case 50: return 2.009;
            case 60: return 2;
            case 80: return 1.990;
            case 100: return 1.984;
            default: return 2.571;
        }
    }
    
    private static double getVariance(double mean, double[] results)
    {
        double temp = 0;
        for(double a : results)
            temp += (a-mean)*(a-mean);
        return temp/(results.length);
    }

    private static double getStdDev(double mean, double[] results)
    {
        return Math.sqrt(getVariance(mean, results));
    }
    
    public static String getFunctionName(int functionType)
    {
        switch(functionType)
        {
            case 0: return "Sum Of Logs";
            case 1: return "Schaffer";
            case 2: return "Rastrigin";
            case 3: return "Rosenbrock";
            case 4: return "Griewanck";
            case 5: return "Ackley";
            default: return "Unknown";
        }
    }
    
    public static String getPSOType(int PSOType)
    {
        switch(PSOType)
        {
            case 0: return "PSO";
            case 1: return "CPSO-S";
            case 2: return "CPSO-Sk";
            case 3: return "CPSO-Hk";
            case 4: return "CPSO-Rk";
            default: return "Unknown";
        }
    }
    
    public static void testFunction(int numParticles, int numSwarms, int Dimensions, int function)
    {
        //PSO
        runTest(numParticles, Dimensions, false, function, 1, 0);
        //CPSO-S
        runTest(numParticles, Dimensions, false, function, 1, 1);
        runTest(numParticles, Dimensions, true, function, 1, 1);
        //CPSO-Sk
        runTest(numParticles, Dimensions, false, function, numSwarms, 2);
        runTest(numParticles, Dimensions, true, function, numSwarms, 2);
        //CPSO-Hk
        runTest(numParticles, Dimensions, false, function, numSwarms, 3);
        runTest(numParticles, Dimensions, true, function, numSwarms, 3);
        //CPSO-Rk
        runTest(numParticles, Dimensions, false, function, numSwarms, 4);
        runTest(numParticles, Dimensions, true, function, numSwarms, 4);
    }
    
    public static void testSchaffer(int numParticles, int numSwarms, int Dimensions)
    {
        testFunction(numParticles, numSwarms, Dimensions, 1);
    }
    
    public static void testRastrigin(int numParticles, int numSwarms, int Dimensions)
    {
        testFunction(numParticles, numSwarms, Dimensions, 2);
    }
    
    public static void testRosenbrock(int numParticles, int numSwarms, int Dimensions)
    {
        testFunction(numParticles, numSwarms, Dimensions, 3);
    }
    
    public static void testGriewanck(int numParticles, int numSwarms, int Dimensions)
    {
        testFunction(numParticles, numSwarms, Dimensions, 4);
    }
    
    public static void testAckley(int numParticles, int numSwarms, int Dimensions)
    {
        testFunction(numParticles, numSwarms, Dimensions, 5);
    }
    
    public static void testPSO(int numParticles, int Dimensions, boolean dt)
    {
        runTest(numParticles, Dimensions, dt, 1, 1, 0);
        runTest(numParticles, Dimensions, dt, 2, 1, 0);
        runTest(numParticles, Dimensions, dt, 3, 1, 0);
        runTest(numParticles, Dimensions, dt, 4, 1, 0);
    }
    
    public static void testCPSO_S(int numParticles, int Dimensions, boolean dt)
    {
        runTest(numParticles, Dimensions, dt, 1, 1, 1);
        runTest(numParticles, Dimensions, dt, 2, 1, 1);
        runTest(numParticles, Dimensions, dt, 3, 1, 1);
        runTest(numParticles, Dimensions, dt, 4, 1, 1);
    }

    public static void testCPSO_Sk(int numParticles, int numSwarms, int Dimensions, boolean dt)
    {
        runTest(numParticles, Dimensions, dt, 1, numSwarms, 2);
        runTest(numParticles, Dimensions, dt, 2, numSwarms, 2);
        runTest(numParticles, Dimensions, dt, 3, numSwarms, 2);
        runTest(numParticles, Dimensions, dt, 4, numSwarms, 2);
    }

    public static void testCPSO_Hk(int numParticles, int numSwarms, int Dimensions, boolean dt)
    {
        runTest(numParticles, Dimensions, dt, 1, numSwarms, 3);
        runTest(numParticles, Dimensions, dt, 2, numSwarms, 3);
        runTest(numParticles, Dimensions, dt, 3, numSwarms, 3);
        runTest(numParticles, Dimensions, dt, 4, numSwarms, 3);
    }

     public static void testCPSO_Rk(int numParticles, int numSwarms, int Dimensions, boolean dt)
    {
        runTest(numParticles, Dimensions, dt, 1, numSwarms, 4);
        runTest(numParticles, Dimensions, dt, 2, numSwarms, 4);
        runTest(numParticles, Dimensions, dt, 3, numSwarms, 4);
        runTest(numParticles, Dimensions, dt, 4, numSwarms, 4);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        getAverageSolveRate();
    }
    
}
