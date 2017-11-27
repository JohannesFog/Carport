/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import FunctionLayer.BillOfMaterials;
import FunctionLayer.Calculator;
import FunctionLayer.CalculatorImpl;
import FunctionLayer.LineItem;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author Christian
 */
public class DrawImplFlatSide implements Draw{
    
    private double skurLength;
    private double skurWidth;
    private double endeUdhæng = 15;
    
    private double slope = 10.0 / 780.0;
    private BillOfMaterials bom;
    //private double //carportWidth;
    private double carportLength;
    private double carportHeight;
    
    private double oversternHeight;
    private double understernHeight;
    
    
    public DrawImplFlatSide(BillOfMaterials bom, double carportLength, double carportHeight,
                            double skurLength, double skurWidth) {
        BillOfMaterials localBom = new BillOfMaterials();
        localBom.mergeBom(bom);
        this.bom = localBom;
        //this.carportWidth = carportWidth;
        this.carportLength = carportLength;
        this.carportHeight = carportHeight;
        
        this.skurLength = skurLength;
        this.skurWidth = skurWidth;
        
    }
    
    private ArrayList<LineItem> relevantBomLines(ArrayList<String> wordsToTestFor, BillOfMaterials bom) {
        ArrayList<LineItem> output = new ArrayList<LineItem>();
        int wordNum = wordsToTestFor.size();
        int lineItemNum = bom.getBomList().size();
        
        String test;
        for (int i = 0; i < wordNum; i++) {
            for (int k = 0; k < bom.getBomList().size(); k++) {
                test = bom.getBomList().get(k).getDescription();
                if (test.contains(wordsToTestFor.get(i))) {
                    output.add(bom.getBomList().get(k));
                }
            }
        }
        
        output = removeDublicates(output);
        return output;
    }
    
        
    private ArrayList<LineItem> removeDublicates(ArrayList<LineItem> items) {
        ArrayList<LineItem> output = items;
        Set setItems = new LinkedHashSet(output);
        output.clear();
        output.addAll(setItems);
        return output;
    }
    
    private void removeLineItemsFromBom(ArrayList<LineItem> items) {
        BillOfMaterials localBom = this.bom;
        BillOfMaterials mynewBom = new BillOfMaterials();
        
        ArrayList<LineItem> localList = localBom.getBomList();
        for (int i = 0; i < items.size(); i++) {
            int itemIndex = localList.indexOf(items.get(i));
            if (itemIndex != -1) {
                localList.remove(itemIndex);
            }
        }
        mynewBom.mergeBom(localBom);
        this.bom = mynewBom;
    }
    
    @Override
    public String tegnTag(int width, int height) {
        String output = String.format("<SVG width=\"%s\" height=%s> "
                + "<rect x=\"000\" y=\"00\" height=\"%s\" width=\"%s\"\n "
                + "style=\"stroke:#000000; fill: #07beb8\"/>", width, height, height, width);
                
        output = output + floor();
        
        output = output + overSternSide();
        output = output + underSternSide();
        if (this.skurLength > 0 && this.skurWidth > 0) {
            //output = output + "<p>"+skurLength + " " +skurWidth+"</p>";
            output = output + skur();
        }
        output = output + stolper();
        //output = output + rectangleTiltDownStraightSides("0", "0", "30", "700", slope);
        
        
        
        output = output + "</SVG>";
        return output;
    }

    /*
        double width = 50;
        double slope = 0.012820513;
        double height_decrease = (width/100)*slope;
    */
    public String rectangleTiltDownStraightSides(String topLeft_DistToLeft, 
            String topLeft_DistToTop, 
            String height,
            String width, 
            double slope) // eg (d)10 / (d)780 = 0,012820513
            {
        
        
        double topRight_x = Double.parseDouble(topLeft_DistToLeft)+Double.parseDouble(width);
        double topRight_y = Double.parseDouble(topLeft_DistToTop)+
                            (Double.parseDouble(width)*slope);
        double downRight_x = topRight_x;
        double downRight_y = topRight_y + Double.parseDouble(height);
        
        double downLeft_x = Double.parseDouble(topLeft_DistToLeft);
        double downLeft_y = Double.parseDouble(topLeft_DistToTop) + Double.parseDouble(height);
        
        String mystr = String.format(
        "<polygon points=\"%s,%s %s,%s %s,%s %s,%s\" style=\"stroke:#000000; fill: #ff0000\" />", 
                topLeft_DistToLeft, topLeft_DistToTop, topRight_x, topRight_y, 
                downRight_x, downRight_y, downLeft_x, downLeft_y);
        
        return mystr;
    }
    
    public String overSternSide() {
        ArrayList<String> words = new ArrayList<String>();
        words.add("Overstern brædder til siderne");
        ArrayList<LineItem> relevantItems = relevantBomLines(words, this.bom);
        removeLineItemsFromBom(relevantItems);
        
        this.oversternHeight = Double.parseDouble(relevantItems.get(0).getName().substring(3, 5));
        String ØversteHeight = Double.toString(this.oversternHeight);
        String ØversteLength = Double.toString(this.carportLength); // user input.
        String ØversteLeft = "0";
        String ØversteDistToTop = "0";
        
        String output1 = String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                            "style=\"stroke:#000000; fill: #ff0000\"/>", 
                            ØversteLeft, ØversteDistToTop, ØversteHeight, ØversteLength);
        //String rectangleTiltDownStraightSides(String topLeft_DistToLeft, 
        //    String topLeft_DistToTop, 
        //    String height,
        //    String width, 
        //    double slope) // eg (d)10 / (d)780 = 0,012820513
        //    {
        output1 = rectangleTiltDownStraightSides(ØversteLeft, ØversteDistToTop, ØversteHeight, ØversteLength, slope);
        
        return output1;
    }
    
    public String underSternSide() {
        ArrayList<String> words = new ArrayList<String>();
        words.add("Understern brædder til siderne");
        ArrayList<LineItem> relevantItems = relevantBomLines(words, this.bom);
        removeLineItemsFromBom(relevantItems);
        
        this.understernHeight = Double.parseDouble(relevantItems.get(0).getName().substring(3, 5));
        String NedersteHeight = Double.toString(this.understernHeight);
        String NedersteLength = Double.toString(this.carportLength); // user input.
        String NedersteLeft = "0";
        String NedersteDistToTop = Double.toString(this.oversternHeight);
        
        
        String output2 = String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                            "style=\"stroke:#000000; fill: #ff0000\"/>", 
                            NedersteLeft, NedersteDistToTop, NedersteHeight, NedersteLength);
        output2 = rectangleTiltDownStraightSides(NedersteLeft, NedersteDistToTop, NedersteHeight, NedersteLength, slope);
        
        
        return output2;
    }
    
    @Override
    public String remme() {
        return "";
    }
    
    public String floor() {
        String output2 = String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                            "style=\"stroke:#000000; fill: #ff0000\"/>", 
                            "0", this.carportHeight, "2", this.carportLength);
        //output2 = rectangleTiltDownStraightSides(NedersteLeft, NedersteDistToTop, NedersteHeight, NedersteLength, slope);
        
        
        return output2;
    }
    
    @Override
    public String stolper() {
        ArrayList<String> words = new ArrayList<String>();
        words.add("Stolper");
        ArrayList<LineItem> relevantItems = relevantBomLines(words, this.bom);
        removeLineItemsFromBom(relevantItems);
        
        /*
        Eksempel på LineItem:
        stolper()
LineItem{name=97x97mm trykimp. stolpe, length=300, quantity=4, unit=stk, description=Stolper nedgraves 90cm i jord.}
        */
        
        // alle afstande regnes i centimeter
        
        //øverste-venstre stolpe.
        String VenstreLeft = "0";
        String VenstreDistToTop = Double.toString(this.oversternHeight);
        String VenstreLength = relevantItems.get(0).getName().substring(3, 4);//9 from description: "97x97mm trykimp. stolpe,..."
        String VenstreHeight = Double.toString(this.carportHeight - 
                                Double.parseDouble(VenstreDistToTop)) ; 
        
        
        int antalBrædder = relevantItems.get(0).getQuantity()/2; // antal brædder (normalt 5)
        double tomLuft = this.carportLength - (antalBrædder * Integer.parseInt(VenstreLength)) - 30;
        String AfstandMellemBraet = Double.toString(  tomLuft/((double) (antalBrædder-1)));
        
        String output = "";
        for (int i = 0; i < antalBrædder; i++) {
            double distToTop = 0; //////////////DIST TO Top SKAL RETTES !!
            //VenstreSpærDistToTop
            VenstreLeft = Double.toString( 15+
                                (i*(Double.parseDouble(AfstandMellemBraet)+Double.parseDouble(VenstreLength))));
            
            String VenstreDistToTopNew = Double.toString(Double.parseDouble(VenstreDistToTop)+
                    (Double.parseDouble(VenstreLeft)*slope));
            
            String VenstreHeightNew = Double.toString(Double.parseDouble(VenstreHeight) - 
                    (Double.parseDouble(VenstreLeft)*slope));
                    
            output += String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                            "style=\"stroke:#000000; fill: #ff0000\"/>", 
                            VenstreLeft, VenstreDistToTopNew
                            , VenstreHeightNew, VenstreLength);//VenstreSpærWidth
        }
        
        return output;
        
        /*
        String output1 = String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                            "style=\"stroke:#000000; fill: #ff0000\"/>", 
                            ØversteVenstreLeft, ØversteVenstreDistToTop, ØversteVenstreWidth, ØversteVenstreLength);
        */
        
    }

    @Override
    public String spaer() {
        return "";
    }

    @Override
    public String kryds() {
        return "";
    }
    
    public static void main(String[] args) {
        System.out.println("start");
        Calculator calc = new CalculatorImpl();
        BillOfMaterials bom = calc.bomCalculator(240, 240, 210,"fladt", 0, 0);
        DrawImplFlatSide draw = new DrawImplFlatSide(bom, 240, 240, 50, 50);
        
        
        System.out.println("****************  result: bom liste:");
        System.out.println("overstern");
        System.out.println(draw.overSternSide());
        System.out.println(draw.stolper());
        System.out.println("Understern");
        System.out.println(draw.underSternSide());
        System.out.println("*********************************************");
        System.out.println("her er skur:");
        System.out.println(draw.skur());
        System.out.println("her slutter skur");
        
        double width = 50;
        double slope = 0.012820513;
        double height_decrease = (width/100)*slope;
        System.out.println(height_decrease);
        
        //output = output + overSternSide();
        //output = output + underSternSide();
        //output = output + stolper();
        
        System.out.println("end");
    }
    
    //******************************************************************
    // ******************************************************************
    //              Sepperat afsnit til skurmetoder.
    //********************************************************************
    //*******************************************************************
    
    public String skur() {
        double Localskurlength = this.skurLength;
        double LocalcarportLength = this.carportLength;
        
        //Man kan ikke have et skur der er længere end Carporten  - 2 * udhæng på enderne
        if (this.skurLength > (carportLength - this.endeUdhæng - this.endeUdhæng)) {
            Localskurlength = carportLength - this.endeUdhæng - this.endeUdhæng;
        } else {
            Localskurlength = this.skurLength;
        }
        
        double skurTopRight_x = carportLength-this.endeUdhæng;
        double skurTopRight_y = (skurTopRight_x*this.slope)+this.oversternHeight; //wrong this.carportHeight-(skurTopRight_x*this.slope);
        
        double skurBottomRight_x = skurTopRight_x;
        double skurBottomRight_y = this.carportHeight;
        
        double skurTopLeft_x = carportLength-this.endeUdhæng - Localskurlength;
        double skurTopLeft_y = (skurTopLeft_x*this.slope)+this.oversternHeight; //wrong this.carportHeight-(skurTopLeft_x*this.slope); 
        
        double skurBottomleft_x = skurTopLeft_x;
        double skurBottomleft_y = this.carportHeight;
        
        
        String output = String.format(
        "<polygon points=\"%s,%s %s,%s %s,%s %s,%s\" style=\"stroke:#000000; fill: #099a0f\" />", 
                skurTopLeft_x, 
                skurTopLeft_y, //wrong
                skurTopRight_x, 
                skurTopRight_y, //wrong
                skurBottomRight_x, 
                skurBottomRight_y, 
                skurBottomleft_x, 
                skurBottomleft_y);
        
        
        return output;  //"<p>"+skurLength + " " +skurWidth+"</p>";
    }
}
