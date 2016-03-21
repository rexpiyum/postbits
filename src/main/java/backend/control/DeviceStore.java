package backend.control;

import backend.persistence.DBConnector;
import backend.service.Device;

import java.util.HashMap;

/**
 * Created by fernando on 6/22/15.
 */
public class DeviceStore {
    private HashMap<String,Device> devicesList;

    DeviceStore(){
        devicesList = new HashMap<String,Device>();
    }

    public void addDevice(String key, Device device){

        devicesList.put(key, device);
        DBConnector.saveDevice(key, device);
    }

    public Device getDevice(String key){
        return devicesList.get(key);
    }

    public HashMap<String,Device> getDevicesList(){
        return devicesList;
    }

    public void loadDevices(){
        DBConnector.loadDevices(devicesList);
    }
}
