/*
 */
package DBAccess;

import FunctionLayer.Exceptions.DataMapperException;
import FunctionLayer.Entities.Order;
import FunctionLayer.Entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * The purpose of OrderMapper is to transfer the order information from the
 * database to an order object or the other way around.
 *
 * @author Christian, Gert, Lene & Mikkel
 */
public class OrderMapper {

    public static void createOrder(Order order) throws DataMapperException {
        Connection con = Connector.connection();
        String SQL = "INSERT INTO `orders`(`length`,`width`,`height`,`roof_angle`,"
                + "`shed_width`,`shed_length`,`orderdate`,`phonenumber`,`status`) "
                + "VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            con.setAutoCommit(false);
            ps.setDouble(1, order.getLength());
            ps.setDouble(2, order.getWidth());
            ps.setDouble(3, order.getHeight());
            ps.setDouble(4, order.getRoofAngle());
            ps.setDouble(5, order.getShedWidth());
            ps.setDouble(6, order.getShedLength());
            ps.setString(7, order.getOrderDate());
            ps.setInt(8, order.getPhone());
            ps.setString(9, order.getStatus());
            ps.executeUpdate();
            con.commit();
            ResultSet ids = ps.getGeneratedKeys();
            ids.next();
            int id = ids.getInt(1);
            order.setoId(id);
        } catch (SQLException ex) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex1) {
                    throw new DataMapperException(ex.getMessage());
                }
            }
            throw new DataMapperException(ex.getMessage());
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                throw new DataMapperException(ex.getMessage());
            }
        }
    }

    public static void confirmOrder(int id) throws DataMapperException {
        try {
            Connection con = Connector.connection();
            String status = "confirmed";
            String SQL = "UPDATE orders SET status=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataMapperException(ex.getMessage());
        }
    }

    public static ArrayList<Order> getAllOrders() throws DataMapperException {
        try {
            ArrayList<Order> orders = new ArrayList<Order>();
            Connection con = Connector.connection();
            String SQL = "SELECT * FROM `orders`";
            PreparedStatement ps = con.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int oId = rs.getInt("id");
                double length = rs.getDouble("length");
                double width = rs.getDouble("width");
                double height = rs.getDouble("height");
                double roofAngle = rs.getDouble("roof_angle");
                double shedWidth = rs.getDouble("shed_width");
                double shedLength = rs.getDouble("shed_length");
                String orderDate = rs.getString("orderdate");
                int phone = rs.getInt("phonenumber");
                String status = rs.getString("status");

                Order order = new Order(oId, length, width, height, roofAngle, shedWidth, shedLength, orderDate, phone, status);
                //order.setoId(id);
                orders.add(order);
            }
            if (orders.isEmpty()) {
                throw new DataMapperException("There are no orders in the system");
            }
            return orders;
        } catch (SQLException ex) {
            throw new DataMapperException(ex.getMessage());
        }
    }

    public static Order getSingleOrder(int oid) throws DataMapperException {
        try {
            Order order = null;
            Connection con = Connector.connection();
            String SQL = "SELECT * FROM orders WHERE id=?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, oid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int oId = rs.getInt("id");
                double length = rs.getDouble("length");
                double width = rs.getDouble("width");
                double height = rs.getDouble("height");
                double roofAngle = rs.getDouble("roof_angle");
                double shedWidth = rs.getDouble("shed_width");
                double shedLength = rs.getDouble("shed_length");
                String orderDate = rs.getString("orderdate");
                int phone = rs.getInt("phonenumber");
                String status = rs.getString("status");

                order = new Order(oId, length, width, height, roofAngle, shedWidth, shedLength, orderDate, phone, status);
            } else {
                throw new DataMapperException("No order in database with this order id");
            }
            return order;
        } catch (SQLException ex) {
            throw new DataMapperException(ex.getMessage());
        }
    }

    public static ArrayList<Order> getAllUserOrders(User user) throws DataMapperException {
        try {
            Order order = null;
            ArrayList<Order> orders = new ArrayList<Order>();
            Connection con = Connector.connection();
            String SQL = "SELECT * FROM orders WHERE phonenumber=?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, user.getPhone());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int oId = rs.getInt("id");
                double length = rs.getDouble("length");
                double width = rs.getDouble("width");
                double height = rs.getDouble("height");
                double roofAngle = rs.getDouble("roof_angle");
                double shedWidth = rs.getDouble("shed_width");
                double shedLength = rs.getDouble("shed_length");
                String orderDate = rs.getString("orderdate");
                int phone = rs.getInt("phonenumber");
                String status = rs.getString("status");

                order = new Order(oId, length, width, height, roofAngle, shedWidth, shedLength, orderDate, phone, status);
                orders.add(order);
            }
            return orders;
        } catch (SQLException ex) {
            throw new DataMapperException(ex.getMessage());
        }

    }

}
