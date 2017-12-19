package PresentationLayer;

import FunctionLayer.Exceptions.DataMapperException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 @author Christian, Gert, Lene & Mikkel
 */
public class UnknownCommand extends Command {

    @Override
    String execute( HttpServletRequest request, HttpServletResponse response ) throws DataMapperException {
        String msg = "Unknown command. Contact IT";
        throw new DataMapperException( msg );
    }

}
