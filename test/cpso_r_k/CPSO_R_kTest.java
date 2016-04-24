/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso_r_k;

import cpso.Swarm;
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
public class CPSO_R_kTest {
    
    public CPSO_R_kTest() {
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
    public void testCPSO_R_k() {
        System.out.println("CPSO_S");
        CPSO_R_k instance = null;
        int expectedDimensions = 6;
        int expectedMaxLoops = 10;
        int expectedSwarmSize = 20;
        double expectedInertia = 0.5;
        double expectedC1 = 0.3;
        double expectedC2 = 0.2;
        int k = 2;
        instance = new CPSO_R_k(expectedDimensions, expectedMaxLoops, expectedSwarmSize, expectedInertia, expectedC1, expectedC2, k, true);
        
        //test the values are set properly
        assertEquals(expectedDimensions, instance.dimensionSize);
        assertEquals(expectedMaxLoops, instance.maxLoops);
        assertEquals(expectedSwarmSize, instance.swarmSize);
        assertEquals(expectedInertia, instance.INERTIA, 0.0);
        assertEquals(expectedC1, instance.C1, 0.0);
        assertEquals(expectedC2, instance.C2, 0.0);
    }
    
    /**
     * Test of initialize swarms method, of class CPSO_S.
     */
    @Test
    public void testInitializeSwarms() {
        System.out.println("Initialize Swarms");
        int expectedDimensions = 6;
        int expectedMaxLoops = 10;
        int expectedSwarmSize = 20;
        double expectedInertia = 0.5;
        double expectedC1 = 0.3;
        double expectedC2 = 0.2;
        int k = 2;
        CPSO_R_k instance = new CPSO_R_k(expectedDimensions, expectedMaxLoops, expectedSwarmSize, expectedInertia, expectedC1, expectedC2,k, true);
        
        Swarm[] swarms = instance.getSwarms();
        
        //ensure it is the correct size
        assertEquals(k, swarms.length);        
        
        for(int i = 0; i < swarms.length/k; i++){
            //ensure the swarms have been initialized
            assertNotNull(swarms[i]);
            assertEquals(expectedSwarmSize, swarms[i].getParticles().length);
        }
        
        double[] solution = instance.getSolution();
        
        
        //ensure it is the correct size
        assertEquals(expectedDimensions, solution.length);  
        
        double value = 0;
        for(int i = 0; i < solution.length; i++){
            //ensure the swarms have been initialized
            value += solution[i];
        }
        
        //ensure the values are set
        assertNotEquals(value, 0);
        
        
    }

    
}