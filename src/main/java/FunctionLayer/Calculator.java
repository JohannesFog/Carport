/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FunctionLayer;

/**
 *
 * @author Mikkel Lindstrøm
 */
public interface Calculator {
    
    //Starting point for calculating bill of materials, this method will call other calculating methods
    public BillOfMaterials bomCalculator(double length, double width, double height);
    
    //Calculates the total price of the carport based on the bill of materials
    public double calculatePrice(BillOfMaterials bom);
    
    public BillOfMaterials calculateStolper(double length, double width, double height);
    
    public BillOfMaterials calculateTagplader(double length, double width);
    
    public BillOfMaterials calculateRemme(double length, double width); 
    
    //understern, overstern, vandbrædder
    public BillOfMaterials calculateStern(double length, double width);
    
    public BillOfMaterials calculateSpær(double length, double width);
    
}
