/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

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
    double bondDist = 2; 
    private double sideUdhæng = 15;
    private double endeUdhæng = 15;
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
        if (this.skurLength > (carportLength - this.endeUdhæng - this.endeUdhæng)) {
            this.skurLength = carportLength - this.endeUdhæng - this.endeUdhæng;
        } 
        //Man kan ikke have et skur der er bredere end Carporten  - 2 * udhæng på siderne
        if (this.skurWidth > (this.carportWidth - this.sideUdhæng - this.sideUdhæng)) {
            this.skurWidth = this.carportWidth - this.sideUdhæng - this.sideUdhæng;
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
    
    public String tegnTag(int width, int height, String DrawFlatAbove) {
        return "";
    }
    
    public String tegnTag(int width, int height) {
        int canvasX = width + 60;
        int canvasY = height + 60;
        int arrowXEnd = width+50;
        int arrowYEnd = height+50;
        int textMiddleX = width/2+50;
        int textMiddleY = height/2+50;
        
        String output = String.format("<SVG width=\"%s\" height=\"%s\"> "
                + "<rect x=\"000\" y=\"00\" height=\"%s\" width=\"%s\"\n "
                + "style=\"stroke:#000000; fill: #ffffff\"/>", canvasX, canvasY, canvasY, canvasX);
        
        output += "<defs> <marker id=\"beginArrow\" 	markerWidth=\"9\" markerHeight=\"9\" refX=\"0\" refY=\"4\" orient=\"auto\"> "
                + "<path d=\"M0,4 L8,0 L8,8 L0,4\" style=\"fill: #000000s;\" /> </marker> <marker id=\"endArrow\" "
                + "markerWidth=\"9\" markerHeight=\"9\" refX=\"8\" refY=\"4\" orient=\"auto\"> "
                + "<path d=\"M0,0 L8,4 L0,8 L0,0\" style=\"fill: #000000;\" /> </marker> </defs> ";
        
        output += String.format("<line x1=\"50\"  y1=\"20\" x2=\"%s\" y2=\"20\" style=\"stroke:#000000; marker-start: url(#beginArrow);"
                + "marker-end: url(#endArrow);\" />", arrowXEnd);
               
        output += String.format("<text x=\"%s\" y=\"30\" font-size=\"12\" text-anchor=\"middle\" alignment-baseline=\"middle\"> %s cm </text> "
                , textMiddleX, width);; 
                        
        output += String.format("<line x1=\"20\"  y1=\"50\" x2=\"20\" y2=\"%s\" style=\"stroke:#000000; marker-start: url(#beginArrow);"
                + "marker-end: url(#endArrow);\" />", arrowYEnd);

        output += String.format("<text x=\"30\" y=\"%s\" font-size=\"12\" text-anchor=\"middle\" alignment-baseline=\"middle\" style=\"writing-mode: tb;\"> %s cm </text>"
                , textMiddleY, height);; 
                        
        output += String.format("<SVG x=\"50\" y=\"50\" width=\"%s\" height=\"%s\">", width, height);
                
        output += remme();        
        if (this.skurLength > 0 && this.skurWidth > 0) {
            output = output + skur();
        }
        
        output = output + spaer();
        
            // der sættes kun kryds på carporten hvis længden er større eller lig bredden.
            if (this.carportLength >= this.carportWidth) {
            output = output + kryds();
            }         
        
        output += stolper();
        
        output = output + "</SVG>";
        
        return output;
    }
    
    public String remme() {
        ArrayList<String> words = new ArrayList<String>();
        words.add("remme");
        words.add("Remme");
        ArrayList<LineItem> relevantItems = relevantBomLines(words, this.bom);
                
        //Eksempel på LineItem:
        //remme()
        //LineItem{name=45x95mm spærtræ ubh., length=300, quantity=2, unit=stk, description=Remme i sider, sadles ned i stolper}      
        
        //øverste rem.
        String ØversteWidth = relevantItems.get(0).getName().substring(0, 2);//45 from description: "45x95mm spærtræ..."
        ØversteWidth = Double.toString(Double.parseDouble(ØversteWidth)/10.0);
        String ØversteLength = Double.toString(this.carportLength); // user input.
        String ØversteLeft = "0";
        String ØversteDistToTop = Double.toString(this.sideUdhæng);
        
        String output1 = String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                            "style=\"stroke:#000000; fill: #ff0000\"/>", 
                            ØversteLeft, ØversteDistToTop, ØversteWidth, ØversteLength);
        
        //nederste rem.
        String NedersteWidth = relevantItems.get(0).getName().substring(0, 2);//45 from description: "45x95mm spærtræ..."
        NedersteWidth = Double.toString(Double.parseDouble(NedersteWidth)/10.0);
        String NedersteLength = Double.toString(this.carportLength); // user input.
        String NedersteLeft = "0";
        String NedersteDistToTop = Double.toString(this.carportWidth-
                                    (Double.parseDouble(NedersteWidth))-this.sideUdhæng)
                                    ;
        
        String output2 = String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                            "style=\"stroke:#000000; fill: #ff0000\"/>", 
                            NedersteLeft, NedersteDistToTop, NedersteWidth, NedersteLength);
        
        return output1 + output2;
        
    }
    
    public String stolper() {
        ArrayList<String> words = new ArrayList<String>();
        words.add("Stolper");
        ArrayList<LineItem> relevantItems = relevantBomLines(words, this.bom);
        int quant = relevantItems.get(0).getQuantity();
        stolpeLaengde();
        
        //1 + 2: brug skurlængde og bredde til at beregne afstand oeverst og nederst
        double pladsForOven = stolpePladsOppe();
        double pladsForNeden = stolpePladsNede();
        int stolperOppe = new Double(pladsForOven / 240).intValue();
        int stolperNede = new Double(pladsForNeden / 240).intValue();
        //lav to booleans: a: er der skur, b: rækker skuret op til oeverste side.
        boolean skurFindes = (this.skurLength > 0) && (this.skurWidth > 0);
        boolean ensAfstandOppeNede = pladsForOven == pladsForNeden;
        
        //3+4: tilføj hjørnestolper
        //Tilfæj hjoerneStolper der ikke overlapper med skur;
        stolperOppe += stolpeHjoerneOppe(skurFindes, ensAfstandOppeNede, pladsForOven);
        //Tilfæj hjoerneStolper der ikke overlapper med skur;
        stolperNede += stolpeHjoerneNede(skurFindes, ensAfstandOppeNede, pladsForNeden);
                
        //5: tegn stolper for oven -- tager parameter: stolper ope, plads for oven, skru findes, ens afstand.
        this.XkoorLeftOppe = stolpePlaceringOppe(stolperOppe, pladsForOven, skurFindes, ensAfstandOppeNede);
        this.XkoorLeftNede = stolpePlaceringNede(stolperNede, pladsForNeden, skurFindes);
        //String stolpetegningoppeDECEMEBER
        double YkoorOppeOppe = this.sideUdhæng;
        double YkoorNedeOppe = (this.carportWidth - this.sideUdhæng) - this.stolpeLength;
        String tegningFraXkoorOppe = stolpeTegnFraXkoor(XkoorLeftOppe, YkoorOppeOppe);
        String tegningFraXkoorNede = stolpeTegnFraXkoor(XkoorLeftNede, YkoorNedeOppe);
        
        
        
        //her er alle de beregninger jeg har brug for ***************
        // når funktionen er færdig, skal output være en "tegnings String".
        String myport = "carportWidth: " + this.carportWidth + " carportLength: " + this.carportLength + "\n";
        String udhaeng = "endeUdhaeng: " + this.endeUdhæng + " sideUdhaeng: " + this.sideUdhæng + "\n";
        String myskur = "skurWidth: " + this.skurWidth + " this.skurLength: " + this.skurLength + "\n";
        String output = "her er december: " + quant + " \n" +
                myport + " "+ udhaeng+" " + myskur + "\n" +
                "pladsForOven: " + pladsForOven + " pladsForNeden: " + pladsForNeden + "\n" +
                "boolean-skurFindes: " + skurFindes + " boolean-ensAfstandOppeNede: " + ensAfstandOppeNede + "\n" +
                "stolperOppe: " + stolperOppe + " stolperNede: " + stolperNede + "\n" +
                "stolpetegningoppeDECEMEBER: " + XkoorLeftOppe;      
        
        // beregninger slut *****************************************
        
        //return output;
        return tegningFraXkoorOppe + tegningFraXkoorNede;//output;
        
    }
            
    public String stolpeTegnFraXkoor(ArrayList<Double> Xkoor, double Ykoor) {
        String output = "";
        for (int i = 0; i < Xkoor.size(); i++) {
            String output1 = String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                            "style=\"stroke:#000000; fill: #ff0000\"/>", 
                            Xkoor.get(i), //ØversteVenstreLeft
                            Ykoor, //ØversteVenstreDistToTop
                            this.stolpeLength, //ØversteVenstreWidth
                            this.stolpeLength); //ØversteVenstreLength
            output += output1;
        }
        return output;
    }
    
    public ArrayList stolpePlaceringNede(int stolperNede, double pladsForNeden, boolean skurFindes) {
        ArrayList nyoutput = new ArrayList<Double>();
        if (pladsForNeden > 0 && skurFindes) {
            //der er et skur == lav ikke stolpe nederst til hoejre
            double smartDist = pladsForNeden/(stolperNede);
            for (int i = 0; i < stolperNede; i++) {
                nyoutput.add(((this.endeUdhæng) + ((smartDist-(this.stolpeLength/2))*i)));;
            }
            //*************************************************
            // 2017-12-08: her laves en stolpe nederst til hoejre. Dette er et hack da der ikke er meget til til review med kasper
            nyoutput.add((this.carportLength - this.endeUdhæng - this.stolpeLength));
            //***********************************************/
        }else if (pladsForNeden == 0) {
        
        }else {
            //nederste række påvirkes ikke af et skur == lav en stolpe nederst til hoejre
            double smartDist = pladsForNeden/(stolperNede-1);
            for (int i = 0; i < (stolperNede-1); i++) {
                nyoutput.add(((this.endeUdhæng) + ((smartDist-(this.stolpeLength/2))*i)));
            }
            nyoutput.add((pladsForNeden + this.endeUdhæng - this.stolpeLength));
        }
        return nyoutput;
    }
    
    
    public ArrayList stolpePlaceringOppe(int stolperOppe, double pladsForOven, boolean skurFindes, boolean ensAfstandOppeNede) {
        String output = "X_koorinater: "; // skriv 
        ArrayList nyoutput = new ArrayList<Double>();
        String punktLeft = "0";
        String punktLeftTilHoejre = "pladsForOven";
        
        if (pladsForOven>0 && skurFindes && ensAfstandOppeNede) {
            //der er et skur der rækker op til toppen == lav ikke en stolpe oeverst til hoejre
            double smartDist = pladsForOven/(stolperOppe); //pladsForoven delt med antallet af "mellemrum" mellemStolperne
            for (int i = 0; i < stolperOppe; i++) {
                nyoutput.add(((this.endeUdhæng) + ((smartDist-(this.stolpeLength/2))*i)));
                output += " " + ((this.endeUdhæng) + ((smartDist-(this.stolpeLength/2))*i));//-this.stolpeLength
            }
        } else if (pladsForOven == 0) {
            
        } else {
            //oeverste række påvirkes ikke af et skur == lav en stolpe oeverst til hoejre
            double smartDist = pladsForOven/(stolperOppe-1); //pladsForoven delt med antallet af "mellemrum" mellemStolperne
            
            for (int i = 0; i < (stolperOppe-1); i++) {
                nyoutput.add(((this.endeUdhæng) + ((smartDist-(this.stolpeLength/2))*i)));
                output += " " + ((this.endeUdhæng) + ((smartDist-(this.stolpeLength/2))*i));//-this.stolpeLength
            }
            nyoutput.add((pladsForOven + this.endeUdhæng - this.stolpeLength));
            output += " " + (pladsForOven + this.endeUdhæng - this.stolpeLength);
            output += " stolpelength " + this.stolpeLength;
        }     
                
        return nyoutput;
        //return output;
    }
    
    public int stolpeHjoerneNede(boolean skurFindes, boolean ensAfstand, double pladsForNeden) {
        //hvis der ikke er skur: tilføj 2 stolper til PladsForneden
        //hvis der er skur og skuret ikke fylder det hele: tilføj 1 stolpe til plads for neden
        int output = 0;
        if (!skurFindes) {output = 2;}
        if (skurFindes) {output = 1;}
        if (skurFindes && pladsForNeden == 0) {output = 0;}
        return output;
    }
    
    public int stolpeHjoerneOppe(boolean skurFindes, boolean ensAfstand, double pladsForOven) {
        //hvis der ikke er skur: tilføj 2 stolper til PladsForoven.
        //hvis der er skur og ikke ens afstand: tilføj 2 stolper til PladsForoven.
        //hvis der er skur og ens afstand og skuret IKKE fylder det hele: tilføj 1 stolpe til pladsForOven
        int output = 0;
        if (!skurFindes) {output = 2;}
        if (skurFindes && !ensAfstand) {output = 2;}
        if (skurFindes && ensAfstand) {output = 1;}
        if (skurFindes && ensAfstand && pladsForOven == 0) {output = 0;}
        return output;
    }
    
    //*****************************************
    public Double stolpePladsNede() {
       double normalDist = this.carportLength - (2 * this.endeUdhæng);
       normalDist -= this.skurLength;     
       return normalDist;
    }
    
    public Double stolpePladsOppe() {
       double normalDist = this.carportLength - (2 * this.endeUdhæng);
       double skurPlads = this.carportWidth - (2 * this.sideUdhæng);
       if (this.skurWidth >= skurPlads) {
           normalDist -= this.skurLength;
       }       
       return normalDist;
    }
        
    public void stolpeLaengde() {
        ArrayList<String> words = new ArrayList<String>();
        words.add("Stolper");
        ArrayList<LineItem> relevantItems = relevantBomLines(words, this.bom);
        String ØversteVenstreLength = relevantItems.get(0).getName().substring(3, 4);//9 from description: "97x97mm trykimp. stolpe,..."
        this.stolpeLength = Double.parseDouble(ØversteVenstreLength);
    }
    
    public String spaer() {
        ArrayList<String> words = new ArrayList<String>();
        words.add("Spær");
        ArrayList<LineItem> relevantItems = relevantBomLines(words, this.bom);
        ////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////removeLineItemsFromBom(relevantItems);   
        /////////////////////////////////////////////////////////////////////////        
        
         /*
        Eksempel på LineItem:
        spaer()
LineItem{name=45x195mm spærtræ ubh., length=300, quantity=5, unit=stk, description=Spær monteres på rem}

        */
        
        String VenstreSpærWidth = relevantItems.get(0).getName().substring(0, 2);//45 from description: "45x195mm spærtræ ubh...."
        VenstreSpærWidth = Double.toString(Double.parseDouble(VenstreSpærWidth)/10.0);
        String VenstreSpærLength = Double.toString(this.carportWidth); //user input 
        
        double portLaengde = this.carportLength;
        String VenstreSpærLeft = "0";//Double.toString(portLaengde -this.carportWidth);
        String VenstreSpærDistToTop = "0";//Integer.toString(Integer.parseInt(VenstreSpærWidth));
        
        int antalBrædder = relevantItems.get(0).getQuantity(); // antal brædder (normalt 5)
        double tomLuft = this.carportLength - (antalBrædder * Double.parseDouble(VenstreSpærWidth));
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

    public String kryds_2() {
        double lengthOfKryds = this.carportWidth - this.sideUdhæng - this.sideUdhæng; //length of kryds = width of kryds = sideUdhæng
        double distFromLeft = (this.carportLength - lengthOfKryds) / 2.0;
        
        String output = "";
        
        //kryds -- Top Left - Bottom Right --Øverst
        String x1 = Double.toString(distFromLeft+bondDist); //this.topLeftStolpeØverstHøjre_X
        String y1 = Double.toString(this.sideUdhæng); //topLeftStolpeØverstHøjre_Y
        String x2 = Double.toString(distFromLeft+bondDist+lengthOfKryds); //bottomRightStolpeØverstHøjre_X
        String y2 = Double.toString(this.sideUdhæng+lengthOfKryds); //bottomRightStolpeØverstHøjre_Y
        output += String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" "
                + "stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/>", 
                            x1, y1, x2, y2);
        
        //kryds -- Top Left - Bottom Right --Nederst
        x1 = Double.toString(distFromLeft); //topLeftStolpeNederstVenstre_X
        y1 = Double.toString(this.sideUdhæng); //topLeftStolpeNederstVenstre_Y
        x2 = Double.toString(distFromLeft+lengthOfKryds); //this.bottomRightStolpeNederstVenstre_X
        y2 = Double.toString(this.sideUdhæng+lengthOfKryds); //bottomRightStolpeNederstVenstre_Y
        output += String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" "
                + "stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/>", 
                            x1, y1, x2, y2);
        
        
        // kryds -- Bottom Left - Top Right --Øverst
        x1 = Double.toString(distFromLeft); //bottomLeftStolpeØverstVenstre_X
        y1 = Double.toString(this.sideUdhæng + lengthOfKryds); //bottomLeftStolpeØverstVenstre_Y
        x2 = Double.toString(distFromLeft+lengthOfKryds); //this.topRightStolpeØverstVenstre_X
        y2 = Double.toString(this.sideUdhæng ); //topRightStolpeØverstVenstre_Y
        output += String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" "
                + "stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/>", 
                            x1, y1, x2, y2);
        
        
        // kryds -- Bottom Left - Top Right --Nederst
        x1 = Double.toString(distFromLeft +bondDist ); //bottomLeftStolpeNederstHøjre_X
        y1 = Double.toString(this.sideUdhæng + lengthOfKryds); //bottomLeftStolpeNederstHøjre_Y
        x2 = Double.toString(distFromLeft+bondDist+lengthOfKryds); //this.topRightStolpeNederstHøjre_X
        y2 = Double.toString(this.sideUdhæng); //topRightStolpeNederstHøjre_Y
        output += String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" "
                + "stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/>", 
                            x1, y1, x2, y2);
        
        return output;
    }
    
    public String kryds() {
        double lengthOfKryds = this.carportWidth - this.sideUdhæng - this.sideUdhæng; //length of kryds = width of kryds = sideUdhæng
        double distFromLeft = (this.carportLength - lengthOfKryds) / 2.0;
        
        String output = "";
        
        //kryds -- Top Left - Bottom Right --Øverst
        String x1 = Double.toString(distFromLeft+bondDist); //this.topLeftStolpeØverstHøjre_X
        String y1 = Double.toString(this.sideUdhæng); //topLeftStolpeØverstHøjre_Y
        String x2 = Double.toString(distFromLeft+bondDist+lengthOfKryds); //bottomRightStolpeØverstHøjre_X
        String y2 = Double.toString(this.sideUdhæng+lengthOfKryds); //bottomRightStolpeØverstHøjre_Y
        output += String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" "
                + "stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/>", 
                            x1, y1, x2, y2);
        
        //kryds -- Top Left - Bottom Right --Nederst
        x1 = Double.toString(distFromLeft); //topLeftStolpeNederstVenstre_X
        y1 = Double.toString(this.sideUdhæng); //topLeftStolpeNederstVenstre_Y
        x2 = Double.toString(distFromLeft+lengthOfKryds); //this.bottomRightStolpeNederstVenstre_X
        y2 = Double.toString(this.sideUdhæng+lengthOfKryds); //bottomRightStolpeNederstVenstre_Y
        output += String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" "
                + "stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/>", 
                            x1, y1, x2, y2);
        
        
        // kryds -- Bottom Left - Top Right --Øverst
        x1 = Double.toString(distFromLeft); //bottomLeftStolpeØverstVenstre_X
        y1 = Double.toString(this.sideUdhæng + lengthOfKryds); //bottomLeftStolpeØverstVenstre_Y
        x2 = Double.toString(distFromLeft+lengthOfKryds); //this.topRightStolpeØverstVenstre_X
        y2 = Double.toString(this.sideUdhæng ); //topRightStolpeØverstVenstre_Y
        output += String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" "
                + "stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/>", 
                            x1, y1, x2, y2);
        
        
        // kryds -- Bottom Left - Top Right --Nederst
        x1 = Double.toString(distFromLeft +bondDist ); //bottomLeftStolpeNederstHøjre_X
        y1 = Double.toString(this.sideUdhæng + lengthOfKryds); //bottomLeftStolpeNederstHøjre_Y
        x2 = Double.toString(distFromLeft+bondDist+lengthOfKryds); //this.topRightStolpeNederstHøjre_X
        y2 = Double.toString(this.sideUdhæng); //topRightStolpeNederstHøjre_Y
        output += String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" "
                + "stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/>", 
                            x1, y1, x2, y2);
        
        return output;
    }

      
    
    public String skur() {       
        double stolpeLength = this.stolpeLength;   
        
        double skurTopRight_x = carportLength-this.endeUdhæng;
        double skurTopRight_y = this.carportWidth-this.sideUdhæng-this.skurWidth;
        
        double skurBottomRight_x = skurTopRight_x;
        double skurBottomRight_y = this.carportWidth-this.sideUdhæng;
        
        double skurTopLeft_x = carportLength-this.endeUdhæng - this.skurLength;
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
        
        
            // ekstra stolpe til skuret:
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
                       
          
        // særskilte stolpe til nederste venstre hjørne. 
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
                
                // ekstra stolpe til skuret. Højre middte op.
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
            if (skurWidth != this.carportWidth-this.sideUdhæng-this.sideUdhæng) {
                // ekstra stolpe til skuret. Øverst midten.
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
