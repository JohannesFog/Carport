/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBAccess;

import FunctionLayer.LoginSampleException;
import FunctionLayer.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author lene_
 */
public class OrderMapper {

    public static void createOrder(Order order) throws LoginSampleException {
        try{
            Connection con = Connector.connection();
            String SQL = "INSERT INTO `orders`(`length`,`width`,`height`,`roof`,`shed`,`orderdate`,`u_name`) VALUES (?,?,?,?,?,?,?)";
            
            PreparedStatement ps = con.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, order.getLength());
            ps.setDouble(2, order.getWidth());
            ps.setDouble(3, order.getHeight());
            ps.setString(4, order.getRoof());
            ps.setString(5, order.getShed());
            ps.setString(6, order.getOrderDate());
            ps.setString(7, order.getuName());
            ps.executeUpdate();
            
            ResultSet ids = ps.getGeneratedKeys();
            ids.next();
            int id = ids.getInt(1);
            order.setoId(id);
        }catch(SQLException | ClassNotFoundException ex){
            throw new LoginSampleException(ex.getMessage());
        }
    }
    
}
