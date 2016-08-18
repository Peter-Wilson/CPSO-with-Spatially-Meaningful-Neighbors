/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pw12nb
 */
public class ParticleTest {
    
    public ParticleTest() {
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
     * Test of getPosition method, of class Particle.
     */
    @Test
    public void testGetPosition() {
        System.out.println("getPosition");
        double[] position = { 2.0, 1.4 };
        Particle instance = new Particle(position, 1);
        double[] result = instance.getPosition();
        assertArrayEquals(position, result, 0.0);
    }

    /**
     * Test of setPosition method, of class Particle.
     */
    @Test
    public void testSetPosition() {
        System.out.println("setPosition");
        double[] start_position = { 2.0, 1.4 };
        double[] new_position = { 6.2, 1.1 };
        Particle instance = new Particle(start_position, 1);
        instance.setPosition(new_position);
        double[] result = instance.getPosition();
        assertArrayEquals(new_position, result, 0.0);
        
    }
    
    /**
     * Test of setPosition method using the index, of class Particle.
     */
    @Test
    public void testSetPositionByIndex() {
        System.out.println("setPositionByIndex");
        double[] start_position = { 2.0, 1.4 };
        double new_position = 8.2;
        Particle instance = new Particle(start_position, 1);
        instance.setPosition(new_position,0);
        double[] result = instance.getPosition();
        assertEquals(new_position, result[0], 0.0);
        
    }

    /**
     * Test of getVelocity method, of class Particle.
     */
    @Test
    public void testGetVelocity() {
        System.out.println("getVelocity");
        double[] start_position = { 2.0, 1.4 };
        Particle instance = new Particle(start_position, 1);
        double[] expResult = {0.0, 0.0};
        double[] result = instance.getVelocity();
        assertThat(expResult, IsNot.not(IsEqual.equalTo(result)));
    }

    /**
     * Test of setVelocity method, of class Particle.
     */
    @Test
    public void testSetVelocity() {
        System.out.println("setVelocity");
        double[] velocity = {8.1, 5.5};
        double[] start_position = { 2.0, 1.4 };
        Particle instance = new Particle(start_position, 1);
        instance.setVelocity(velocity);
        assertArrayEquals(instance.getVelocity(), velocity, 0.0);
    }
    
    /**
     * Test of setVelocity method by index, of class Particle.
     */
    @Test
    public void testSetVelocityByIndex() {
        System.out.println("setVelocity by index");
        double velocity = 8.1;
        double[] start_position = { 2.0, 1.4 };
        Particle instance = new Particle(start_position, 1);
        instance.setVelocity(velocity, 0);
        assertEquals(instance.getVelocity()[0], velocity, 0.0);
    }

    /**
     * Test of getpBest method, of class Particle.
     */
    @Test
    public void testGetpBest() {
        System.out.println("getpBest");
        double[] start_position = { 2.0, 1.4 };
        Particle instance = new Particle(start_position, 1);
        double[] expResult = {0.0, 0.0};
        double[] result = instance.getpBest();
        assertNotNull(result);
        assertArrayEquals(result, start_position, 0.0);
    }

    /**
     * Test of setpBest method, of class Particle.
     */
    @Test
    public void testSetpBest() {
        System.out.println("setpBest");
        double[] pBest = {2.2, 8.6};
        double[] start_position = { 2.0, 1.4 };
        Particle instance = new Particle(start_position, 1);
        instance.setpBest(pBest);
        assertArrayEquals(instance.getpBest(), pBest, 0.0);
    }
    
    /**
     * Test of setpBest method by index, of class Particle.
     */
    @Test
    public void testSetpBestByIndex() {
        System.out.println("setpBest by index");
        double pBest = 3.5;
        double[] start_position = { 2.0, 1.4 };
        double[] defaultpBest = {2.3,5.5};
        Particle instance = new Particle(start_position, 1);
        instance.setpBest(defaultpBest);
        instance.setpBest(pBest, 0);
        assertEquals(instance.getpBest()[0], pBest, 0.0);
    }

    /**
     * Test of setPosition method, of class Particle.
     */
    @Test
    public void testSetPosition_doubleArr() {
        System.out.println("setPosition");
        double[] position = {50, 2, 27, 18};
        double[] position_new = {3, 12, 12, 2};
        Particle instance = new Particle(position, 1);
        instance.setPosition(position_new);
        assertArrayEquals(instance.getPosition(), position_new, 0.0);
    }

    /**
     * Test of setPosition method, of class Particle.
     */
    @Test
    public void testSetPosition_double_int() {
        System.out.println("setPosition");
        double position = 56.12;
        int index = 3;
        double[] initialPosition = {4.3, 5.22, 43.3,88.7,2,56,8};
        Particle instance = new Particle(initialPosition, 1);
        instance.setPosition(position, index);
        assertEquals(instance.getPosition()[index], position, 0.0);
    }

    /**
     * Test of setVelocity method, of class Particle.
     */
    @Test
    public void testSetVelocity_doubleArr() {
        System.out.println("setVelocity");
        double[] velocity = {4, 2, 27, 18};
        double[] position = {6, 12, 12, 2};
        Particle instance = new Particle(position, 0);
        instance.setVelocity(velocity);
        assertArrayEquals(instance.getVelocity(), velocity, 0.0);
    }

    /**
     * Test of setVelocity method, of class Particle.
     */
    @Test
    public void testSetVelocity_double_int() {
        System.out.println("setVelocity");
        double velocity = 3.456;
        int index = 3;
        double[] position = {6, 34, 12, 3, 2, 12};
        Particle instance = new Particle(position, 0);
        instance.setVelocity(velocity, index);
        assertEquals(instance.getVelocity()[index], velocity, 0.0);
    }

    /**
     * Test of setpBest method, of class Particle.
     */
    @Test
    public void testSetpBest_doubleArr() {
        System.out.println("setpBest");
        double[] pBest = {5.5,2.2,4};
        double[] startPosition = {4,2,6};
        Particle instance = new Particle(startPosition, 1);
        instance.setpBest(pBest);
        assertArrayEquals(instance.getpBest(), pBest, 0.0);
    }

    /**
     * Test of setpBest method, of class Particle.
     */
    @Test
    public void testSetpBest_double_int() {
        System.out.println("setpBest");
        double pBest = 55.7;
        double[] pBest_Default = {5.5,2.2,4};
        int index = 1;
        double[] startPosition = {4,2,6};
        Particle instance = new Particle(startPosition, 1);
        instance.setpBest(pBest_Default);
        instance.setpBest(pBest, index);
        assertEquals(instance.getpBest()[index], pBest, 0.0);
    }
    
}
