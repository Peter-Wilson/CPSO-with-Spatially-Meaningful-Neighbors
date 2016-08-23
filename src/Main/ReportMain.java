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

/**
 *
 * @author Peter
 */
public class ReportMain {
    
    public static void getAverageSolveRate()
    {
        testCPSO_S(20, 6, false); // no dt        
            System.out.println("\br");
        testCPSO_S(20, 6, true); // dt
            System.out.println("\br");
        testCPSO_Sk(20, 3, 6, false);
            System.out.println("\br");
        testCPSO_Sk(20, 3, 6, true);
            System.out.println("\br");
        testCPSO_Hk(20, 3, 6, false);
            System.out.println("\br");
        testCPSO_Hk(20, 2, 6, true);
            System.out.println("\br");
        testCPSO_Rk(20, 3, 6, false);
            System.out.println("\br");
        testCPSO_Rk(20, 2, 6, true);
            System.out.println("\br");
    }
    
    public static void runTest(int numParticles, int Dimensions, boolean dt, int function, int numSwarms, int type)
    {
        int completed = 0;
            int iteration = 0;
            boolean worked = false;
            int successDT = 0;
            int unsuccessDT = 0;
            
            for(int i = 0; i < 50; i++)
            {
                //System.out.println("Starting"+(i+1));
                worked = false;
                try{
                        CPSO cpso;
                        switch(type)
                        {
                            case 2:
                                cpso = new CPSO_S_k(Dimensions, 3000, numParticles, 1.0, 1.49618, 1.49618, numSwarms, dt, function, true);
                                break;
                            case 3:
                                cpso = new CPSO_H_k(Dimensions, 3000, numParticles, 1.0, 1.49618, 1.49618, numSwarms, dt, function, true);
                                break;
                            case 4:
                                cpso = new CPSO_R_k(Dimensions, 3000, numParticles, 1.0, 1.49618, 1.49618, numSwarms, dt, function, true);
                                break;
                            default:
                                cpso = new CPSO_S(Dimensions, 3000, numParticles, 1.0, 1.49618, 1.49618, dt, function, true);
                                break;
                        }
                        Result r = cpso.start();
                        if(r.solved)
                        {
                            completed++;
                            iteration += r.iterationsToSolve;
                        }
                        successDT += r.successfulDTs;
                        unsuccessDT = r.unsuccessfulDTs;
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
