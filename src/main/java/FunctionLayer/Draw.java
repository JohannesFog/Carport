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
public interface Draw {
    
    //sætter to strenge sammen i et billede
    public String merge(String svgOutside, String svgInside);
    
    //sætter to billeder sammen (den anden under den første) i den totale SVG.
    // Det er ikke sikkert denne metode skal bruges. det kommer an på hvordan tegn-stoler#41 implementeres
    //public String add(String svg);
    
    // tag set fra overn    
    public String tagFraOven(BillOfMaterials bom);
    
    public String remme(String svg);
    
    public String stolper(String svg);
    
    public String spaer(String svg);
    
    public String kryds(String svg);
    
    
}
