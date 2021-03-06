
package DBAccess;

import FunctionLayer.Exceptions.DataMapperException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * The purpose of MaterialMapper is to obtain material prices
 * from the database and return this to the logic layer.
 * 
 * @author Christian, Gert, Lene & Mikkel
 */
public class MaterialMapper {
    
/**
 * This method is used to get prices from the material database.
 * @param name is the name of the material for which a price is wanted
 * @return the price for this material
 * @throws DataMapperException if no material is found with the specific name
 * or if a general SQL error occurs.
 */    

        public static double getPrice( String name) throws DataMapperException {
            Connection con = Connector.connection();
            String SQL = "SELECT `price` FROM materials "
                    + "WHERE name=?";
            try {
            PreparedStatement ps = con.prepareStatement( SQL );
            ps.setString( 1, name );
            ResultSet rs = ps.executeQuery();
            if ( rs.next() ) {
                double price = rs.getDouble( "price" );
                return price;
            } else {
                throw new DataMapperException( "Material not found" );
            }
        } catch ( SQLException ex ) {
            throw new DataMapperException(ex.getMessage());
        }
    }
    
}
