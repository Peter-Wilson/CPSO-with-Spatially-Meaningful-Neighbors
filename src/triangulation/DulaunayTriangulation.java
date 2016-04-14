/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package triangulation;

import cpso.Particle;

/**
 *
 * @author pw12nb
 */
public class DulaunayTriangulation {
    
    public static int[][] compute_delaunay(Particle[] particles)
    {
        int numParticles = particles.length;
        int[][] adjMatrix = new int[numParticles][numParticles];
        
        //convert to dimension+1
        //the extra coordinate = |p|^2 (where p = point)
        //calculate convex hull on these points
               //quick hull works with any dimensionality
        //remove last dimension from convex hull
        
        return adjMatrix;
    }
    
    public static Particle chooseBestNeighbour(Particle[] particles, int index, int[] adjMatrix)
    {
        boolean hasConnectedNeighbours = false;
        Particle best = null;
        
        //choose best from list (see algorithm)
        
        return best;        
    }
}
