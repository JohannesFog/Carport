/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import java.util.regex.*;  
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
    private double sideUdhæng = 15;
    private double endeUdhæng = 15;
    
    private double slope = 10.0 / 780.0;
    private BillOfMaterials bom;
    private double carportWidth;
    private double carportLength;
    private double carportHeight;
    
    private double oversternHeight;
    private double understernHeight;
    
    
    public DrawImplFlatSide(BillOfMaterials bom, double carportWidth, double carportLength, double carportHeight,
                            double skurLength, double skurWidth) {
        this.carportWidth = carportWidth;
        
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
        return "";
    }
    
    @Override
    public String tegnTag(int width, int height, String drawFlatAbove) {
        String output = String.format("<SVG width=\"%s\" height=%s> "
                + "<rect x=\"000\" y=\"00\" height=\"%s\" width=\"%s\"\n "
                + "style=\"stroke:#000000; fill: #07beb8\"/>", width, height, height, width);
                
        output = output + floor();
        
        output = output + stolper2Foroven();
        
        output = output + overSternSide();
        output = output + underSternSide();
        if (this.skurLength > 0 && this.skurWidth > 0) {
            //output = output + "<p>"+skurLength + " " +skurWidth+"</p>";
            output = output + skur();
        }
        output = output + stolper2Forneden(drawFlatAbove);
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
    
    /*
        Eksempel på LineItem:
        stolper()
LineItem{name=97x97mm trykimp. stolpe, length=300, quantity=4, unit=stk, description=Stolper nedgraves 90cm i jord.}
        */
    
    public String stolper2Foroven() {
        return "";
    }
    
    public String stolper2Forneden(String DrawFlatAbove) {
        String output = "";
        
        ArrayList<String> words = new ArrayList<String>();
        words.add("Stolper");
        ArrayList<LineItem> relevantItems = relevantBomLines(words, this.bom);
        String ØversteVenstreLength = relevantItems.get(0).getName().substring(3, 4);//9 from description: "97x97mm trykimp. stolpe,..."
        double stolpelength = Double.parseDouble(ØversteVenstreLength);
        
        
        String port720720210210 = "<SVG width=\"750\" height=750> <rect x=\"000\" y=\"00\" height=\"750\" width=\"750\"\n" +
                    " style=\"stroke:#000000; fill: #ffff00\"/>"
                + "<rect x=\"0\" y=\"15.0\" height=\"4.5\" width=\"720.0\"style=\"stroke:#000000; fill: #ff0000\"/>"
                + "<rect x=\"0\" y=\"700.5\" height=\"4.5\" width=\"720.0\"style=\"stroke:#000000; fill: #ff0000\"/>"
                + "<polygon points=\"495.0,495.0 705.0,495.0 705.0,705.0 495.0,705.0\" style=\"stroke:#000000; fill: #099a0f\" />"
                + "<rect x=\"495.0\" y=\"495.0\" height=\"9\" width=\"9\"style=\"stroke:#000000; fill: #ff0000\"/>"
                + "<rect x=\"696.0\" y=\"495.0\" height=\"9\" width=\"9\"style=\"stroke:#000000; fill: #ff0000\"/>"
                + "<rect x=\"495.0\" y=\"696.0\" height=\"9\" width=\"9\"style=\"stroke:#000000; fill: #ff0000\"/>"
                + "<rect x=\"696.0\" y=\"595.5\" height=\"9\" width=\"9\"style=\"stroke:#000000; fill: #ff0000\"/>"
                + "<rect x=\"595.5\" y=\"495.0\" height=\"9\" width=\"9\"style=\"stroke:#000000; fill: #ff0000\"/>"
                + "<rect x=\"0.0\" y=\"0\" height=\"720.0\" width=\"4.5\"style=\"stroke:#000000; fill: #ff0000\"/>"
                + "<rect x=\"55.03846153846154\" y=\"0\" height=\"720.0\" width=\"4.5\"style=\"stroke:#000000; fill: #ff0000\"/>"
                + "<rect x=\"110.07692307692308\" y=\"0\" height=\"720.0\" width=\"4.5\"style=\"stroke:#000000; fill: #ff0000\"/>"
                + "<rect x=\"165.1153846153846\" y=\"0\" height=\"720.0\" width=\"4.5\"style=\"stroke:#000000; fill: #ff0000\"/>"
                + "<rect x=\"220.15384615384616\" y=\"0\" height=\"720.0\" width=\"4.5\"style=\"stroke:#000000; fill: #ff0000\"/>"
                + "<rect x=\"275.1923076923077\" y=\"0\" height=\"720.0\" width=\"4.5\"style=\"stroke:#000000; fill: #ff0000\"/>"
                + "<rect x=\"330.2307692307692\" y=\"0\" height=\"720.0\" width=\"4.5\"style=\"stroke:#000000; fill: #ff0000\"/>"
                + "<rect x=\"385.2692307692308\" y=\"0\" height=\"720.0\" width=\"4.5\"style=\"stroke:#000000; fill: #ff0000\"/>"
                + "<rect x=\"440.3076923076923\" y=\"0\" height=\"720.0\" width=\"4.5\"style=\"stroke:#000000; fill: #ff0000\"/>"
                + "<rect x=\"495.34615384615387\" y=\"0\" height=\"720.0\" width=\"4.5\"style=\"stroke:#000000; fill: #ff0000\"/>"
                + "<rect x=\"550.3846153846154\" y=\"0\" height=\"720.0\" width=\"4.5\"style=\"stroke:#000000; fill: #ff0000\"/>"
                + "<rect x=\"605.4230769230769\" y=\"0\" height=\"720.0\" width=\"4.5\"style=\"stroke:#000000; fill: #ff0000\"/>"
                + "<rect x=\"660.4615384615385\" y=\"0\" height=\"720.0\" width=\"4.5\"style=\"stroke:#000000; fill: #ff0000\"/>"
                + "<rect x=\"715.5\" y=\"0\" height=\"720.0\" width=\"4.5\"style=\"stroke:#000000; fill: #ff0000\"/><line x1=\"17.0\" y1=\"15.0\" x2=\"707.0\" y2=\"705.0\" stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/><line x1=\"15.0\" y1=\"15.0\" x2=\"705.0\" y2=\"705.0\" stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/><line x1=\"15.0\" y1=\"705.0\" x2=\"705.0\" y2=\"15.0\" stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/><line x1=\"17.0\" y1=\"705.0\" x2=\"707.0\" y2=\"15.0\" stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/><rect x=\"15.0\" y=\"15.0\" height=\"9\" width=\"9\"style=\"stroke:#000000; fill: #ff0000\"/>"
                + "<rect x=\"696.0\" y=\"15.0\" height=\"9\" width=\"9\"style=\"stroke:#000000; fill: #ff0000\"/><rect x=\"15.0\" y=\"696.0\" height=\"9\" width=\"9\"style=\"stroke:#000000; fill: #ff0000\"/><rect x=\"696.0\" y=\"696.0\" height=\"9\" width=\"9\"style=\"stroke:#000000; fill: #ff0000\"/><rect x=\"187.5\" y=\"15.0\" height=\"9\" width=\"9\"style=\"stroke:#000000; fill: #ff0000\"/><rect x=\"355.5\" y=\"15.0\" height=\"9\" width=\"9\"style=\"stroke:#000000; fill: #ff0000\"/><rect x=\"523.5\" y=\"15.0\" height=\"9\" width=\"9\"style=\"stroke:#000000; fill: #ff0000\"/><rect x=\"250.5\" y=\"696.0\" height=\"9\" width=\"9\"style=\"stroke:#000000; fill: #ff0000\"/></SVG>"
                + "<p> start-Stolpe2: 187.5\n" +
                    "355.5\n" +
                    "523.5\n" +
                    "elems: 3\n" +
                    "stolperOppe: 3\n" +
                    "stolperNede: 1\n" +
                    "afstand oppe: 672.0\n" +
                    "afstand nede: 462.0\n" +
                    "stolpeOppeDouble: 2.358974358974359\n" +
                    "stolpeNedeDouble: 1.641025641025641\n" +
                    "ratio: 1.4375\n" +
                    "stolpe3test: [187.5, 355.5, 523.5]\n" +
                    "</p>";
        
        String localDrawAbove = port720720210210;
        
        /*
        String output2 = String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                            "style=\"stroke:#000000; fill: #ff0000\"/>", 
                            NedersteLeft, NedersteDistToTop, NedersteWidth, NedersteLength);
        */
        
        double distFromTop = this.carportWidth - this.sideUdhæng - stolpelength;
        output = "";//"hello regex string: " + distFromTop;
        
        double dubLeft = this.endeUdhæng;
        double dubRight = this.carportLength - this.endeUdhæng - stolpelength;
        double dubSkur = dubRight + stolpelength - this.skurLength;
        
        double DLheight = this.carportHeight - (dubLeft * slope); 
        double DRheight = this.carportHeight - (dubRight * slope); 
        double DSheight = this.carportHeight - (dubSkur * slope); 
        
        String stolpeLeft = String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                            "style=\"stroke:#000000; fill: #ff0000\"/>", 
                            dubLeft, (dubLeft * slope), DLheight, stolpelength);
        String stolpeRight = String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                            "style=\"stroke:#000000; fill: #ff0000\"/>", 
                            dubRight, (dubRight * slope), DRheight, stolpelength);
        String stolpeSkur = String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                            "style=\"stroke:#000000; fill: #ff0000\"/>", 
                            dubSkur, (dubSkur * slope), DSheight, stolpelength);
        output += stolpeLeft + stolpeRight + stolpeSkur;
        
        //String VenstreHeight = Double.toString(this.carportHeight - 
        //                        Double.parseDouble(VenstreDistToTop)) ; 
        //String VenstreDistToTop = Double.toString(this.oversternHeight);
        
        /*
        List<String> allMatches = new ArrayList<String>();
        Matcher m = Pattern.compile("your regular expression here")
            .matcher(yourStringHere);
        while (m.find()) {
          allMatches.add(m.group());
        }
        */
        
        //double distFromSide = this.; lav en regex der leder efter y=\"15.0\" height=\"9\" width=\"9\"
                                        // herefter får den de 10 tegn der står før det. 
                                        // herefter leder man efter tal som man typecaster til en double.
                                        // så har man left-x værdien
        // regResult:
        //String pattern = "y=\""+Double.toString(this.sideUdhæng)+
        //        "\" height=\"9\" width=\"9\"";
        
        
        
        //ArrayList<String> allMatches = new ArrayList<String>();
        //String pattern = "y=\"15.0\" height=\"9\" width=\"9\"";
        //boolean isMatch = Pattern.("", "");//localDrawAbove
        //output = Boolean.toString(isMatch);
        
        return output;
    }
    
    /*
            String NedersteVenstreWidth = relevantItems.get(0).getName().substring(0, 1);//9 from description: "97x97mm trykimp. stolpe,..."
            String NedersteVenstreLength = relevantItems.get(0).getName().substring(3, 4);//9 from description: "97x97mm trykimp. stolpe,..."
            String NedersteVenstreLeft = eachLeft;//Double.toString(this.endeUdhæng);
            String NedersteVenstreDistToTop = Double.toString(this.carportWidth-
                                    Integer.parseInt(NedersteVenstreWidth) - this.sideUdhæng);;//Double.toString(this.carportWidth-
                                        //Integer.parseInt(NedersteVenstreWidth) - this.sideUdhæng);

            output += String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                                "style=\"stroke:#000000; fill: #ff0000\"/>", 
                                NedersteVenstreLeft, NedersteVenstreDistToTop, NedersteVenstreWidth, NedersteVenstreLength);

    */
    
    @Override
    public String stolper() {
        ArrayList<String> words = new ArrayList<String>();
        words.add("Stolper");
        ArrayList<LineItem> relevantItems = relevantBomLines(words, this.bom);
        removeLineItemsFromBom(relevantItems);
                
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
//        System.out.println("start");
//        Calculator calc = new CalculatorImpl();
//
//        BillOfMaterials bom = calc.bomCalculator(240, 240, 210,"fladt", 0, 0);
//        DrawImplFlatSide draw = new DrawImplFlatSide(bom, 240, 240, 240, 50, 50);
        /*
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
        */
        //output = output + overSternSide();
        //output = output + underSternSide();
//        //output = output + stolper();
//        System.out.println(draw.stolper2Forneden(""));
//        System.out.println("end");
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
