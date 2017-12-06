/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import Exceptions.DataMapperException;
import FunctionLayer.LogicFacade;
import FunctionLayer.Order;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mikkel Lindstrøm <Mikkel.Lindstrøm>
 */
public class GetConfirmOrder extends Command {

    public GetConfirmOrder() {
    }

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws DataMapperException {
        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute("order");
        LogicFacade.doConfirmOrder(order);
        
        return "odetailsemployee";
    }
    
}