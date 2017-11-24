/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import FunctionLayer.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author Christian
 */
public class DrawImplFlatAbove implements Draw{

    private BillOfMaterials bom;
    private double carportWidth;
    private double carportLength;
    private double topLeftStolpeØverstHøjre_X = 0;
    private double topLeftStolpeØverstHøjre_Y = 0;
    private double topLeftStolpeNederstVenstre_X = 0;
    private double topLeftStolpeNederstVenstre_Y = 0;
    private double topRightStolpeØverstVenstre_X = 0;
    private double topRightStolpeØverstVenstre_Y = 0;
    private double topRightStolpeNederstHøjre_X = 0;
    private double topRightStolpeNederstHøjre_Y = 0;
    
    private double bottomLeftStolpeØverstVenstre_X = 0;
    private double bottomLeftStolpeØverstVenstre_Y = 0;
    private double bottomLeftStolpeNederstHøjre_X = 0;
    private double bottomLeftStolpeNederstHøjre_Y = 0;
    private double bottomRightStolpeØverstHøjre_X = 0;
    private double bottomRightStolpeØverstHøjre_Y = 0;
    private double bottomRightStolpeNederstVenstre_X = 0;
    private double bottomRightStolpeNederstVenstre_Y = 0;
    
    public DrawImplFlatAbove(BillOfMaterials bom, double carportWidth, double carportLength) {
        BillOfMaterials localBom = new BillOfMaterials();
        localBom.mergeBom(bom);
        this.bom = localBom;
        this.carportWidth = carportWidth;
        this.carportLength = carportLength;
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
                + "style=\"stroke:#000000; fill: #ffff00\"/>", width, height, height, width);
                
        output = output + spaer();
        output = output + remme();
        output = output + stolper();
        output = output + kryds();
        
        output = output + "</SVG>";
        
        return output;
    }
    
    @Override
    public String remme() {
        ArrayList<String> words = new ArrayList<String>();
        words.add("remme");
        words.add("Remme");
        ArrayList<LineItem> relevantItems = relevantBomLines(words, this.bom);
        removeLineItemsFromBom(relevantItems);
        
        /*
        Eksempel på LineItem:
        remme()
LineItem{name=45x95mm spærtræ ubh., length=300, quantity=2, unit=stk, description=Remme i sider, sadles ned i stolper}

        */
        
        //øverste rem.
        String ØversteWidth = relevantItems.get(0).getName().substring(0, 1);//4 from description: "45x95mm spærtræ..."
        String ØversteLength = Double.toString(this.carportLength); // user input.
        String ØversteLeft = "0";
        String ØversteDistToTop = "0";
        
        String output1 = String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                            "style=\"stroke:#000000; fill: #ff0000\"/>", 
                            ØversteLeft, ØversteDistToTop, ØversteWidth, ØversteLength);
        
        //nederste rem.
        String NedersteWidth = relevantItems.get(0).getName().substring(0, 1);//4 from description: "45x95mm spærtræ..."
        String NedersteLength = Double.toString(this.carportLength); // user input.
        String NedersteLeft = "0";
        String NedersteDistToTop = Double.toString(this.carportWidth-
                                    (
                                    Integer.parseInt(NedersteWidth)))
                                    ;
        
        String output2 = String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                            "style=\"stroke:#000000; fill: #ff0000\"/>", 
                            NedersteLeft, NedersteDistToTop, NedersteWidth, NedersteLength);
        
        return output1 + output2;
        
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
        String ØversteVenstreWidth = relevantItems.get(0).getName().substring(0, 1);//9 from description: "97x97mm trykimp. stolpe,..."
        String ØversteVenstreLength = relevantItems.get(0).getName().substring(3, 4);//9 from description: "97x97mm trykimp. stolpe,..."
        String ØversteVenstreLeft = "15";
        String ØversteVenstreDistToTop = "0";
        this.topLeftStolpeNederstVenstre_X = Double.parseDouble(ØversteVenstreLeft);
        this.topLeftStolpeNederstVenstre_Y = Double.parseDouble(ØversteVenstreWidth);
        this.topLeftStolpeØverstHøjre_X = Double.parseDouble(ØversteVenstreLength) + Double.parseDouble(ØversteVenstreLeft);
        this.topLeftStolpeØverstHøjre_Y = Double.parseDouble(ØversteVenstreDistToTop);
                                        
        
        
        String output1 = String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                            "style=\"stroke:#000000; fill: #ff0000\"/>", 
                            ØversteVenstreLeft, ØversteVenstreDistToTop, ØversteVenstreWidth, ØversteVenstreLength);
        
        //øverste-højre stolpe.
        String ØversteHøjreWidth = relevantItems.get(0).getName().substring(0, 1);//9 from description: "97x97mm trykimp. stolpe,..."
        String ØversteHøjreLength = relevantItems.get(0).getName().substring(3, 4);//9 from description: "97x97mm trykimp. stolpe,..."
        String ØversteHøjreLeft = Double.toString(this.carportLength-
                                    Integer.parseInt(ØversteHøjreLength)-15);
        String ØversteHøjreDistToTop = "0";
        this.topRightStolpeØverstVenstre_X = Double.parseDouble(ØversteHøjreLeft);
        this.topRightStolpeØverstVenstre_Y = Double.parseDouble(ØversteHøjreDistToTop);
        this.topRightStolpeNederstHøjre_X = Double.parseDouble(ØversteHøjreLeft) +
                                            Double.parseDouble(ØversteHøjreLength);
        this.topRightStolpeNederstHøjre_Y = Double.parseDouble(ØversteHøjreWidth);;
        
        
        String output2 = String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                            "style=\"stroke:#000000; fill: #ff0000\"/>", 
                            ØversteHøjreLeft, ØversteHøjreDistToTop, ØversteHøjreWidth, ØversteHøjreLength);
        
        //nederste-venstre stolpe.
        String NedersteVenstreWidth = relevantItems.get(0).getName().substring(0, 1);//9 from description: "97x97mm trykimp. stolpe,..."
        String NedersteVenstreLength = relevantItems.get(0).getName().substring(3, 4);//9 from description: "97x97mm trykimp. stolpe,..."
        String NedersteVenstreLeft = "15";
        String NedersteVenstreDistToTop = Double.toString(this.carportWidth-
                                    Integer.parseInt(NedersteVenstreWidth));
        
        this.bottomLeftStolpeNederstHøjre_X = Double.parseDouble(NedersteVenstreLength)+
                                              Double.parseDouble(NedersteVenstreLeft);
        this.bottomLeftStolpeNederstHøjre_Y = Double.parseDouble(NedersteVenstreDistToTop) +
                                              Double.parseDouble(NedersteVenstreWidth);
        this.bottomLeftStolpeØverstVenstre_X = Double.parseDouble(NedersteVenstreLeft);;
        this.bottomLeftStolpeØverstVenstre_Y = Double.parseDouble(NedersteVenstreDistToTop);
        
        String output3 = String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                            "style=\"stroke:#000000; fill: #ff0000\"/>", 
                            NedersteVenstreLeft, NedersteVenstreDistToTop, NedersteVenstreWidth, NedersteVenstreLength);
        
        //nederste-højre stolpe.
        String NedersteHøjreWidth = relevantItems.get(0).getName().substring(0, 1);//9 from description: "97x97mm trykimp. stolpe,..."
        String NedersteHøjreLength = relevantItems.get(0).getName().substring(3, 4);//9 from description: "97x97mm trykimp. stolpe,..."
        String NedersteHøjreLeft = Double.toString(this.carportLength-
                                    Integer.parseInt(NedersteHøjreLength)-15);
        String NedersteHøjreDistToTop = Double.toString(this.carportWidth-
                                    Integer.parseInt(NedersteHøjreWidth));
        
        this.bottomRightStolpeNederstVenstre_X = Double.parseDouble(NedersteHøjreLeft);
        this.bottomRightStolpeNederstVenstre_Y = Double.parseDouble(NedersteHøjreDistToTop)+
                                                 Double.parseDouble(NedersteHøjreWidth);
        this.bottomRightStolpeØverstHøjre_X = Double.parseDouble(NedersteHøjreLeft)+
                                              Double.parseDouble(NedersteHøjreLength);
        this.bottomRightStolpeØverstHøjre_Y = Double.parseDouble(NedersteHøjreDistToTop);
        
        
        String output4 = String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                            "style=\"stroke:#000000; fill: #ff0000\"/>", 
                            NedersteHøjreLeft, NedersteHøjreDistToTop, NedersteHøjreWidth, NedersteHøjreLength);
        
        
        return output1+output2+output3+output4;
        
    }

    @Override
    public String spaer() {
        ArrayList<String> words = new ArrayList<String>();
        words.add("Spær");
        ArrayList<LineItem> relevantItems = relevantBomLines(words, this.bom);
        removeLineItemsFromBom(relevantItems);   
                
         /*
        Eksempel på LineItem:
        spaer()
LineItem{name=45x195mm spærtræ ubh., length=300, quantity=5, unit=stk, description=Spær monteres på rem}

        */
        
        String VenstreSpærWidth = relevantItems.get(0).getName().substring(3, 5);//19 from description: "45x195mm spærtræ ubh...."
        String VenstreSpærLength = Double.toString(this.carportWidth); //user input 
        
        double portLaengde = this.carportLength;
        String VenstreSpærLeft = "0";//Double.toString(portLaengde -this.carportWidth);
        String VenstreSpærDistToTop = "0";//Integer.toString(Integer.parseInt(VenstreSpærWidth));
        
        int antalBrædder = relevantItems.get(0).getQuantity(); // antal brædder (normalt 5)
        double tomLuft = this.carportLength - (antalBrædder * Integer.parseInt(VenstreSpærWidth));
        String AfstandMellemBraet = Double.toString(  tomLuft/((double) (antalBrædder-1)));
        
        String outputTest = "antal: "
                + antalBrædder
                + "tomluft: "
                + tomLuft
                + "AfstandMellemBraet"
                + AfstandMellemBraet; //beregn afstande
        
        String output = "";
        for (int i = 0; i < antalBrædder; i++) {
            double distToTop = 0; //////////////DIST TO Top SKAL RETTES !!
            //VenstreSpærDistToTop
            VenstreSpærLeft = Double.toString( 
                                (i*(Double.parseDouble(AfstandMellemBraet)+Double.parseDouble(VenstreSpærWidth))));
            output += String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                            "style=\"stroke:#000000; fill: #ff0000\"/>", 
                            VenstreSpærLeft, VenstreSpærDistToTop
                            , VenstreSpærLength, VenstreSpærWidth);//VenstreSpærWidth
        }
        
        return output;
       
    }

    @Override
    public String kryds() {
        ArrayList<String> words = new ArrayList<String>();
        words.add("vindkryds");
        words.add("Vindkryds");
        ArrayList<LineItem> relevantItems = relevantBomLines(words, this.bom);
        removeLineItemsFromBom(relevantItems);
        
        /*
        Eksempel på LineItem:
        kryds()
LineItem{name=Hulbånd 1x20mm 10meter, length=0, quantity=2, unit=ruller, description=Til vindkryds på spær}
        */
        
        String output = "";
        
        //kryds -- Top Left - Bottom Right --Øverst
        String x1 = Double.toString(this.topLeftStolpeØverstHøjre_X);
        String y1 = Double.toString(this.topLeftStolpeØverstHøjre_Y);
        String x2 = Double.toString(this.bottomRightStolpeØverstHøjre_X);
        String y2 = Double.toString(this.bottomRightStolpeØverstHøjre_Y);
        output += String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" "
                + "stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/>", 
                            x1, y1, x2, y2);
        
        //kryds -- Top Left - Bottom Right --Nederst
        x1 = Double.toString(this.topLeftStolpeNederstVenstre_X);
        y1 = Double.toString(this.topLeftStolpeNederstVenstre_Y);
        x2 = Double.toString(this.bottomRightStolpeNederstVenstre_X);
        y2 = Double.toString(this.bottomRightStolpeNederstVenstre_Y);
        output += String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" "
                + "stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/>", 
                            x1, y1, x2, y2);
        
        //kryds -- Bottom Left - Top Right --Øverst
        x1 = Double.toString(this.bottomLeftStolpeØverstVenstre_X);
        y1 = Double.toString(this.bottomLeftStolpeØverstVenstre_Y);
        x2 = Double.toString(this.topRightStolpeØverstVenstre_X);
        y2 = Double.toString(this.topRightStolpeØverstVenstre_Y);
        output += String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" "
                + "stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/>", 
                            x1, y1, x2, y2);
        
        //kryds -- Bottom Left - Top Right --Nederst
        x1 = Double.toString(this.bottomLeftStolpeNederstHøjre_X);
        y1 = Double.toString(this.bottomLeftStolpeNederstHøjre_Y);
        x2 = Double.toString(this.topRightStolpeNederstHøjre_X);
        y2 = Double.toString(this.topRightStolpeNederstHøjre_Y);
        output += String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" "
                + "stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/>", 
                            x1, y1, x2, y2);
        
        return output;     
    }

    
    // main klasse til test.s
    /*
    public static void main(String[] args) {
        System.out.println("start");
        Calculator calc = new CalculatorImpl();
        BillOfMaterials bom = calc.bomCalculator(240, 240, 210,"fladt", "uden");
        DrawImplFlatAbove draw = new DrawImplFlatAbove(bom, 240, 240);
        
        System.out.println("****************  første bom liste:");
        for (int k = 0; k < draw.bom.getBomList().size(); k++) {
            System.out.println(draw.bom.getBomList().get(k));
        }
        
        System.out.println("****************  result: bom liste:");
        System.out.println(draw.remme());
        System.out.println(draw.stolper());
        System.out.println(draw.spaer());
        System.out.println(draw.kryds());
        
        System.out.println("****************  anden bom liste:");
        for (int k = 0; k < draw.bom.getBomList().size(); k++) {
            System.out.println(draw.bom.getBomList().get(k));
        }
        
        System.out.println("end");
    }
    */
}
