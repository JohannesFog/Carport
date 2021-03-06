package PresentationLayer;

import FunctionLayer.LogicFacade;
import FunctionLayer.Exceptions.DataMapperException;
import FunctionLayer.Entities.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 @author Christian, Gert, Lene & Mikkel
 */
public class Login extends Command {

    @Override
    String execute( HttpServletRequest request, HttpServletResponse response ) throws DataMapperException {
        String email = request.getParameter( "email" );
        String password = request.getParameter( "password" );
        User user = LogicFacade.login( email, password );
        HttpSession session = request.getSession();
        session.setAttribute( "user", user );
        return user.getRole() + "page";
    }

}
