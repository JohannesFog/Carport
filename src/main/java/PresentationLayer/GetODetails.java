/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import Exceptions.DataMapperException;
import FunctionLayer.LogicFacade;
import FunctionLayer.Order;
import FunctionLayer.User;
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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        String from = request.getParameter("from");
        
        if(from.equals("customerpage")){
            return "customerOrderList";
        }else if (from.equals("cust") || from.equals("emp") ){
            
        String id = request.getParameter("orderId");
        int oId = Integer.parseInt(id);
        Order order = LogicFacade.getOrderById(oId);
        session.setAttribute("order", order);
        return "odetails"+ user.getRole();
        }
        return user.getRole() + "page";
        }
        
    }
