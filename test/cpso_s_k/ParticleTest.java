/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso_s_k;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Peter
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
        Particle instance = new Particle(position);
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
        Particle instance = new Particle(start_position);
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
        Particle instance = new Particle(start_position);
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
        Particle instance = new Particle(start_position);
        double[] expResult = {0.0, 0.0};
        double[] result = instance.getVelocity();
        assertArrayEquals(expResult, result, 0.0);
    }

    /**
     * Test of setVelocity method, of class Particle.
     */
    @Test
    public void testSetVelocity() {
        System.out.println("setVelocity");
        double[] velocity = {8.1, 5.5};
        double[] start_position = { 2.0, 1.4 };
        Particle instance = new Particle(start_position);
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
        Particle instance = new Particle(start_position);
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
        Particle instance = new Particle(start_position);
        double[] expResult = {0.0, 0.0};
        double[] result = instance.getpBest();
        assertNull(result);
    }

    /**
     * Test of setpBest method, of class Particle.
     */
    @Test
    public void testSetpBest() {
        System.out.println("setpBest");
        double[] pBest = {2.2, 8.6};
        double[] start_position = { 2.0, 1.4 };
        Particle instance = new Particle(start_position);
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
        Particle instance = new Particle(start_position);
        instance.setpBest(defaultpBest);
        instance.setpBest(pBest, 0);
        assertEquals(instance.getpBest()[0], pBest, 0.0);
    }

    /**
     * Test of getFitness method, of class Particle.
     */
    @Test
    public void testGetFitness() {
        System.out.println("getFitness");
        double[] start_position = { 2.0, 1.4 };
        Particle instance = new Particle(start_position);
        double expResult = 0.0;
        double result = instance.getFitness();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setFitness method, of class Particle.
     */
    @Test
    public void testSetFitness() {
        System.out.println("setFitness");
        double fitness = 0.0;
        double[] start_position = { 2.0, 1.4 };
        Particle instance = new Particle(start_position);
        instance.setFitness(fitness);
        assertEquals(instance.getFitness(), fitness, 0.0);
    }
    
    /**
     * Test of UpdatePersonalBest method, of class Particle for when the values are not set
     */
    @Test
    public void testUpdatePersonalBestA() {
        System.out.println("Update Personal Best for values not yet set");
        double[] start_position = { 2.0, 1.4 };
        Particle instance = new Particle(start_position);
        double expectedFitness = 5.0;
        double[] expectedPosition = {6.0, 8.0};
        instance.UpdatePersonalBest(expectedFitness, expectedPosition, true);
        
        assertEquals(instance.getFitness(), expectedFitness, 0.0);
        assertArrayEquals(instance.getpBest(), expectedPosition, 0.0);
    }
    
    /**
     * Test of UpdatePersonalBest method, of class Particle for when the values are min
     */
    @Test
    public void testUpdatePersonalBestB() {
        System.out.println("Update Personal Best for min values");
        
        double[] start_position = { 2.0, 1.4 };
        Particle instance = new Particle(start_position);
        
        //test when it shouldn't update
        instance.setFitness(3.0);
        double[] initPBest = {2.0, 1.6};
        instance.setpBest(initPBest);
        double expectedFitness = 5.0;
        double[] expectedPosition = {6.0, 2.3};
        instance.UpdatePersonalBest(expectedFitness, expectedPosition, true);
        
        assertNotEquals(instance.getFitness(), expectedFitness);
        assertNotEquals(instance.getpBest(), expectedPosition);
        
        //test when it should update
        instance.setFitness(3.0);
        double[] initPBest2 = {2.0, 8.3};
        instance.setPosition(initPBest);
        expectedFitness = 2.0;
        double[] expectedPosition2 = {1.0, 5.0};
        instance.UpdatePersonalBest(expectedFitness, expectedPosition2, true);
        
        assertEquals(instance.getFitness(), expectedFitness, 0.0);
        assertArrayEquals(instance.getpBest(), expectedPosition2, 0.0);
    }
    
    /**
     * Test of UpdatePersonalBest method, for when the values are max.
     */
    @Test
    public void testUpdatePersonalBestC() {
        System.out.println("Update Personal Best for max values");
        
        double[] start_position = { 2.0, 1.4 };
        Particle instance = new Particle(start_position);
        
        //test when it shouldn't update
        instance.setFitness(3.0);
        double[] initPBest = {2.0, 1.6};
        instance.setpBest(initPBest);
        double expectedFitness = 2.0;
        double[] expectedPosition = {1.0, 5.0};
        instance.UpdatePersonalBest(expectedFitness, expectedPosition, false);
        
        assertNotEquals(instance.getFitness(), expectedFitness);
        assertNotEquals(instance.getpBest(), expectedPosition);
        
        //test when it should update
        instance.setFitness(3.0);
        double[] initPBest2 = {2.4, 8.8};
        instance.setPosition(initPBest2);
        expectedFitness = 5.0;
        double[] expectedPosition2 = {6.0, 8.7};
        instance.UpdatePersonalBest(expectedFitness, expectedPosition2, false);
        
        assertEquals(instance.getFitness(), expectedFitness, 0.0);
        assertArrayEquals(instance.getpBest(), expectedPosition2, 0.0);
    }
    
}
