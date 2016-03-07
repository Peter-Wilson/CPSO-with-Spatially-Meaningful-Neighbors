/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso_s_k;


/**
 *
 * @author Peter
 */
public class Particle {
    private double[] position;
    private double[] velocity;
    private double[] pBest;
    private double fitness;
    
    public Particle(double[] initialPosition)
    {
        setPosition(initialPosition);
        double[] value = new double[initialPosition.length];
        setVelocity(value);        
    }

    /**
     * @return the position
     */
    public double[] getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(double[] position) {
        this.position = position;
    }
    
    /**
     * Set the position based on the index dimension
     * @param position the position to set 
     */
    public void setPosition(double position, int index) {
        this.position[index] = position;
    }
    

    /**
     * @return the velocity
     */
    public double[] getVelocity() {
        return velocity;
    }

    /**
     * @param velocity the velocity to set
     */
    public void setVelocity(double[] velocity) {
        this.velocity = velocity;
    }
    
    /**
     * set the velocity based on the index
     * @param velocity the velocity to set
     */
    public void setVelocity(double velocity, int index) {
        this.velocity[index] = velocity;
    }

    /**
     * @return the pBest
     */
    public double[] getpBest() {
        return pBest;
    }

    /**
     * @param pBest the pBest to set
     */
    public void setpBest(double[] pBest) {
        this.pBest = pBest;
    }
    
    /**
     * set the pBest based on the index
     * @param pBest the pBest to set
     */
    public void setpBest(double pBest, int index) {
        this.pBest[index] = pBest;
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
    public boolean UpdatePersonalBest(double newFitness, double[] newPosition, boolean min)
    {
        //if the pBest hasn't been set or the new position is better
            //update the pBest value
            if ((fitness == 0.0 || pBest == null) ||
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
