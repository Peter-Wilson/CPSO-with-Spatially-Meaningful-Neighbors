/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso;

import java.util.ArrayList;

/**
 *
 * @author Peter
 */
public class Result {
    public boolean solved;
    public int iterationsToSolve;
    public double finalFitness;
    public ArrayList<Double> globalBestPerIteration;
    
    public Result()
    {
        globalBestPerIteration = new ArrayList<Double>();
        solved = false;
    }
}
