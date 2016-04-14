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
           //convert to dimension+1
           //add the extra dimension to the corresponding array
           //the extra coordinate = |p|^2 (where p = point)
        }
        
        //Find the endpoints
        int start = 0;
        int end = 0;
        
        //calculate convex hull on these points
        //quick hull works with any dimensionality
        QuickHull(start, end, extraDimension, particles, adjMatrix);
        QuickHull(end, start, extraDimension, particles, adjMatrix);
        
        return adjMatrix;
    }
    
    /**
     * an nlogn algorithm that computes the quick hull in any dimension
     * @param start the index of the start particle
     * @param end the index of the end particle
     * @param extraDimension the pre-computed extra dimension values
     * @param particles the list of particles to scan through
     * @param adjMatrix the adjacency matrix that will be updated
     */
    private static void QuickHull(int start, int end, int[] extraDimension, Particle[] particles, int[][] adjMatrix)
    {
        
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
