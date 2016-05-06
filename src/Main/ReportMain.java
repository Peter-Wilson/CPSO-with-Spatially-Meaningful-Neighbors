/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

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
        testCPSO_S(10, 6, false); // no dt        
        testCPSO_S(10, 6, true); // dt
        testCPSO_Sk(10, 2, 6, false);
        testCPSO_Sk(10, 2, 6, true);
        testCPSO_Hk(10, 2, 6, false);
        testCPSO_Hk(10, 2, 6, true);
        testCPSO_Rk(10, 2, 6, false);
        testCPSO_Rk(10, 2, 6, true);
    }
    
    public static void testCPSO_S(int numParticles, int Dimensions, boolean dt)
    {
        //2D Schaffer
            int completed = 0;
            int iteration = 0;
            
            for(int i = 0; i < 50; i++)
            {
                CPSO_S cpso = new CPSO_S(Dimensions, 10000, numParticles, 1.0, 1.49618, 1.49618, dt, 1);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO-S Schaffer DT?"+dt+":\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
            
            //Rastrigin
            completed = 0;
            iteration = 0;
            
            for(int i = 0; i < 50; i++)
            {
                CPSO_S cpso = new CPSO_S(Dimensions, 10000, numParticles, 1.0, 1.49618, 1.49618, dt, 2);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO-S Rastrigin DT?"+dt+":\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
            
            //Rosenbrock            
            completed = 0;
            iteration = 0;            
            for(int i = 0; i < 50; i++)
            {
                CPSO_S cpso = new CPSO_S(Dimensions, 10000, numParticles, 1.0, 1.49618, 1.49618, dt, 3);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO-S Rosenbrock DT?"+dt+":\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
            
            //Greiwanck
            completed = 0;
            iteration = 0;
            
            for(int i = 0; i < 50; i++)
            {
                CPSO_S cpso = new CPSO_S(Dimensions, 10000, numParticles, 1.0, 1.49618, 1.49618, dt, 4);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO-S reiwanck DT?"+dt+":\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
            
            //Ackley
            completed = 0;
            iteration = 0;
            
            for(int i = 0; i < 50; i++)
            {
                CPSO_S cpso = new CPSO_S(Dimensions, 10000, numParticles, 1.0, 1.49618, 1.49618, dt, 5);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO-S Ackley DT?"+dt+":\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
    }

    public static void testCPSO_Sk(int numParticles, int numSwarms, int Dimensions, boolean dt)
    {
        //2D Schaffer
            int completed = 0;
            int iteration = 0;
            
            for(int i = 0; i < 50; i++)
            {
                CPSO_S_k cpso = new CPSO_S_k(Dimensions, 10000, numParticles, 1.0, 1.49618, 1.49618, numSwarms, dt, 2);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO-S-k Schaffer DT?"+dt+":\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
            
            //Rastrigin
            completed = 0;
            iteration = 0;
            
            for(int i = 0; i < 50; i++)
            {
                CPSO_S_k cpso = new CPSO_S_k(Dimensions, 10000, numParticles, 1.0, 1.49618, 1.49618,numSwarms, dt, 2);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO-S-k Rastrigin DT?"+dt+":\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
            
            //Rosenbrock            
            completed = 0;
            iteration = 0;            
            for(int i = 0; i < 50; i++)
            {
                CPSO_S_k cpso = new CPSO_S_k(Dimensions, 10000, numParticles, 1.0, 1.49618, 1.49618, numSwarms, dt, 3);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO-S-k Rosenbrock DT?"+dt+":\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
            
            //Greiwanck
            completed = 0;
            iteration = 0;
            
            for(int i = 0; i < 50; i++)
            {
                CPSO_S_k cpso = new CPSO_S_k(Dimensions, 10000, numParticles, 1.0, 1.49618, 1.49618, numSwarms, dt, 4);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO-S-k reiwanck DT?"+dt+":\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
            
            //Ackley
            completed = 0;
            iteration = 0;
            
            for(int i = 0; i < 50; i++)
            {
                CPSO_S_k cpso = new CPSO_S_k(Dimensions, 10000, numParticles, 1.0, 1.49618, 1.49618, numSwarms, dt, 5);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO-S_k Ackley DT?"+dt+":\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
    }

    public static void testCPSO_Hk(int numParticles, int numSwarms, int Dimensions, boolean dt)
    {
        //2D Schaffer
            int completed = 0;
            int iteration = 0;
            
            for(int i = 0; i < 50; i++)
            {
                CPSO_H_k cpso = new CPSO_H_k(Dimensions, 10000, numParticles, 1.0, 1.49618, 1.49618, numSwarms, dt, 2);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO-h-k Schaffer DT?"+dt+":\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
            
            //Rastrigin
            completed = 0;
            iteration = 0;
            
            for(int i = 0; i < 50; i++)
            {
                CPSO_H_k cpso = new CPSO_H_k(Dimensions, 10000, numParticles, 1.0, 1.49618, 1.49618,numSwarms, dt, 2);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO-h-k Rastrigin DT?"+dt+":\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
            
            //Rosenbrock            
            completed = 0;
            iteration = 0;            
            for(int i = 0; i < 50; i++)
            {
                CPSO_H_k cpso = new CPSO_H_k(Dimensions, 10000, numParticles, 1.0, 1.49618, 1.49618, numSwarms, dt, 3);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO-h-k Rosenbrock DT?"+dt+":\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
            
            //Greiwanck
            completed = 0;
            iteration = 0;
            
            for(int i = 0; i < 50; i++)
            {
                CPSO_H_k cpso = new CPSO_H_k(Dimensions, 10000, numParticles, 1.0, 1.49618, 1.49618, numSwarms, dt, 4);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO-h-k reiwanck DT?"+dt+":\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
            
            //Ackley
            completed = 0;
            iteration = 0;
            
            for(int i = 0; i < 50; i++)
            {
                CPSO_H_k cpso = new CPSO_H_k(Dimensions, 10000, numParticles, 1.0, 1.49618, 1.49618, numSwarms, dt, 5);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO-h_k Ackley DT?"+dt+":\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
    }

     public static void testCPSO_Rk(int numParticles, int numSwarms, int Dimensions, boolean dt)
    {
        //2D Schaffer
            int completed = 0;
            int iteration = 0;
            
            for(int i = 0; i < 50; i++)
            {
                CPSO_R_k cpso = new CPSO_R_k(Dimensions, 10000, numParticles, 1.0, 1.49618, 1.49618, numSwarms, dt, 2);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO_R_k Schaffer DT?"+dt+":\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
            
            //Rastrigin
            completed = 0;
            iteration = 0;
            
            for(int i = 0; i < 50; i++)
            {
                CPSO_R_k cpso = new CPSO_R_k(Dimensions, 10000, numParticles, 1.0, 1.49618, 1.49618,numSwarms, dt, 2);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO_R_k Rastrigin DT?"+dt+":\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
            
            //Rosenbrock            
            completed = 0;
            iteration = 0;            
            for(int i = 0; i < 50; i++)
            {
                CPSO_R_k cpso = new CPSO_R_k(Dimensions, 10000, numParticles, 1.0, 1.49618, 1.49618, numSwarms, dt, 3);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO_R_k Rosenbrock DT?"+dt+":\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
            
            //Greiwanck
            completed = 0;
            iteration = 0;
            
            for(int i = 0; i < 50; i++)
            {
                CPSO_R_k cpso = new CPSO_R_k(Dimensions, 10000, numParticles, 1.0, 1.49618, 1.49618, numSwarms, dt, 4);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO_R_k reiwanck DT?"+dt+":\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
            
            //Ackley
            completed = 0;
            iteration = 0;
            
            for(int i = 0; i < 50; i++)
            {
                CPSO_R_k cpso = new CPSO_R_k(Dimensions, 10000, numParticles, 1.0, 1.49618, 1.49618, numSwarms, dt, 5);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO_R_k Ackley DT?"+dt+":\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        getAverageSolveRate();
    }
    
}
