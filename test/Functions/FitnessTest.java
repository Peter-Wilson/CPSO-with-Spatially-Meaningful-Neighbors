/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Functions;

import cpso.CPSO;
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
public class FitnessTest {
    
    public FitnessTest() {
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
     * Test of Schaffer method, of class CPSO.
     */
    @Test
    public void testSchaffer() {
        System.out.println("Schaffer");
        double[] solution = {0,0};
        double expectedValue = 0;
        double actualValue = Fitness.Schaffer(solution);
        assertEquals(expectedValue, actualValue, 0.0);
    }

    /**
     * Test of Rastrigin method, of class CPSO.
     */
    @Test
    public void testRastrigin() {
        System.out.println("Rastrigin");
        double[] solution = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        double expectedValue = 0;
        double actualValue = Fitness.Rastrigin(solution,15);
        assertEquals(expectedValue, actualValue, 0.0);
    }
    
    /**
     * Test of Rosenbrock method, of class CPSO.
     */
    @Test
    public void testRosenbrock() {
        System.out.println("Rosenbrock");
        double[] solution = {1,1,1,1,1,1,1};
        double expectedValue = 0;
        double actualValue = Fitness.Rosenbrock(solution,7);
        assertEquals(expectedValue, actualValue, 0.0);
    }
    
    /**
     * Test of Griewanck method, of class CPSO.
     */
    @Test
    public void testGriewanck() {
        System.out.println("Griewanck");
        double[] solution = {0,0,0,0,0,0,0,0,0,0};
        double expectedValue = 0;
        double actualValue = Fitness.Griewanck(solution,10);
        assertEquals(expectedValue, actualValue, 0.0);
    }

    /**
     * Test of Ackley method, of class CPSO.
     */
    @Test
    public void testAckley() {
        System.out.println("Ackley");
        double[] solution = {0,0,0,0,0,0,0,0,0,0};
        double expectedValue = 0;
        double actualValue = Fitness.Ackley(solution, 10);
        assertEquals(expectedValue, actualValue, 0.001);
    }
    
}
