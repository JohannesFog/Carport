/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

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
        
        String længde = request.getParameter( "længde" );
        double length = Double.parseDouble(længde);
        String bredde = request.getParameter( "bredde" );
        double width = Double.parseDouble(bredde);
        String højde = request.getParameter( "højde" );
        double height = Double.parseDouble(længde);
        return "pricepage";
    }

}

