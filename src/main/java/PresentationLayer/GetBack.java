/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import FunctionLayer.Entities.User;
import FunctionLayer.Exceptions.DataMapperException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mikkel Lindstrøm
 */
public class GetBack extends Command {

    public GetBack() {

    }

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws DataMapperException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String from = request.getParameter("from");

        if (from.equals("home")) {
            if (user != null) {
                return user.getRole() + "page";
            }
        } else if (from.equals("logout")) {
            user = null;
            session.setAttribute("user", user);
        }
        return "../index";

    }

}
