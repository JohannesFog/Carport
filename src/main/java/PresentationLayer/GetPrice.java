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
    String execute(HttpServletRequest request, HttpServletResponse response) throws LoginSampleException {

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
        double angle = 0.0;
        if (type.equals("skråt")) {
            String vinkel = request.getParameter("vinkel");
            angle = Double.parseDouble(vinkel);
        } 
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

        session.setAttribute( "price", price );
        session.setAttribute( "bom", bom );



        return "pricepage";
    }

}
