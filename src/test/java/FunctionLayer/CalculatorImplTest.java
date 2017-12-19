/*
 */
package FunctionLayer;

import FunctionLayer.Entities.LineItem;
import FunctionLayer.Entities.BillOfMaterials;
import DBAccess.Connector;
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
public class CalculatorImplTest {
      
    private static Connection testConnection;
    private static String USER = "testuser";
    private static String USERPW = "trytotest1234";
    private static String DBNAME = "carportTest";
    private static String HOST = "207.154.247.212";
    
    public CalculatorImplTest() {
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



    @Test
    public void testCalculateStolperUdenSkurKortCarport() throws DataMapperException{
        System.out.println("Test: Beregn stolper for kort carport uden skur");
        double length = 240.0;
        double width = 240.0;
        double height = 300.0;
        double skurLength = 0.0;
        double skurWidth = 0.0;
        CalculatorImpl instance = new CalculatorImpl();
        int expResult = 4;
        BillOfMaterials bom = instance.calculateStolper(length, width, height, skurLength, skurWidth);
        int result = 0;
        for (LineItem li : bom.getBomList()) {
            result += li.getQuantity();
        }
        assertEquals(expResult, result);
    }

    @Test
    public void testCalculateStolperUdenSkurLangCarport() throws DataMapperException{
        System.out.println("Test: Beregn stolper for lang carport uden skur");
        double length = 720.0;
        double width = 240.0;
        double height = 300.0;
        double skurLength = 0.0;
        double skurWidth = 0.0;
        CalculatorImpl instance = new CalculatorImpl();
        int expResult = 6;
        BillOfMaterials bom = instance.calculateStolper(length, width, height, skurLength, skurWidth);
        int result = 0;
        for (LineItem li : bom.getBomList()) {
            result += li.getQuantity();
        }
        assertEquals(expResult, result);
    }
    
    @Test
    public void testCalculateStolperMedSkurSammeBreddeSomRemme() throws DataMapperException{
        System.out.println("Test: Beregn stolper for carport med skur samme bredde som remmene");
        double length = 600.0;
        double width = 360.0;
        double height = 300.0;
        double skurLength = 240.0;
        double skurWidth = 330.0;
        CalculatorImpl instance = new CalculatorImpl();
        int expResult = 9;
        BillOfMaterials bom = instance.calculateStolper(length, width, height, skurLength, skurWidth);
        int result = 0;
        for (LineItem li : bom.getBomList()) {
            result += li.getQuantity();
        }
        assertEquals(expResult, result);
    }
    
    @Test
    public void testCalculateStolperMedSkurSmallereEndRemme() throws DataMapperException{
        System.out.println("Test: Beregn stolper for carport med skur smallere end remmene");
        double length = 600.0;
        double width = 360.0;
        double height = 300.0;
        double skurLength = 240.0;
        double skurWidth = 270.0;
        CalculatorImpl instance = new CalculatorImpl();
        int expResult = 10;
        BillOfMaterials bom = instance.calculateStolper(length, width, height, skurLength, skurWidth);
        int result = 0;
        for (LineItem li : bom.getBomList()) {
            result += li.getQuantity();
        }
        assertEquals(expResult, result);
    }
    
}