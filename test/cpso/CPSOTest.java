/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso;

import cpso.Classes.Swarm;
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
public class CPSOTest {
    
    public CPSOTest() {
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
     * Test of PSO method, of class CPSO.
     */
    /*
    @Test
    public void testPSO() {
        System.out.println("PSO");
        CPSO instance = new CPSO();
        instance.PSO();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
    
    /**
     * Test of CPSO constructor.
     */
    @Test
    public void testCPSO() {
        System.out.println("CPSO constructor");
        int expectedListSize = 5;
        CPSO instance = new CPSO(expectedListSize);
        
        for(Swarm s : instance.getSwarms())
        {
            assertNotNull(s);
        }
        
        assertEquals(instance.getSwarms().length, expectedListSize);
    }
    
    
    /**
     * Test of PSO method, of class CPSO.
     */
    @Test
    public void testCalculateFitness() {
        System.out.println("Calculate Fitness");
        CPSO instance = new CPSO(1);
        double position = 2.34;
        double expectedFitness = -2.4756;
        double result = instance.CalculateFitness(position);
        
        
        //ensure the actual fitness is the same
        assertEquals(result, expectedFitness, 0.1);  
    }
    
}
