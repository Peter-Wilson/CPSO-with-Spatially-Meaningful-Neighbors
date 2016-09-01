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
    
    @Test
    public void testCPSO_withDT(){
        System.out.println("CPSO_S");
        CPSO instance = null;
        int expectedDimensions = 6;
        int expectedMaxLoops = 10;
        int expectedSwarmSize = 20;
        double expectedInertia = 0.5;
        double expectedC1 = 0.3;
        double expectedC2 = 0.2;
        int k = 2;
        boolean DT = true;
        instance = new CPSO(expectedDimensions, expectedMaxLoops, expectedSwarmSize, expectedInertia, expectedC1, expectedC2, k, DT, 0, true);
        
        //test the values are set properly
        assertEquals(expectedDimensions, instance.dimensionSize);
        assertEquals(expectedMaxLoops, instance.maxLoops);
        assertEquals(expectedSwarmSize, instance.swarmSize);
        assertEquals(expectedInertia, instance.INERTIA, 0.0);
        assertEquals(expectedC1, instance.C1, 0.0);
        assertEquals(expectedC2, instance.C2, 0.0);        
    }
    
    @Test
    public void testCPSO_withoutDT(){
        System.out.println("CPSO_S");
        CPSO instance = null;
        int expectedDimensions = 6;
        int expectedMaxLoops = 10;
        int expectedSwarmSize = 20;
        double expectedInertia = 0.5;
        double expectedC1 = 0.3;
        double expectedC2 = 0.2;
        int k = 2;
        boolean DT = false;
        instance = new CPSO(expectedDimensions, expectedMaxLoops, expectedSwarmSize, expectedInertia, expectedC1, expectedC2, k, DT, 0, true);
        
        //test the values are set properly
        assertEquals(expectedDimensions, instance.dimensionSize);
        assertEquals(expectedMaxLoops, instance.maxLoops);
        assertEquals(expectedSwarmSize, instance.swarmSize);
        assertEquals(expectedInertia, instance.INERTIA, 0.0);
        assertEquals(expectedC1, instance.C1, 0.0);
        assertEquals(expectedC2, instance.C2, 0.0);        
    }

    /**
     * Test of InitializeSwarms method, of class CPSO.
     */
    @Test
    public void testInitializeSwarms1() {
        System.out.println("Initialize Swarms");
        int expectedDimensions = 6;
        int expectedMaxLoops = 10;
        int swarmSize = 20;
        int expectedSwarmSize = swarmSize/2;
        double expectedInertia = 0.5;
        double expectedC1 = 0.3;
        double expectedC2 = 0.2;
        int k = 2;
        CPSO instance = new CPSO(expectedDimensions, expectedMaxLoops, swarmSize, expectedInertia, expectedC1, expectedC2,k, true, 0, true);
        instance.InitializeSwarms(false);
        
        Swarm[] swarms = instance.getSwarms();
        
        //ensure it is the correct size
        assertEquals(k, swarms.length);        
        
        for(int i = 0; i < swarms.length; i++){
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
            assertNotEquals(solution[i], 0.0);
            value += solution[i];
        }
        
        //ensure the values are set
        assertNotEquals(value, 0);
    }
    
    /**
     * Test of InitializeSwarms method, of class CPSO.
     */
    @Test
    public void testInitializeSwarms2() {
        System.out.println("Initialize Swarms for size 1");
        int expectedDimensions = 6;
        int expectedMaxLoops = 10;
        int expectedSwarmSize = 20;
        double expectedInertia = 0.5;
        double expectedC1 = 0.3;
        double expectedC2 = 0.2;
        int k = 1;
        CPSO instance = new CPSO(expectedDimensions, expectedMaxLoops, expectedSwarmSize, expectedInertia, expectedC1, expectedC2,k, true, 0, true);
        instance.InitializeSwarms(false);
        
        Swarm[] swarms = instance.getSwarms();
        
        //ensure it is the correct size
        assertEquals(k, swarms.length);        
        
        for(int i = 0; i < swarms.length; i++){
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
            assertNotEquals(solution[i], 0.0);
            value += solution[i];
        }
        
        //ensure the values are set
        assertNotEquals(value, 0);
    }
    
    /**
     * Test of InitializeSwarms method, of class CPSO.
     */
    @Test
    public void testInitializeSwarms3() {
        System.out.println("Initialize Swarms for random sizes");
        int expectedDimensions = 20;
        int expectedMaxLoops = 10;
        int swarmSize = 20;
        int expectedSwarmSize = swarmSize/10;
        double expectedInertia = 0.5;
        double expectedC1 = 0.3;
        double expectedC2 = 0.2;
        int k = 10;
        CPSO instance = new CPSO(expectedDimensions, expectedMaxLoops, swarmSize, expectedInertia, expectedC1, expectedC2,k, true, 0, true);
        instance.InitializeSwarms(true);
        
        Swarm[] swarms = instance.getSwarms();
        
        //ensure it is the correct size
        assertEquals(k, swarms.length);        
        
        for(int i = 0; i < swarms.length; i++){
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
            assertNotEquals(solution[i], 0.0);
            value += solution[i];
        }
        
        //ensure the values are set
        assertNotEquals(value, 0);
    }
    
    /**
     * Test of InitializeSwarms method, of class CPSO.
     */
    @Test
    public void testInitializeSwarms4() {
        System.out.println("Initialize Swarms of uneven size");
        int expectedDimensions = 5;
        int expectedMaxLoops = 10;
        int swarmSize = 20;
        int expectedSwarmSize = swarmSize/2;
        double expectedInertia = 0.5;
        double expectedC1 = 0.3;
        double expectedC2 = 0.2;
        int k = 2;
        CPSO instance = new CPSO(expectedDimensions, expectedMaxLoops, swarmSize, expectedInertia, expectedC1, expectedC2,k, true, 0, true);
        instance.InitializeSwarms(false);
        
        Swarm[] swarms = instance.getSwarms();
        
        //ensure it is the correct size
        assertEquals(k, swarms.length);        
        
        for(int i = 0; i < swarms.length; i++){
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
            assertNotEquals(solution[i], 0.0);
            value += solution[i];
        }
        
        //ensure the values are set
        assertNotEquals(value, 0);
    }

     /**
     * Test of InitializeSwarms method, of class CPSO.
     */
    @Test
    public void testInitializeSwarms5() {
        System.out.println("Initialize Swarms of less even size");
        int expectedDimensions = 5;
        int expectedMaxLoops = 10;
        int swarmSize = 20;
        int expectedSwarmSize = swarmSize/4;
        double expectedInertia = 0.5;
        double expectedC1 = 0.3;
        double expectedC2 = 0.2;
        int k = 4;
        CPSO instance = new CPSO(expectedDimensions, expectedMaxLoops, swarmSize, expectedInertia, expectedC1, expectedC2,k, true, 0, true);
        instance.InitializeSwarms(false);
        
        Swarm[] swarms = instance.getSwarms();
        
        //ensure it is the correct size
        assertEquals(k, swarms.length);        
        
        for(int i = 0; i < swarms.length; i++){
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
            assertNotEquals(solution[i], 0.0);
            value += solution[i];
        }
        
        //ensure the values are set
        assertNotEquals(value, 0);
    }    
    
    /**
     * Test of CalculateFitness method, of class CPSO_S.
     */
    @Test
    public void testCalculateFitness() throws Exception {
        System.out.println("CalculateFitness");
        int index = 0;
        int k = 6;
        CPSO instance = new CPSO(6, 5, 20, 0.5, 0.3, 0.2, k, true, 0, true);
        instance.InitializeSwarms(false);
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
    public void testCalculateFitness2_log() throws Exception {
        System.out.println("CalculateFitness");
        int index = 0;
        double[] position = {50.0};
        int k = 6;
        CPSO instance = new CPSO(6, 5, 20, 0.5, 0.3, 0.2, k, true, 0, true);
        instance.InitializeSwarms(false);
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
    public void testCalculateFitnessZero_log() throws Exception {
        System.out.println("CalculateFitnessZero");
        int index = 0;
        double[] position = {0.0};
        int k = 6;
        CPSO instance = new CPSO(6, 5, 20, 0.5, 0.3, 0.2, k, true, 0, true);
        instance.InitializeSwarms(false);
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
    public void testCalculateFitnessNegative_log() throws Exception {
        System.out.println("CalculateFitnessNegative");
        int index = 0;
        double[] position = {-50.0};
        int k = 6;
        CPSO instance = new CPSO(6, 5, 20, 0.5, 0.3, 0.2, k, true, 0, true);
        instance.InitializeSwarms(false);
        double[] testSolution = {-12.0,-3.2,-6.2,-8.0,-14.1,-12.7};
        instance.setSolution(testSolution);
        double expResult = Double.NaN;
        double result = instance.CalculateFitness(index, position);
        assertEquals(expResult, result, 0.1);
    }
    
    /**
     * Test of CalculateFitness method, of class CPSO_S.
     */
    @Test
    public void testCalculateFitnessMulti_log() throws Exception {
        System.out.println("CalculateFitness - multidimensional");
        int index = 0;
        int k = 3;
        CPSO instance = new CPSO(6, 5, 20, 0.5, 0.3, 0.2, k, true, 0, true);
        instance.InitializeSwarms(false);
        double[] testSolution = {1.0,1.0,1.0,1.0,1.0,1.0};
        instance.setSolution(testSolution);
        double expResult = 0.0;
        double[] position = {1.0, 1.0};
        double result = instance.CalculateFitness(3, position);
        assertEquals(expResult, result, 0.0);
    }

     /**
     * Test of CalculateFitness method, of class CPSO_S.
     */
    @Test
    public void testCalculateFitnessMultiFalse_log() throws Exception {
        System.out.println("CalculateFitness - multidimensional");
        int index = 0;
        int k = 3;
        CPSO instance = new CPSO(6, 5, 20, 0.5, 0.3, 0.2, k, true, 0, true);
        instance.InitializeSwarms(false);
        double[] testSolution = {1.0,1.0,1.0,1.0,1.0,1.0};
        instance.setSolution(testSolution);
        double expResult = 0.0;
        double[] position = {1.0, 500.0};
        double result = instance.CalculateFitness(0, position);
        assertNotEquals(expResult, result, 0.0);
    }

    
    /**
     * Test of CalculateFitness method, of class CPSO_S.
     */
    @Test
    public void testCalculateFitness2Multi_log() throws Exception {
        System.out.println("CalculateFitness 2  - multidimensional");
        int index = 0;
        double[] position = {50.0, 3.2};
        int k = 3;
        CPSO instance = new CPSO(6, 5, 20, 0.5, 0.3, 0.2, k, true, 0, true);
        instance.InitializeSwarms(false);
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
    public void testCalculateFitnessZeroMulti_log() throws Exception {
        System.out.println("CalculateFitnessZero  - multidimensional");
        int index = 0;
        double[] position = {0.0, 0.0};
        int k = 3;
        CPSO instance = new CPSO(6, 5, 20, 0.5, 0.3, 0.2, k, true, 0, true);
        instance.InitializeSwarms(false);
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
    public void testCalculateFitnessNegativeMulti_log() throws Exception {
        System.out.println("CalculateFitnessNegative  - multidimensional");
        int index = 0;
        double[] position = {-50.0,-3.2};
        int k = 3;
        CPSO instance = new CPSO(6, 5, 20, 0.5, 0.3, 0.2, k, true, 0, true);
        instance.InitializeSwarms(false);
        double[] testSolution = {-12.0,-3.2,-6.2,-8.0,-14.1,-12.7};
        instance.setSolution(testSolution);
        double expResult = Double.NaN;
        double result = instance.CalculateFitness(index, position);
        assertEquals(expResult, result, 0.1);
    }

    /**
     * Test of CaluclateFinalFitness method, of class CPSO_S.
     */
    @Test
    public void testCalculateFinalFitness_log() throws Exception {
        System.out.println("Calculate Final Fitness");
        int index = 0;
        double[] position = {1.0, 1.0};
        int k = 2;
        CPSO instance = new CPSO(5, 5, 20, 0.5, 0.3, 0.2, k, true, 0, true);
        instance.InitializeSwarms(false);
        double[] testSolution = {1.0,1.0,1.0,1.0,1.0};
        instance.setSolution(testSolution);
        double expResult = 0.0;
        double result = instance.CalculateFinalFitness(instance.getSolution());
        assertEquals(expResult, result, 0.0);
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
        CPSO instance = new CPSO(5, 5, 20, 0.5, 0.3, 0.2, k, true,0, true);
        double[] testSolution = {1.0,1.0,1.0,1.0,1.0};
        instance.setSolution(testSolution);
        double expResult = 0.0;
        double result = instance.CalculateFinalFitness(instance.getSolution());
        assertEquals(expResult, result, 0.0);
    }

     /**
     * Test of getSwarms method, of class CPSO_S.
     */
    @Test
    public void testGetSwarms() {
        System.out.println("getSwarms");
        double[] position = {50.0, 1.0};
        int k = 2;
        CPSO instance = new CPSO(6, 5, 20, 0.5, 0.3, 0.2, k, true, 0, true);
        instance.InitializeSwarms(false);
        int expResult = k;
        Swarm[] result = instance.getSwarms();
        assertEquals(expResult, result.length);
        
        for(int i = 0; i < expResult; i++)
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
        CPSO instance = new CPSO(6, 5, 20, 0.5, 0.3, 0.2, k, true, 0, true);
        instance.InitializeSwarms(false);
        
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
        CPSO instance = new CPSO(5, 5, 20, 0.5, 0.3, 0.2, k, true, 0, true);
        instance.InitializeSwarms(false);
        
        // test setting a correct size startSolution
        instance.setSolution(solution);
        double[] result = instance.getSolution();
        for(int i = 0; i < result.length; i++)
        {
            assertEquals(solution[i], result[i], 0.0);
        }
        
        //test setting an incorrect size startSolution
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
     * Test of UpdatePersonalBest method, of class Particle for when the values are not set
     */
    @Test
    public void testUpdateBestsA() {
        System.out.println("Update Personal Best for values not yet set");
        double[] start_position = { 2.0, 1.4 };
        double[] new_position = { 1.0, 1.0 };
        int k = 1;
        
        CPSO instance = new CPSO(2, 5, 20, 0.5, 0.3, 0.2, k, true, 0, true);
        instance.InitializeSwarms(false);
        
        instance.getSwarms()[0].getParticles()[0].setPosition(new_position);
        
        instance.UpdateBests(instance.getSwarms()[0].getParticles()[0], 0);
        assertArrayEquals(instance.getSwarms()[0].getParticles()[0].getpBest(), new_position, 0.0);
    }
    
    /**
     * Test of UpdatePersonalBest method, of class Particle for when the values are min
     */
    @Test
    public void testUpdateBestsB() {
        System.out.println("Update Personal Best for min values");
        
        double[] bad_position = { 2.0, 1.4 };
        double[] good_position = { 1.0, 1.0 };
        CPSO instance = new CPSO(2, 5, 20, 0.5, 0.3, 0.2, 1, true, 0, true);        
        instance.InitializeSwarms(false);
        
        //test when it shouldn't update
        instance.getSwarms()[0].getParticles()[0].setpBest(good_position);
        instance.getSwarms()[0].getParticles()[0].setPosition(bad_position);
        
        instance.UpdateBests(instance.getSwarms()[0].getParticles()[0], 0);
        assertArrayEquals(instance.getSwarms()[0].getParticles()[0].getpBest(), good_position, 0.0);
        
        //test when it should update
        instance.getSwarms()[0].getParticles()[0].setpBest(bad_position);
        instance.getSwarms()[0].getParticles()[0].setPosition(good_position);
        
        instance.UpdateBests(instance.getSwarms()[0].getParticles()[0], 0);
        assertArrayEquals(instance.getSwarms()[0].getParticles()[0].getpBest(), good_position, 0.0);
    }
    
    /**
     * Test of UpdatePersonalBest method, for when the values are max.
     */
    @Test
    public void testUpdateBestsC() {
        System.out.println("Update Personal Best for max values");
        
        double[] bad_position = { 1.0, 1.0 };
        double[] good_position = { 2.0, 1.4 };
        CPSO instance = new CPSO(2, 5, 20, 0.5, 0.3, 0.2, 1, false, 0, false);        
        instance.InitializeSwarms(false);
        
        //test when it shouldn't update
        instance.getSwarms()[0].getParticles()[0].setpBest(good_position);
        instance.getSwarms()[0].getParticles()[0].setPosition(bad_position);
        
        instance.UpdateBests(instance.getSwarms()[0].getParticles()[0], 0);
        assertArrayEquals(instance.getSwarms()[0].getParticles()[0].getpBest(), good_position, 0.0);
        
        //test when it should update
        instance.getSwarms()[0].getParticles()[0].setpBest(bad_position);
        instance.getSwarms()[0].getParticles()[0].setPosition(good_position);
        
        instance.UpdateBests(instance.getSwarms()[0].getParticles()[0], 0);
        assertArrayEquals(instance.getSwarms()[0].getParticles()[0].getpBest(), good_position, 0.0);
    }
    
    /**
     * Tests to see if it updates the initial solution correctly
     */
    @Test
    public void testUpdateSoltuion()
    {
        CPSO instance = new CPSO(6, 5, 20, 0.5, 0.3, 0.2, 3, false, 0, false);       
        instance.InitializeSwarms(false);
        
        double[] positionA = {1.0,1.0};
        double[] positionB = {1.0,1.0};
        double[] positionC = {1.0,1.0};
        double[] expectedSolution = {1.0,1.0,1.0,1.0,1.0,1.0};
        
        //Set the global bests
        instance.swarms[0].getParticles()[0].setpBest(positionA);
        instance.swarms[0].setGlobalBest(instance.swarms[0].getParticles()[0]);
        
        instance.swarms[1].getParticles()[0].setpBest(positionB);
        instance.swarms[1].setGlobalBest(instance.swarms[1].getParticles()[0]);
        
        instance.swarms[2].getParticles()[0].setpBest(positionC);
        instance.swarms[2].setGlobalBest(instance.swarms[2].getParticles()[0]);
        
        //run update solution
        instance.UpdateSolution();
        
        assertArrayEquals(expectedSolution, instance.startSolution, 0.0);
    }


    
    
    
    
}
