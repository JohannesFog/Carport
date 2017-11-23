/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import FunctionLayer.BillOfMaterials;
import FunctionLayer.Calculator;
import FunctionLayer.CalculatorImpl;
import FunctionLayer.LogicFacade;
import FunctionLayer.LoginSampleException;
import FunctionLayer.User;
import static java.lang.Compiler.command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mikkel Lindstrøm <Mikkel.Lindstrøm>
 */
public class GetPrice extends Command {
   
    @Override
    String execute( HttpServletRequest request, HttpServletResponse response ) throws LoginSampleException {
        
        HttpSession session = request.getSession();
        String længde = request.getParameter( "laengde" );
        double length = Double.parseDouble(længde);
        session.setAttribute( "laengde", length );
        String bredde = request.getParameter( "bredde" );
        double width = Double.parseDouble(bredde);
        session.setAttribute( "bredde", width );
        String højde = request.getParameter( "hoejde" );
        double height = Double.parseDouble(højde);
        session.setAttribute( "hoejde", height );
        String tagtype = request.getParameter( "tagtype" );
        session.setAttribute( "tagtype", tagtype );
        
        String skurBredde = request.getParameter( "skurbredde" );
        double skurWidth = Double.parseDouble(skurBredde);
        session.setAttribute( "skurbredde", skurWidth );
        
        String skurLængde = request.getParameter( "skurlaengde" );
        double skurLength = Double.parseDouble(skurLængde);
        session.setAttribute( "skurlaengde", skurLength );
        
//        session.setAttribute( "skur", skur );
        
        Calculator calc = new CalculatorImpl();
        BillOfMaterials bom = calc.bomCalculator(length, width, height, tagtype, skurLength, skurWidth);
        double price = calc.calculatePrice(bom);
        
        String draw = "";
                
        if (tagtype.equals("fladt")) {
            DrawImplFlatAbove drawFlatAbove = new DrawImplFlatAbove(bom, width, length); 
            String drawingFlatAbove = drawFlatAbove.tegnTag(750, 750);
            DrawImplFlatSide drawFlatSide = new DrawImplFlatSide(bom, width, length, height);
            String drawintFlatSide = drawFlatSide.tegnTag(750, 750);
            draw = drawingFlatAbove + drawintFlatSide;
        } else {
            draw = "ingen support for skraat tag endnu";
        }
        
        session.setAttribute( "price", price );
        session.setAttribute( "bom", bom );
        session.setAttribute( "draw", draw );
        
        return "pricepage";
    }

}

