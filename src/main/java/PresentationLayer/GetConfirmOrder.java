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
 * @author Mikkel Lindstrøm 
 */
public class GetConfirmOrder extends Command {

    public GetConfirmOrder() {
    }

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws DataMapperException {
        String oId = request.getParameter("orderId");
        String from = request.getParameter("from");
        int id = Integer.parseInt(oId);
       
        LogicFacade.doConfirmOrder(id);
        Order order = LogicFacade.getOrderById(id);
        HttpSession session = request.getSession();
        session.setAttribute("order", order);
        
        if (from.equals("fromEmpList")){
            return "employeepage";
        }else 
            return "odetailsemployee";
        }
        
        
    }
    
