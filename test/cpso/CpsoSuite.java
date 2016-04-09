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
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Peter
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({cpso.ParticleTest.class, cpso.SwarmTest.class})
public class CpsoSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    /**
     * Test of CalculateFitness method, of class CPSO_S.
     */
    @Test
    public void testCalculateFitness() throws Exception {
        System.out.println("CalculateFitness");
        int index = 0;
        int k = 6;
        CPSO instance = new CPSO(6, 5, 20, 0.5, 0.3, 0.2, k);
        double[] testSolution = {1.0,1.0,1.0,1.0,1.0,1.0};
        instance.setSolution(testSolution);
        double expResult = 0.0;
        double[] position = {1.0};
        double result = instance.CalculateFitness(3, position);
        assertEquals(expResult, result, 0.0);
    }

    
    /**
     * Test of CalculateFitness method, of class CPSO_S.
     */
    @Test
    public void testCalculateFitness2() throws Exception {
        System.out.println("CalculateFitness");
        int index = 0;
        double[] position = {50.0};
        int k = 6;
        CPSO instance = new CPSO(6, 5, 20, 0.5, 0.3, 0.2, k);
        double[] testSolution = {12.0,3.2,6.2,8.0,14.1,1.0};
        instance.setSolution(testSolution);
        double expResult = 11.6253394463;
        double result = instance.CalculateFitness(index, position);
        assertEquals(expResult, result, 0.1);
    }
    
    /**
     * Test of CalculateFitness method, of class CPSO_S.
     */
    @Test
    public void testCalculateFitnessZero() throws Exception {
        System.out.println("CalculateFitnessZero");
        int index = 0;
        double[] position = {0.0};
        int k = 6;
        CPSO instance = new CPSO(6, 5, 20, 0.5, 0.3, 0.2, k);
        double[] testSolution = {0.0,0.0,0.0,0.0,0.0,0.0};
        instance.setSolution(testSolution);
        double expResult = Double.NEGATIVE_INFINITY;
        double result = instance.CalculateFitness(index, position);
        assertEquals(expResult, result, 0.1);
    }
    
    /**
     * Test of CalculateFitness method, of class CPSO_S.
     */
    @Test
    public void testCalculateFitnessNegative() throws Exception {
        System.out.println("CalculateFitnessNegative");
        int index = 0;
        double[] position = {-50.0};
        int k = 6;
        CPSO instance = new CPSO(6, 5, 20, 0.5, 0.3, 0.2, k);
        double[] testSolution = {-12.0,-3.2,-6.2,-8.0,-14.1,-12.7};
        instance.setSolution(testSolution);
        double expResult = Double.NaN;
        double result = instance.CalculateFitness(index, position);
        assertEquals(expResult, result, 0.1);
    }
    
    /**
     * Test of getSwarms method, of class CPSO_S.
     */
    @Test
    public void testGetSwarms() {
        System.out.println("getSwarms");
        double[] position = {50.0, 1.0};
        int k = 2;
        CPSO instance = new CPSO(6, 5, 20, 0.5, 0.3, 0.2, k);
        int expResult = k;
        Swarm[] result = instance.getSwarms();
        assertEquals(expResult, result.length);
        
        for(int i = 0; i < result.length/k; i++)
        {
            assertNotNull(result[i]);
        }
    }

    /**
     * Test of getSolution method, of class CPSO_S.
     */
    @Test
    public void testGetSolution() {
        System.out.println("getSolution");
        int expectedDimensions = 6;
        double[] position = {50.0, 1.0};
        int k = 2;
        CPSO instance = new CPSO(6, 5, 20, 0.5, 0.3, 0.2, k);
        
        double[] result = instance.getSolution();
        
        //ensure it is the correct size
        assertEquals(expectedDimensions, result.length);  
        
        double value = 0;
        for(int i = 0; i < result.length; i++){
            //ensure the swarms have been initialized
            value += result[i];
        }
        
        //ensure the values are set
        assertNotEquals(value, 0);
    }

    /**
     * Test of setSolution method, of class CPSO_S.
     */
    @Test
    public void testSetSolution() throws Exception {
        System.out.println("setSolution");
        double[] solution = {1.0,2.0,3.0,4.0,5.0};
        double[] position = {50.0, 1.0};
        int k = 2;
        CPSO instance = new CPSO(5, 5, 20, 0.5, 0.3, 0.2, k);
        
        // test setting a correct size solution
        instance.setSolution(solution);
        double[] result = instance.getSolution();
        for(int i = 0; i < result.length; i++)
        {
            assertEquals(solution[i], result[i], 0.0);
        }
        
        //test setting an incorrect size solution
        try{
            double[] solution2 = {0.0};
            instance.setSolution(solution2);
            fail("This should not be allowed");
        }
        catch(Exception e)
        {
            System.out.println("incorrect solution size not alowed to be set: SUCCESS");
        }
    }

    /**
     * Test of start method, of class CPSO_S.
     */
    @Test
    public void testStart() {
        System.out.println("start");
        double[] position = {50.0, 1.0};
        int k = 2;
        CPSO instance = new CPSO(6, 5, 20, 0.5, 0.3, 0.2, k);
        //instance.start();
        //TODO need to add a test for this
    }

    /**
     * Test of CaluclateFinalFitness method, of class CPSO_S.
     */
    @Test
    public void testCalculateFinalFitness() throws Exception {
        System.out.println("Calculate Final Fitness");
        int index = 0;
        double[] position = {1.0, 1.0};
        int k = 2;
        CPSO instance = new CPSO(5, 5, 20, 0.5, 0.3, 0.2, k);
        double[] testSolution = {1.0,1.0,1.0,1.0,1.0};
        instance.setSolution(testSolution);
        double expResult = 0.0;
        double result = instance.CalculateFinalFitness();
        assertEquals(expResult, result, 0.0);
    }
    
}
