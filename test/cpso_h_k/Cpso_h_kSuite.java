/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso_h_k;

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
@Suite.SuiteClasses({cpso_h_k.CPSO_H_kTest.class, cpso_h_k.ParticleTest.class, cpso_h_k.SwarmTest.class, cpso_h_k.MainTest.class})
public class Cpso_h_kSuite {

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