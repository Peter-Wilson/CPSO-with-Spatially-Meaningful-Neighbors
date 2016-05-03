/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso;

import java.util.Random;
import org.hamcrest.core.Is;
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
    public void testUpdateVelocity() {
        System.out.println("Velocity update");
        //calculate a test velocity
        //calculate a test velocity
        Swarm instance = new Swarm(20, 0.2, 0.3, 0.5, true, 1, 0);
        Particle p = instance.getParticles()[0];
        double[] velocity = {5.0};
        double[] position = {2.0};
        double[] pBest = {0.0};
        p.setVelocity(velocity);
        p.setPosition(position);
        p.setpBest(position);
        instance.setGlobalBest(new Particle(pBest));
        instance.UpdateVelocity(p, true);
        
        double[] expectedVelocity = {1.9};
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
        double[] velocity = {5, 76.5, -2, 43, 8.65};
        double[] position = {6, 32.1, 11, 235, 103};
        p.setVelocity(velocity);
        p.setPosition(position);
        instance.setGlobalBest(new Particle(position));
        instance.UpdatePosition(p);
        
        double[] expectedVelocity = {11, 108.6, 9, 278, 111.65};
        
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
    
}
