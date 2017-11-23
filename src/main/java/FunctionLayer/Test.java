/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FunctionLayer;

import java.util.ArrayList;

/**
 *
 * @author Mikkel Lindstrøm <Mikkel.Lindstrøm>
 */
public class Test {
    
     public static void main(String[] args) {
        
        CalculatorImpl calc = new CalculatorImpl();

        double hyp = calc.calculateHypotenuse(10.0, 25.0);
         System.out.println(hyp);
        
//        BillOfMaterials bom = calc.bomCalculator(600.0, 510.0);
//        
//        
//        
//        ArrayList<LineItem> lines = bom.getBomList();
//        
//        for (LineItem item : lines){
//            System.out.println(item.toString());
//            
//        }
//        
//        
//        
        
    }
    
}
