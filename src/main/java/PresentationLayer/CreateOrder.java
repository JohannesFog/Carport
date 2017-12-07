/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import FunctionLayer.LogicFacade;
import Exceptions.DataMapperException;
import FunctionLayer.Order;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lene_
 */
public class CreateOrder extends Command {
    
    
    public CreateOrder() {
    }

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws DataMapperException {
        HttpSession session = request.getSession();
        
        //Order
//        double length = (double) session.getAttribute("laengde");
//        double width = (double) session.getAttribute("bredde");
//        double height = (double) session.getAttribute("hoejde");
//        //String roof = (String) session.getAttribute("tagtype");
//        //double roofAngle = 0;
//        double roofAngle = (double) session.getAttribute("vinkel");
//        double shedWidth = (double) session.getAttribute("skurbredde");
//        double shedLength = (double) session.getAttribute("skurlaengde");
        
        Order order = (Order) session.getAttribute("order");
        
        //I den nuværende order i session er status=draft, derfor ændrer vi status nu
        order.setStatus("unconfirmed");
        
        //Order tempOrder = null;
        
        //indsætter ordredatoen
        Date date = new Date();
        SimpleDateFormat sdrf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdrf.format(date);
        order.setOrderDate(dateString);
        
        String role = (String) session.getAttribute("role");
        
        if ( role == null || role.equals("employee") ){
          String name = request.getParameter("name");
          String adresse = request.getParameter("address");
          String zip = request.getParameter("zip");
          int tlf = Integer.parseInt(request.getParameter("tlf"));
          String email = request.getParameter("email");
          String notice = request.getParameter("notice");
          
          LogicFacade.createNewUserWithoutPassword(tlf, email, adresse, name, adresse, tlf, role);
          
          //tempOrder = new Order(length, width, height, roofAngle, shedWidth, shedLength, dateString, tlf);
          LogicFacade.createOrder(order); 
          
        }else{
            int tlf = Integer.parseInt(request.getParameter("tlf"));
            //tempOrder = new Order(length, width, height, roofAngle, shedWidth, shedLength, dateString, tlf);
            LogicFacade.createOrder(order);   
        }
        return "confirmationpage";
    }
    
}
