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
    
    //DECEMBER
    public ArrayList XkoorLeftOppe;
    public ArrayList XkoorLeftNede;
    
    public ArrayList<Double> stople3Test = new ArrayList<Double>();
    public double stolpeOppeDouble;
    public double stolpeNedeDouble;
    public int stolpeOppeTest;
    public int stolpeNedeTest;
    public double afstandOeverstVenstre;
    public double afstandNederstVenstre;
    public double ratio;
    
    /*
            String stolper3(int stolperOppe, int stolperNede, 
            double afstandOeverstVenstre, double afstandNederstVenstre)
            */
    private double skurLength;
    private double skurWidth;
    
    private double stolpeLength;
    double bondDist = 2; 
    private double sideUdhæng = 15;
    private double endeUdhæng = 15;
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
    
    public DrawImplFlatAbove(BillOfMaterials bom, double carportWidth, double carportLength, double skurLength, double skurWidth) {
        BillOfMaterials localBom = new BillOfMaterials();
        localBom.mergeBom(bom);
        this.bom = localBom;
        this.carportWidth = carportWidth;
        this.carportLength = carportLength;
                
        this.skurLength = skurLength;
        this.skurWidth = skurWidth;
        
        //Man kan ikke have et skur der er længere end Carporten  - 2 * udhæng på enderne
        if (this.skurLength > (carportLength - this.endeUdhæng - this.endeUdhæng)) {
            //Localskurlength = carportLength - this.endeUdhæng - this.endeUdhæng;
            this.skurLength = carportLength - this.endeUdhæng - this.endeUdhæng;
        } //else {
          //  Localskurlength = this.skurLength;
        //}
        //Man kan ikke have et skur der er bredere end Carporten  - 2 * udhæng på siderne
        if (this.skurWidth > (this.carportWidth - this.sideUdhæng - this.sideUdhæng)) {
            //LocalskurWidth = 
            this.skurWidth = this.carportWidth - this.sideUdhæng - this.sideUdhæng;
        } //else {
         //   LocalskurWidth = this.skurWidth;
        //}
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
    
    public String tegnTag(int width, int height, String DrawFlatAbove) {
        return "";
    }
    
    @Override
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
            //output = output + "<p>"+skurLength + " " +skurWidth+"</p>";
            output = output + skur();
        }
        
        output = output + spaer();
        
        //if (carportWidth > 400 || carportLength > 400) {  
            // der sættes kun kryds på hvis den samlede længde eller bredde er over 400 cm.
            if (this.carportLength >= this.carportWidth) {
            output = output + kryds_2();
            } else {
                output = output + kryds();
            }
        //}
        
        
        output += stolperDECEMBER();
        //output = output + stolper();
        //output = output + stolper2();    
        output = output + "</SVG>";
        
        /*
        //debugging stolper2()
        //output = output + stolper2();
        output = output + "<p>"+" start-Stolpe2: ";
        
        for (int i = 0; i < this.stople3Test.size(); i++) {
            output = output + this.stople3Test.get(i) + "\n";
        }
        output = output +"elems: " + stople3Test.size() + "\n";
        output = output +"stolperOppe: " + this.stolpeOppeTest+ "\n";
        output = output +"stolperNede: " + this.stolpeNedeTest+ "\n";
        output = output +"afstand oppe: " + this.afstandOeverstVenstre+ "\n";
        output = output +"afstand nede: " + this.afstandNederstVenstre+ "\n";
        output = output +"stolpeOppeDouble: " + this.stolpeOppeDouble + "\n";
        output = output +"stolpeNedeDouble: " + this.stolpeNedeDouble + "\n";
        output = output +"ratio: " + this.ratio + "\n";
        output = output +"stolpe3test: " + this.stople3Test.toString() + "\n";
        
        output = output +"</p>";
        */
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

    public int stolperPaaSkur() {
        
        ArrayList<String> words = new ArrayList<String>();
        words.add("Stolper");
        ArrayList<LineItem> relevantItems = relevantBomLines(words, this.bom);
        
        // samlede antal stolper.
        int quant = relevantItems.get(0).getQuantity();
        // antal stolper sat på skuret:
        boolean connected = false;
        if (this.carportWidth == this.skurWidth+(2*this.sideUdhæng)) {
            connected = true;
        }
        
        int stolperPaaSkur = 0;
        if (skurLength != 0 && skurWidth != 0) {
            //stolperPaaSkur += 1;// stolper til de 2 hjørner længst til højre.
            if (skurWidth > 400) {
                stolperPaaSkur += 5;
            } else {
                stolperPaaSkur += 3;
            }
            if (connected == false) {
                stolperPaaSkur += 2;
            }
            if (connected == false) {
                stolperPaaSkur += 1;
            }else {
                stolperPaaSkur += 2;
            }
        }
        
        //int stolperIkkePaaSkur = quant-stolperPaaSkur;
        return stolperPaaSkur;
        
        //return ""+relevantItems.get(0)
        //        + "\n"+"hello stolper 2: " + quant + " " + connected;
        
    }
    
    
    public String stolper2() {
        ArrayList<String> words = new ArrayList<String>();
        words.add("Stolper");
        ArrayList<LineItem> relevantItems = relevantBomLines(words, this.bom);
        
        // samlede antal stolper.
        int quant = relevantItems.get(0).getQuantity();
        int stolperPaaSkur = stolperPaaSkur();
        boolean connected = false;
        if (this.carportWidth == this.skurWidth+(2*this.sideUdhæng)) {
            connected = true;
        }
                
        double afstandNederstVenstre = this.carportLength - (this.endeUdhæng*2) - this.skurLength;
        
        // beregn antallet af stolper tilbage. ++ beregn den procentvise forskel mellem
        // afstanden i bunden og afstanden for oven, og fordel de resterende stolper derefter.
        
        double afstandOeverstVenstre = 0;
        if (connected) {
            afstandOeverstVenstre = afstandNederstVenstre;
        } else {
            afstandOeverstVenstre = this.carportLength - (this.endeUdhæng*2);
        }
        // **************************************************
        // beregn herefter: forholdet mellem afstandOeverstvenstre og afstandnederstVenstre
        double ratio;
        if (afstandNederstVenstre == 0) {
            afstandNederstVenstre = -1;
        } 
        ratio = (afstandOeverstVenstre)/afstandNederstVenstre;
        //ratio is now a double, greate then or equal to 1.
        // if ratio is positive, there is a difference between oeverst og nederst, and neither is 0
        
        
        
        // fra resterende: fjern stolper der SKAL placeres i de sidste hjørner.
        int resterende  = quant - stolperPaaSkur;
        
        // af de resterende (stolper ikke på skur) vil nogle af stolperne 
        // høre til i hjørnerne. 
        if ( !connected) {
            resterende = resterende - 1;
            // da skuret ikke spænder hele carportens bredde, 
            // vil 1 af de resterende stopler høre blive assignet til øverste højre hjørne.
        }
        if (afstandNederstVenstre > 0) {
            resterende = resterende - 1;
        }
        if (afstandOeverstVenstre > 0) {
            resterende = resterende - 1;
        }
        
        //*******************************************************
        // beregn fordelingen af resterende stolper, og placer dem ud.
        // resterende stolper er antallet af stolper der ikke sidder på skuret, og ikke sidder i 
        // de 4 hjørner
        
        double stolperØverst = 0;
        double stolperNederst = 0;
        this.ratio = ratio;
        if (ratio <= 0) {
            stolperNederst = 0;
            stolperØverst = resterende;
            if (afstandOeverstVenstre == 0) {
                stolperØverst = 0;
            }
        } else {
            stolperØverst = ratio/((ratio+1.0)/resterende);//4 * Math.abs((1.0/ratio)-1);
            stolperNederst = 1/((ratio+1.0)/resterende);//4 * (1.0/ratio);//stolperNederst
            //stolperØverst = ((1/(ratio/100.0))/100) * ((double) resterende);
            //stolperNederst = ((double) resterende) - stolperØverst;//stolperNederst
            //nu er stolperØverst en double der viser hvor mange stolper der "burde" være for oven,
            // hvis man kunne have fraktioner af stolper.
            // stolperNederst er antallet af stlper der "burde" være for neden
        }
        
        //************************test
        this.stolpeOppeDouble = stolperØverst;
        this.stolpeNedeDouble = stolperNederst;
        // nu burde stolpe øverst og stolpe neders være kommatal.
        // Disse skal laves om til integers, ved afrunding.
        int stolpeNedAfrund = 0;
        int stolpeOpAfrund = 0;
        if (stolperØverst % 1 != 0) {
            stolpeNedAfrund = (int) stolperNederst;
            stolpeOpAfrund = ((int) stolperØverst);
            stolpeOpAfrund++;
        } else {
             stolpeNedAfrund = (int) stolperNederst;
            stolpeOpAfrund = (int) stolperØverst;
        }
        
        
        
        return stolper3(stolpeOpAfrund, stolpeNedAfrund, afstandOeverstVenstre, afstandNederstVenstre);
                //test output;
                /*
                ""+ratio + " resterende: " + resterende
                + " stolpNederst: " + stolperNederst + " stolpOeverst: " + stolperØverst + 
                " stolpeNedAfrund: "+stolpeNedAfrund + " stolpeOpAfrund: " + stolpeOpAfrund;
                */
    }
    
    // denne metode tager to Integers "øverste stolpe" og "nederste stolpe".
    // 
    public String stolper3(int stolperOppe, int stolperNede, 
            double afstandOeverstVenstre, double afstandNederstVenstre) {
        this.stolpeOppeTest = stolperOppe;
        this.stolpeNedeTest = stolperNede;
        //this.afstandOeverstVenstre = afstandOeverstVenstre;
        //this.afstandNederstVenstre = afstandNederstVenstre;
        
        
        ArrayList<String> words = new ArrayList<String>();
        words.add("Stolper");
        ArrayList<LineItem> relevantItems = relevantBomLines(words, this.bom);
        String ØversteVenstreLength = relevantItems.get(0).getName().substring(3, 4);//9 from description: "97x97mm trykimp. stolpe,..."
        double stolpelength = Double.parseDouble(ØversteVenstreLength);
        // 1: træk stolpelængden * 2 fra afstandOeverstVenstre og afstandNederstVenstre
        // 2: find x-akse midterpunkterne til stolperne. (husk at der nu er mere mellemrum end der er stolper)
        // 3: justér punkterne så de passer med left-siden.
        // 4: læg punkterne in  i en arrayList.
        // 5: brug listen til at skrive stolperne ud til en streng.
        double ledigAfstandOeverstVenstre = afstandOeverstVenstre - (stolpelength*2);
        double ledigAfstandNederstVenstre = afstandNederstVenstre - (stolpelength*2);
        
        this.afstandOeverstVenstre = ledigAfstandOeverstVenstre;
        this.afstandNederstVenstre = ledigAfstandNederstVenstre;
        
        ArrayList<Double> stolpeLeftPunkterOeverst = new ArrayList<Double>();
        double antalMellemrumOeverst = stolperOppe + 1;
        double mellemrumStoerelseOeven = ledigAfstandOeverstVenstre / antalMellemrumOeverst;
        double startPointOeven = this.endeUdhæng + stolpelength;
        
        double eachLeftPoint = 0;
        for (int i = 1; i <= (stolperOppe); i++) {
            //i++;
            eachLeftPoint = startPointOeven + (i*mellemrumStoerelseOeven);//dette giver midtpunktet
            eachLeftPoint = eachLeftPoint - (stolpelength / 2.0); // dette giver left-punktet;
            stolpeLeftPunkterOeverst.add(eachLeftPoint);
        }
        
        //***********************************************************
        this.stople3Test = stolpeLeftPunkterOeverst;
        /*
        this.stolpeNedeTest;
        this.stolpeOppeTest;
        this.afstandNederstVenstre;
        this.afstandOeverstVenstre;
        */
        //***********************************************************
        
        String output = "";
        for (int j = 0; j < stolpeLeftPunkterOeverst.size(); j++) {
            String eachLeft = Double.toString(stolpeLeftPunkterOeverst.get(j));
            ///////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////
            String NedersteVenstreWidth = relevantItems.get(0).getName().substring(0, 1);//9 from description: "97x97mm trykimp. stolpe,..."
            String NedersteVenstreLength = relevantItems.get(0).getName().substring(3, 4);//9 from description: "97x97mm trykimp. stolpe,..."
            String NedersteVenstreLeft = eachLeft;//Double.toString(this.endeUdhæng);
            String NedersteVenstreDistToTop = Double.toString(this.sideUdhæng);//Double.toString(this.carportWidth-
                                        //Integer.parseInt(NedersteVenstreWidth) - this.sideUdhæng);

            this.bottomLeftStolpeNederstHøjre_X = Double.parseDouble(NedersteVenstreLength)+
                                                  Double.parseDouble(NedersteVenstreLeft);
            this.bottomLeftStolpeNederstHøjre_Y = Double.parseDouble(NedersteVenstreDistToTop) +
                                                  Double.parseDouble(NedersteVenstreWidth);
            this.bottomLeftStolpeØverstVenstre_X = Double.parseDouble(NedersteVenstreLeft);;
            this.bottomLeftStolpeØverstVenstre_Y = Double.parseDouble(NedersteVenstreDistToTop);

            output += String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                                "style=\"stroke:#000000; fill: #ff0000\"/>", 
                                NedersteVenstreLeft, NedersteVenstreDistToTop, NedersteVenstreWidth, NedersteVenstreLength);

            ///////////////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////////////////
        }
        
        // her skrives ekstra stolperne nederst:
        
        double antalMellemrumNederst = stolperNede + 1;//afstandNederstVenstre
        double mellemrumStoerelseNede = ledigAfstandNederstVenstre / antalMellemrumNederst;
        double startPointNede = this.endeUdhæng + stolpelength;
        ArrayList<Double> stolpeLeftPunkterNederst = new ArrayList<Double>();
        
        eachLeftPoint = 0;
        for (int i = 1; i <= (stolperNede); i++) {/////////
            //i++;
            eachLeftPoint = startPointNede + (i*mellemrumStoerelseNede);//dette giver midtpunktet
            eachLeftPoint = eachLeftPoint - (stolpelength / 2.0); // dette giver left-punktet;
            stolpeLeftPunkterNederst.add(eachLeftPoint);
        }
        
        //output = "";
        for (int j = 0; j < stolpeLeftPunkterNederst.size(); j++) {
            String eachLeft = Double.toString(stolpeLeftPunkterNederst.get(j));
            ///////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////
            String NedersteVenstreWidth = relevantItems.get(0).getName().substring(0, 1);//9 from description: "97x97mm trykimp. stolpe,..."
            String NedersteVenstreLength = relevantItems.get(0).getName().substring(3, 4);//9 from description: "97x97mm trykimp. stolpe,..."
            String NedersteVenstreLeft = eachLeft;//Double.toString(this.endeUdhæng);
            String NedersteVenstreDistToTop = Double.toString(this.carportWidth-
                                    Integer.parseInt(NedersteVenstreWidth) - this.sideUdhæng);;//Double.toString(this.carportWidth-
                                        //Integer.parseInt(NedersteVenstreWidth) - this.sideUdhæng);

            this.bottomLeftStolpeNederstHøjre_X = Double.parseDouble(NedersteVenstreLength)+
                                                  Double.parseDouble(NedersteVenstreLeft);
            this.bottomLeftStolpeNederstHøjre_Y = Double.parseDouble(NedersteVenstreDistToTop) +
                                                  Double.parseDouble(NedersteVenstreWidth);
            this.bottomLeftStolpeØverstVenstre_X = Double.parseDouble(NedersteVenstreLeft);;
            this.bottomLeftStolpeØverstVenstre_Y = Double.parseDouble(NedersteVenstreDistToTop);

            output += String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                                "style=\"stroke:#000000; fill: #ff0000\"/>", 
                                NedersteVenstreLeft, NedersteVenstreDistToTop, NedersteVenstreWidth, NedersteVenstreLength);

            ///////////////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////////////////
        }
        
        return output;
    }
    
    //ØversteVenstreLeft, ØversteVenstreDistToTop, ØversteVenstreWidth, ØversteVenstreLength);
    public String stolperDECEMBER() {
        ArrayList<String> words = new ArrayList<String>();
        words.add("Stolper");
        ArrayList<LineItem> relevantItems = relevantBomLines(words, this.bom);
        int quant = relevantItems.get(0).getQuantity();
        stolpeLengthDECEMBER();
        
        //1 + 2: brug skurlængde og bredde til at beregne afstand oeverst og nederst
        double pladsForOven = stolperDECEMBER_nr_1pladsOven();
        double pladsForNeden = stolperDECEMBER_nr_2pladsNeden();
        int stolperOppe = new Double(pladsForOven / 240).intValue();
        int stolperNede = new Double(pladsForNeden / 240).intValue();
        //lav to booleans: a: er der skur, b: rækker skuret op til oeverste side.
        boolean skurFindes = (this.skurLength > 0) && (this.skurWidth > 0);
        boolean ensAfstandOppeNede = pladsForOven == pladsForNeden;
        
        //3+4: tilføj hjørnestolper
        //Tilfæj hjoerneStolper der ikke overlapper med skur;
        stolperOppe += stolperDECEMBER_nr_3hjoernerOppe(skurFindes, ensAfstandOppeNede, pladsForOven);
        //Tilfæj hjoerneStolper der ikke overlapper med skur;
        stolperNede += stolperDECEMBER_nr_4hjoernerNede(skurFindes, ensAfstandOppeNede, pladsForNeden);
                
        //5: tegn stolper for oven -- tager parameter: stolper ope, plads for oven, skru findes, ens afstand.
        this.XkoorLeftOppe = stolperDECEMBER_nr_5tegnXkoor(stolperOppe, pladsForOven, skurFindes, ensAfstandOppeNede);
        this.XkoorLeftNede = stolperDECEMBER_nr_7tegnXkoor(stolperNede, pladsForNeden, skurFindes);
        //String stolpetegningoppeDECEMEBER
        double YkoorOppeOppe = this.sideUdhæng;
        double YkoorNedeOppe = (this.carportWidth - this.sideUdhæng) - this.stolpeLength;
        String tegningFraXkoorOppe = stolperDECEMBER_nr_6tegnFromXkoor(XkoorLeftOppe, YkoorOppeOppe);
        String tegningFraXkoorNede = stolperDECEMBER_nr_6tegnFromXkoor(XkoorLeftNede, YkoorNedeOppe);
        
        
        
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
    
    public ArrayList<Double> XkoorOppeDECEMBER() {
        return this.XkoorLeftOppe;
    }
    
    public ArrayList<Double> XkoorNedeDECEMBER() {
        return this.XkoorLeftNede;
    }
    
    public String stolperDECEMBER_nr_6tegnFromXkoor(ArrayList<Double> Xkoor, double Ykoor) {
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
    
    public ArrayList stolperDECEMBER_nr_7tegnXkoor(int stolperNede, double pladsForNeden, boolean skurFindes) {
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
    
    
    public ArrayList stolperDECEMBER_nr_5tegnXkoor(int stolperOppe, double pladsForOven, boolean skurFindes, boolean ensAfstandOppeNede) {
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
    
    public int stolperDECEMBER_nr_4hjoernerNede(boolean skurFindes, boolean ensAfstand, double pladsForNeden) {
        //hvis der ikke er skur: tilføj 2 stolper til PladsForneden
        //hvis der er skur og skuret ikke fylder det hele: tilføj 1 stolpe til plads for neden
        int output = 0;
        if (!skurFindes) {output = 2;}
        if (skurFindes) {output = 1;}
        if (skurFindes && pladsForNeden == 0) {output = 0;}
        return output;
    }
    
    public int stolperDECEMBER_nr_3hjoernerOppe(boolean skurFindes, boolean ensAfstand, double pladsForOven) {
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
    public Double stolperDECEMBER_nr_2pladsNeden() {
       double normalDist = this.carportLength - (2 * this.endeUdhæng);
       normalDist -= this.skurLength;     
       return normalDist;
    }
    
    public Double stolperDECEMBER_nr_1pladsOven() {
       double normalDist = this.carportLength - (2 * this.endeUdhæng);
       double skurPlads = this.carportWidth - (2 * this.sideUdhæng);
       if (this.skurWidth >= skurPlads) {
           normalDist -= this.skurLength;
       }       
       return normalDist;
    }
        
    public void stolpeLengthDECEMBER() {
        ArrayList<String> words = new ArrayList<String>();
        words.add("Stolper");
        ArrayList<LineItem> relevantItems = relevantBomLines(words, this.bom);
        String ØversteVenstreLength = relevantItems.get(0).getName().substring(3, 4);//9 from description: "97x97mm trykimp. stolpe,..."
        this.stolpeLength = Double.parseDouble(ØversteVenstreLength);
    }
    
    @Override
    public String stolper() {
        ArrayList<String> words = new ArrayList<String>();
        words.add("Stolper");
        ArrayList<LineItem> relevantItems = relevantBomLines(words, this.bom);
        //removeLineItemsFromBom(relevantItems);
        
        /*
        Eksempel på LineItem:
        stolper()
LineItem{name=97x97mm trykimp. stolpe, length=300, quantity=4, unit=stk, description=Stolper nedgraves 90cm i jord.}
        */
        
        // alle afstande regnes i centimeter
        
        //øverste-venstre stolpe.
        String ØversteVenstreWidth = relevantItems.get(0).getName().substring(0, 1);//9 from description: "97x97mm trykimp. stolpe,..."
        String ØversteVenstreLength = relevantItems.get(0).getName().substring(3, 4);//9 from description: "97x97mm trykimp. stolpe,..."
        this.stolpeLength = Double.parseDouble(ØversteVenstreLength);
        String ØversteVenstreLeft = Double.toString(endeUdhæng);
        String ØversteVenstreDistToTop = Double.toString(this.sideUdhæng);
        this.topLeftStolpeNederstVenstre_X = Double.parseDouble(ØversteVenstreLeft);
        this.topLeftStolpeNederstVenstre_Y = Double.parseDouble(ØversteVenstreWidth);
        this.topLeftStolpeØverstHøjre_X = Double.parseDouble(ØversteVenstreLength) + 
                                            Double.parseDouble(ØversteVenstreLeft);
        this.topLeftStolpeØverstHøjre_Y = Double.parseDouble(ØversteVenstreDistToTop);
                                        
        
        
        String output1 = String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                            "style=\"stroke:#000000; fill: #ff0000\"/>", 
                            ØversteVenstreLeft, ØversteVenstreDistToTop, ØversteVenstreWidth, ØversteVenstreLength);
        
        //øverste-højre stolpe.
        String ØversteHøjreWidth = relevantItems.get(0).getName().substring(0, 1);//9 from description: "97x97mm trykimp. stolpe,..."
        String ØversteHøjreLength = relevantItems.get(0).getName().substring(3, 4);//9 from description: "97x97mm trykimp. stolpe,..."
        String ØversteHøjreLeft = Double.toString(this.carportLength-
                                    Integer.parseInt(ØversteHøjreLength)-
                                    this.endeUdhæng);
        String ØversteHøjreDistToTop = Double.toString(this.sideUdhæng);
        this.topRightStolpeØverstVenstre_X = Double.parseDouble(ØversteHøjreLeft);
        this.topRightStolpeØverstVenstre_Y = Double.parseDouble(ØversteHøjreDistToTop);
        this.topRightStolpeNederstHøjre_X = Double.parseDouble(ØversteHøjreLeft) +
                                            Double.parseDouble(ØversteHøjreLength);
        this.topRightStolpeNederstHøjre_Y = Double.parseDouble(ØversteHøjreWidth);
        
        
        String output2 = String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                            "style=\"stroke:#000000; fill: #ff0000\"/>", 
                            ØversteHøjreLeft, ØversteHøjreDistToTop, ØversteHøjreWidth, ØversteHøjreLength);
        
        //nederste-venstre stolpe.
        String NedersteVenstreWidth = relevantItems.get(0).getName().substring(0, 1);//9 from description: "97x97mm trykimp. stolpe,..."
        String NedersteVenstreLength = relevantItems.get(0).getName().substring(3, 4);//9 from description: "97x97mm trykimp. stolpe,..."
        String NedersteVenstreLeft = Double.toString(this.endeUdhæng);
        String NedersteVenstreDistToTop = Double.toString(this.carportWidth-
                                    Integer.parseInt(NedersteVenstreWidth) - this.sideUdhæng);
        
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
                                    Integer.parseInt(NedersteHøjreLength)-this.endeUdhæng);
        String NedersteHøjreDistToTop = Double.toString(this.carportWidth-
                                    Integer.parseInt(NedersteHøjreWidth) - this.sideUdhæng);
        
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
        String x1 = Double.toString(this.topLeftStolpeØverstHøjre_X+this.bondDist);
        String y1 = Double.toString(this.topLeftStolpeØverstHøjre_Y);
        String x2 = Double.toString(this.bottomRightStolpeNederstVenstre_X );
        String y2 = Double.toString(this.bottomRightStolpeNederstVenstre_Y);
        output += String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" "
                + "stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/>", 
                            x1, y1, x2, y2);
        
        //kryds -- Top Left - Bottom Right --Nederst
        x1 = Double.toString(this.topLeftStolpeØverstHøjre_X);
        y1 = Double.toString(this.topLeftStolpeØverstHøjre_Y);
        x2 = Double.toString(this.bottomRightStolpeNederstVenstre_X - this.bondDist);
        y2 = Double.toString(this.bottomRightStolpeNederstVenstre_Y);
        output += String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" "
                + "stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/>", 
                            x1, y1, x2, y2);
        
        //kryds -- Bottom Left - Top Right --Øverst
        x1 = Double.toString(this.bottomLeftStolpeNederstHøjre_X);
        y1 = Double.toString(this.bottomLeftStolpeNederstHøjre_Y);
        x2 = Double.toString(this.topRightStolpeØverstVenstre_X- this.bondDist);
        y2 = Double.toString(this.topRightStolpeØverstVenstre_Y);
        output += String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" "
                + "stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/>", 
                            x1, y1, x2, y2);
        
        //kryds -- Bottom Left - Top Right --Nederst
        x1 = Double.toString(this.bottomLeftStolpeNederstHøjre_X+this.bondDist);
        y1 = Double.toString(this.bottomLeftStolpeNederstHøjre_Y);
        x2 = Double.toString(this.topRightStolpeØverstVenstre_X);
        y2 = Double.toString(this.topRightStolpeØverstVenstre_Y);
        output += String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" "
                + "stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"2, 5\"/>", 
                            x1, y1, x2, y2);
        
        return output;     
    }

    
    // main klasse til test.s
    
//    public static void main(String[] args) {
//        System.out.println("start");
//        Calculator calc = new CalculatorImpl();
//        
//        // BillOfMaterials bomCalculator(double length, double width, double height,
//        //    String type, String material, double angle,
//        //    double skurLength, double skurWidth)
//        
//        //BillOfMaterials bom = calc.bomCalculator(510, 510, 210,"fladt", "uden", 0, 0, 0);
//        //BillOfMaterials bom = calc.bomCalculator(510, 510, 210,"fladt", "uden", 210, 210, 210);
//        BillOfMaterials bom = calc.bomCalculator(510, 510, 210,"fladt", "uden", 210, 210, 510);
//        //BillOfMaterials bom = calc.bomCalculator(510, 510, 210,"fladt", "uden", 210, 210, 210);
//        //public DrawImplFlatAbove(BillOfMaterials bom, double carportWidth, double carportLength, double skurLength, double skurWidth) {
//        //DrawImplFlatAbove draw = new DrawImplFlatAbove(bom, 510, 510, 0, 0);
//        DrawImplFlatAbove draw = new DrawImplFlatAbove(bom, 510, 510, 210, 510);
//        //DrawImplFlatAbove draw = new DrawImplFlatAbove(bom, 510, 510, 210, 210);
//        
//        
//        System.out.println("****************  første bom liste:");
//        for (int k = 0; k < draw.bom.getBomList().size(); k++) {
//            System.out.println(draw.bom.getBomList().get(k));
//        }
//        
//        System.out.println("*******************************************************");
//        System.out.println("Her er det antal stolper der bliver tegnet (ikke det der er beregnet)");
//        System.out.println("stolper: " + draw.stolperDECEMBER());
//        
//        System.out.println("****************  result: bom liste:");
//        System.out.println(draw.remme());
//        System.out.println(draw.stolper());
//        System.out.println(draw.spaer());
//        System.out.println(draw.kryds());
//        
//        System.out.println("****************  anden bom liste:");
//        for (int k = 0; k < draw.bom.getBomList().size(); k++) {
//            System.out.println(draw.bom.getBomList().get(k));
//        }
//        
//        System.out.println("end");
//    }

   
    
    //******************************************************************
    // ******************************************************************
    //              Sepperat afsnit til skurmetoder.
    //********************************************************************
    //*******************************************************************
    
    // lav en beregning af dedikerede skurstolper (se tegning)
    
    public String skur() {
        //double Localskurlength = this.skurLength;
        //double LocalcarportLength = this.carportLength;
        //double LocalskurWidth = this.skurWidth;
        
        
        
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
        
        
            // ekstra stolpe til skuret. Øverst til Venstre.
            ArrayList<String> words = new ArrayList<String>();
            words.add("Stolper");
            ArrayList<LineItem> relevantItems = relevantBomLines(words, this.bom);
        
            String TopLeftWidth = relevantItems.get(0).getName().substring(0, 1);//9 from description: "97x97mm trykimp. stolpe,..."
            String TopLeftLength = relevantItems.get(0).getName().substring(3, 4);//9 from description: "97x97mm trykimp. stolpe,..."
            String TopLeftLeft = Double.toString(skurBottomleft_x);
            String TopLeftDistToTop = Double.toString(skurTopLeft_y);

            output += String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                                "style=\"stroke:#000000; fill: #ff0000\"/>", 
                                TopLeftLeft, TopLeftDistToTop, TopLeftWidth, TopLeftLength);
            
            // ekstra stolpe til skuret. Øverst til højre.
            String TopRightWidth = relevantItems.get(0).getName().substring(0, 1);//9 from description: "97x97mm trykimp. stolpe,..."
            String TopRightLength = relevantItems.get(0).getName().substring(3, 4);//9 from description: "97x97mm trykimp. stolpe,..."
            String TopRightLeft = Double.toString(skurBottomRight_x - Double.parseDouble(TopRightLength));
            String TopRightDistToTop = Double.toString(skurTopLeft_y);

            output += String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                                "style=\"stroke:#000000; fill: #ff0000\"/>", 
                                TopRightLeft, TopRightDistToTop, TopRightWidth, TopRightLength);
            
            // ekstra stolpe til skuret. Nederst til venstre.
            String EkstraWidth = relevantItems.get(0).getName().substring(0, 1);//9 from description: "97x97mm trykimp. stolpe,..."
            String EkstraLength = relevantItems.get(0).getName().substring(3, 4);//9 from description: "97x97mm trykimp. stolpe,..."
            String EkstraLeft = Double.toString(skurBottomleft_x);
            String EkstraDistToTop = Double.toString(skurBottomleft_y - Double.parseDouble(EkstraWidth));

            output += String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                                "style=\"stroke:#000000; fill: #ff0000\"/>", 
                                EkstraLeft, EkstraDistToTop, EkstraWidth, EkstraLength);
            
            
            
          
        // særskilte stolpe til nederste venstre hjørne: ******************************************
        //double antalStolper = relevantItems.get(0).getQuantity();
            if (skurWidth > 400) {
                //quantity += 5;
                
                // ekstra stolpe til skuret. Højre middte op.
                String RightMiddleWidth2 = relevantItems.get(0).getName().substring(0, 1);//9 from description: "97x97mm trykimp. stolpe,..."
                String RightMiddleLength2 = relevantItems.get(0).getName().substring(3, 4);//9 from description: "97x97mm trykimp. stolpe,..."
                String RightMiddleLeft2 = Double.toString(skurBottomRight_x - 
                                        Double.parseDouble(TopRightLength));
                double twoThirdsSkurWidth = ((this.skurWidth/3.0)*2.0) + 
                                        (Double.parseDouble(RightMiddleWidth2)/2.0);
                        //((Double.parseDouble(RightMiddleWidth2) /3.0)*2)-
                          //                          ((LocalskurWidth) / 3.0);
                String RightMiddleDistToTop2 = Double.toString(skurBottomRight_y -twoThirdsSkurWidth);
                
                output += String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                                    "style=\"stroke:#000000; fill: #ff0000\"/>", 
                                    RightMiddleLeft2, RightMiddleDistToTop2, RightMiddleWidth2, RightMiddleLength2);
                
                // ekstra stolpe til skuret. Højre middte op.
                String RightMiddleWidth1 = relevantItems.get(0).getName().substring(0, 1);//9 from description: "97x97mm trykimp. stolpe,..."
                String RightMiddleLength1 = relevantItems.get(0).getName().substring(3, 4);//9 from description: "97x97mm trykimp. stolpe,..."
                String RightMiddleLeft1 = Double.toString(skurBottomRight_x - 
                                        Double.parseDouble(TopRightLength));
                double oneThirdSkurWidth = ((this.skurWidth/3.0)) + 
                                        (Double.parseDouble(RightMiddleWidth2)/2.0);
                        //((Double.parseDouble(RightMiddleWidth2) /3.0)*2)-
                          //                          ((LocalskurWidth) / 3.0);
                String RightMiddleDistToTop1 = Double.toString(skurBottomRight_y -oneThirdSkurWidth);
                
                output += String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                                    "style=\"stroke:#000000; fill: #ff0000\"/>", 
                                    RightMiddleLeft1, RightMiddleDistToTop1, RightMiddleWidth1, RightMiddleLength1);
                
                // ekstra stolpe til skuret. Venstre midte.
                String LeftMiddleWidth = relevantItems.get(0).getName().substring(0, 1);//9 from description: "97x97mm trykimp. stolpe,..."
                String LeftMiddleLength = relevantItems.get(0).getName().substring(3, 4);//9 from description: "97x97mm trykimp. stolpe,..."
                String LeftMiddleLeft = Double.toString(skurBottomRight_x - this.skurLength);
                                        //Double.parseDouble(TopRightLength));
                String LeftMiddleDistToTop = Double.toString(skurBottomRight_y -
                                            ((Double.parseDouble(LeftMiddleWidth)+
                                                    this.skurWidth) / 2.0));

                output += String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                                    "style=\"stroke:#000000; fill: #ff0000\"/>", 
                                    LeftMiddleLeft, LeftMiddleDistToTop, LeftMiddleWidth, LeftMiddleLength);
                
            } else {
                // ekstra stolpe til skuret. Højre middte.
                String RightMiddleWidth = relevantItems.get(0).getName().substring(0, 1);//9 from description: "97x97mm trykimp. stolpe,..."
                String RightMiddleLength = relevantItems.get(0).getName().substring(3, 4);//9 from description: "97x97mm trykimp. stolpe,..."
                String RightMiddleLeft = Double.toString(skurBottomRight_x - 
                                        Double.parseDouble(TopRightLength));
                String RightMiddleDistToTop = Double.toString(skurBottomRight_y -
                                            ((Double.parseDouble(RightMiddleWidth)+
                                                    this.skurWidth) / 2.0));

                output += String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                                    "style=\"stroke:#000000; fill: #ff0000\"/>", 
                                    RightMiddleLeft, RightMiddleDistToTop, RightMiddleWidth, RightMiddleLength);
                
            }
            if (skurWidth != this.carportWidth-this.sideUdhæng-this.sideUdhæng) {
                //quantity += 2;
                // ekstra stolpe til skuret. Øverst midten.
                String TopMiddleWidth = relevantItems.get(0).getName().substring(0, 1);//9 from description: "97x97mm trykimp. stolpe,..."
                String TopMiddleLength = relevantItems.get(0).getName().substring(3, 4);//9 from description: "97x97mm trykimp. stolpe,..."
                String TopMiddleLeft = Double.toString(skurBottomRight_x - 
                                        ((Double.parseDouble(TopRightLength)+this.skurLength) / 2.0));
                String TopMiddleDistToTop = Double.toString(skurTopLeft_y);

                output += String.format("<rect x=\"%s\" y=\"%s\" height=\"%s\" width=\"%s\""+      
                                    "style=\"stroke:#000000; fill: #ff0000\"/>", 
                                    TopMiddleLeft, TopMiddleDistToTop, TopMiddleWidth, TopMiddleLength);
            }
        
        return output;  //"<p>"+skurLength + " " +skurWidth+"</p>";
    }
}
