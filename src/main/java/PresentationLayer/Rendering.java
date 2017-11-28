/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import FunctionLayer.BillOfMaterials;
import FunctionLayer.LineItem;
import FunctionLayer.LogicFacade;
import FunctionLayer.Order;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author Christian
 */
public class Rendering {

    public String getHTML(double price, BillOfMaterials bom) {
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.CEILING);
        String output = ""
                + "<h1>Velkommen til Pricepage</h1>"
                + "<p>" + df.format(price) + ",- kr." + "</p>";
        ArrayList<LineItem> items = bom.getBomList();
        for (LineItem li : items) {
            output = output + "<p>" + li.toString() + "</p>";
        }

        return output;
    }

    public String getNicerHTML(double price, BillOfMaterials bom) {
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.CEILING);
        StringBuilder sb = new StringBuilder();
        sb.append("<h1>Velkommen til Pricepage</h1>");
        sb.append("<h3>" + df.format(price) + ",- kr." + "</h3>");
        ArrayList<LineItem> items = bom.getBomList();
        sb.append("<table>");
        sb.append("<tr><th>Navn</th><th>Længde</th><th>Antal</th><th>Enhed</th><th>Beskrivelse</th></tr>");
        for (LineItem li : items) {
            sb.append("<tr>");
            sb.append("<td>" + li.getName() + "</td>");
            if (li.getLength() == 0) {
                sb.append("<td></td>");
            } else {
                sb.append("<td>" + li.getLength() + "</td>");
            }

            sb.append("<td>" + li.getQuantity() + "</td><td>" + li.getUnit() + "</td>"
                    + "<td>" + li.getDescription() + "</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");

        return sb.toString();
    }
    
     public String getOrderlistTable() {
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.CEILING);
        StringBuilder sb = new StringBuilder();
        sb.append("<h1>Velkommen til Orderlisten</h1>");
        ArrayList<Order> orders = LogicFacade.getAllOrdersEmp();
        sb.append("<table>");
        sb.append("<tr><th>Telefon</th><th>Status</th><th>Dato</th>");
        for (Order o : orders) {
            sb.append("<tr>");
            sb.append("<td>" + o.getPhone() + "</td>");
            sb.append("<td>" + o.getStatus() + "</td>"
                    + "<td>" + o.getOrderDate() + "</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");

        return sb.toString();
    }
    
    
    
    
    
}
