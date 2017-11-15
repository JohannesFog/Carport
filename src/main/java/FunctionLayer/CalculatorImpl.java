/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FunctionLayer;

import java.util.ArrayList;

/**
 *
 * @author GertLehmann
 */
public class CalculatorImpl implements Calculator {
    
    @Override
    public double calculatePrice(BillOfMaterials bom) {
        ArrayList<LineItem> list = bom.getBomList();
        double totalPrice = 0;
        for (LineItem lineItem : list) {
            totalPrice += lineItem.getTotalPrice();
        }
        return totalPrice;
    }

    @Override
    public BillOfMaterials bomCalculator(double length, double width, double height) {
        BillOfMaterials totalBom = new BillOfMaterials();
        
        totalBom.mergeBom(calculateStolper(length, width, height));
        totalBom.mergeBom(calculateTagplader(length, width));
        totalBom.mergeBom(calculateRemme(length, width));
        totalBom.mergeBom(calculateStern(length, width));
        totalBom.mergeBom(calculateSpær(length, width));
        
        return totalBom;
    }

    @Override
    public BillOfMaterials calculateStolper(double length, double width, double height) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BillOfMaterials calculateTagplader(double length, double width) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BillOfMaterials calculateRemme(double length, double width) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BillOfMaterials calculateStern(double length, double width) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BillOfMaterials calculateSpær(double length, double width) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
