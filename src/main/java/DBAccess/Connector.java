package DBAccess;


import FunctionLayer.Exceptions.DataMapperException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Christian, Gert, Lene & Mikkel
 */
public class Connector {

    private static final String url = "jdbc:mysql://207.154.247.212:3306/carport";
    private static final String username = "************";
    private static final String password = "************";

    private static Connection singleton;

    public static Connection connection() throws DataMapperException  {
        
        try {
            if (singleton == null || singleton.isClosed()) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    singleton = DriverManager.getConnection(url, username, password);
                } catch (ClassNotFoundException | SQLException ex) {    
                    throw new DataMapperException(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            throw new DataMapperException(ex.getMessage());
        }
        return singleton;
    }
        
    public static void setConnection(Connection con) {
        singleton = con;
    }

}