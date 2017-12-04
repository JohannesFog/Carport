/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import FunctionLayer.LogicFacade;
import Exceptions.DataMapperException;
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
        
        String role = (String) session.getAttribute("role");
        
        if ( role == null || role.equals("employee") ){
          String name = request.getParameter("name");
          String adresse = request.getParameter("address");
          String zip = request.getParameter("zip");
          int tlf = Integer.parseInt(request.getParameter("tlf"));
          String email = request.getParameter("email");
          String notice = request.getParameter("notice");
          
          LogicFacade.createNewUserWithoutPassword(tlf, email, adresse, name, adresse, tlf, role);
          LogicFacade.createOrder(length, width, height, roofAngle, shedWidth, shedLength, tlf); 
        }else{
            int tlf = Integer.parseInt(request.getParameter("tlf"));
            LogicFacade.createOrder(length, width, height, roofAngle, shedWidth, shedLength, tlf);   
        }
        return "confirmationpage";
    }
    
}
