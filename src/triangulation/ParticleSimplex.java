/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package triangulation;

import cpso.Particle;
import java.util.ArrayList;

/**
 *
 * @author pw12nb
 */
public class ParticleSimplex {
    
    private ArrayList<Particle> points;
    private ArrayList<Particle> outsideList;
    
    public ParticleSimplex()
    {
        points = new ArrayList<Particle>();
        outsideList = new ArrayList<Particle>();
    }
    
    public ParticleSimplex(Particle[] particles)
    {
        outsideList = new ArrayList<Particle>();
        points = new ArrayList<Particle>();
        for(int i = 0; i < particles.length; i++)
        {
            points.add(particles[i]);
        }
    }
    
    public Particle[] getPoints()
    {
        return (Particle[])points.toArray();
    }
    
    public void addParticle(Particle p)
    {
        if(points.size() >= 0 && points.size() < points.get(0).getPosition().length + 1)
        {
            points.add(p);
        }
    }
    
    public void addToOutsideList(Particle p)
    {
        outsideList.add(p);
    }
    
    public void removeFromOutsideList(Particle p)
    {
        outsideList.remove(p);
    }
    
    public int outsideLength()
    {
        return outsideList.size();
    }
            
    public boolean particleAbove(Particle p, int extraDimension)
    {
        //TODO: return true if the particle stated is above the current simplex
        return true;
    }

    Particle getFurthestPoint() {
       double maxDistance = Double.MIN_VALUE;
       Particle furthestParticle = null;
       
       for(Particle p : outsideList)
       {
          double distance = getDistance(p, this.getPoints());
          if(distance > maxDistance)
          {
              maxDistance = distance;
              furthestParticle = p;
          }
       }
       return furthestParticle;
    }

    boolean above(Particle p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private double getDistance(Particle p, Particle[] points) {
        if(points.length == 1)
        {
            return EuclideanDistance(p,points[0]);
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private double EuclideanDistance(Particle p, Particle point) {
        int distance = 0;
        double[] pointPosition = point.getPosition();
        double[] pPosition = p.getPosition();
        
        for(int i = 0; i < pPosition.length; i++)
        {
            distance += Math.pow((pointPosition[i] - pPosition[i]), 2);
        }
        
        return Math.sqrt(distance);
    }
    
}
