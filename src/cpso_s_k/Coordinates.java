/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso_s_k;

import cpso_s.Coordinates;
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
public class CoordinatesTest {
    
    public CoordinatesTest() {
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
     * Test of getX method, of class Coordinates.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        Coordinates instance = new Coordinates(0.1, 0.5);
        double expResult = 0.1;
        double result = instance.getX();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setX method, of class Coordinates.
     */
    @Test
    public void testSetX() {
        System.out.println("setX");
        double x = 0.0;
        Coordinates instance = new Coordinates(0.1, 0.5);
        instance.setX(x);
        assertEquals(instance.getX(), x, 0.0);
    }

    /**
     * Test of getY method, of class Coordinates.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        Coordinates instance = new Coordinates(0.1, 0.5);
        double expResult = 0.5;
        double result = instance.getY();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setY method, of class Coordinates.
     */
    @Test
    public void testSetY() {
        System.out.println("setY");
        double y = 0.0;
        Coordinates instance = new Coordinates(0.1, 0.5);
        instance.setY(y);
        assertEquals(y, instance.getY(), 0.0);
    }
    
}
