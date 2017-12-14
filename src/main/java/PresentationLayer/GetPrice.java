/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import FunctionLayer.Entities.BillOfMaterials;
import FunctionLayer.LogicFacade;
import FunctionLayer.Exceptions.DataMapperException;
import FunctionLayer.Entities.Order;
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
        double angle = Double.parseDouble(request.getParameter("vinkel"));
        double skurWidth = Double.parseDouble(request.getParameter("skurbredde"));
        double skurLength = Double.parseDouble(request.getParameter("skurlaengde"));
        
        //Vi bruger ikke denne på nuværende tidspunkt, men lader den stå til fremtidig brug
        String material = request.getParameter("tagmateriale");
        
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
}
