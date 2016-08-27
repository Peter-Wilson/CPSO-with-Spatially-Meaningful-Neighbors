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
    int function;
    private double[] pBest;
    private Particle socialNeighbour = null;
    
    public Particle(double[] initialPosition, int function)
    {
        this.function = function;
        setPosition(initialPosition);
        setpBest(this.getPosition());
        setVelocity(randomizeVelocity(initialPosition)); 
        //setVelocity(new double[initialPosition.length], function);        
    }

    /**
     * @return the position
     */
    public double[] getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     * @param function the function that is being tested (to see the bounds)
     */
    public void setPosition(double[] position) {
        //force the new position to be a valid position
        double diameter = Swarm.getDiameter(function);
        for(int i = 0; i < position.length; i++)
        {
            if(position[i] < -diameter) position[i] = -diameter;
            else if(position[i] > diameter) position[i] = diameter;
        }
        
        this.position = position.clone();
    }
    
    /**
     * Set the position based on the index dimension
     * @param position the position to set 
     * @param index set the value at that specific index
     * @param function the function that is being tested (to see the bounds)
     */
    public void setPosition(double position, int index) {
        //force the new position to be a valid position
        double diameter = Swarm.getDiameter(function);
        if(position < -diameter) position = -diameter;
        else if(position > diameter) position = diameter;
        
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
     * @param function the function that is being tested (to see the bounds)
     */
    public void setVelocity(double[] velocity) {
        double diameter = Swarm.getDiameter(function);
        //ensure new velocity will lead to a valid solution
        for(int i = 0; i < velocity.length; i++)
        {
            if((position[i]+velocity[i]) < -diameter) velocity[i] = -diameter-position[i];
            else if((position[i]+velocity[i]) > diameter) velocity[i] = diameter-position[i];
        }
        
        this.velocity = velocity.clone();
    }
    
    /**
     * set the velocity based on the index
     * @param velocity the velocity to set
     * @param index set the index value at that specific index
     * @param function the function that is being tested (to see the bounds)
     */
    public void setVelocity(double velocity, int index) {
        double diameter = Swarm.getDiameter(function);
        //ensure new velocity will lead to a valid solution
        if((position[index]+velocity) < -diameter) velocity = -diameter-position[index];
        else if((position[index]+velocity) > diameter) velocity = diameter-position[index];
        
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
        //force the new position to be a valid position
        double diameter = Swarm.getDiameter(function);
        for(int i = 0; i < pBest.length; i++)
        {
            if(pBest[i] < -diameter) pBest[i] = -diameter;
            else if(pBest[i] > diameter) pBest[i] = diameter;
        }
        
        this.pBest = pBest.clone();
    }
    
    /**
     * set the pBest based on the index
     * @param pBest the pBest to set
     * @param index set the pBest value at the specific index
     */
    public void setpBest(double pBest, int index) {
        
        double diameter = Swarm.getDiameter(function);
        if(pBest < -diameter) pBest = -diameter;
        else if(pBest > diameter) pBest = diameter;
        this.pBest[index] = pBest;
    }

    private double[] randomizeVelocity(double[] position) {
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

    /**
     * @return the socialNeighbour
     */
    public Particle getSocialNeighbour() {
        return socialNeighbour;
    }

    /**
     * @param socialNeighbour the socialNeighbour to set
     */
    public void setSocialNeighbour(Particle socialNeighbour) {
        this.socialNeighbour = socialNeighbour;
    }
}
