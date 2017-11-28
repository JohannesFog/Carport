/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import FunctionLayer.LogicFacade;
import FunctionLayer.LoginSampleException;
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
    String execute(HttpServletRequest request, HttpServletResponse response) throws LoginSampleException {
        HttpSession session = request.getSession();
        double length = (double) session.getAttribute("laengde");
        double width = (double) session.getAttribute("bredde");
        double height = (double) session.getAttribute("hoejde");
        String roof = (String) session.getAttribute("tagtype");
        double roofAngle = 0;
        if(session.getAttribute("vinkel") != null){
            roofAngle = (double) session.getAttribute("vinkel");
        }
        double shedWidth = (double) session.getAttribute("skurbredde");
        double shedLength = (double) session.getAttribute("skurlaengde");
        String name = request.getParameter("name");
        String phonenr = request.getParameter("phone");
        int phone = Integer.parseInt(phonenr);
        
        session.setAttribute("name",name);
        
        LogicFacade.createOrder(length, width, height, roofAngle, shedWidth, shedLength, phone);
        
        return "confirmationpage";
    }
    
}
