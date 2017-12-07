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
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author GertLehmann
 */
public class MaterialMapperTest {

    private static Connection testConnection;
    private static String USER = "testinguser";
    private static String USERPW = "try1try2tryAgain";
    private static String DBNAME = "carportTest";
    private static String HOST = "207.154.247.212";

    public MaterialMapperTest() {
    }

@Before
public void setUp() {
    try {
        // awoid making a new connection for each test
        if ( testConnection == null ) {
                String url = String.format( "jdbc:mysql://%s:3306/%s", HOST, DBNAME );
                Class.forName( "com.mysql.jdbc.Driver" );

            testConnection = DriverManager.getConnection( url, USER, USERPW );
            // Make mappers use test 
            Connector.setConnection( testConnection );
        }
        // reset test database
        try ( Statement stmt = testConnection.createStatement() ) {
            stmt.execute( "drop table if exists materials" );
            stmt.execute( "create table materials like materialsTest" );
            stmt.execute( "insert into materials select * from materialsTest" );
        }

    } catch ( ClassNotFoundException | SQLException ex ) {
        testConnection = null;
        System.out.println( "Could not open connection to database: " + ex.getMessage() );
    }
}  

    @Test
    public void testGetPrice() throws Exception {
        String name = "19x100 mm. trykimp. bræt";
        double expResult = 6.95;
        double result = MaterialMapper.getPrice(name);
        assertEquals(expResult, result, 0.0);
    }
//
//    @Test (expected = DataMapperException.class)
//    public void testGetPriceNoMatch() throws Exception {       
//        MaterialMapper.getPrice("Placebo");      
//    }

//    @Test
//    public void testXX() throws Exception {
//        String name = "testmateriale";
//        double expResult = 10.95;
//        double result = MaterialMapper.getPrice(name);
//        assertEquals(expResult, result, 0.0);
//    }

    
    
    
    
}
