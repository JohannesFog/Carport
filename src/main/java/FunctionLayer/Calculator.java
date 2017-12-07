package FunctionLayer;

import Exceptions.DataMapperException;

/**
 * 
 * @author Mikkel Lindstrøm
 */
public interface Calculator {
    
    //Starting point for calculating bill of materials, this method will call other calculating methods

    public BillOfMaterials bomCalculator(Order order) throws DataMapperException;
    
    public BillOfMaterials bomCalculatorSkråtTag(double length, double width, double height, double angle, double skurLength, double skurWidth) throws DataMapperException;

    public BillOfMaterials bomCalculatorFladtTag(double length, double width, double height, double skurLength, double skurWidth) throws DataMapperException;
    
    public BillOfMaterials bomCalculatorSkur(double length, double width, double height, double skurLength, double skurWidth) throws DataMapperException;
    
    //Calculates the total price of the carport based on the bill of materials
    public double calculatePrice(BillOfMaterials bom);
    
    public BillOfMaterials calculateStolper(double length, double width, double height, double skurLength, double skurWidth) throws DataMapperException;
    
    public BillOfMaterials calculateTagplader(double length, double width) throws DataMapperException;

    public BillOfMaterials calculateTagMedSten(double length, double width, double hypotenuse) throws DataMapperException;

    public BillOfMaterials calculateRemme(double length) throws DataMapperException; 
 
    public BillOfMaterials calculateFladtStern(double length, double width) throws DataMapperException;

    public BillOfMaterials calculateSkråtStern(double length) throws DataMapperException;
    
    public BillOfMaterials calculateVindskeder(double hypotenuse) throws DataMapperException;

    public BillOfMaterials calculateFladtSpær(double length, double width) throws DataMapperException;
    
    public BillOfMaterials calculateSkråtSpær(double length, double width) throws DataMapperException;
       
    public BillOfMaterials calculateHulbånd(double width) throws DataMapperException;
     
    public BillOfMaterials calculateFladtBeslag(double length)throws DataMapperException;
    
    public BillOfMaterials calculateSkråtBeslag(double length) throws DataMapperException;

    public BillOfMaterials calculateSkruerStern(double length, double width) throws DataMapperException;
    
    public BillOfMaterials calculateFladtSkruerBeslag(double length, double width) throws DataMapperException;
        
    public BillOfMaterials calculateBræddebolt(double length) throws DataMapperException;
    
    public BillOfMaterials calculateFirkantskiver(double length) throws DataMapperException;
    
    public BillOfMaterials calculateLøsholter(double width, double skurLength, double skurWidth) throws DataMapperException;

    public BillOfMaterials calculateBeklædningSkur(double height, double skurLength, double skurWidth) throws DataMapperException;

    public BillOfMaterials calculateBeklædningGavl (double width, double katete) throws DataMapperException;
    
    
    
}
