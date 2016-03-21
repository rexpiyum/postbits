package backend.service;

import backend.persistence.DBConnector;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by fernando on 6/22/15.
 */

@XmlRootElement(name = "device")
public class Device implements Serializable{

    private long id;
    private String title;
    private String location;
    private String address;
    private int mappingType;
    private boolean newDevice;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void persist(){
        DBConnector.saveDevice(address,this);
    }

    public int getMappingType() {
        return mappingType;
    }

    public void setMappingType(int mappingType) {
        this.mappingType = mappingType;
    }

    public boolean isNewDevice() {
        return newDevice;
    }

    public void setNewDevice(boolean newDevice) {
        this.newDevice = newDevice;
    }
}
