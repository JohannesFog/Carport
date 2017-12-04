/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Configuration;

import java.util.logging.Logger;

/**
 *
 * @author GertLehmann
 */
public class Conf {
    public static final boolean PRODUCTION = false;
    public static final String LOGFILEPATH = "/var/log/tomcat8/Carport.log";
    public static Logger carportLogger = Logger.getLogger("");
}
