/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso;

import cpso.Classes.Particle;

/**
 *
 * @author Peter
 */
public class Main {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int dimensionSize = 5;
        CPSO_S cpso = new CPSO_S(dimensionSize, 1, 20);
        cpso.start();
    }
    
    
    
}
