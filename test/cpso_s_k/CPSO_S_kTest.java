/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso_s_k;

import cpso.Swarm;
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
public class CPSO_S_kTest {
    
    public CPSO_S_kTest() {
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
     * Test of CPSO_S method, of class CPSO_S.
     */
    @Test
    public void testCPSO_S() {
        System.out.println("CPSO_S");
        CPSO_S_k instance = null;
        int expectedDimensions = 6;
        int expectedMaxLoops = 10;
        int expectedSwarmSize = 20;
        double expectedInertia = 0.5;
        double expectedC1 = 0.3;
        double expectedC2 = 0.2;
        int k = 2;
        instance = new CPSO_S_k(expectedDimensions, expectedMaxLoops, expectedSwarmSize, expectedInertia, expectedC1, expectedC2, k);
        
        //test the values are set properly
        assertEquals(expectedDimensions, instance.dimensionSize);
        assertEquals(expectedMaxLoops, instance.maxLoops);
        assertEquals(expectedSwarmSize, instance.swarmSize);
        assertEquals(expectedInertia, instance.INERTIA, 0.0);
        assertEquals(expectedC1, instance.C1, 0.0);
        assertEquals(expectedC2, instance.C2, 0.0);
    }

    /**
     * Test of start method, of class CPSO_S_k.
     */
    @Test
    public void testStart() {
        System.out.println("start");
        CPSO_S_k instance = null;
        instance.start();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
