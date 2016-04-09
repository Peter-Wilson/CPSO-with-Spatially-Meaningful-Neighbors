/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso;

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
        Swarm instance = new Swarm(20, 0.1, 0.1, 0.1, true, 1);
        for(Particle i : instance.getParticles())
        {
            assertNotNull(i);
        }
    }
    
    /**
     * Test for Updating the Velocity.
     */
    @Test
    public void testUpdateVelocity() {
        System.out.println("Velocity update");
        //calculate a test velocity
        //calculate a test velocity
        Swarm instance = new Swarm(20, 0.2, 0.3, 0.5, true, 1);
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
        Swarm instance = new Swarm(20, 0.2, 0.3, 0.5, true, 5);
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
        Swarm instance = new Swarm(expSize, 0.5, 0.2, 0.3, true, 5);
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
     * Test of UpdateVelocity method, of class Swarm.
     */
    @Test
    public void testUpdateVelocity_Particle() {
        System.out.println("UpdateVelocity");
        Particle p = null;
        Swarm instance = null;
        instance.UpdateVelocity(p);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of UpdateVelocity method, of class Swarm.
     */
    @Test
    public void testUpdateVelocity_Particle_boolean() {
        System.out.println("UpdateVelocity");
        Particle p = null;
        boolean test = false;
        Swarm instance = null;
        instance.UpdateVelocity(p, test);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGlobalBest method, of class Swarm.
     */
    @Test
    public void testGetGlobalBest() {
        System.out.println("getGlobalBest");
        Swarm instance = null;
        Particle expResult = null;
        Particle result = instance.getGlobalBest();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setGlobalBest method, of class Swarm.
     */
    @Test
    public void testSetGlobalBest() {
        System.out.println("setGlobalBest");
        Particle globalBest = null;
        Swarm instance = null;
        instance.setGlobalBest(globalBest);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRandomParticle method, of class Swarm.
     */
    @Test
    public void testSetRandomParticle() {
        System.out.println("setRandomParticle");
        double[] position = null;
        double[] velocity = null;
        Swarm instance = null;
        boolean expResult = false;
        boolean result = instance.setRandomParticle(position, velocity);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
