/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import FunctionLayer.BillOfMaterials;
import FunctionLayer.LineItem;
import java.util.ArrayList;

/**
 *
 * @author Christian
 */
public class Rendering {    
    
    public String getHTML(double price, BillOfMaterials bom) {
        String output = ""
                + "<h1>Velkommen til Pricepage</h1>"
                + "<p>"+ price + " kr."+"</p>";
        ArrayList<LineItem> items = bom.getBomList();
        for (LineItem li: items) {
            output = output + "<p>"+li.toString()+"</p>";
        }
                
        return output;
    }
}
