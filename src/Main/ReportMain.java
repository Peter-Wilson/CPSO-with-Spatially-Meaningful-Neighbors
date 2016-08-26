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

        
        
        //testSchaffer(15, 2, 4); // no dt        
        //    System.out.println("\b");
        testRastrigin(20, 2, 4); // dt
            System.out.println("\b");
        testRosenbrock(20, 2, 4); // no dt        
            System.out.println("\b");
        testGriewanck(20, 2, 4); // dt
            System.out.println("\b");
        testAckley(20, 2, 4);
            
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
            int numIterations = 1000;
            double[] averageBest = new double[numIterations];
            
            for(int i = 0; i < 50; i++)
            {
                //System.out.println("Starting"+(i+1));
                worked = false;
                try{
                        CPSO cpso;
                        switch(type)
                        {
                            case 0:
                                //PSO
                                cpso = new CPSO_R_k(Dimensions, numIterations, numParticles, 1.0, 1.49618, 1.49618, 1, dt, function, true);
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
            
            int successAverage = (successDT+unsuccessDT <= 0)?0:(successDT/(successDT+unsuccessDT))*100;
            System.out.println("Function?"+ function + " type?"+type+" DT?"+dt+":\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed))+"\tDT Success rate:"+successAverage+"%");
            try{
            if(writer != null)
            {
                writer.write(getPSOType(type)+"-"+getFunctionName(function)+",");
                for(int i = 0; i < averageBest.length; i++)
                {
                    writer.write(averageBest[i]/50 + ",");
                }
                writer.newLine();
                writer.flush();
            }
            }catch(IOException io)
            {
                
            }
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
        runTest(numParticles, Dimensions, false, function, 1, 2);
        runTest(numParticles, Dimensions, true, function, 1, 2);
        //CPSO-Hk
        //runTest(numParticles, Dimensions, false, function, numSwarms, 3);
        //runTest(numParticles, Dimensions, true, function, numSwarms, 3);
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
