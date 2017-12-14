/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import FunctionLayer.Entities.LineItem;
import FunctionLayer.Entities.BillOfMaterials;
import java.lang.*;
import FunctionLayer.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author Christian
 * 
 */
public class DrawImplFlatAbove implements Draw{
    
    public ArrayList XkoorLeftOppe;
    public ArrayList XkoorLeftNede;
        
    private double skurLength;
    private double skurWidth;
    
    private double stolpeLength;
    double BONDDIST = 2; 
    private double SIDEUDHAENG = 15;
    private double ENDEUDHAENG = 15;
    private int ARROWSPACE = 60;
    private int ARROWADJUST = 50;
    private BillOfMaterials bom;
    private double carportWidth;
    private double carportLength;
    
    public DrawImplFlatAbove(BillOfMaterials bom, double carportWidth, double carportLength, double skurLength, double skurWidth) {
        
        ArrayList<String> words = new ArrayList<String>();
        words.add("Stolper");
        ArrayList<LineItem> relevantItems = relevantBomLines(words, bom);
        String stolpeLengthString = relevantItems.get(0).getName().substring(0, 1);//9 from description: "97x97mm trykimp. stolpe,..."
        this.stolpeLength = Double.parseDouble(stolpeLengthString);
        
        BillOfMaterials localBom = new BillOfMaterials();
        localBom.mergeBom(bom);
        this.bom = localBom;
        this.carportWidth = carportWidth;
        this.carportLength = carportLength;
                
        this.skurLength = skurLength;
        this.skurWidth = skurWidth;
        
        //Man kan ikke have et skur der er længere end Carporten  - 2 * udhæng på enderne
        if (this.skurLength > (carportLength - this.ENDEUDHAENG - this.ENDEUDHAENG)) {
            this.skurLength = carportLength - this.ENDEUDHAENG - this.ENDEUDHAENG;
        } 
        //Man kan ikke have et skur der er bredere end Carporten  - 2 * udhæng på siderne
        if (this.skurWidth > (this.carportWidth - this.SIDEUDHAENG - this.SIDEUDHAENG)) {
            this.skurWidth = this.carportWidth - this.SIDEUDHAENG - this.SIDEUDHAENG;
        } 
    }
        
    private ArrayList<LineItem> relevantBomLines(ArrayList<String> wordsToTestFor, BillOfMaterials bom) {
        ArrayList<LineItem> output = new ArrayList<LineItem>();
        int wordNum = wordsToTestFor.size();
        
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
        
    private String canvas(int width, int height) {
        int canvasX = width + ARROWSPACE;
        int canvasY = height + ARROWSPACE;
        
        String output = String.format("<SVG width=\"%s\" height=\"%s\"> "
                + "<rect x=\"000\" y=\"00\" height=\"%s\" width=\"%s\"\n "
                + "style=\"stroke:#000000; fill: #ffffff\"/>", canvasX, canvasY, canvasY, canvasX);
        return output;
    }
    
    private String arrowDefinition() {
        String output = "<defs> <marker id=\"beginArrow\" 	markerWidth=\"9\" markerHeight=\"9\" refX=\"0\" refY=\"4\" orient=\"auto\"> "
                + "<path d=\"M0,4 L8,0 L8,8 L0,4\" style=\"fill: #000000s;\" /> </marker> <marker id=\"endArrow\" "
                + "markerWidth=\"9\" markerHeight=\"9\" refX=\"8\" refY=\"4\" orient=\"auto\"> "
                + "<path d=\"M0,0 L8,4 L0,8 L0,0\" style=\"fill: #000000;\" /> </marker> </defs> ";
        return output;
    }
    
    private String arrows(int width, int height) {
        int arrowXEnd = width+ARROWADJUST;
        int arrowYEnd = height+ARROWADJUST;
        int textMiddleX = width/2+ARROWADJUST;
        int textMiddleY = height/2+ARROWADJUST;
        
        String output = String.format("<line x1=\"50\"  y1=\"20\" x2=\"%s\" y2=\"20\" style=\"stroke:#000000; marker-start: url(#beginArrow);"
                + "marker-end: url(#endArrow);\" />", arrowXEnd);
        output += String.format("<text x=\"%s\" y=\"30\" font-size=\"12\" text-anchor=\"middle\" alignment-baseline=\"middle\"> %s cm </text> "
                , textMiddleX, width);
        output += String.format("<line x1=\"20\"  y1=\"50\" x2=\"20\" y2=\"%s\" style=\"stroke:#000000; marker-start: url(#beginArrow);"
                + "marker-end: url(#endArrow);\" />", arrowYEnd);
        output += String.format("<text x=\"30\" y=\"%s\" font-size=\"12\" text-anchor=\"middle\" alignment-baseline=\"middle\" style=\"writing-mode: tb;\"> %s cm </text>"
                , textMiddleY, height);
        
        output += String.format("<text x=\"30\" y=\"%s\" font-size=\"12\" text-anchor=\"middle\" alignment-baseline=\"middle\" style=\"writing-mode: tb;\"> %s cm </text>"
                , textMiddleY, height);
        output += String.format("<SVG x=\"50\" y=\"50\" width=\"%s\" height=\"%s\">", width, height);
        
        return output;
    }
    
    public String tegnTag(int width, int height) {
                
        String output = canvas(width, height);
        
        output += arrowDefinition();
        
        output += arrows(width, height);
                
        output += remme();        
        if (this.skurLength > 0 && this.skurWidth > 0) {
            output = output + skur();
        }
        
        output = output + spaer();
        
        if (this.carportLength >= this.carportWidth) {
            output = output + kryds();
        }         
        
        output += stolper();
        
        output = output + "</SVG>";
        
        return output;
    }
    
    private String remme() {
        ArrayList<String> words = new ArrayList<String>();
        words.add("remme");
        words.add("Remme");
        ArrayList<LineItem> relevantItems = relevantBomLines(words, this.bom);
                
        //Eksempel på LineItem:
        //LineItem{name=45x95mm spærtræ ubh., length=300, quantity=2, unit=stk, description=Remme i sider, sadles ned i stolper}      
        
        //øverste rem.
        String ØversteWidth = relevantItems.get(0).getName().substring(0, 2);//45 from description: "45x95mm spærtræ..."
        ØversteWidth = Double.toString(Double.parseDouble(ØversteWidth)/10.0);
        String ØversteLength = Double.toString(this.carportLength); // user input.
        String ØversteLeft = "0";
        String ØversteDistToTop = Double.toString(this.SIDEUDHAENG);
        
        String output1 = String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                            "style=\"stroke:#000000; fill: #ff0000\"/>", 
                            ØversteLeft, ØversteDistToTop, ØversteWidth, ØversteLength);
        
        //nederste rem.
        String NedersteWidth = relevantItems.get(0).getName().substring(0, 2);//45 from description: "45x95mm spærtræ..."
        NedersteWidth = Double.toString(Double.parseDouble(NedersteWidth)/10.0);
        String NedersteLength = Double.toString(this.carportLength); // user input.
        String NedersteLeft = "0";
        String NedersteDistToTop = Double.toString(this.carportWidth-
                                    (Double.parseDouble(NedersteWidth))-this.SIDEUDHAENG)
                                    ;
        
        String output2 = String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                            "style=\"stroke:#000000; fill: #ff0000\"/>", 
                            NedersteLeft, NedersteDistToTop, NedersteWidth, NedersteLength);
        
        return output1 + output2;
        
    }
    
    private String stolper() {
        ArrayList<String> words = new ArrayList<String>();
        words.add("Stolper");
        ArrayList<LineItem> relevantItems = relevantBomLines(words, this.bom);
        int quant = relevantItems.get(0).getQuantity();
        stolpeLaengde();
        
        double pladsForOven = stolpePladsOppe();
        double pladsForNeden = stolpePladsNede();
        int stolperOppe = new Double(pladsForOven / 240).intValue();
        int stolperNede = new Double(pladsForNeden / 240).intValue();
       
        boolean skurFindes = (this.skurLength > 0) && (this.skurWidth > 0);
        boolean ensAfstandOppeNede = pladsForOven == pladsForNeden;
        
        //Tilfæj hjoerneStolper der ikke overlapper med skur;
        stolperOppe += stolpeHjoerneOppe(skurFindes, ensAfstandOppeNede, pladsForOven);
        //Tilfæj hjoerneStolper der ikke overlapper med skur;
        stolperNede += stolpeHjoerneNede(skurFindes, ensAfstandOppeNede, pladsForNeden);
                
        this.XkoorLeftOppe = stolpePlaceringOppe(stolperOppe, pladsForOven, skurFindes, ensAfstandOppeNede);
        this.XkoorLeftNede = stolpePlaceringNede(stolperNede, pladsForNeden, skurFindes);
        
        double YkoorOppeOppe = this.SIDEUDHAENG;
        double YkoorNedeOppe = (this.carportWidth - this.SIDEUDHAENG) - this.stolpeLength;
        String tegningFraXkoorOppe = stolpeTegnFraXkoor(XkoorLeftOppe, YkoorOppeOppe);
        String tegningFraXkoorNede = stolpeTegnFraXkoor(XkoorLeftNede, YkoorNedeOppe);
        
        String myport = "carportWidth: " + this.carportWidth + " carportLength: " + this.carportLength + "\n";
        String udhaeng = "endeUdhaeng: " + this.ENDEUDHAENG + " sideUdhaeng: " + this.SIDEUDHAENG + "\n";
        String myskur = "skurWidth: " + this.skurWidth + " this.skurLength: " + this.skurLength + "\n";
        String output = "her er december: " + quant + " \n" +
                myport + " "+ udhaeng+" " + myskur + "\n" +
                "pladsForOven: " + pladsForOven + " pladsForNeden: " + pladsForNeden + "\n" +
                "boolean-skurFindes: " + skurFindes + " boolean-ensAfstandOppeNede: " + ensAfstandOppeNede + "\n" +
                "stolperOppe: " + stolperOppe + " stolperNede: " + stolperNede + "\n" +
                "stolpetegningoppeDECEMEBER: " + XkoorLeftOppe;      
        
        return tegningFraXkoorOppe + tegningFraXkoorNede;
        
    }
            
    private String stolpeTegnFraXkoor(ArrayList<Double> Xkoor, double Ykoor) {
        String output = "";
        for (int i = 0; i < Xkoor.size(); i++) {
            String output1 = String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                            "style=\"stroke:#000000; fill: #ff0000\"/>", 
                            Xkoor.get(i), 
                            Ykoor, 
                            this.stolpeLength, 
                            this.stolpeLength); 
            output += output1;
        }
        return output;
    }
    
    private ArrayList stolpePlaceringNede(int stolperNede, double pladsForNeden, boolean skurFindes) {
        ArrayList nyoutput = new ArrayList<Double>();
        if (pladsForNeden > 0 && skurFindes) {
            double smartDist = pladsForNeden/(stolperNede);
            for (int i = 0; i < stolperNede; i++) {
                nyoutput.add(((this.ENDEUDHAENG) + ((smartDist-(this.stolpeLength/2))*i)));;
            }
            nyoutput.add((this.carportLength - this.ENDEUDHAENG - this.stolpeLength));
            
        }else if (pladsForNeden == 0) {
        
        }else {
            double smartDist = pladsForNeden/(stolperNede-1);
            for (int i = 0; i < (stolperNede-1); i++) {
                nyoutput.add(((this.ENDEUDHAENG) + ((smartDist-(this.stolpeLength/2))*i)));
            }
            nyoutput.add((pladsForNeden + this.ENDEUDHAENG - this.stolpeLength));
        }
        return nyoutput;
    }
    
    
    private ArrayList stolpePlaceringOppe(int stolperOppe, double pladsForOven, boolean skurFindes, boolean ensAfstandOppeNede) {
        ArrayList output = new ArrayList<Double>();
        
        if (pladsForOven>0 && skurFindes && ensAfstandOppeNede) {
            double smartDist = pladsForOven/(stolperOppe); 
            for (int i = 0; i < stolperOppe; i++) {
                output.add(((this.ENDEUDHAENG) + ((smartDist-(this.stolpeLength/2))*i)));
            }
        } else if (pladsForOven == 0) {
            
        } else {
            double smartDist = pladsForOven/(stolperOppe-1); 
            for (int i = 0; i < (stolperOppe-1); i++) {
                output.add(((this.ENDEUDHAENG) + ((smartDist-(this.stolpeLength/2))*i)));
            }
            output.add((pladsForOven + this.ENDEUDHAENG - this.stolpeLength));
        }     
                
        return output;
    }
    
    private int stolpeHjoerneNede(boolean skurFindes, boolean ensAfstand, double pladsForNeden) {
        int output = 0;
        if (!skurFindes) {output = 2;}
        if (skurFindes) {output = 1;}
        if (skurFindes && pladsForNeden == 0) {output = 0;}
        return output;
    }
    
    private int stolpeHjoerneOppe(boolean skurFindes, boolean ensAfstand, double pladsForOven) {
        int output = 0;
        if (!skurFindes) {output = 2;}
        if (skurFindes && !ensAfstand) {output = 2;}
        if (skurFindes && ensAfstand) {output = 1;}
        if (skurFindes && ensAfstand && pladsForOven == 0) {output = 0;}
        return output;
    }
    
    private Double stolpePladsNede() {
       double normalDist = this.carportLength - (2 * this.ENDEUDHAENG);
       normalDist -= this.skurLength;     
       return normalDist;
    }
    
    private Double stolpePladsOppe() {
       double normalDist = this.carportLength - (2 * this.ENDEUDHAENG);
       double skurPlads = this.carportWidth - (2 * this.SIDEUDHAENG);
       if (this.skurWidth >= skurPlads) {
           normalDist -= this.skurLength;
       }       
       return normalDist;
    }
        
    private void stolpeLaengde() {
        ArrayList<String> words = new ArrayList<String>();
        words.add("Stolper");
        ArrayList<LineItem> relevantItems = relevantBomLines(words, this.bom);
        String ØversteVenstreLength = relevantItems.get(0).getName().substring(3, 4);//9 from description: "97x97mm trykimp. stolpe,..."
        this.stolpeLength = Double.parseDouble(ØversteVenstreLength);
    }
    
    private String spaer() {
        ArrayList<String> words = new ArrayList<String>();
        words.add("Spær");
        ArrayList<LineItem> relevantItems = relevantBomLines(words, this.bom);     
        
         /*
        Eksempel på LineItem:
        spaer()
LineItem{name=45x195mm spærtræ ubh., length=300, quantity=5, unit=stk, description=Spær monteres på rem}

        */
        
        String VenstreSpærWidth = relevantItems.get(0).getName().substring(0, 2);//45 from description: "45x195mm spærtræ ubh...."
        VenstreSpærWidth = Double.toString(Double.parseDouble(VenstreSpærWidth)/10.0);
        String VenstreSpærLength = Double.toString(this.carportWidth); 
        
        double portLaengde = this.carportLength;
        String VenstreSpærLeft = "0";
        String VenstreSpærDistToTop = "0";
        
        int antalBrædder = relevantItems.get(0).getQuantity(); 
        double tomLuft = this.carportLength - (antalBrædder * Double.parseDouble(VenstreSpærWidth));
        String AfstandMellemBraet = Double.toString(  tomLuft/((double) (antalBrædder-1)));
        
        String outputTest = "antal: "
                + antalBrædder
                + "tomluft: "
                + tomLuft
                + "AfstandMellemBraet"
                + AfstandMellemBraet;
        
        String output = "";
        for (int i = 0; i < antalBrædder; i++) {
            double distToTop = 0;
            VenstreSpærLeft = Double.toString( 
                                (i*(Double.parseDouble(AfstandMellemBraet)+Double.parseDouble(VenstreSpærWidth))));
            output += String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                            "style=\"stroke:#000000; fill: #ff0000\"/>", 
                            VenstreSpærLeft, VenstreSpærDistToTop
                            , VenstreSpærLength, VenstreSpærWidth);
        }
        
        return output;
       
    }

    private String kryds_2() {
        double lengthOfKryds = this.carportWidth - this.SIDEUDHAENG - this.SIDEUDHAENG; 
        double distFromLeft = (this.carportLength - lengthOfKryds) / 2.0;
        
        String output = "";
        
        //kryds -- Top Left - Bottom Right --Øverst
        String x1 = Double.toString(distFromLeft+BONDDIST); 
        String y1 = Double.toString(this.SIDEUDHAENG); 
        String x2 = Double.toString(distFromLeft+BONDDIST+lengthOfKryds); 
        String y2 = Double.toString(this.SIDEUDHAENG+lengthOfKryds); 
        output += String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" "
                + "stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/>", 
                            x1, y1, x2, y2);
        
        //kryds -- Top Left - Bottom Right --Nederst
        x1 = Double.toString(distFromLeft); 
        y1 = Double.toString(this.SIDEUDHAENG); 
        x2 = Double.toString(distFromLeft+lengthOfKryds);
        y2 = Double.toString(this.SIDEUDHAENG+lengthOfKryds); 
        output += String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" "
                + "stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/>", 
                            x1, y1, x2, y2);
        
        
        // kryds -- Bottom Left - Top Right --Øverst
        x1 = Double.toString(distFromLeft); 
        y1 = Double.toString(this.SIDEUDHAENG + lengthOfKryds); 
        x2 = Double.toString(distFromLeft+lengthOfKryds); 
        y2 = Double.toString(this.SIDEUDHAENG ); 
        output += String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" "
                + "stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/>", 
                            x1, y1, x2, y2);
        
        
        // kryds -- Bottom Left - Top Right --Nederst
        x1 = Double.toString(distFromLeft +BONDDIST ); 
        y1 = Double.toString(this.SIDEUDHAENG + lengthOfKryds); 
        x2 = Double.toString(distFromLeft+BONDDIST+lengthOfKryds); 
        y2 = Double.toString(this.SIDEUDHAENG); 
        output += String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" "
                + "stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/>", 
                            x1, y1, x2, y2);
        
        return output;
    }
    
    private String kryds() {
        double lengthOfKryds = this.carportWidth - this.SIDEUDHAENG - this.SIDEUDHAENG; 
        double distFromLeft = (this.carportLength - lengthOfKryds) / 2.0;
        
        String output = "";
        
        //kryds -- Top Left - Bottom Right --Øverst
        String x1 = Double.toString(distFromLeft+BONDDIST);
        String y1 = Double.toString(this.SIDEUDHAENG); 
        String x2 = Double.toString(distFromLeft+BONDDIST+lengthOfKryds); 
        String y2 = Double.toString(this.SIDEUDHAENG+lengthOfKryds); 
        output += String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" "
                + "stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/>", 
                            x1, y1, x2, y2);
        
        //kryds -- Top Left - Bottom Right --Nederst
        x1 = Double.toString(distFromLeft);
        y1 = Double.toString(this.SIDEUDHAENG);
        x2 = Double.toString(distFromLeft+lengthOfKryds); 
        y2 = Double.toString(this.SIDEUDHAENG+lengthOfKryds); 
        output += String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" "
                + "stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/>", 
                            x1, y1, x2, y2);
        
        
        // kryds -- Bottom Left - Top Right --Øverst
        x1 = Double.toString(distFromLeft); 
        y1 = Double.toString(this.SIDEUDHAENG + lengthOfKryds); 
        x2 = Double.toString(distFromLeft+lengthOfKryds); 
        y2 = Double.toString(this.SIDEUDHAENG ); 
        output += String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" "
                + "stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/>", 
                            x1, y1, x2, y2);
        
        
        // kryds -- Bottom Left - Top Right --Nederst
        x1 = Double.toString(distFromLeft +BONDDIST ); 
        y1 = Double.toString(this.SIDEUDHAENG + lengthOfKryds); 
        x2 = Double.toString(distFromLeft+BONDDIST+lengthOfKryds); 
        y2 = Double.toString(this.SIDEUDHAENG); 
        output += String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" "
                + "stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/>", 
                            x1, y1, x2, y2);
        
        return output;
    }

      
    
    private String skur() {       
        double stolpeLength = this.stolpeLength;   
        
        double skurTopRight_x = carportLength-this.ENDEUDHAENG;
        double skurTopRight_y = this.carportWidth-this.SIDEUDHAENG-this.skurWidth;
        
        double skurBottomRight_x = skurTopRight_x;
        double skurBottomRight_y = this.carportWidth-this.SIDEUDHAENG;
        
        double skurTopLeft_x = carportLength-this.ENDEUDHAENG - this.skurLength;
        double skurTopLeft_y = skurTopRight_y;
        
        double skurBottomleft_x = skurTopLeft_x;
        double skurBottomleft_y = skurBottomRight_y;
        
        
        String output = String.format(
        "<polygon points=\"%s,%s %s,%s %s,%s %s,%s\" style=\"stroke:#000000; fill: #099a0f\" />", 
                skurTopLeft_x, 
                skurTopLeft_y, 
                skurTopRight_x, 
                skurTopRight_y, 
                skurBottomRight_x, 
                skurBottomRight_y, 
                skurBottomleft_x, 
                skurBottomleft_y);
        
        
            // stolper til skuret:
            String TopLeftLeft = Double.toString(skurBottomleft_x);
            String TopLeftDistToTop = Double.toString(skurTopLeft_y);

            output += String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                                "style=\"stroke:#000000; fill: #ff0000\"/>", 
                                TopLeftLeft, TopLeftDistToTop, stolpeLength, stolpeLength);
            
            String TopRightLeft = Double.toString(skurBottomRight_x - stolpeLength);
            String TopRightDistToTop = Double.toString(skurTopLeft_y);

            output += String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                                "style=\"stroke:#000000; fill: #ff0000\"/>", 
                                TopRightLeft, TopRightDistToTop, stolpeLength, stolpeLength);
            
            String EkstraStolpeLeft = Double.toString(skurBottomleft_x);
            String EkstraStolpeDistToTop = Double.toString(skurBottomleft_y - stolpeLength);

            output += String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                                "style=\"stroke:#000000; fill: #ff0000\"/>", 
                                EkstraStolpeLeft, EkstraStolpeDistToTop, stolpeLength, stolpeLength);
                       
          
            // ekstra stolper til brede skure
            if (skurWidth > 400) {
                
                // ekstra stolpe til skuret. Højre middte op.
                String RightMiddleLeft2 = Double.toString(skurBottomRight_x - 
                                        stolpeLength);
                double twoThirdsSkurWidth = ((this.skurWidth/3.0)*2.0) + 
                                        (stolpeLength/2.0);
                String RightMiddleDistToTop2 = Double.toString(skurBottomRight_y -twoThirdsSkurWidth);
                
                output += String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                                    "style=\"stroke:#000000; fill: #ff0000\"/>", 
                                    RightMiddleLeft2, RightMiddleDistToTop2, stolpeLength, stolpeLength);
                
                // ekstra stolpe til skuret. Højre middte ned.
                String RightMiddleLeft1 = Double.toString(skurBottomRight_x - 
                                        stolpeLength);
                double oneThirdSkurWidth = ((this.skurWidth/3.0)) + 
                                        (stolpeLength/2.0);
                String RightMiddleDistToTop1 = Double.toString(skurBottomRight_y -oneThirdSkurWidth);
                
                output += String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                                    "style=\"stroke:#000000; fill: #ff0000\"/>", 
                                    RightMiddleLeft1, RightMiddleDistToTop1, stolpeLength, stolpeLength);
                
                // ekstra stolpe til skuret. Venstre midte.
                String LeftMiddleLeft = Double.toString(skurBottomRight_x - this.skurLength);
                String LeftMiddleDistToTop = Double.toString(skurBottomRight_y -
                                            ((stolpeLength+
                                                    this.skurWidth) / 2.0));

                output += String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                                    "style=\"stroke:#000000; fill: #ff0000\"/>", 
                                    LeftMiddleLeft, LeftMiddleDistToTop, stolpeLength, stolpeLength);
                
            } else {
                // ekstra stolpe til skuret. Højre middte.
                String RightMiddleLeft = Double.toString(skurBottomRight_x - 
                                        stolpeLength);
                String RightMiddleDistToTop = Double.toString(skurBottomRight_y -
                                            ((stolpeLength+
                                                    this.skurWidth) / 2.0));

                output += String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                                    "style=\"stroke:#000000; fill: #ff0000\"/>", 
                                    RightMiddleLeft, RightMiddleDistToTop, stolpeLength, stolpeLength);
                
            }
            if (skurWidth != this.carportWidth-this.SIDEUDHAENG-this.SIDEUDHAENG) {
                // ekstra stolpe ifald skuret ikke rækker hele vejen til carportens anden ende
                String TopMiddleLeft = Double.toString(skurBottomRight_x - 
                                        ((stolpeLength+this.skurLength) / 2.0));
                String TopMiddleDistToTop = Double.toString(skurTopLeft_y);

                output += String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                                    "style=\"stroke:#000000; fill: #ff0000\"/>", 
                                    TopMiddleLeft, TopMiddleDistToTop, stolpeLength, stolpeLength);
            }
        
        return output;  
    }
    
}
