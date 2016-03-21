package backend.control;

import backend.service.Content;

/**
 * Created by fernando on 8/31/15.
 */

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import backend.service.DeviceFactory;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.io.XmlReader;
public class NewsMapping extends RefreshableMapping {

    private String category;
    private String title;
    private String link;

    public NewsMapping(String address, String category){
        super(MappingType.NEWS_MAPPING,address);
        this.category = category;
        refresh();
    }

    @Override
    public boolean refresh() {
        String urlString = "http://rss.cnn.com/rss/cnn_"+category+".rss";
        try {
            URL url = new URL(urlString);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));
            SyndEntry entry = (SyndEntry) feed.getEntries().get(0);
            if(title != null && title.equals(entry.getTitle())){
                return false;
            }
            title = entry.getTitle();
            link = entry.getLink();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;

    }

    @Override
    public void createImage() {
       // refresh();

        List<Text> textList = new ArrayList<Text>();
        textList.add(new Text("CNN Top Story @"+category,new Font("Arial", Font.ITALIC, 20)));
        textList.add(new Text(title,new Font("Arial", Font.BOLD, 20)));
        String id = String.valueOf(DeviceFactory.getDeviceID(getAddress()));
        setData(ImageCreator.createImageFromText(textList,id));

    }

    @Override
    public Content getText() {
        Content content = new Content();
        content.setTexts(new String[]{"CNN Top Story @"+category,
                title,
        });
        return content;
    }

    @Override
    public boolean hasNewData(int messageToken) {

        return super.hasNewData(messageToken)|refresh();
    }
}
