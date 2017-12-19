/*
 */
package PresentationLayer;

import FunctionLayer.Exceptions.DataMapperException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Christian, Gert, Lene & Mikkel
 */
public class GetCarport extends Command {

    public GetCarport() {
    }

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws DataMapperException {
        String out = "";
        String in = request.getParameter("cpType");
        if(in.equals("Fladt tag")){
            out="fladttag";
        }else if(in.equals("Rejst tag")){
            out="rejsttag";
        }else{
            UnknownCommand uc = new UnknownCommand();
            out = uc.execute(request, response);
        }
        
        return out;
    }
    
}
