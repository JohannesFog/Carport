/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBAccess;

import Exceptions.DataMapperException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author GertLehmann
 */
public class MaterialMapperTest {

    private static Connection testConnection;
    private static String USER = "testuser";
    private static String USERPW = "trytotest1234";
    private static String DBNAME = "carportTest";
    private static String HOST = "207.154.247.212";

    public MaterialMapperTest() {
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
                stmt.execute("drop table if exists materials");
                stmt.execute("create table materials like materialsTest");
                stmt.execute("insert into materials select * from materialsTest");
            }

        } catch (ClassNotFoundException | SQLException ex) {
            testConnection = null;
            System.out.println("Could not open connection to database: " + ex.getMessage());
        }
    }
              
    @After
    public void tearDown() throws SQLException { 
        testConnection.close();
    }

    @Test
    public void testGetPrice() throws Exception {
        System.out.println("Test: Get price from database");
        String name = "19x100 mm. trykimp. bræt";
        double expResult = 6.95;
        double result = MaterialMapper.getPrice(name);
        assertEquals(expResult, result, 0.0);
    }

    @Test(expected = DataMapperException.class)
    public void testGetPriceNoMatch() throws Exception {
        System.out.println("Test: No material match in database");
        MaterialMapper.getPrice("Placebo");
    }

}
