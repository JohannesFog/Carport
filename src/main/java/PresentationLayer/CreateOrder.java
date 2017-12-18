/*
 */
package PresentationLayer;

import FunctionLayer.LogicFacade;
import FunctionLayer.Exceptions.DataMapperException;
import FunctionLayer.Entities.Order;
import FunctionLayer.Entities.User;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Christian, Gert, Lene & Mikkel
 */
public class CreateOrder extends Command {

    public CreateOrder() {
    }

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws DataMapperException {
        HttpSession session = request.getSession();

        Order order = (Order) session.getAttribute("order");

        //I den nuværende order i session er status=draft, derfor ændrer vi status nu
        order.setStatus("unconfirmed");

        //indsætter ordredatoen
        Date date = new Date();
        SimpleDateFormat sdrf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdrf.format(date);
        order.setOrderDate(dateString);

        User user = (User) session.getAttribute("user");
        String role = "";
        if (user != null) {
            role = user.getRole();
        }

        if (user == null || role.equals("employee")) {
            String name = request.getParameter("name");
            String adresse = request.getParameter("address");
            String zip = request.getParameter("zip");
            int tlf = Integer.parseInt(request.getParameter("tlf"));
            String email = request.getParameter("email");
            String notice = request.getParameter("notice");
            role = "customer";

            LogicFacade.createNewUserWithoutPassword(tlf, email, adresse, name, adresse, tlf, role);
            
            order.setPhone(tlf);
            LogicFacade.createOrder(order);

        } else {
            int tlf = Integer.parseInt(request.getParameter("tlf"));

            order.setPhone(tlf);
            LogicFacade.createOrder(order);
        }
        return "confirmationpage";
    }

}
