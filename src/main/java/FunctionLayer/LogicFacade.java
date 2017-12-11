package FunctionLayer;

import DBAccess.Connector;
import DBAccess.MaterialMapper;
import Exceptions.DataMapperException;
import DBAccess.OrderMapper;
import DBAccess.UserMapper;
import PresentationLayer.DrawImplFlatAbove;
import PresentationLayer.DrawImplFlatSide;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    /*
    public static void createOrder(double length, double width, double height, 
           double roofAngle,double shedWidth, double shedLength, int phone) throws DataMapperException{
      Date date = new Date();
      SimpleDateFormat sdrf = new SimpleDateFormat("yyyy-MM-dd");
      String dateString = sdrf.format(date);
      
      Order order = new Order(length,width,height,roofAngle,shedWidth,shedLength,dateString,phone);
      OrderMapper.createOrder(order);
    }
     */
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
        
        if (angle == 0.0) {
            DrawImplFlatAbove drawFlatAbove = new DrawImplFlatAbove(bom, width, length, skurLength, skurWidth);

            String drawingFlatAbove = drawFlatAbove.tegnTag(750, 750);
            DrawImplFlatSide drawFlatSide = new DrawImplFlatSide(bom, width, length, height, skurLength, skurWidth);
            String drawintFlatSide = drawFlatSide.tegnTag(750, 750, drawingFlatAbove,
                    drawFlatAbove.XkoorLeftOppe, drawFlatAbove.XkoorLeftNede);

            draw = drawingFlatAbove + drawintFlatSide;

        } else {
            //DrawImplFlatAbove drawFlatAbove = new DrawImplFlatAbove(bom, width, length, skurLength, skurWidth); 
            //String drawingFlatAbove = drawFlatAbove.tegnTag(750, 750);
            draw = "Ingen support for tegninger af skråt tag endnu";
            //C:\chrdiv
        }

        return draw;
    }

}
