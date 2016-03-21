package backend.control;

import backend.persistence.FLLDBConnector;
import backend.service.Content;
import backend.service.DeviceFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fernando on 1/25/16.
 */
public class FLLMapping extends Mapping{

    private String lux;
    private String temperature;
    private String humidity;

    public FLLMapping(String address){
        super(MappingType.FLL_MAPPING,address);
        lux = " ";
        temperature=" ";
        humidity=" ";
    }
    @Override
    public boolean refresh() {
        boolean b = false;
        String [] fllData = FLLDBConnector.getFLLData();
        if(fllData[0] != null && !lux.equals(fllData[0])){
            lux = fllData[0];
            b = true;
        }
        if(fllData[1] != null && !temperature.equals(fllData[1])){
            temperature = fllData[1];
            b = true;
        }
        if(fllData[2] != null && !humidity.equals(fllData[2])){
            humidity = fllData[2];
            b = true;
        }

        return b;
    }

    @Override
    public void createImage() {
        List<Text> textList = new ArrayList<Text>();
        textList.add(new Text("Lux :"+lux+"lx",new Font("Arial", Font.PLAIN, 20)));
        textList.add(new Text("Temperature :"+temperature+"F",new Font("Arial", Font.PLAIN, 20)));
        textList.add(new Text("Humidity :"+humidity+"RH",new Font("Arial", Font.PLAIN, 20)));

        String id = String.valueOf(DeviceFactory.getDeviceID(getAddress()));
        setData(ImageCreator.createImageFromText(textList,id));
    }

    @Override
    public Content getText() {
        Content content = new Content();
        content.setTexts(new String[]{"Lux :"+lux+"lx","Temperature :"+temperature+"F","Humidity :"+humidity+"%"});
        return content;
    }

    @Override
    public boolean hasNewData(int messageToken) {
        return super.hasNewData(messageToken)|refresh();
    }
}
