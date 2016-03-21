package backend.control;

import backend.service.Content;
import backend.service.DeviceFactory;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fernando on 6/29/15.
 */
public class WeatherMapping extends Mapping {

    private String city ="";
    private String weather = "";
    private String temperature = "";

    public WeatherMapping(String address, String city){
        super(MappingType.WEATHER_MAPPING,address);
        this.city = city;
        refresh();
    }

    @Override
    public boolean refresh() {


        String urlStr = "http://api.openweathermap.org/data/2.5/forecast?q="+city.replace(" ","_")+"&units=metric";
        HttpURLConnection conn;
        //String bearerToken = "AAAAAAAAAAAAAAAAAAAAAJQ5gQAAAAAAwfK0%2FCSQUzgA4R3U5ir91W66T%2B8%3DjnJzQAMZ4sJnocimbZs5JD17YakII4IXzQhDeDEHGXqrVGc8t4";

        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            InputStream inputStream = conn.getInputStream();
            JsonReader reader = Json.createReader(inputStream);
            JsonObject obj = reader.readObject();
            JsonArray array = obj.getJsonArray("list");
            JsonObject data = (JsonObject)array.get(1);
            JsonArray weatherArray = data.getJsonArray("weather");
            JsonObject weatherObj = (JsonObject)weatherArray.get(0);
            String tmp_weather = weatherObj.getString("description");
            String tmp_temperature = data.getJsonObject("main").getJsonNumber("temp").toString();
            if(tmp_temperature == null || tmp_weather == null){
                return false;
            }
            if(tmp_temperature.equals(temperature) && tmp_weather.equals(weather)){
                return false;
            }
            weather = tmp_weather;
            temperature = tmp_temperature;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void createImage() {
        //refresh();

        List<Text> textList = new ArrayList<Text>();
        textList.add(new Text("Weather Forecast for "+ city,new Font("Arial", Font.ITALIC, 25)));
        textList.add(new Text("Weather: " + weather,new Font("Arial", Font.PLAIN, 20)));
        textList.add(new Text("Temperature: " + temperature +"C",new Font("Arial", Font.PLAIN, 20)));
        String id = String.valueOf(DeviceFactory.getDeviceID(getAddress()));
        setData(ImageCreator.createImageFromText(textList,id));

    }

    @Override
    public Content getText() {
        Content content = new Content();
        content.setTexts(new String[]{"Weather Forecast for "+ city,
                                        "Weather: " + weather,
                                        "Temperature: " + temperature +"C"
                                        });
        return content;

    }

    @Override
    public boolean hasNewData(int messageToken) {

        return super.hasNewData(messageToken)|refresh();
    }
}
