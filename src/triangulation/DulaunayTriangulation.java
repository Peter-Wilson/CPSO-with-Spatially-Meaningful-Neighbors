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
        
        // determine if i-j-k is a circle with no interior points 
        for (int i = 0; i < numParticles; i++) {
            for (int j = i+1; j < numParticles; j++) {
                for (int k = j+1; k < numParticles; k++) {
                    boolean isTriangle = true;
                    //confirms no other points are inside the triangle
                    for (int a = 0; a < numParticles; a++) {
                        if (a == i || a == j || a == k) continue;
                        if (particles[a].inside(particles[i], particles[j], particles[k])) {
                           isTriangle = false;
                           break;
                        }
                    }
                    //if no, then this is the right triangulation
                    //set up adjacency matrix connections
                    if (isTriangle) {
                        adjMatrix[i][j] = 1;                        
                        adjMatrix[i][k] = 1;
                        adjMatrix[j][i] = 1;                        
                        adjMatrix[j][k] = 1;
                        adjMatrix[k][i] = 1;                        
                        adjMatrix[k][j] = 1;
                    }
                }
            }
        } 
        
        return adjMatrix;
    }
    
    public static Particle chooseBestNeighbour(Particle[] particles, int index, int[] adjMatrix)
    {
        boolean hasConnectedNeighbours = false;
        
        
    }
}
