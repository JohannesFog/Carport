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
        
        String længde = request.getParameter( "laengde" );
        double length = Double.parseDouble(længde);
        String bredde = request.getParameter( "bredde" );
        double width = Double.parseDouble(bredde);
        Calculator calc = new CalculatorImpl();
        BillOfMaterials bom = calc.bomCalculator(length, width);
        double price = calc.calculatePrice(bom);
        HttpSession session = request.getSession();
        session.setAttribute( "price", price );
        session.setAttribute( "bom", bom );
        return "pricepage";
    }

}

