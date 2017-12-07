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
 * @author lene_
 */
public class GetODetails extends Command {

    public GetODetails() {
    }

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws DataMapperException {
        String id = request.getParameter("orderId");
        int oId = Integer.parseInt(id);
        Order order = LogicFacade.getOrderById(oId);
        HttpSession session = request.getSession();
        session.setAttribute("order", order);
        return "odetailsemployee";
    }
    
}