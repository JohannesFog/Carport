/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import FunctionLayer.BillOfMaterials;
import FunctionLayer.Calculator;
import FunctionLayer.CalculatorImpl;
import FunctionLayer.Draw;
import FunctionLayer.DrawImplFlat;
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
        String skur = request.getParameter( "skur" );
        session.setAttribute( "skur", skur );
        
        Calculator calc = new CalculatorImpl();
        BillOfMaterials bom = calc.bomCalculator(length, width, height,tagtype, skur);
        double price = calc.calculatePrice(bom);
        
        DrawImplFlat draw = new DrawImplFlat(bom, width, length); //DrawImplFlat skal tage en bom som argument
        String drawing = draw.tagFraOven(900, 900);
        
        session.setAttribute( "price", price );
        session.setAttribute( "bom", bom );
        session.setAttribute( "drawing", drawing );
        return "pricepage";
    }

}

