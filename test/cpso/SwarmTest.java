/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso;

import Functions.Triangulation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.jzy3d.plot3d.builder.delaunay.jdt.Delaunay_Triangulation;
import org.jzy3d.plot3d.builder.delaunay.jdt.Point_dt;
import org.jzy3d.plot3d.builder.delaunay.jdt.Triangle_dt;

/**
 *
 * @author pw12nb
 */
public class SwarmTest {
    
    public SwarmTest() {
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
     * Test for initializing Particles.
     */
    @Test
    public void testInitializeParticles() {
        System.out.println("Particle Initialization");
        Swarm instance = new Swarm(20, 0.1, 0.1, 0.1, true, 1, 0);
        for(Particle i : instance.getParticles())
        {
            assertNotNull(i);
        }
    }
    
     /**
     * Test of initializing Particles of different functions
     */
    @Test
    public void testGetRandomNumber() {
        System.out.println("testGetRandomNumber");
        double random = 0;
        double expected = 0;
        Random rand = new Random();
        
        //log function
        expected = 50;
        Swarm instance = new Swarm(20, 0.2, 0.3, 0.5, true, 1, 0);
        random = instance.getRandomNumber(rand, instance.function);
        assertTrue(random >= -expected);
        assertTrue(random <= expected);
        
        //schaffer function
        expected = 100;
        instance = new Swarm(20, 0.2, 0.3, 0.5, true, 1, 1);
        random = instance.getRandomNumber(rand, instance.function);
        assertTrue(random >= -expected);
        assertTrue(random <= expected);
        
        //Rastrigin function
        expected = 5.12;
        instance = new Swarm(20, 0.2, 0.3, 0.5, true, 1, 2);
        random = instance.getRandomNumber(rand, instance.function);
        assertTrue(random >= -expected);
        assertTrue(random <= expected);
        
        //Rosenbrock function
        expected = 30;
        instance = new Swarm(20, 0.2, 0.3, 0.5, true, 1, 3);
        random = instance.getRandomNumber(rand, instance.function);
        assertTrue(random >= -expected);
        assertTrue(random <= expected);
        
        //Griewanck function
        expected = 600;
        instance = new Swarm(20, 0.2, 0.3, 0.5, true, 1, 4);
        random = instance.getRandomNumber(rand, instance.function);
        assertTrue(random >= -expected);
        assertTrue(random <= expected);
        
        //Ackley function
        expected = 32;
        instance = new Swarm(20, 0.2, 0.3, 0.5, true, 1, 5);
        random = instance.getRandomNumber(rand, instance.function);
        assertTrue(random >= -expected);
        assertTrue(random <= expected);
    }
    
    /**
     * Test for Updating the Velocity.
     */
    @Test
    public void testGetDiameter(){
        //function 1
        double result = Swarm.getDiameter(0);
        double expResult = 50;
        assertEquals(result, expResult, 0.0);
        
        //function 2
        result = Swarm.getDiameter(1);
        expResult = 100;
        assertEquals(result, expResult, 0.0);
        
        //function 3
        result = Swarm.getDiameter(2);
        expResult = 5.12;
        assertEquals(result, expResult, 0.0);
        
        //function 4
        result = Swarm.getDiameter(3);
        expResult = 30;
        assertEquals(result, expResult, 0.0);
        
        //function 5
        result = Swarm.getDiameter(4);
        expResult = 600; 
        assertEquals(result, expResult, 0.0);
        
        //function 6
        result = Swarm.getDiameter(5);
        expResult = 32;
        assertEquals(result, expResult, 0.0);
        
        //function 6
        result = Swarm.getDiameter(-1);
        expResult = -1;
        assertEquals(result, expResult, 0.0);
    }
    
    /**
     * Test for Updating the Velocity.
     */
    @Test
    public void testaddClosestParticles1D(){
        System.out.println("Get 2 closest particles in 1D");
        double[] position1 = {0};
        double[] position2 = {1};
        double[] position3 = {-1.5};
        double[] position4 = {8};
        double[] position5 = {1.2};
        double[] position6 = {-1};
        Swarm s = new Swarm(6, 0.2, 0.3, 0.5, true, 1, 5);
        s.getParticles()[0] = new Particle(position1, 5);
        s.getParticles()[1] = new Particle(position2, 5);
        s.getParticles()[2] = new Particle(position3, 5);
        s.getParticles()[3] = new Particle(position4, 5);
        s.getParticles()[4] = new Particle(position5, 5);
        s.getParticles()[5] = new Particle(position6, 5);
        ArrayList<Point_dt> connected = new ArrayList<Point_dt>();
        
        s.addClosestParticles1D(s.getParticles()[0], connected);
        
        int expected1 = 2;
        assertEquals(expected1, connected.size());
        assertEquals(1, connected.get(0).x(), 0.0);
        assertEquals(-1, connected.get(1).x(), 0.0);
    }
    
    /**
     * Test for Updating the Velocity.
     */
    @Test
    public void testUpdateVelocity() {
        System.out.println("Velocity update");
        //calculate a test velocity
        //calculate a test velocity
        Swarm instance = new Swarm(20, 0.2, 0.3, 0.5, true, 1, 1);
        Particle p = instance.getParticles()[0];
        double[] velocity = {5.0};
        double[] position = {2.0};
        double[] pBest = {0.0};
        p.setVelocity(velocity,0);
        p.setPosition(position,0);
        p.setpBest(position);
        instance.setGlobalBest(new Particle(pBest, 1));
        instance.UpdateVelocity(p, 0, true);
        
        double[] expectedVelocity = {1.9};
        assertArrayEquals(expectedVelocity, p.getVelocity(), 0.0);
        
        System.out.println("Gets correct value");
        
        double[] velocity2 = {5};
        p.setVelocity(velocity2,0);
        instance.UpdateVelocity(p, 0, true);
        double[] newValue = Triangulation.add(p.getPosition(), p.getVelocity());
        
        for(int i = 0; i < newValue.length; i++)
        {
            assertTrue(newValue[i] >= -(instance.diameter/2));
            assertTrue(newValue[i] <= (instance.diameter/2));
        }
        
        System.out.println("Is successfully clamped");
        
    }
    
    /**
     * Test for Updating the Velocity.
     */
    @Test
    public void testUpdatePosition() {
        System.out.println("Velocity update");
        //calculate a test velocity
        Swarm instance = new Swarm(20, 0.2, 0.3, 0.5, true, 5, 0);
        Particle p = instance.getParticles()[0];
        double[] velocity = {5, 16.5, -2, 22, 8.65};
        double[] position = {6, 32.1, 11, 15, 13};
        p.setPosition(position,0);
        p.setVelocity(velocity,0);
        instance.setGlobalBest(new Particle(position, 0));
        instance.UpdatePosition(p);
        
        double[] expectedVelocity = {11, 48.6, 9, 37, 21.65};
        
        //ensure the actual velocity is the same
        assertArrayEquals(p.getPosition(), expectedVelocity, 0.1);  
    }

    /**
     * Test of getParticles method, of class Swarm.
     */
    @Test
    public void testGetParticles() {
        System.out.println("getParticles");
        int expSize = 20;
        Swarm instance = new Swarm(expSize, 0.5, 0.2, 0.3, true, 5, 0);
        Particle[] result = instance.getParticles();
        
        //check if correct size
        assertEquals(expSize, result.length);
        
        //check if not null
        for(int i = 0; i < result.length; i++)
        {
            assertNotNull(result[i]);
        }
    }
    
    /**
     * 
     */
    @Test
    public void testCalculateDelaunayTriangulation3D()
    {
        System.out.println("test calculate delaunay triangulation 3D");
        Swarm instance = new Swarm(20, 0.5, 0.2, 0.3, true, 3, 0);
        instance.CalculateDelaunayTriangulation();
        Delaunay_Triangulation dt = instance.dt;
        
        //test that there is more than one 
        assertNotEquals(dt.trianglesSize(), 0);
        
        System.out.println("getting the triangles");
        Iterator<Triangle_dt> iterator = dt.trianglesIterator();
        int count = 1;
        
        while(iterator.hasNext())
        {            
            System.out.println("testing triangle: "+count++);
            Triangle_dt triangles = iterator.next(); 
        }
        
    }
    
    /**
     * 
     */
    @Test
    public void testCalculateDelaunayTriangulation2D()
    {
        System.out.println("test calculate delaunay triangulation 2D");
        Swarm instance = new Swarm(20, 0.5, 0.2, 0.3, true, 2, 0);
        instance.CalculateDelaunayTriangulation();
        Delaunay_Triangulation dt = instance.dt;
        
        //test that there is more than one 
        assertNotEquals(dt.trianglesSize(), 0);
        
        System.out.println("getting the triangles");
        Iterator<Triangle_dt> iterator = dt.trianglesIterator();
        int count = 1;
        
        while(iterator.hasNext())
        {            
            System.out.println("testing triangle: "+count++);
            Triangle_dt triangles = iterator.next(); 
        }
        
    }
    
    /**
     * 
     */
    @Test
    public void testCalculateDelaunayTriangulation1D()
    {
        System.out.println("test calculate delaunay triangulation 1D");
        Swarm instance = new Swarm(20, 0.5, 0.2, 0.3, true, 1, 0);
        instance.CalculateDelaunayTriangulation();
        Delaunay_Triangulation dt = instance.dt;
        
        //test that there is more than one 
        assertNotEquals(dt.trianglesSize(), 0);
        
        System.out.println("getting the triangles");
        Iterator<Triangle_dt> iterator = dt.trianglesIterator();
        int count = 1;
        
        while(iterator.hasNext())
        {            
            System.out.println("testing triangle: "+count++);
            Triangle_dt triangles = iterator.next(); 
        }
        
    }
    
}
