package backend.service;

import backend.control.Controller;
import backend.persistence.ActivityLogger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

/**
 * Created by fernando on 6/22/15.
 */


public class DeviceService {

    @GET
    @Path("/list/")
    public Response getDeviceList(){

//        Message m = new Message();
//        m.setOperation("append");
//        m.setType("list");
//        m.setText(new String[]{"asdada","opipoip"});
//
//        return Response.ok(m).type(MediaType.APPLICATION_JSON_TYPE).build();

        Collection<Device> list = Controller.getDeviceStore().getDevicesList().values();
        return Response.ok(list.toArray(new Device []{})).type(MediaType.APPLICATION_JSON_TYPE).build();
        //return Controller.getDeviceStore().getDevicesList().values().toArray(new Device[]{});
    }



    @POST
    @Path("/update/")
    @Consumes("application/json")
    public Response addList(Device device) {
        ActivityLogger.logActivity("Update Device: "+device.getTitle()+"-"+device.getLocation(), device.getAddress());
        device.setMappingType(Controller.getMappingStore().getMapping(device.getAddress()).getType());
        device.setNewDevice(false);
        device.setId(DeviceFactory.getDeviceID(device.getAddress()));
        Controller.getDeviceStore().addDevice(device.getAddress(),device);
        return Response.ok().build();
    }



}
