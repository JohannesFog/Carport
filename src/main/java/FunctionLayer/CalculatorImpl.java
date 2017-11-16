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
    public BillOfMaterials bomCalculator(double length, double width) {
        BillOfMaterials totalBom = new BillOfMaterials();
        
        totalBom.mergeBom(calculateStolper(length, width));
        totalBom.mergeBom(calculateTagplader(length, width));
        totalBom.mergeBom(calculateRemme(length, width));
        totalBom.mergeBom(calculateStern(length, width));
        totalBom.mergeBom(calculateSpær(length, width));
        
        return totalBom;
    }

    @Override
    public BillOfMaterials calculateStolper(double length, double width) {
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
