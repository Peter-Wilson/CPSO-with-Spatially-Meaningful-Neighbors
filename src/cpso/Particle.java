/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso;


/**
 *
 * @author Peter
 */
public class Particle {
    private double[] position;
    private double[] velocity;
    private double[] pBest;
    private double fitness;
    private double pBestFitness = Integer.MAX_VALUE;
    
    public Particle(double[] initialPosition, int function)
    {
        setPosition(initialPosition);
        setpBest(initialPosition);
        setVelocity(randomizeVelocity(initialPosition, function));        
    }
    
    public Particle(double[] initialPosition)
    {
        setPosition(initialPosition);
        setpBest(initialPosition);
        double[] velocity = new double[initialPosition.length];
        setVelocity(velocity);        
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
     * @return the fitness
     */
    public double getpBestFitness() {
        return pBestFitness;
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
        if(this.pBestFitness == Integer.MAX_VALUE) 
            pBestFitness = fitness;
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
        boolean value = false;
        //if the pBest hasn't been set or the new position is better
            //update the pBest value
            if ((fitness == 0.0 || pBest == null) ||
                (newFitness < fitness && min) ||
                (newFitness > fitness && !min))
            {
                pBestFitness = newFitness;
                pBest = newPosition;
                value = true;
            }
            
            fitness = newFitness;
            return value;
    }
    
    public boolean inside(Particle a, Particle b, Particle c)
    {
        //TODO: finish
        return false;
    }

    private double[] randomizeVelocity(double[] position, int function) {
        double[] velocity = new double[position.length];
        double diameter = Swarm.getDiameter(function);
        double upperBound =  (diameter/2);
        double lowerBound = -(diameter/2);

        if(function == 0)
        {
            lowerBound = 1;
            upperBound = diameter+1;
        }
                
        for(int i = 0; i < velocity.length; i++)
        {
            double randomNumber = 0;
            do
            {
                randomNumber = (Math.random()*diameter)-(diameter/2);
            }
            while(position[i]+randomNumber < lowerBound || position[i]+randomNumber > upperBound);
            velocity[i] = randomNumber;
        }
        return velocity;
    }
}
