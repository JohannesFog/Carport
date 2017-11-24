/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FunctionLayer;

import PresentationLayer.Rendering;
import java.util.ArrayList;

/**
 *
 * @author Mikkel Lindstrøm <Mikkel.Lindstrøm>
 */
public class Test {
    
     public static void main(String[] args) {


         
       CalculatorImpl calc = new CalculatorImpl();


        double hyp = calc.calculateHypotenuse(720.0, 45.0);
        int hypInt = (int) Math.ceil(hyp);
        
        int testdata = (int) Math.ceil(100.0 / 28.5);

        double kat = calc.calculateKatete(240.0, 15.0);
        int katInt = (int) Math.ceil(kat);

        
        System.out.println(hyp + "-" + hypInt);
        
     
        
    }
    
}
