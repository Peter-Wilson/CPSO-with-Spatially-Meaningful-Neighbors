/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Functions;

import cpso.Particle;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.jzy3d.plot3d.builder.delaunay.jdt.Point_dt;

/**
 *
 * @author pw12nb
 */
public class TriangulationTest {
    
    public TriangulationTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of closestNeighbour method, of class Triangulation.
     */
    @Test
    public void testClosestNeighbour() {
        System.out.println("closestNeighbour");
        Point_dt item = new Point_dt(1,1,0);
        ArrayList<Point_dt> neighbours = new ArrayList<Point_dt>();
        neighbours.add(new Point_dt(3,3,0));
        neighbours.add(new Point_dt(1,4,0));
        neighbours.add(item);
        Point_dt expResult = neighbours.get(0);
        Point_dt result = Triangulation.closestNeighbour(item, neighbours);
        assertEquals(expResult, result);
    }

    /**
     * Test of lengthVector method, of class Triangulation.
     */
    @Test
    public void testLengthVector() {
        System.out.println("lengthVector");
        double[] v = {8.0, 0, 0};
        double expResult = 8.0;
        double result = Triangulation.lengthVector(v);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of working_together method, of class Triangulation.
     */
    @Test
    public void testWorking_together() {
        System.out.println("working_together following");
        Point_dt item = new Point_dt(1,1,1);
        Point_dt connected = new Point_dt(2,2,2);
        double[] initialPosition1 = {1,1,1};
        double[] velocity = {1,1,1};
        Particle expected1 = new Particle(initialPosition1, 1);
        expected1.setVelocity(velocity);
        
        
        double[] initialPosition2 = {2,2,2};
        Particle expected2 = new Particle(initialPosition2, 1);
        expected2.setVelocity(velocity);
        Particle[] p = {expected1, expected2};
        boolean expResult = true;
        boolean result = Triangulation.working_together(item, connected, p);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of working_together method, of class Triangulation.
     */
    @Test
    public void testWorking_together2() {
        System.out.println("working_together heading towards eachother");
        Point_dt item = new Point_dt(1,1,1);
        Point_dt connected = new Point_dt(4,1,1);
        double[] initialPosition1 = {1,1,1};
        double[] velocity = {1,0,0};
        Particle expected1 = new Particle(initialPosition1, 1);
        expected1.setVelocity(velocity);
        
        
        double[] velocity2 = {-1,0,0};
        double[] initialPosition2 = {4,1,1};
        Particle expected2 = new Particle(initialPosition2, 1);
        expected2.setVelocity(velocity2);
        Particle[] p = {expected1, expected2};
        boolean expResult = true;
        boolean result = Triangulation.working_together(item, connected, p);
        assertEquals(expResult, result);
    }

    /**
     * Test of working_together method, of class Triangulation.
     */
    @Test
    public void testWorking_together3() {
        System.out.println("not working_together");
        Point_dt item = new Point_dt(1,1,1);
        Point_dt connected = new Point_dt(2,2,2);
        double[] initialPosition1 = {1,1,1};
        double[] velocity = {1,1,1};
        Particle expected1 = new Particle(initialPosition1, 1);
        expected1.setVelocity(velocity);
        
        
        double[] velocity2 = {-1,-1,-1};
        double[] initialPosition2 = {2,2,2};
        Particle expected2 = new Particle(initialPosition2, 1);
        expected2.setVelocity(velocity2);
        Particle[] p = {expected1, expected2};
        boolean expResult = false;
        boolean result = Triangulation.working_together(item, connected, p);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of add method, of class Triangulation.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        double[] a = {3,4};
        double[] b = {5,-1};
        double[] expResult = {8,3};
        double[] result = Triangulation.add(a, b);
        assertArrayEquals(expResult, result, 0.0);
    }

    /**
     * Test of subtract method, of class Triangulation.
     */
    @Test
    public void testSubtract() {
        System.out.println("subtract");
        double[] a = {3,4};
        double[] b = {5,-1};
        double[] expResult = {-2,5};
        double[] result = Triangulation.subtract(a, b);
        assertArrayEquals(expResult, result, 0.0);
    }

    /**
     * Test of dot method, of class Triangulation.
     */
    @Test
    public void testDot() {
        System.out.println("dot");
        double[] a = {0,3,-7};
        double[] b = {2,3,1};
        double expResult = 2.0;
        double result = Triangulation.dot(a, b);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getParticle method, of class Triangulation.
     */
    @Test
    public void testGetParticle() {
        System.out.println("getParticle");
        Point_dt connected = new Point_dt(1,2,3);
        double[] initialPosition = {1,2,3};
        Particle expResult = new Particle(initialPosition, 1);
        double[] initialPosition2 = {3,2,1};
        Particle expResult2 = new Particle(initialPosition2, 1);
        Particle[] particles = {expResult2, expResult};
        Particle result = Triangulation.getParticle(connected, particles);
        assertEquals(expResult, result);
    }

    /**
     * Test of convertParticletoPoint method, of class Triangulation.
     */
    @Test
    public void testConvertParticletoPoint() {
        System.out.println("convertParticletoPoint");
        double[] initialPosition = {1.0,2.0,3.0};
        Particle p = new Particle(initialPosition, 1);
        Point_dt expResult = new Point_dt(1.0,2.0,3.0);
        Point_dt result = Triangulation.convertParticletoPoint(p);
        assertEquals(expResult.x(), result.x(), 0.0);
        assertEquals(expResult.y(), result.y(), 0.0);
        assertEquals(expResult.z(), result.z(), 0.0);
    }
    
}
