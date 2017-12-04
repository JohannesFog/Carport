/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import Exceptions.DataMapperException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author lene_
 */
public class GetCarport extends Command {

    public GetCarport() {
    }

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws DataMapperException {
        String out = "";
        String in = request.getParameter("cpType");
        if(in.equals("Carport med fladt tag")){
            out="fladttag";
        }else if(in.equals("Carport med rejsning")){
            out="rejsttag";
        }else{
            UnknownCommand uc = new UnknownCommand();
            out = uc.execute(request, response);
        }
        
        return out;
    }
    
}
