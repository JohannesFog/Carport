/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBAccess;

import Exceptions.DataMapperException;
import FunctionLayer.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author GertLehmann
 */
public class MaterialMapper {
    
        public static double getPrice( String name) throws DataMapperException {
        try {
            Connection con = Connector.connection();
            String SQL = "SELECT `price` FROM materials "
                    + "WHERE name=?";
            PreparedStatement ps = con.prepareStatement( SQL );
            ps.setString( 1, name );
            ResultSet rs = ps.executeQuery();
            if ( rs.next() ) {
                double price = rs.getDouble( "price" );
                return price;
            } else {
                throw new DataMapperException( "Material not found" );
            }
        } catch ( ClassNotFoundException | SQLException ex ) {
            throw new DataMapperException(ex.getMessage());
        }
    }
    
}
