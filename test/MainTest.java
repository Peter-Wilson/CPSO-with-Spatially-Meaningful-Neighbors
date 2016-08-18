/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import cpso_h_k.CPSO_H_k;
import cpso_r_k.CPSO_R_k;
import cpso_s.CPSO_S;
import cpso_s_k.CPSO_S_k;
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
public class MainTest {
    
    public MainTest() {
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
     * Test of main method, of class Main.
     */
    @Test
    public void testCPSOHK() {
        System.out.println("testing: Hk variant");
        int dimensionSize = 6;
        CPSO_H_k cpso = new CPSO_H_k(dimensionSize, 200, 20, 0.5, 0.3, 0.2,2,false, 0, true);
        //cpso.start();
    }
    
    /**
     * Test of main method, of class Main.
     */
    @Test
    public void testCPSORK() {
        System.out.println("testing: Rk variant");
        int dimensionSize = 6;
        CPSO_R_k cpso = new CPSO_R_k(dimensionSize, 200, 20, 0.5, 0.3, 0.2,2,false, 0, true);
        //cpso.start();
    }
    
     /**
     * Test of main method, of class Main.
     */
    @Test
    public void testCPSOS() {
        System.out.println("testing: S variant");
        int dimensionSize = 5;
        CPSO_S cpso = new CPSO_S(dimensionSize, 200, 20, 0.5, 0.3, 0.2, false, 0, true);
        //cpso.start();
    }
    
    /**
     * Test of main method, of class Main.
     */
    @Test
    public void testCPSOSK() {
        System.out.println("testing: Sk variant");
        int dimensionSize = 6;
        CPSO_S_k cpso = new CPSO_S_k(dimensionSize, 200, 20, 0.5, 0.3, 0.2,3, false, 0, true);
        //cpso.start();
    }
    
}
