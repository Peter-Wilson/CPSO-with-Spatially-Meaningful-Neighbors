/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso;

import Functions.Triangulation;
import java.util.Random;
import org.jzy3d.plot3d.builder.delaunay.jdt.Delaunay_Triangulation;
import org.jzy3d.plot3d.builder.delaunay.jdt.Point_dt;
import org.jzy3d.plot3d.builder.delaunay.jdt.Triangle_dt;


/**
 *
 * @author Peter
 */
public class Swarm
    {
        private Particle[] particles;
        private Particle globalBest;
        boolean min = true;
        int swarmSize;
        double C1 = 1;
        double C2 = 1;
        double INERTIA = 1;
        Delaunay_Triangulation dt;
        int k = 0;
        int function = 0;
        double diameter = 0;

        public Swarm(int swarmSize, double C1, double C2, double INERTIA, boolean min, int k, int function)
        {
            this.min = min;
            this.swarmSize = swarmSize;
            this.C1 = C1;
            this.C2 = C2;
            this.k = k;
            this.INERTIA = INERTIA;
            this.function = function;
            setDiameter(function);
            InitializeParticles();
        }

        private void InitializeParticles()
        {
            setGlobalBest(null);
            particles = new Particle[swarmSize];
            
            Random rand = new Random();
            for (int i = 0; i < swarmSize; i++)
            {
                double[] position = new double[k];
                for(int j = 0; j < k; j++)
                    
                    position[j] = getRandomNumber(rand,function);
                
                particles[i] = new Particle(position);
            }
        }
        
        public double getRandomNumber(Random rand, int function)
        {
            return (rand.nextDouble()*diameter)-(diameter/2);
        }
        
        public final void setDiameter(int function)
        {
            switch(function)
            {
                case 0: diameter = 50;
                case 1: diameter = 200;
                case 2: diameter = 10.24;
                case 3: diameter = 60;
                case 4: diameter = 1200;
                case 5: diameter = 64;
            }
        }
        
        //Update the velocity of the points based on the velocity solution
        public void UpdateVelocity(Particle p)
        {
            UpdateVelocity(p, false);
        }
        

        //Update the velocity of the points based on the velocity solution
        public void UpdateVelocity(Particle p, boolean test)
        {
            Random random = new Random();
            double R1 = random.nextDouble();
            double R2 = random.nextDouble();
            
            //for testing purposes remove the randomness
            if(test){ R1 = 1; R2 = 1; }

            for(int i = 0; i < k; i ++)
            p.setVelocity((INERTIA * p.getVelocity()[i]) +
                         C1 * R1 * (p.getpBest()[i] - p.getPosition()[i]) +
                         C2 * R2 * (getGlobalBest().getPosition()[i] - p.getPosition()[i]), i);
        }

        //Use the velocity to update the position
        public void UpdatePosition(Particle p)
        {
            for(int i = 0; i < k; i++)
            {
                p.setPosition(p.getPosition()[i] + p.getVelocity()[i], i);
            }
        }

        /**
         * @return the particles
         */
        public Particle[] getParticles() {
            return particles;
        }

        /**
         * @return the globalBest
         */
        public Particle getGlobalBest() {
            return globalBest;
        }

        /**
         * @param globalBest the globalBest to set
         */
        public void setGlobalBest(Particle globalBest) { 
            this.globalBest = globalBest;
        }
        
        /**
         * Sets the value of a random particle to the supplied value
         * @param value 
         */
        public boolean setRandomParticle(double[] position, double[] velocity)
        {
            if(position.length != k || velocity.length != k)
                return false;
            else
            {
                int randomIndex = (int)(Math.random()*k);
                particles[randomIndex].setPosition(position);
                particles[randomIndex].setVelocity(velocity);
                return true;
            }
        }
        
        /**
         * Creates the Delaunay Triangulation
         * @throws Exception if the dimensions are incorrect
         */
        public void CalculateDelaunayTriangulation()
        {
            Point_dt[] points = new Point_dt[particles.length];
            for(int i = 0; i < particles.length; i++)
            {
               points[i] =  Triangulation.convertParticletoPoint(particles[i]);          
            }
            
           dt = new Delaunay_Triangulation(points);
        }
        
        
        
        /**
         * Returns the best item to swap with based on the criteria noted
         * @param item the item that is looking to swap
         * @return the item that is best to swap with
         */
        public Particle chooseBestNeighbour(Particle item)
        {
            if(particles[0].getPosition().length == 1) return null;
            boolean hasConnectedNeighbours = false;
            Point_dt particlePoint = Triangulation.convertParticletoPoint(item);
            
            Triangle_dt neighbours = dt.find(particlePoint);
            Point_dt[] connected = {neighbours.p1(),neighbours.p2(),neighbours.p3()};
            
            //Pf = min(Nk)
            Point_dt point = Triangulation.closestNeighbour(particlePoint, connected);
            Point_dt temp = null;
            
            //for k = 1 to neighbourset_size do
            for(int i = 0; i < 3; i++)
            {
                if(connected[i] == particlePoint) continue;
                
                //if working_together(Pi, Pk)and
                if(Triangulation.working_together(particlePoint, connected[i], particles))
                {
                    
                    //if dist(Xi − Pc) < dist(Xi − Pk)and fitness(Pk) < fitness(Xi) then
                    if(particlePoint.distance3D(point) < particlePoint.distance3D(connected[i]) &&
                        Triangulation.getParticle(connected[i], particles).getFitness() < Triangulation.getParticle(particlePoint, particles).getFitness())
                    {
                        temp = connected[i];
                        hasConnectedNeighbours = true;
                    }
                }
            }
            
            //localExploitationRatio = swarm.diameter/200
            double localExploitationRatio = diameter/200;
            
            if(hasConnectedNeighbours &&
                    particlePoint.distance3D(temp)/ particlePoint.distance3D(point) >
                    localExploitationRatio &&
                    Triangulation.lengthVector(item.getVelocity()) < 2*particlePoint.distance3D(temp))
            {
                return Triangulation.getParticle(temp, particles);
            }
            else
            {
                return Triangulation.getParticle(point, particles);
            }  
            
        }

    

    }
