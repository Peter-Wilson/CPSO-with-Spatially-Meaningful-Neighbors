/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
@Suite.SuiteClasses({GUI.GUISuite.class, PSO_ParticleTest.class, cpso_s_k.Cpso_s_kSuite.class, cpso_s.Cpso_sSuite.class, cpso_h_k.Cpso_h_kSuite.class})
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
