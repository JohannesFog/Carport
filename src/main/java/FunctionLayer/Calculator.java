package FunctionLayer;

/**
 * 
 * @author Mikkel Lindstrøm
 */
public interface Calculator {
    
    //Starting point for calculating bill of materials, this method will call other calculating methods
    public BillOfMaterials bomCalculator(Order order);
    
    public BillOfMaterials bomCalculatorSkråtTag(double length, double width, double height, double angle, double skurLength, double skurWidth);
    
    public BillOfMaterials bomCalculatorFladtTag(double length, double width, double height, double skurLength, double skurWidth);
    
    public BillOfMaterials bomCalculatorSkur(double length, double width, double height, double skurLength, double skurWidth);
    
    //Calculates the total price of the carport based on the bill of materials
    public double calculatePrice(BillOfMaterials bom);
    
    public BillOfMaterials calculateStolper(double length, double width, double height, double skurLength, double skurWidth);
    
    public BillOfMaterials calculateTagplader(double length, double width);

    public BillOfMaterials calculateTagMedSten(double length, double width, double hypotenuse);
    
    public BillOfMaterials calculateRemme(double length); 
 
    public BillOfMaterials calculateFladtStern(double length, double width);

    public BillOfMaterials calculateSkråtStern(double length);
    
    public BillOfMaterials calculateVindskeder(double hypotenuse);

    public BillOfMaterials calculateFladtSpær(double length, double width);
    
    public BillOfMaterials calculateSkråtSpær(double length, double width);
       
    public BillOfMaterials calculateHulbånd(double width);
     
    public BillOfMaterials calculateFladtBeslag(double length);
    
    public BillOfMaterials calculateSkråtBeslag(double length);

    public BillOfMaterials calculateSkruerStern(double length, double width);
    
    public BillOfMaterials calculateFladtSkruerBeslag(double length, double width);
        
    public BillOfMaterials calculateBræddebolt(double length);
    
    public BillOfMaterials calculateFirkantskiver(double length);
    
    public BillOfMaterials calculateLøsholter(double width, double skurLength, double skurWidth);

    public BillOfMaterials calculateBeklædningSkur(double height, double skurLength, double skurWidth);

    public BillOfMaterials calculateBeklædningGavl (double width, double katete);
    
    
    
}
