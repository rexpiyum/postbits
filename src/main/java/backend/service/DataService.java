package backend.service;

import backend.control.*;
import backend.persistence.ActivityLogger;
import backend.persistence.DBConnector;
import org.apache.geronimo.mail.util.Base64;

import javax.imageio.ImageIO;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.*;

/**
 * Created by fernando on 6/24/15.
 */
public class DataService {

    boolean isLoaded = false;

    @GET
    @Path("/{address}/{token}/")
    public byte[] getData(@PathParam("address") String address, @PathParam("token") String token){
        System.out.println("Request from: " + address);
        System.out.println("token: " + token);

        if(address != null) {
            Mapping mapping;
            if (Controller.getDeviceStore().getDevice(address) != null) {
                mapping = Controller.getMappingStore().getMapping(address);
                System.out.println("Type: "+ mapping.getType());
//            }else{
//                mapping = new EmptyMapping(address);
//                Controller.getMappingStore().addMapping(address,mapping);
//                Device device = DeviceFactory.newInstance(address);
//                device.setMappingType(mapping.getType());
//                Controller.getDeviceStore().addDevice(address, device);
//
//            }
                int messageToken = -1;
                try {
                    messageToken = token.charAt(0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (mapping.hasNewData(messageToken)) {
                    mapping.createImage();
                    System.out.println("sending new");
                    return mapping.getData();
                }
            }
        }
        System.out.println("not sending");
        return new byte[]{};
    }

    @GET
    @Path("content/{address}/")
    public Response getContent(@PathParam("address") String address){
        Mapping mapping = Controller.getMappingStore().getMapping(address);
        if(mapping != null){
            return Response.ok(mapping.getText()).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
        return null;
    }

    @GET
    @Path("image/{address}/")
    public String getImage(@PathParam("address") String address){
        Mapping mapping = Controller.getMappingStore().getMapping(address);
        BufferedImage img = new BufferedImage(264, 176, BufferedImage.TYPE_INT_ARGB);
        DataBuffer dataBuffer = img.getRaster().getDataBuffer();
        if(mapping != null){
           byte [] data = mapping.getData();
            for(int i=4; i<5812; i++){
                byte b = data[i];

                for(int j=0; j<8; j++){
                    if((b & (1 << j)) != 0){
                        int k = (i-4)*8+j;
                        int index = dataBuffer.getSize() - 264 + k%264 - k + k%264;
                        dataBuffer.setElem(index,-16777216);
                    }
                }
            }
        }

        byte[] imageInByte = null;
        String data = "";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {

            ImageIO.write(img, "png", baos);
            ImageIO.write(img, "png", new File("test.png"));
            baos.flush();
            imageInByte = baos.toByteArray();
            //imageInByte = Base64.encode(imageInByte);
            data = new sun.misc.BASE64Encoder().encode(imageInByte);
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    @GET
    @Path("event/{address}/")
    public Response processEvent(@PathParam("address") String address){
        ActivityLogger.logActivity("Shake Event :", address);
        Mapping mapping = new EmptyMapping(address);
        Controller.getMappingStore().addMapping(address,mapping);
        Device device = DeviceFactory.newInstance(address);
        device.setMappingType(mapping.getType());
        Controller.getDeviceStore().addDevice(address, device);

        return Response.ok().build();
    }

    @GET
    @Path("touch/{address}/")
    public Response processTouch(@PathParam("address") String address){
        ActivityLogger.logActivity("Touch Event :",address);
        return Response.ok().build();
    }

    @GET
    @Path("clear/{passcode}/")
    public Response clear(@PathParam("passcode") String passcode){
        if(passcode.equals("AH_pbits_4001")){
            DBConnector.clearAll();
        }
        return Response.ok().build();
    }
}
