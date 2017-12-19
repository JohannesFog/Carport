package DBAccess;

import FunctionLayer.Exceptions.DataMapperException;
import FunctionLayer.Entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Christian, Gert, Lene & Mikkel
 */
public class UserMapper {

    public static void createUser(User user) throws DataMapperException {
        Connection con = Connector.connection();
        String SQL = "INSERT INTO user (name,address,zipcode,phonenumber,email,password,role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {

            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            con.setAutoCommit(false);
            ps.setString(1, user.getName());
            ps.setString(2, user.getAddress());
            ps.setInt(3, user.getZip());
            ps.setInt(4, user.getPhone());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getPassword());
            ps.setString(7, user.getRole());
            ps.executeUpdate();
            con.commit();

            ResultSet ids = ps.getGeneratedKeys();
            ids.next();
            int id = ids.getInt(1);
            user.setId(id);
        } catch (SQLException ex) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex1) {
                    throw new DataMapperException(ex1.getMessage());
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

    public static User login(String email, String password) throws DataMapperException {
        Connection con = Connector.connection();
        String SQL = "SELECT `id`, `name`, `address`, `zipcode`, `phonenumber`, `role` FROM user "
                + "WHERE email=? AND password=?";
        try {
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                int zip = rs.getInt("zipcode");
                int phone = rs.getInt("phonenumber");
                String role = rs.getString("role");
                User user = new User(phone, email, password, name, address, zip, role);
                user.setId(id);
                return user;
            } else {
                throw new DataMapperException("Could not validate user");
            }
        } catch (SQLException ex) {
            throw new DataMapperException(ex.getMessage());
        }
    }

}
