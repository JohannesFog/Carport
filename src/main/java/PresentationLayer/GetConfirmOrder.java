/*
 */
package PresentationLayer;

import FunctionLayer.Exceptions.DataMapperException;
import FunctionLayer.LogicFacade;
import FunctionLayer.Entities.Order;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Christian, Gert, Lene & Mikkel
 */
public class GetConfirmOrder extends Command {

    public GetConfirmOrder() {
    }

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws DataMapperException {
        String oId = request.getParameter("orderId");
        String from = request.getParameter("from");
        int id = Integer.parseInt(oId);
       
        LogicFacade.doConfirmOrder(id);
        Order order = LogicFacade.getOrderById(id);
        HttpSession session = request.getSession();
        session.setAttribute("order", order);
        
        if (from.equals("emp")){
            return "employeepage";
        }else if (from.equals("empDetail")){
            return "odetailsemployee";
        }else
            return "odetailsemployee";
        }
        
        
    }
    
