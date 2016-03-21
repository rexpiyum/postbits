package backend.persistence;

import backend.service.DeviceFactory;

import java.util.logging.Logger;

/**
 * Created by fernando on 7/30/15.
 */
public class ActivityLogger {

    static Logger logger = Logger.getLogger(ActivityLogger.class.getName());

    public static void logActivity(String message, String address){

        String logString = "[POST_BIT] " + message;
        if(address != null){
            logString += ": " + DeviceFactory.getDeviceID(address);
        }

        logger.info(logString);

    }
}
