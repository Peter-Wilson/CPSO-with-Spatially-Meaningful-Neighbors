/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

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
public class DisplayTest {
    
    public DisplayTest() {
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
     * Test of getSelectedFunction method, of class CPSO.
     */
    @Test
    public void testgetSelectedFunction() {
        System.out.println("getSelectedFunction");
        String output = "";
        Display test = new Display();
        int value = 0;
        
        //test schaffer
        test.rbSchaffer.setSelected(true);
        value = test.getSelectedFunction();
        assertEquals(value, 1);
        
        //test schaffer
        test.rbLog.setSelected(true);
        value = test.getSelectedFunction();
        assertEquals(value, 0);
        
        //test Rastrigin
        test.rbRastrigin.setSelected(true);
        value = test.getSelectedFunction();
        assertEquals(value, 2);
        
        //test Rosenbrock
        test.rbRosenbrock.setSelected(true);
        value = test.getSelectedFunction();
        assertEquals(value, 3);
        
        //test Griewanck
        test.rbGriewanck.setSelected(true);
        value = test.getSelectedFunction();
        assertEquals(value, 4);
        
        //test Ackley
        test.rbAckley.setSelected(true);
        value = test.getSelectedFunction();
        assertEquals(value, 5);
    }
    
}
