package backend.control;

import backend.service.Content;
import backend.service.DeviceFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by fernando on 7/13/15.
 */
public class ImageMapping extends Mapping {

    private byte [] WIF_Image = new byte[5824];


    public ImageMapping(String address, byte[] image){
        super(MappingType.IMAGE_MAPPING,address);
        BufferedImage srcImage = null;
        try {
            srcImage = ImageIO.read(new ByteArrayInputStream(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String id = String.valueOf(DeviceFactory.getDeviceID(getAddress()));
        WIF_Image = ImageCreator.createImageFromImage(srcImage,id);
        //setData(WIF_Image);



    }
    @Override
    public boolean refresh() {
        return true;
    }

    @Override
    public void createImage() {
//        BufferedImage srcImage = null;
//        try {
//            srcImage = ImageIO.read(new ByteArrayInputStream(image));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        setData(WIF_Image);

    }

    @Override
    public Content getText() {
        Content content = new Content();
        content.setTexts(new String[]{"Image will be displayed here"});
        return content;
    }
}
