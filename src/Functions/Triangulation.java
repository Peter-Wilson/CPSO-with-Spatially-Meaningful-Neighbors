/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Functions;

import cpso.Particle;
import java.util.ArrayList;
import org.jzy3d.plot3d.builder.delaunay.jdt.Point_dt;

/**
 *
 * @author pw12nb
 */
public class Triangulation {
    public static Point_dt closestNeighbour(Point_dt item, ArrayList<Point_dt> neighbours) {
        double minDistance = Double.MAX_VALUE;
        Point_dt closest = null;
        for(int i = 0; i < neighbours.size(); i++)
        {
            if(neighbours.get(i) == item) continue;
            
            double distance = item.distance3D(neighbours.get(i));
            if(distance < minDistance)
            {
                closest = neighbours.get(i);
                minDistance = distance;
            }
        }
        return closest;
    }
    
    public static double lengthVector(double[] v)
    {
        int length = 0;
        for(int i = 0; i < v.length; i++)
            length += Math.pow(v[i],2);
        return Math.sqrt(length);
    }

    /**
     * returns if 2 particles are working together
     * 2 particles are working together if:
           1) if a particle P1 is following behind another particle
           P2 then a directed connection is made from P1 to P2.
           This represents particles heading in the same general
           direction for which the trailing particle is connected to
           the leading one. Figure 6(left) illustrates this case.
           2) If two particles, P1 and P2, are heading towards each
           other (but not past one another) they are considered to
           be cooperating and an undirected connection is made
           between the two. This case is shown in Figure 6 (right).
     * @param item the first item to check
     * @param connected the connected item to test 
     * @param p the list of all the particles in the swarm
     * @return true or false if the two particles are working together
     */
    public static boolean working_together(Point_dt item, Point_dt connected, Particle[] p) {
               
        Particle a = getParticle(item, p);
        Particle b = getParticle(connected, p);
        
        double[] v1 = a.getVelocity();
        double[] n1 = add(a.getPosition(), v1);
        double[] n2 = add(b.getPosition(), b.getVelocity());
        
        double[] u1to2 = subtract(n2,n1);
        
        return dot(v1,u1to2) > 0;
    }
    
    public static double[] add(double[] a, double[] b)
    {
        double[] temp = new double[a.length];
        for(int i = 0; i < a.length; i++)
        {
            temp[i] = a[i] + b[i];
        }
        return temp;
    }
    
    public static double[] subtract(double[] a, double[] b)
    {
        double[] temp = new double[a.length];
        for(int i = 0; i < a.length; i++)
        {
            temp[i] = a[i] - b[i];
        }
        return temp;
    }
    
    /**
     * Dot Product of 2 points
     * @param a the first point
     * @param b the second point
     * @return the resulting value
     */
    public static double dot(double[] a, double[] b)
    {
        int result = 0;
        
        for(int i = 0; i < a.length; i++)
        {
            result+= (a[i]*b[i]);
        }
        
        return result;        
    }

    /**
     * Returns the particle at the specific position
     * @param connected the point that you are looking for
     * @param particles the list of particles that the point will be looked for in
     * @return  the particle
     */
    public static Particle getParticle(Point_dt connected, Particle[] particles) {
        //remove the new dimension to be able to find the point
        if(connected == null) return null;
        
        if(particles[0].getPosition().length == 1)
        {
            connected = new Point_dt(connected.x(), 0, 0);
        }
        
        for(Particle p : particles)
        {
            double[] position = p.getPosition();
            //check if first digit is wrong
            if(position == null || position[0] != connected.x()) continue;
            
            //check if second digit is wrong
            if((position.length > 1 && position[1] != connected.y()) ||
                    (position.length <= 1 && connected.y() != 0))
                    continue;
            
            //check if third digit is wrong
            if((position.length > 2 && position[2] != connected.z()) ||
                    (position.length <= 2 && connected.z() != 0))
                    continue;
            
            return p;
        }
        
        return null;
    }
    
    /**
         * Note only works for up to 3 dimensions
         * @param p the particle to convert
         * @return the new Point_dt containing the particles position
         */
        public static Point_dt convertParticletoPoint(Particle p)
        {
            int dimensions = p.getPosition().length;
            if(dimensions == 1)
            {
                double[] part = p.getPosition();
                // make sure the points are not all on the same line so
                //you can make a triangle out of it
                return new Point_dt(part[0], Math.pow(part[0],2), 0);
            }
            if(dimensions == 2)
            {
                double[] part = p.getPosition();
                return new Point_dt(part[0], part[1], 0);
            }
            else 
            {
                double[] part = p.getPosition();
                return new Point_dt(part[0], part[1], part[2]);
            }  
        }
}
