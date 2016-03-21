package backend.control;

import backend.service.Content;
import backend.service.DeviceFactory;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * Created by fernando on 6/29/15.
 */
public class TwitterMapping extends Mapping {

    private String feedID;
    private String data;

    public TwitterMapping(String address, String feedID){
        super(MappingType.TWITTER_MAPPING,address);
        this.feedID = feedID;
        refresh();
    }

    @Override
    public boolean refresh() {

        String urlStr = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name="+feedID+"&count=1";
        HttpURLConnection conn;
        String bearerToken = "AAAAAAAAAAAAAAAAAAAAAJQ5gQAAAAAAwfK0%2FCSQUzgA4R3U5ir91W66T%2B8%3DjnJzQAMZ4sJnocimbZs5JD17YakII4IXzQhDeDEHGXqrVGc8t4";

        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization","Bearer " + bearerToken);

            InputStream inputStream = conn.getInputStream();
            JsonReader reader = Json.createReader(inputStream);
            JsonArray array = reader.readArray();
            JsonObject obj = (JsonObject) array.get(0);
            if(data != null && data.equals(obj.getString("text"))){
                return false;
            }
            data = obj.getString("text");
            System.out.println("twitter" + data);
        } catch (Exception e) {
            data = "Cannot retrieve twitter messages right now!";
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void createImage() {
       // refresh();
        List<Text> textList = new ArrayList<Text>();
        textList.add(new Text("@"+feedID,new Font("Arial", Font.ITALIC, 20)));
        textList.add(new Text(data,new Font("Arial", Font.PLAIN, 20)));
        String id = String.valueOf(DeviceFactory.getDeviceID(getAddress()));
        setData(ImageCreator.createImageFromText(textList,id));

    }

    @Override
    public Content getText() {
        Content content = new Content();
        content.setTexts(new String[]{"@"+feedID, data});
        return content;

    }

    @Override
    public boolean hasNewData(int messageToken) {
        return super.hasNewData(messageToken)|refresh();
    }
}
