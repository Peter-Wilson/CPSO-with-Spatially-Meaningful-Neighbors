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
public class DulaunayTriangulation {
    
    /**
     * Compute Delaunay by adding an extra dimension and computing quick hull
     * this gives an nlogn algorithm that works on any dimension
     * @param particles the list of particles (points) in the swarm
     * @return the adjacency matrix the notes who is connected with who
     */
    public static int[][] compute_delaunay(Particle[] particles)
    {
        int numParticles = particles.length;
        int[][] adjMatrix = new int[numParticles][numParticles];
        int[] extraDimension = new int[numParticles];
        
        for(int i = 0; i < extraDimension.length; i++)
        {
           //add the extra dimension to the corresponding array
            extraDimension[i] = 0;
            for(int j = 0; j < particles[i].getPosition().length; j++)
            {
                //the extra coordinate = |p|^2 (where p = point)
                extraDimension[i] += Math.pow(particles[i].getPosition()[j],2);
            }
        }
        
        
        return adjMatrix;
    }
    
    /**
     * Computes the convex hull of the specified list and returns the list of connected 
     * faces (ArrayList of Simplexes)
     * @param particles the list of points to compute the convex hull
     * @param extraDimension the newly computed dimension
     * @return the list of connected faces in the convex hull in an arraylist
     */
    static ArrayList<ParticleSimplex> convexHull(Particle[] particles, int[] extraDimension)
    {
        ArrayList<Particle> used = new ArrayList<Particle>();
        
        //find the 'edge' points (the furthest d+1 points)
        Particle[] points = findFurthest(particles);
        
        //create a simplex of d+1 points
        ArrayList<ParticleSimplex> faces = new ArrayList<ParticleSimplex>();
        ParticleSimplex startSimplex = new ParticleSimplex(points);
        faces.add(startSimplex);
        
        //for each facet F
        for(int f = 0; f < faces.size(); f++)
        {
            //for each unassigned point p
            for(int p = 0; p < particles.length; p++)
            {
                //if p is above F
                if(faces.get(f).particleAbove(particles[p], extraDimension[p]))
                {
                    //assign p to F's outside set
                    faces.get(f).addToOutsideList(particles[p]);
                }
            }
        }
        
        //for each facet F with a non-empty outside set
        
            //select the furthest point p on F's outside set
            //initialize the visible set V to F
            //for all unvisited neighbors N of facets in V
                //if p is above N
                    //add N to V
            //the boundary of V is the set of horizon ridges H
            //for each ridge R in H
                //create a new facet from R and p
                //link the new facet to its neighbors
            //for each new facet F'
                //for each unassigned point q in an outside set of a facet in V
                    //if q is above F'
                        //assign q to F's outside set
            //delete the facets in V
        
        return faces;
    }
    
    static Particle[] findFurthest(Particle[] p)
    {
        Particle[] points = new Particle[p[0].getPosition().length+1];
        //find the d+1 outlier points
        
        return points;
    }
    
    // dist_Point_to_Line(): get the distance of a point to a line
    //     Input:  a Point P and a Line L (in any dimension)
    //     Return: the shortest distance from P to L
    static double distancePointToLine( double[] dest, double[] start, double[] finish)
    {
         //double[] v = L.P1 - L.P0;
         double[] v = minusPoints(finish, start);
         //double[] w = P - L.P0;         
         double[] w = minusPoints(dest, start);

         double c1 = dot(w,v);
         double c2 = dot(v,v);
         double b = c1 / c2;

         double[] point = new double[start.length];
         for(int i = 0; i < start.length; i++)
         {
            point[i] = start[i] + b * v[i];
         }
         return distanceBetweenPoints(dest, point);
    }
    
    /**
     * Return the distance between 2 points in n-space
     * @param a the first point
     * @param b the second point
     * @return the distance
     */
    static double distanceBetweenPoints(double[] a, double[] b)
    {
        double result = 0;
        
        for(int i = 0; i < a.length; i++)
        {
            result += Math.pow((a[i] - b[i]),2);
        }
        
        return Math.sqrt(result);
    }
    
    /**
     * Subtract 2 points no matter the dimensions
     * @param a the point to subtract from
     * @param b the point to subtract by
     * @return the new point
     */
    static double[] minusPoints(double[] a, double[] b)
    {
        double[] result = new double[a.length];
        for(int i = 0; i < a.length; i++)
        {
            result[i] = a[i] - b[i];
        }
        return result;
    }
    
    /**
     * Dot Product of 2 points
     * @param a the first point
     * @param b the second point
     * @return the resulting value
     */
    static double dot(double[] a, double[] b)
    {
        int result = 0;
        
        for(int i = 0; i < a.length; i++)
        {
            result+= (a[i]*b[i]);
        }
        
        return result;        
    }
    
    /**
     * Return the best of the connected neighbours after delaunay triangulation
     * has been completed
     * @param particles the list of particles to compare
     * @param index the index of the current particle
     * @param adjMatrix the matrix of the particles it is connected to
     * @return the best particle to swap information with
     */
    public static Particle chooseBestNeighbour(Particle[] particles, int index, int[] adjMatrix)
    {
        boolean hasConnectedNeighbours = false;
        Particle best = null;
        
        //choose best from list (see algorithm)
        
        return best;        
    }
}
