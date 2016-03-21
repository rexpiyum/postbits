package backend.control;

import backend.persistence.DBConnector;
import backend.service.Content;

import java.io.Serializable;

/**
 * Created by fernando on 6/19/15.
 */
public abstract class Mapping implements Serializable{

    private int type;
    private String address;
    private byte [] data = new byte[5824];
    private byte token;

    Mapping(int type, String address){
        this.type = type;
        this.address = address;
        token = -1;
    }

    public abstract boolean refresh();

    public abstract void createImage();

    public abstract Content getText();

    public void persist(){
        DBConnector.saveMapping(address, this);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        address = address;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        setNewData();
        data[ImageCreator.IMAGE_SIZE -1] = token;
        this.data = data;
    }

    public int getToken() {
        return token;
    }

    public void setToken(byte token) {
        this.token = token;
    }

    public boolean hasNewData(int messageToken) {
        System.out.println("message token: " + messageToken);
        if(messageToken != this.token){
            return true;
        }
        return false;

        //return true; //Just for now!
    }

    public void setNewData() {

        if(token == -1){
            token = 65;
        }

        if(token < 90 ) {
            token++;
        }else{
            token = 65;
        }
    }
}
