package PresentationLayer;

import FunctionLayer.Exceptions.DataMapperException;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 @author Christian, Gert, Lene & Mikkel
 */
abstract class Command {

    private static HashMap<String, Command> commands;

    private static void initCommands() {
        commands = new HashMap<>();
        commands.put( "Login", new Login() );
        commands.put( "GetPrice", new GetPrice() );
        commands.put( "Order", new CreateOrder());
        commands.put( "GetCarport", new GetCarport());
        commands.put( "GetConfirmOrder", new GetConfirmOrder());
        commands.put( "GetODetails", new GetODetails());
        commands.put( "GetBack", new GetBack());

    }

    static Command from( HttpServletRequest request ) {
        String commandName = request.getParameter( "command" );
        if ( commands == null ) {
            initCommands();
        }
        return commands.getOrDefault(commandName, new UnknownCommand() );
    }

    abstract String execute( HttpServletRequest request, HttpServletResponse response ) throws DataMapperException;

}
