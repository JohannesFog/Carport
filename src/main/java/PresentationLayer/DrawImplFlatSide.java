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
    
    private BillOfMaterials bom;
    private double carportWidth;
    private double carportLength;
    private double carportHeight;
    
    private double oversternHeight;
    private double understernHeight;
    
    
    public DrawImplFlatSide(BillOfMaterials bom, double carportWidth, double carportLength, double carportHeight) {
        BillOfMaterials localBom = new BillOfMaterials();
        localBom.mergeBom(bom);
        this.bom = localBom;
        this.carportWidth = carportWidth;
        this.carportLength = carportLength;
        this.carportHeight = carportHeight;
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
                
        
        output = output + overSternSide();
        output = output + underSternSide();
        output = output + stolper();
        
        output = output + "</SVG>";
        
        return output;
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
        
        
        return output2;
    }
    
    @Override
    public String remme() {
        return "";
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
        
        
        int antalBrædder = relevantItems.get(0).getQuantity(); // antal brædder (normalt 5)
        double tomLuft = this.carportLength - (antalBrædder * Integer.parseInt(VenstreLength));
        String AfstandMellemBraet = Double.toString(  tomLuft/((double) (antalBrædder-1)));
        
        String output = "";
        for (int i = 0; i < antalBrædder; i++) {
            double distToTop = 0; //////////////DIST TO Top SKAL RETTES !!
            //VenstreSpærDistToTop
            VenstreLeft = Double.toString( 
                                (i*(Double.parseDouble(AfstandMellemBraet)+Double.parseDouble(VenstreLength))));
            output += String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                            "style=\"stroke:#000000; fill: #ff0000\"/>", 
                            VenstreLeft, VenstreDistToTop
                            , VenstreHeight, VenstreLength);//VenstreSpærWidth
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
        DrawImplFlatSide draw = new DrawImplFlatSide(bom, 240, 240, 240);
        
        
        System.out.println("****************  result: bom liste:");
        System.out.println("overstern");
        System.out.println(draw.overSternSide());
        System.out.println(draw.stolper());
        System.out.println("Understern");
        System.out.println(draw.underSternSide());
        
        //output = output + overSternSide();
        //output = output + underSternSide();
        //output = output + stolper();
        
        System.out.println("end");
    }
    
}
