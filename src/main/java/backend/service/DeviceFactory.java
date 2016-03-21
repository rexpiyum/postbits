package backend.service;

import backend.CloudDropConstants;

/**
 * Created by fernando on 6/23/15.
 */
public class DeviceFactory {

    public static Device newInstance(String address){
        Device device = new Device();
        device.setAddress(address);
        device.setId(getDeviceID(address));
        device.setTitle(CloudDropConstants.project_name+" #" + device.getId());
        device.setNewDevice(true);
        return device;
    }

    public static Device newInstance(String address, String title, String location){
        Device device = new Device();
        device.setAddress(address);
        device.setId(getDeviceID(address));
        device.setTitle(title);
        device.setLocation(location);

        return device;
    }

    public static long getDeviceID(String address){

        if(address.equalsIgnoreCase("0013A20040BE4796")){
            return 1;
        }else if(address.equalsIgnoreCase("0013A20040E44213")){
            return 2;
        }else if(address.equalsIgnoreCase("0013A20040E44279")){
            return 3;
        }
        else if(address.equalsIgnoreCase("0013A20040AD5783")){
            return 4;
        }else if(address.equalsIgnoreCase("0013A20040E441FE")){
            return 5;
        }else if(address.equalsIgnoreCase("0013A20040E44220")){//
            return 6;
        }
        else if(address.equalsIgnoreCase("0013A20040ADD42F")){//0013a20040add42f
            return 7;
        }else if(address.equalsIgnoreCase("0013A20040E441FF")){
            return 8;
        }else if(address.equalsIgnoreCase("0013A20040E4420A")){
            return 9;
        }
        else if(address.equalsIgnoreCase("0013A20040BE45B1")){
            return 10;
        }else if(address.equalsIgnoreCase("0013A20040E44227")){
            return 11;
        }else if(address.equalsIgnoreCase("0013A20040E44227")){
            return 12;
        }
        else if(address.equalsIgnoreCase("0013A20040ADD0E5")){
            return 13;
        }else if(address.equalsIgnoreCase("0013A20040E4421B")){
            return 14;
        }else if(address.equalsIgnoreCase("0013A20040b3E2A4")){
            return 15;
        }
        else{
            return 0;
        }
//00:13:a2:00:40:be:45:b1!

    }
}
