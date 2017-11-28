package FunctionLayer;

import DBAccess.OrderMapper;
import DBAccess.UserMapper;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The purpose of LogicFacade is to...
 * @author kasper
 */
public class LogicFacade {

    public static User login( String email, String password ) throws LoginSampleException {
        return UserMapper.login( email, password );
    } 

    public static User createUser( String email, String password ) throws LoginSampleException {
        User user = new User(email, password, "customer");
        UserMapper.createUser( user );
        return user;
    }
    
    public static void createOrder(double length, double width, double height, 
            String roof, double roofAngle,double shedWidth, double shedLength, int phone) throws LoginSampleException{
      Date date = new Date();
      SimpleDateFormat sdrf = new SimpleDateFormat("yyyy-MM-dd");
      String dateString = sdrf.format(date);
      
      Order order = new Order(length,width,height,roof,roofAngle,shedWidth,shedLength,dateString,phone);
      OrderMapper.createOrder(order);
    }

}
