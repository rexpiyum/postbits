package backend.service;

import backend.control.*;
import backend.persistence.ActivityLogger;

import javax.imageio.ImageIO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;


/**
 * Created by fernando on 6/23/15.
 */


public class MappingService {



    @POST
    @Path("/list/")
    @Consumes("application/json")
    public Response addListMapping(Message message) {

        if (message.getType().equalsIgnoreCase(MessageType.LIST_MESSAGE)) {
            Mapping mapping = Controller.getMappingStore().getMapping(message.getAddress());
            ListMapping listMapping;
            if (mapping != null && mapping instanceof ListMapping) {
                listMapping = (ListMapping) mapping;
            } else {
                listMapping = new ListMapping(message.getAddress());
            }
            byte token = -1;
            listMapping.setToken(token);
            if (message.getOperation().equalsIgnoreCase(MessageType.LIST_APPEND)) {
                ActivityLogger.logActivity("Append List Item", message.getAddress());
                for (String s : message.getText()) {
                    listMapping.addListItem(s);
                }
            }else{
                ActivityLogger.logActivity("Creating new list", message.getAddress());
                listMapping.setListItems(new ArrayList<String>(Arrays.asList(message.getText())));
            }
            Controller.getMappingStore().addMapping(message.getAddress(),listMapping);
            Device device = Controller.getDeviceStore().getDevice(message.getAddress());
            device.setMappingType(MappingType.LIST_MAPPING);
            Controller.getDeviceStore().addDevice(device.getAddress(),device);


        }
        return Response.ok().build();

    }



    @POST
    @Path("/text/")
    @Consumes("application/json")
    public Response addTextMapping(Message message) {

        ActivityLogger.logActivity("Adding Text", message.getAddress());

        if (message.getType().equalsIgnoreCase(MessageType.TEXT_MESSAGE)) {
            Controller.getMappingStore().addMapping(message.getAddress(),
                                                    new TextMapping(message.getAddress(),message.getText()[0]));
            Device device = Controller.getDeviceStore().getDevice(message.getAddress());
            device.setMappingType(MappingType.TEXT_MAPPING);
            Controller.getDeviceStore().addDevice(device.getAddress(),device);

        }
        return Response.ok().build();

    }

    @POST
    @Path("/twitter/{address}/{feed}")
    public Response addTwitterMapping(@PathParam("address") String address, @PathParam("feed") String feed) {
        ActivityLogger.logActivity("Adding Twitter",address);
        if (address != null && feed != null) {
            Controller.getMappingStore().addMapping(address,new TwitterMapping(address,feed));
            Device device = Controller.getDeviceStore().getDevice(address);
            device.setMappingType(MappingType.TWITTER_MAPPING);
            Controller.getDeviceStore().addDevice(device.getAddress(),device);

        }
        return Response.ok().build();

    }

    @POST
    @Path("/weather/{address}/{city}")
    public Response addWeatherMapping(@PathParam("address") String address, @PathParam("city") String city) {
        ActivityLogger.logActivity("Adding Weather",address);
        if (address != null && city != null) {
            Controller.getMappingStore().addMapping(address,new WeatherMapping(address,city));
            Device device = Controller.getDeviceStore().getDevice(address);
            device.setMappingType(MappingType.WEATHER_MAPPING);
            Controller.getDeviceStore().addDevice(device.getAddress(),device);

        }
        return Response.ok().build();

    }

    @POST
    @Path("/news/{address}/{category}")
    public Response addNewsMapping(@PathParam("address") String address, @PathParam("category") String category) {
        ActivityLogger.logActivity("Adding News @"+category,address);
        if (address != null && category != null) {
            Controller.getMappingStore().addMapping(address,new NewsMapping(address,category));
            Device device = Controller.getDeviceStore().getDevice(address);
            device.setMappingType(MappingType.NEWS_MAPPING);
            Controller.getDeviceStore().addDevice(device.getAddress(),device);

        }
        return Response.ok().build();

    }

    @POST
    @Consumes(MediaType.WILDCARD)
    @Produces(MediaType.WILDCARD)
    @Path("/image/{address}")
    public Response addImageMapping(@PathParam("address") String address, byte[] input) {

        ActivityLogger.logActivity("Adding Image",address);
        if (address != null && input != null) {
            Controller.getMappingStore().addMapping(address,new ImageMapping(address,input));
            Device device = Controller.getDeviceStore().getDevice(address);
            device.setMappingType(MappingType.IMAGE_MAPPING);
            Controller.getDeviceStore().addDevice(device.getAddress(),device);

        }

        return Response.ok().build();

    }


    @POST
    @Path("/FLL/{address}")
    public Response addFLLMapping(@PathParam("address") String address) {
        ActivityLogger.logActivity("Adding FLL",address);
        if (address != null) {
            Controller.getMappingStore().addMapping(address,new FLLMapping(address));
            Device device = Controller.getDeviceStore().getDevice(address);
            device.setMappingType(MappingType.FLL_MAPPING);
            Controller.getDeviceStore().addDevice(device.getAddress(),device);
        }
        return Response.ok().build();

    }
}
