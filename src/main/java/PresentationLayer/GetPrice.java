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
import FunctionLayer.Order;
import FunctionLayer.User;
import static java.lang.Compiler.command;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mikkel Lindstrøm 
 */
public class GetPrice extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws DataMapperException {

        HttpSession session = request.getSession();
        
        //request
        double length = Double.parseDouble(request.getParameter("laengde"));
        double width = Double.parseDouble(request.getParameter("bredde"));
        double height = Double.parseDouble(request.getParameter("hoejde"));
        String type = request.getParameter("tagtype");
        String material = request.getParameter("tagmateriale");
        double angle = Double.parseDouble(request.getParameter("vinkel"));
        double skurWidth = Double.parseDouble(request.getParameter("skurbredde"));
        double skurLength = Double.parseDouble(request.getParameter("skurlaengde"));
        
        Order order = new Order(length,width,height,angle,skurWidth,skurLength,"draft");
        session.setAttribute("order",order);
        
        
        BillOfMaterials bom = LogicFacade.getBillOfMaterials(order);
        session.setAttribute( "bom", bom );

        double price = LogicFacade.getCarportPrice(bom);
        session.setAttribute( "price", price );      

        String draw = LogicFacade.getDrawing(bom, order);
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
