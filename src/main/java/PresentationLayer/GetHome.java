/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import Exceptions.DataMapperException;
import FunctionLayer.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mikkel Lindstrøm
 */
public class GetHome extends Command{
    
    public GetHome(){
        
    }
    
    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws DataMapperException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        String from = request.getParameter("from");
        
        if(from.equals("home")){
        if(user!=null){
            return user.getRole() + "page";
        }
        return "../index";
        }else if(from.equals("logout")){
            user = null;
            session.setAttribute("user", user);
            return "../index";
        }else 
        return "../index";
    }
    
    
    
}
