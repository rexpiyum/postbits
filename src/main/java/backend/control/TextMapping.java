package backend.control;

import backend.service.Content;
import backend.service.DeviceFactory;

/**
 * Created by fernando on 6/19/15.
 */

public class TextMapping extends Mapping {

    private String message;

    public TextMapping(String address, String message){
        super(MappingType.TEXT_MAPPING,address);
        this.message = message;
    }

    @Override
    public void createImage() {
        String id = String.valueOf(DeviceFactory.getDeviceID(getAddress()));
        setData(ImageCreator.createImageFromText(new Text(message),id));

    }

    @Override
    public Content getText() {
        Content content = new Content();
        content.setTexts(new String[]{message});
        return content;
    }

    @Override
    public boolean refresh() {
        return true;
    }
}
