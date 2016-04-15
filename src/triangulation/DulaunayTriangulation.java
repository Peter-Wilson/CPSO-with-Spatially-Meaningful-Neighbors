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
           //add the extra dimension to the corresponding array
            extraDimension[i] = 0;
            for(int j = 0; j < particles[i].getPosition().length; j++)
            {
                //the extra coordinate = |p|^2 (where p = point)
                extraDimension[i] += Math.pow(particles[i].getPosition()[j],2);
            }
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
	int max = 0;
	double maxDistance = Double.MIN_VALUE;
	double d, minD, current;
        
	for(int i = 0; i < particles.length; i++)
	{
		//if the point is inbetween the line and is not drawn yet
		if (point->drawn == 0 && i != start && i != end)
		{
			d = distancePointToLine(particles[i].getPosition(), particles[start].getPosition(), particles[end].getPosition());

			if (d == 0)
			{
				//check if collinear point
				//if slope is infinity, use the points to determine if it is in between
				if (line->start->y - point->y == 0 && (Min(line->start->x, line->end->x) > point->x || Max(line->start->x, line->end->x) < point->x))
					continue; 
				//if slope is 0, use the points
				else if (line->start->x - point->x == 0 && (Min(line->start->y, line->end->y) > point->y || Max(line->start->y, line->end->y) < point->y))
					continue;
				//otherwise, use opposing slopes to determine if in the middle
				else if ((((line->start->x - point->x) / (line->start->y - point->y)) + ((line->end->x - point->x) / (line->end->y - point->y))) != 0)
					continue;

			}
			if(d > maxDistance)
			{
				max = i;
				maxDistance = d;
			}
		}
	}

	if(maxDistance >= 0)
	{
		edge* one = AddEdge(line->start, max);
		edge* two = AddEdge(max, line->end);
		RemoveEdge(line);

		Quick_Hull(head, one);
		Quick_Hull(head, two);
	}
	/*
	else if (maxDistance == 0)
	{
		if ()
		{
			edge* one = AddEdge(line->start, max);
			edge* two = AddEdge(max, line->end);
			RemoveEdge(line);

			Quick_Hull(head, one);
			Quick_Hull(head, two);
		}
	}*/
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
