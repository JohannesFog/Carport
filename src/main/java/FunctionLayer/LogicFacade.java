package FunctionLayer;

import FunctionLayer.Entities.BillOfMaterials;
import FunctionLayer.Entities.User;
import FunctionLayer.Entities.Order;
import DBAccess.MaterialMapper;
import FunctionLayer.Exceptions.DataMapperException;
import DBAccess.OrderMapper;
import DBAccess.UserMapper;
import PresentationLayer.DrawImplFlatAbove;
import PresentationLayer.DrawImplFlatSide;
import java.util.ArrayList;

/**
 * The purpose of LogicFacade is to...
 *
 * @author kasper
 */
public class LogicFacade {

    public static User login(String email, String password) throws DataMapperException {
        return UserMapper.login(email, password);
    }

    public static User createUser(int phone, String email, String password, String name, String address, int zip, String role) throws DataMapperException {
        User user = new User(phone, email, password, name, address, zip, role);
        UserMapper.createUser(user);
        return user;
    }

    public static User createNewUserWithoutPassword(int phone, String email, String password, String name, String address, int zip, String role) throws DataMapperException {
        User user = new User(phone, email, null, name, address, zip, role);
        UserMapper.createUser(user);
        return user;
    }

    public static void createOrder(Order order) throws DataMapperException {
        OrderMapper.createOrder(order);
    }

    public static ArrayList<Order> getAllOrdersEmp() throws DataMapperException {
        return OrderMapper.getAllOrders();
    }

    public static double getMaterialPrice(String name) throws DataMapperException {
        return MaterialMapper.getPrice(name);
    }

    public static ArrayList<Order> getAllOrdersUser(User user) throws DataMapperException {
       ArrayList<Order> order = OrderMapper.getAllUserOrders(user);
       return order;
    }

    public static void doConfirmOrder(int id) throws DataMapperException {
        OrderMapper.confirmOrder(id);
    }

    public static Order getOrderById(int id) throws DataMapperException {
        return OrderMapper.getSingleOrder(id);
    }

    public static BillOfMaterials getBillOfMaterials(Order order) throws DataMapperException {
        Calculator calc = new CalculatorImpl();
        return calc.bomCalculator(order);
    }

    public static double getCarportPrice(BillOfMaterials bom) {
        Calculator calc = new CalculatorImpl();
        return calc.calculatePrice(bom);
    }

    public static String getDrawing(BillOfMaterials bom, Order order) {
        String draw = "";
        
        double length = order.getLength();
        double width = order.getWidth();
        double height = order.getHeight();
        double angle = order.getRoofAngle();
        double skurLength = order.getShedLength();
        double skurWidth = order.getShedWidth();
        int graphYUp = (int) width;
        int graphYSide = (int) height;
        int graphX = (int) length;
        
        if (angle == 0.0) {
            DrawImplFlatAbove drawFlatAbove = new DrawImplFlatAbove(bom, width, length, skurLength, skurWidth);

            String drawingFlatAbove = drawFlatAbove.tegnTag(graphX, graphYUp);
            DrawImplFlatSide drawFlatSide = new DrawImplFlatSide(bom, length, height, skurLength, skurWidth);
            String drawintFlatSide = drawFlatSide.tegnTag(graphX, graphYSide, drawingFlatAbove,
                    drawFlatAbove.XkoorLeftOppe, drawFlatAbove.XkoorLeftNede);

            draw = drawingFlatAbove + "<br><br>" + drawintFlatSide + "<br>";

        } else {
            //DrawImplFlatAbove drawFlatAbove = new DrawImplFlatAbove(bom, width, length, skurLength, skurWidth); 
            //String drawingFlatAbove = drawFlatAbove.tegnTag(750, 750);
            draw = "Ingen support for tegninger af skråt tag endnu";
            //C:\chrdiv
        }

        return draw;
    }

}
