package backend.service;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by fernando on 6/23/15.
 */

@XmlRootElement(name = "message")
public class Message {

    private String type;
    private String address;
    private String operation;
    private String [] text;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String[] getText() {
        return text;
    }

    public void setText(String[] text) {
        this.text = text;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
