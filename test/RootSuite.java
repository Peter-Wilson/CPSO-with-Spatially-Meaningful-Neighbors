/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import GUI.GUISuite;
import cpso.CpsoSuite;
import cpso_h_k.Cpso_h_kSuite;
import cpso_r_k.Cpso_r_kSuite;
import cpso_s.Cpso_sSuite;
import cpso_s_k.Cpso_s_kSuite;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author pw12nb
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({GUISuite.class, Cpso_s_kSuite.class, Cpso_r_kSuite.class, Cpso_sSuite.class, CpsoSuite.class, Cpso_h_kSuite.class})
public class RootSuite {

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
    
}
