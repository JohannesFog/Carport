/*
 */
package Configuration;

import java.util.logging.Logger;

/**
 *
 * @author Christian, Gert, Lene & Mikkel
 */
public class Conf {
    public static final boolean PRODUCTION = true;
    public static final String LOGFILEPATH = "/var/log/tomcat8/carport.log";
    public static Logger carportLogger = Logger.getLogger("");
}
