/*
 */
package DBAccess;

import FunctionLayer.Exceptions.DataMapperException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Christian, Gert, Lene & Mikkel
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

    @Test
    public void testConfirmOrder() throws Exception {
        System.out.println("Test: Change order status on order in database");
        int oid = 1;
        OrderMapper.confirmOrder(oid);
        String expResult = "confirmed";
        String result = OrderMapper.getSingleOrder(oid).getStatus();
        assertEquals(expResult, result);
    }

    @Test(expected = DataMapperException.class)
    public void testNoOrderMatch() throws Exception {
        System.out.println("Test: No order id match for order in database");
        OrderMapper.getSingleOrder(2);
    }


}
