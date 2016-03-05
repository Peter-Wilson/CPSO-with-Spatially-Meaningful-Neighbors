/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso_s_k;

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
     * Test of UpdatePosition method, of class Swarm.
     */
    @Test
    public void testUpdatePosition() {
        System.out.println("UpdatePosition");
        Particle p = null;
        Swarm instance = null;
        instance.UpdatePosition(p);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getParticles method, of class Swarm.
     */
    @Test
    public void testGetParticles() {
        System.out.println("getParticles");
        Swarm instance = null;
        Particle[] expResult = null;
        Particle[] result = instance.getParticles();
        assertArrayEquals(expResult, result);
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
    
}
