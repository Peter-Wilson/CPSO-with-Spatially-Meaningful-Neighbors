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
        //get average for CPSO-S
            //2D Schaffer
            int completed = 0;
            int iteration = 0;
            
            for(int i = 0; i < 50; i++)
            {
                CPSO_S cpso = new CPSO_S(6, 10000, 10, 1.0, 1.49618, 1.49618, false, 1);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO-S Schaffer No DT:\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
            
            //Rastrigin
            completed = 0;
            iteration = 0;
            
            for(int i = 0; i < 50; i++)
            {
                CPSO_S cpso = new CPSO_S(6, 10000, 10, 1.0, 1.49618, 1.49618, false, 2);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO-S Rastrigin No DT:\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
            
            //Rosenbrock            
            completed = 0;
            iteration = 0;            
            for(int i = 0; i < 50; i++)
            {
                CPSO_S cpso = new CPSO_S(6, 10000, 10, 1.0, 1.49618, 1.49618, false, 3);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO-S Rosenbrock No DT:\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
            
            //Greiwanck
            completed = 0;
            iteration = 0;
            
            for(int i = 0; i < 50; i++)
            {
                CPSO_S cpso = new CPSO_S(6, 10000, 10, 1.0, 1.49618, 1.49618, false, 4);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO-S reiwanck No DT:\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
            
            //Ackley
            completed = 0;
            iteration = 0;
            
            for(int i = 0; i < 50; i++)
            {
                CPSO_S cpso = new CPSO_S(6, 10000, 10, 1.0, 1.49618, 1.49618, false, 5);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO-S Ackley No DT:\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
            
        
        //get average for CPSO-Sk
            completed = 0;
            iteration = 0;
            
            for(int i = 0; i < 50; i++)
            {
                CPSO_S_k cpso = new CPSO_S_k(6, 10000, 10, 1.0, 1.49618, 1.49618, 3, false, 1);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO-S-k Schaffer No DT:\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
            
            //Rastrigin
            completed = 0;
            iteration = 0;
            
            for(int i = 0; i < 50; i++)
            {
                CPSO_S_k cpso = new CPSO_S_k(6, 10000, 10, 1.0, 1.49618, 1.49618, 3, false, 2);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO-S-k Rastrigin No DT:\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
        
        //get average for CPSO-Hk
            completed = 0;
            iteration = 0;
            
            for(int i = 0; i < 50; i++)
            {
                CPSO_H_k cpso = new CPSO_H_k(6, 10000, 10, 1.0, 1.49618, 1.49618, 3, false, 1);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO-H-k Schaffer No DT:\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
        
            //Rastrigin
            completed = 0;
            iteration = 0;
            
            for(int i = 0; i < 50; i++)
            {
                CPSO_H_k cpso = new CPSO_H_k(6, 10000, 10, 1.0, 1.49618, 1.49618, 3, false, 1);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO-H-k Rastrigin No DT:\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
        
        //get average for CPSO-Rk
            completed = 0;
            iteration = 0;
            
            for(int i = 0; i < 50; i++)
            {
                CPSO_R_k cpso = new CPSO_R_k(6, 10000, 10, 1.0, 1.49618, 1.49618, 3, false, 1);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO-R-k Schaffer No DT:\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
            
            //Rastrigin
            completed = 0;
            iteration = 0;
            
            for(int i = 0; i < 50; i++)
            {
                CPSO_R_k cpso = new CPSO_R_k(6, 10000, 10, 1.0, 1.49618, 1.49618, 3, false, 1);
                Result r = cpso.start();
                if(r.solved)
                {
                    completed++;
                    iteration += r.iterationsToSolve;
                }
            }
            System.out.println("CPSO-R-k Rastrigin No DT:\t\tCompleted: "+completed/0.5+"%\t\taverage iterations:"+((completed==0)? 0: (iteration/completed)));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        getAverageSolveRate();
    }
    
}
