package backend.control;

import backend.service.Content;
import backend.service.DeviceFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by fernando on 6/24/15.
 */
public class EmptyMapping extends Mapping{

    public EmptyMapping(String address){

        super(MappingType.EMPTY_MAPPING, address);
    }

    @Override
    public boolean refresh() {
        return true;
    }

    @Override
    public void createImage() {
        BufferedImage srcImage = null;
        try {
            srcImage = ImageIO.read(new File("repository/deployment/server/webapps/cloudDrop_backend/image.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String id = String.valueOf(DeviceFactory.getDeviceID(getAddress()));
        setData(ImageCreator.createImageFromImage(srcImage,id));
        //String message = "Welcome to the world of PostBits!";
        //setData(ImageCreator.createImageFromText(new Text(message)));
    }

    @Override
    public Content getText() {
        return null;
    }
}
