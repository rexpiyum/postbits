package backend.persistence;

import backend.control.Controller;

/**
 * Created by fernando on 6/25/15.
 */
public class DBLoader extends Thread {

    @Override
    public void run() {
        boolean loaded = false;
        while (!loaded){
            try {
                System.out.println("in/////////////////////////////////////");
                Thread.sleep(10000);
                Controller.getMappingStore().loadMappings();
                Controller.getDeviceStore().loadDevices();
                loaded = true;
                System.out.printf("success----------------------------------------");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
