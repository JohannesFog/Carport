/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBAccess;

import Exceptions.DataMapperException;
import FunctionLayer.User;
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
public class UserMapperTest {

    private static Connection testConnection;
    private static String USER = "testuser";
    private static String USERPW = "trytotest1234";
    private static String DBNAME = "carportTest";
    private static String HOST = "207.154.247.212";

    public UserMapperTest() {
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
                stmt.execute("drop table if exists user");
                stmt.execute("create table user like userTest");
                stmt.execute("insert into user select * from userTest");
            }

        } catch (ClassNotFoundException | SQLException ex) {
            testConnection = null;
            System.out.println("Could not open connection to database: " + ex.getMessage());
        }
    }

    @Test(expected = DataMapperException.class)
    public void testNoUserMatch() throws Exception {
        System.out.println("Test: No user match in database");
        UserMapper.login("lars@email.com", "4321");
    }
    

    
    @Test
    public void testLoginEmployee() throws Exception {
        System.out.println("Test: Get employee user data from database");
        String email = "lars@email.com";
        String password = "1234";
        String expNameResult = "Lars Larsen";
        String expAddressResult = "Hveen Boulevard 8";
        int expZipResult = 2630;
        int expPhoneResult = 38383838;
        String expRoleResult = "employee";

        User testUser = UserMapper.login(email, password);

        String nameResult = testUser.getName();
        String adressResult = testUser.getAddress();
        int zipResult = testUser.getZip();
        int phoneResult = testUser.getPhone();
        String roleResult = testUser.getRole();

        assertEquals(expNameResult, nameResult);
        assertEquals(expAddressResult, adressResult);
        assertEquals(expZipResult, zipResult);
        assertEquals(expPhoneResult, phoneResult);
        assertEquals(expRoleResult, roleResult);
    }

    @Test
    public void testLoginCustomer() throws Exception {
        System.out.println("Test: Get customer user data from database");
        String email = "jens@mail.dk";
        String password = "5678";
        String expNameResult = "Jens Hansen";
        String expAddressResult = "Sivsangervej 19";
        int expZipResult = 3080;
        int expPhoneResult = 49758900;
        String expRoleResult = "customer";

        User testUser = UserMapper.login(email, password);

        String nameResult = testUser.getName();
        String adressResult = testUser.getAddress();
        int zipResult = testUser.getZip();
        int phoneResult = testUser.getPhone();
        String roleResult = testUser.getRole();

        assertEquals(expNameResult, nameResult);
        assertEquals(expAddressResult, adressResult);
        assertEquals(expZipResult, zipResult);
        assertEquals(expPhoneResult, phoneResult);
        assertEquals(expRoleResult, roleResult);
    }
    
    
    @Test
    public void testCreateUser() throws Exception {
        System.out.println("Test: Create user in database");
        String email = "test@mail.com";
        String password = "pw1234";
        String name = "Ole Olsen";
        String address = "Landevejen 10";
        int zip = 9000;
        int phone = 11223344;
        String role = "customer";   
        User testUser = new User(phone, email, password, name, address, zip , role);
        
        UserMapper.createUser(testUser);
       
        User dbUser = UserMapper.login(email, password);
        
        String nameResult = dbUser.getName();
        String adressResult = dbUser.getAddress();
        int zipResult = dbUser.getZip();
        int phoneResult = dbUser.getPhone();
        String roleResult = dbUser.getRole();      
        
        assertEquals(name, nameResult);
        assertEquals(address, adressResult);
        assertEquals(zip, zipResult);
        assertEquals(phone, phoneResult);
        assertEquals(role, roleResult);
     
    }

}
