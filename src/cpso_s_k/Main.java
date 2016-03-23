/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpso_s_k;


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
        int dimensionSize = 6;
        CPSO_S_k cpso = new CPSO_S_k(dimensionSize, 20, 200, 0.5, 0.3, 0.2,2);
        cpso.start();
    }
    
    
    
}
