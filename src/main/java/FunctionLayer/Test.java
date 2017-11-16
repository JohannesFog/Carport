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
        
        Calculator calc = new CalculatorImpl();
        BillOfMaterials bom = calc.bomCalculator(600.0, 500.0);
        
        
        
        ArrayList<LineItem> lines = bom.getBomList();
        
        for (LineItem item : lines){
            System.out.println(item.toString());
            
        }
        
        
        
        
    }
    
}
