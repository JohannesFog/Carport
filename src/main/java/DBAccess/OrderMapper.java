/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBAccess;

import Exceptions.DataMapperException;
import FunctionLayer.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lene_
 */
public class OrderMapper {

    public static void createOrder(Order order) throws DataMapperException {
        try{
            Connection con = Connector.connection();
            String SQL = "INSERT INTO `orders`(`length`,`width`,`height`,`roof_angle`,`shed_width`,`shed_length`,`orderdate`,`phonenumber`,`status`) "
                    + "VALUES (?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement ps = con.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, order.getLength());
            ps.setDouble(2, order.getWidth());
            ps.setDouble(3, order.getHeight());
            ps.setDouble(4, order.getRoofAngle());
            ps.setDouble(5, order.getShedWidth());
            ps.setDouble(6, order.getShedLength());
            ps.setString(7,order.getOrderDate());
            ps.setInt(8, order.getPhone());
            ps.setString(9, order.getStatus());
            ps.executeUpdate();
            
            ResultSet ids = ps.getGeneratedKeys();
            ids.next();
            int id = ids.getInt(1);
            order.setoId(id);
        }catch(SQLException | ClassNotFoundException ex){
            throw new DataMapperException(ex.getMessage());
        }
    }
    
    public static ArrayList<Order> getAllOrders() throws DataMapperException{
        try{
           ArrayList<Order> orders = new ArrayList<Order>();
           Connection con = Connector.connection();
           String SQL = "SELECT * FROM `orders`";
           PreparedStatement ps = con.prepareStatement(SQL);
           ResultSet rs = ps.executeQuery();
           while(rs.next()){
               int id = rs.getInt("id");
               double length = rs.getDouble("length");
               double width = rs.getDouble("width");
               double height = rs.getDouble("height");
               double roofAngle = rs.getDouble("roof_angle");
               double shedWidth = rs.getDouble("shed_width");
               double shedLength = rs.getDouble("shed_length");
               String orderDate = rs.getString("orderdate");
               int phone = rs.getInt("phonenumber");
               String status = rs.getString("status");
               
               Order order = new Order(length, width, height, roofAngle, shedWidth, shedLength, orderDate, phone,status);
               order.setoId(id);
               orders.add(order);
           }
           if(orders.isEmpty()){
               throw new DataMapperException("There are no orders in the system");
           }
           return orders;
        }catch(SQLException | ClassNotFoundException ex){
            throw new DataMapperException(ex.getMessage());
        }
    }
    
}
