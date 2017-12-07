/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBAccess;

import FunctionLayer.Order;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author GertLehmann
 */
public class OrderMapperTest {

    private static Connection testConnection;
    private static String USER = "testuser";
    private static String USERPW = "trytotest1234";
    private static String DBNAME = "carportTest";
    private static String HOST = "207.154.247.212";

    public OrderMapperTest() {
    }

    @Before
    public void setUp() {
        try {
            // awoid making a new connection for each test
            if (testConnection == null) {
                String url = String.format("jdbc:mysql://%s:3306/%s", HOST, DBNAME);
                Class.forName("com.mysql.jdbc.Driver");

                testConnection = DriverManager.getConnection(url, USER, USERPW);
                // Make mappers use test 
                Connector.setConnection(testConnection);
            }
            // reset test database
            try (Statement stmt = testConnection.createStatement()) {
                stmt.execute("drop table if exists orders");
                stmt.execute("create table orders like ordersTest");
                stmt.execute("insert into orders select * from ordersTest");
            }

        } catch (ClassNotFoundException | SQLException ex) {
            testConnection = null;
            System.out.println("Could not open connection to database: " + ex.getMessage());
        }
    }

//    @Test
//    public void testCreateOrder() throws Exception {
//        System.out.println("createOrder");
//        Order order = null;
//        OrderMapper.createOrder(order);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testConfirmOrder() throws Exception {
//        System.out.println("confirmOrder");
//        Order order = null;
//        OrderMapper.confirmOrder(order);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testGetAllOrders() throws Exception {
//        System.out.println("getAllOrders");
//        ArrayList<Order> expResult = null;
//        ArrayList<Order> result = OrderMapper.getAllOrders();
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testGetSingleOrder() throws Exception {
//        System.out.println("getSingleOrder");
//        int oid = 0;
//        Order expResult = null;
//        Order result = OrderMapper.getSingleOrder(oid);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
}
