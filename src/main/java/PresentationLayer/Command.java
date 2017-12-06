package PresentationLayer;

import Exceptions.DataMapperException;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 The purpose of Command is to...

 @author kasper
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
