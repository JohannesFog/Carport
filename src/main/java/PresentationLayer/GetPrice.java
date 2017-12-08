/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import java.io.*;
import java.util.*;
import FunctionLayer.BillOfMaterials;
import FunctionLayer.Calculator;
import FunctionLayer.CalculatorImpl;
import FunctionLayer.LogicFacade;
import Exceptions.DataMapperException;
import FunctionLayer.User;
import static java.lang.Compiler.command;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mikkel Lindstrøm <Mikkel.Lindstrøm>
 */
public class GetPrice extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws DataMapperException {

        HttpSession session = request.getSession();
        String længde = request.getParameter("laengde");
        double length = Double.parseDouble(længde);
        session.setAttribute("laengde", length);
        String bredde = request.getParameter("bredde");
        double width = Double.parseDouble(bredde);
        session.setAttribute("bredde", width);
        String højde = request.getParameter("hoejde");
        double height = Double.parseDouble(højde);
        session.setAttribute("hoejde", height);

        String type = request.getParameter("tagtype");
        session.setAttribute("tagtype", type);
        String material = request.getParameter("tagmateriale");
        session.setAttribute("tagmateriale", material);
        String vinkel = request.getParameter("vinkel");
        double angle = Double.parseDouble(vinkel);

        session.setAttribute("vinkel", angle);

        String skurBredde = request.getParameter("skurbredde");
        double skurWidth = Double.parseDouble(skurBredde);
        session.setAttribute("skurbredde", skurWidth);
        String skurLængde = request.getParameter("skurlaengde");
        double skurLength = Double.parseDouble(skurLængde);
        session.setAttribute("skurlaengde", skurLength);

//        session.setAttribute( "skur", skur );
        Calculator calc = new CalculatorImpl();
        BillOfMaterials bom = calc.bomCalculator(length, width, height, type, material, angle, skurLength, skurWidth);
        double price = calc.calculatePrice(bom);
        
        String draw = "";
                
        if (type.equals("fladt")) {
            DrawImplFlatAbove drawFlatAbove = new DrawImplFlatAbove(bom, width, length, skurLength, skurWidth); 

            String drawingFlatAbove = drawFlatAbove.tegnTag(750, 750);
            DrawImplFlatSide drawFlatSide = new DrawImplFlatSide(bom, width, length, height, skurLength, skurWidth);
            String drawintFlatSide = drawFlatSide.tegnTag(750, 750, drawingFlatAbove, 
                    drawFlatAbove.XkoorLeftOppe, drawFlatAbove.XkoorLeftNede);
            
            draw = drawingFlatAbove + drawintFlatSide;
            
        } else {
            //DrawImplFlatAbove drawFlatAbove = new DrawImplFlatAbove(bom, width, length, skurLength, skurWidth); 
            //String drawingFlatAbove = drawFlatAbove.tegnTag(750, 750);
            draw = "ingen support for skraat tag endnu";
            //C:\chrdiv
        }
        
        session.setAttribute( "price", price );
        session.setAttribute( "bom", bom );
        session.setAttribute( "draw", draw );
        
        return "pricepage";
    }

    
    /*
            //****write to file -- debugging
            BufferedWriter out = null;
            try {
                out = new BufferedWriter(new FileWriter("C:\\chrdiv\\test.txt"));
            } catch (IOException ex) {
                Logger.getLogger(GetPrice.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                out.write(drawingFlatAbove);  //Replace with the string 
                                                         //you are trying to write
            }
            catch (IOException e)
            {
                System.out.println("Exception write to file");

            }
            finally
            {
                try {
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(GetPrice.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
    */
}
