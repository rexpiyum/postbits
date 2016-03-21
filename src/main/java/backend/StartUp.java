package backend;

/**
 * Created by fernando on 6/18/15.
 */
import backend.control.Controller;
import backend.persistence.DBLoader;

import javax.servlet.*;

public class StartUp implements javax.servlet.ServletContextListener {

    public void contextInitialized(ServletContextEvent context) {
        new Controller().startRefresher();
        Controller.getMappingStore().loadMappings();
        Controller.getDeviceStore().loadDevices();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent){}
}
