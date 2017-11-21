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
        String shed = (String) session.getAttribute("skur");
        String name = request.getParameter("name");
        
        session.setAttribute("name",name);
        
        LogicFacade.createOrder(length, width, height, roof, shed, name);
        
        return "confirmationpage";
    }
    
}
