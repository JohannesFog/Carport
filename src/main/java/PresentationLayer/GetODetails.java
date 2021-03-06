/*
 */
package PresentationLayer;

import FunctionLayer.Exceptions.DataMapperException;
import FunctionLayer.LogicFacade;
import FunctionLayer.Entities.Order;
import FunctionLayer.Entities.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Christian, Gert, Lene & Mikkel
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
