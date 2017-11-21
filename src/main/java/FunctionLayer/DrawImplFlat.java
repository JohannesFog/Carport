/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FunctionLayer;

/**
 *
 * @author Christian
 */
public class DrawImplFlat implements Draw{

    @Override
    public String merge(String svgOutside, String svgInside) {
        String input = svgOutside;
        String output = "";
        output = input.substring(0,input.length() - 6);
        output = output + svgInside;
        output = output + "</SVG>";
        return output;
    }
    
    //public String add(String svg){
     //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    //}
    
    @Override
    public String tagFraOven(BillOfMaterials bom) {
        String output = "<SVG width=\"1000\" height=600>"
                + "<rect x=\"000\" y=\"00\" height=\"600\" width=\"1000\"\n" +
"                    style=\"stroke:#000000; fill: #ffff00\"/>"
                + "</SVG>";
        
        String mystr2 = merge(output, "<rect x=\"000\" y=\"40\" height=\"5\" width=\"565\"\n" +
                                            "style=\"stroke:#000000; fill: #ff0000\"/>");
        
        return mystr2;
    }

    @Override
    public String remme(String svg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String stolper(String svg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String spaer(String svg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String kryds(String svg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void main(String[] args) {
        DrawImplFlat myflat = new DrawImplFlat();
        
        //String mystr = myflat.tagFraOven("");
        //String mystr2 = myflat.merge(mystr, "<rect x=\"000\" y=\"40\" height=\"5\" width=\"565\"\n" +
        //                                    "style=\"stroke:#000000; fill: #ff0000\"/>");
        //System.out.println(mystr);
        //System.out.println(mystr2);
    }
    
}
