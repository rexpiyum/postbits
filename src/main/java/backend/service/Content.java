package backend.service;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by fernando on 7/1/15.
 */

@XmlRootElement(name = "content")
public class Content {

    private String[] texts;

    public String[] getTexts() {
        return texts;
    }

    public void setTexts(String[] texts) {
        this.texts = texts;
    }
}
