/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso_s_k;

import cpso_s.*;

/**
 *
 * @author Peter
 */
public class Particle {
    private double position;
    private double velocity;
    private double pBest;
    private double fitness;
    
    public Particle(double initialPosition)
    {
        setPosition(initialPosition);
    }

    /**
     * @return the position
     */
    public double getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(double position) {
        this.position = position;
    }

    /**
     * @return the velocity
     */
    public double getVelocity() {
        return velocity;
    }

    /**
     * @param velocity the velocity to set
     */
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    /**
     * @return the pBest
     */
    public double getpBest() {
        return pBest;
    }

    /**
     * @param pBest the pBest to set
     */
    public void setpBest(double pBest) {
        this.pBest = pBest;
    }

    /**
     * @return the fitness
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * @param fitness the fitness to set
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
    
    /**
     * Updates the personal best value if the new value is better
     * @param newFitness the newly changes fitness
     * @param newPosition the newly changed position
     * @param min whether or not it is checking for minimum or max value
     * @return true/false if the value is updated
     */
    public boolean UpdatePersonalBest(double newFitness, double newPosition, boolean min)
    {
        //if the pBest hasn't been set or the new position is better
            //update the pBest value
            if ((fitness == 0.0 || pBest == 0.0) ||
                (newFitness < fitness && min) ||
                (newFitness > fitness && !min))
            {
                fitness = newFitness;
                pBest = newPosition;
                return true;
            }
            return false;
    }
}
