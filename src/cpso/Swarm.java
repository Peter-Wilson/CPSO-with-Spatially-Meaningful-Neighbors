/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso;

import java.util.Random;
import org.jzy3d.plot3d.builder.delaunay.jdt.Delaunay_Triangulation;
import org.jzy3d.plot3d.builder.delaunay.jdt.Point_dt;


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

        public Swarm(int swarmSize, double C1, double C2, double INERTIA, boolean min, int k, int function)
        {
            this.min = min;
            this.swarmSize = swarmSize;
            this.C1 = C1;
            this.C2 = C2;
            this.k = k;
            this.INERTIA = INERTIA;
            this.function = function;
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
            switch(function)
            {
                case 0: return (rand.nextDouble()*50)+1;
                case 1: return (rand.nextDouble()*200)-100;
                case 2: return (rand.nextDouble()*10.24)-5.12;
                case 3: return (rand.nextDouble()*60)-30;
                case 4: return (rand.nextDouble()*1200)-600;
                case 5: return (rand.nextDouble()*64)-32;
                default: return 0;
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
        public void CalculateDelaunatTriangulation3D() throws Exception
        {
            Point_dt[] points = new Point_dt[particles.length];
            int dimensions = particles[0].getPosition().length;
            for(int i = 0; i < particles.length; i++)
            {
                if(dimensions == 2)
                {
                    double[] part = particles[i].getPosition();
                    points[i] = new Point_dt(part[0], part[1]);
                }
                else if(dimensions == 3)
                {
                    double[] part = particles[i].getPosition();
                    points[i] = new Point_dt(part[0], part[1], part[2]);
                }
                else
                {
                    throw new Exception("Only works for 2D and 3D swarms");
                }                
            }
            
           dt = new Delaunay_Triangulation(points);
        }

    }
