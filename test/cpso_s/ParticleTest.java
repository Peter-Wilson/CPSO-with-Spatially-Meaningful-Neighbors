/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso_s;

import cpso_s.Particle;
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
        Particle instance = new Particle(5.0);
        double expResult = 5.0;
        double result = instance.getPosition();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setPosition method, of class Particle.
     */
    @Test
    public void testSetPosition() {
        System.out.println("setPosition");
        double position = 0.0;
        Particle instance = new Particle(5.0);
        instance.setPosition(position);
        double result = instance.getPosition();
        assertEquals(position, result, 0.0);
        
    }

    /**
     * Test of getVelocity method, of class Particle.
     */
    @Test
    public void testGetVelocity() {
        System.out.println("getVelocity");
        Particle instance = new Particle(5.0);
        double expResult = 0.0;
        double result = instance.getVelocity();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setVelocity method, of class Particle.
     */
    @Test
    public void testSetVelocity() {
        System.out.println("setVelocity");
        double velocity = 0.0;
        Particle instance = new Particle(5.0);
        instance.setVelocity(velocity);
        assertEquals(instance.getVelocity(), velocity, 0.0);
    }

    /**
     * Test of getpBest method, of class Particle.
     */
    @Test
    public void testGetpBest() {
        System.out.println("getpBest");
        Particle instance = new Particle(5.0);
        double expResult = 0.0;
        double result = instance.getpBest();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setpBest method, of class Particle.
     */
    @Test
    public void testSetpBest() {
        System.out.println("setpBest");
        double pBest = 0.0;
        Particle instance = new Particle(5.0);
        instance.setpBest(pBest);
        assertEquals(instance.getpBest(), pBest, 0.0);
    }

    /**
     * Test of getFitness method, of class Particle.
     */
    @Test
    public void testGetFitness() {
        System.out.println("getFitness");
        Particle instance = new Particle(5.0);
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
        Particle instance = new Particle(5.0);
        instance.setFitness(fitness);
        assertEquals(instance.getFitness(), fitness, 0.0);
    }
    
    /**
     * Test of UpdatePersonalBest method, of class Particle for when the values are not set
     */
    @Test
    public void testUpdatePersonalBestA() {
        System.out.println("Update Personal Best for values not yet set");
        Particle instance = new Particle(0.0);
        double expectedFitness = 5.0;
        double expectedPosition = 6.0;
        instance.UpdatePersonalBest(expectedFitness, expectedPosition, true);
        
        assertEquals(instance.getFitness(), expectedFitness, 0.0);
        assertEquals(instance.getpBest(), expectedPosition, 0.0);
    }
    
    /**
     * Test of UpdatePersonalBest method, of class Particle for when the values are min
     */
    @Test
    public void testUpdatePersonalBestB() {
        System.out.println("Update Personal Best for min values");
        
        Particle instance = new Particle(0.0);
        
        //test when it shouldn't update
        instance.setFitness(3.0);
        instance.setpBest(2.0);
        double expectedFitness = 5.0;
        double expectedPosition = 6.0;
        instance.UpdatePersonalBest(expectedFitness, expectedPosition, true);
        
        assertNotEquals(instance.getFitness(), expectedFitness);
        assertNotEquals(instance.getpBest(), expectedPosition);
        
        //test when it should update
        instance.setFitness(3.0);
        instance.setPosition(2.0);
        expectedFitness = 2.0;
        expectedPosition = 1.0;
        instance.UpdatePersonalBest(expectedFitness, expectedPosition, true);
        
        assertEquals(instance.getFitness(), expectedFitness, 0.0);
        assertEquals(instance.getpBest(), expectedPosition, 0.0);
    }
    
    /**
     * Test of UpdatePersonalBest method, for when the values are max.
     */
    @Test
    public void testUpdatePersonalBestC() {
        System.out.println("Update Personal Best for max values");
        
        Particle instance = new Particle(0.0);
        
        //test when it shouldn't update
        instance.setFitness(3.0);
        instance.setpBest(2.0);
        double expectedFitness = 2.0;
        double expectedPosition = 1.0;
        instance.UpdatePersonalBest(expectedFitness, expectedPosition, false);
        
        assertNotEquals(instance.getFitness(), expectedFitness);
        assertNotEquals(instance.getpBest(), expectedPosition);
        
        //test when it should update
        instance.setFitness(3.0);
        instance.setPosition(2.0);
        expectedFitness = 5.0;
        expectedPosition = 6.0;
        instance.UpdatePersonalBest(expectedFitness, expectedPosition, false);
        
        assertEquals(instance.getFitness(), expectedFitness, 0.0);
        assertEquals(instance.getpBest(), expectedPosition, 0.0);
    }
    
}
