package FunctionLayer;

import DBAccess.MaterialMapper;
import Exceptions.DataMapperException;
import DBAccess.OrderMapper;
import DBAccess.UserMapper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * The purpose of LogicFacade is to...
 * @author kasper
 */
public class LogicFacade {

    public static User login( String email, String password ) throws DataMapperException {
        return UserMapper.login( email, password );
    } 

    public static User createUser(int phone, String email, String password, String name, String address, int zip, String role) throws DataMapperException {
        User user = new User(phone,email,password,name,address,zip,role);
        UserMapper.createUser( user );
        return user;
    }
    
     public static User createNewUserWithoutPassword(int phone, String email, String password, String name, String address, int zip,String role ) throws DataMapperException {
        User user = new User(phone,email,null,name,address,zip,role);
        UserMapper.createUser( user );
        return user;
    }
    
    
    
    public static void createOrder(double length, double width, double height, 
           double roofAngle,double shedWidth, double shedLength, int phone) throws DataMapperException{
      Date date = new Date();
      SimpleDateFormat sdrf = new SimpleDateFormat("yyyy-MM-dd");
      String dateString = sdrf.format(date);
      
      Order order = new Order(length,width,height,roofAngle,shedWidth,shedLength,dateString,phone);
      OrderMapper.createOrder(order);
    }

    public static ArrayList<Order> getAllOrdersEmp() throws DataMapperException{
        return OrderMapper.getAllOrders();
    }
    
    public static double getPrice(String name) throws DataMapperException{
        return MaterialMapper.getPrice(name);
    }
    
}

