/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Functions;

/**
 *
 * @author Peter
 */
public class QHullJNI {
    static {
        System.loadLibrary(qHull);
    }
    
}
