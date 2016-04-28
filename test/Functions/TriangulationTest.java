/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Functions;

import cpso.Particle;
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
        Point_dt item = null;
        Point_dt[] neighbours = null;
        Point_dt expResult = null;
        Point_dt result = Triangulation.closestNeighbour(item, neighbours);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of lengthVector method, of class Triangulation.
     */
    @Test
    public void testLengthVector() {
        System.out.println("lengthVector");
        double[] v = null;
        double expResult = 0.0;
        double result = Triangulation.lengthVector(v);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of working_together method, of class Triangulation.
     */
    @Test
    public void testWorking_together() {
        System.out.println("working_together");
        Point_dt item = null;
        Point_dt connected = null;
        Particle[] p = null;
        boolean expResult = false;
        boolean result = Triangulation.working_together(item, connected, p);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of add method, of class Triangulation.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        double[] a = null;
        double[] b = null;
        double[] expResult = null;
        double[] result = Triangulation.add(a, b);
        assertArrayEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of subtract method, of class Triangulation.
     */
    @Test
    public void testSubtract() {
        System.out.println("subtract");
        double[] a = null;
        double[] b = null;
        double[] expResult = null;
        double[] result = Triangulation.subtract(a, b);
        assertArrayEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dot method, of class Triangulation.
     */
    @Test
    public void testDot() {
        System.out.println("dot");
        double[] a = null;
        double[] b = null;
        double expResult = 0.0;
        double result = Triangulation.dot(a, b);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getParticle method, of class Triangulation.
     */
    @Test
    public void testGetParticle() {
        System.out.println("getParticle");
        Point_dt connected = null;
        Particle[] particles = null;
        Particle expResult = null;
        Particle result = Triangulation.getParticle(connected, particles);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of convertParticletoPoint method, of class Triangulation.
     */
    @Test
    public void testConvertParticletoPoint() {
        System.out.println("convertParticletoPoint");
        Particle p = null;
        Point_dt expResult = null;
        Point_dt result = Triangulation.convertParticletoPoint(p);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
