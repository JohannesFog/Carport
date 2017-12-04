package DBAccess;

import Exceptions.DataMapperException;
import FunctionLayer.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 The purpose of UserMapper is to...

 @author kasper
 */
public class UserMapper {

    public static void createUser( User user ) throws DataMapperException {
        try {
            Connection con = Connector.connection();
            String SQL = "INSERT INTO user (name,address,zipcode,phonenumber,email,password,role) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement( SQL, Statement.RETURN_GENERATED_KEYS );
            ps.setString( 1, user.getName() );
            ps.setString( 2, user.getAddress() );
            ps.setInt( 3, user.getZip() );
            ps.setInt( 4, user.getPhone() );
            ps.setString( 5, user.getEmail() );
            ps.setString( 6, user.getPassword() );
            ps.setString( 7, user.getRole());
            
            ps.executeUpdate();
            ResultSet ids = ps.getGeneratedKeys();
            ids.next();
            int id = ids.getInt( 1 );
            user.setId( id );
        } catch ( SQLException | ClassNotFoundException ex ) {
            throw new DataMapperException( ex.getMessage() );
        }
    }

    public static User login( String email, String password ) throws DataMapperException {
        try {
            Connection con = Connector.connection();
            String SQL = "SELECT `id`, `role` FROM user "
                    + "WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement( SQL );
            ps.setString( 1, email );
            ps.setString( 2, password );
            ResultSet rs = ps.executeQuery();
            if ( rs.next() ) {
                String role = rs.getString( "role" );
                int id = rs.getInt( "id" );
                User user = new User( email, password, role );
                user.setId( id );
                return user;
            } else {
                throw new DataMapperException( "Could not validate user" );
            }
        } catch ( ClassNotFoundException | SQLException ex ) {
            throw new DataMapperException(ex.getMessage());
        }
    }

}
