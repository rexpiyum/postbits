package backend.control;

import java.awt.*;

/**
 * Created by fernando on 6/19/15.
 */
public class Text {

    private String text;
    private Font font;

    public Text(String text){
        this.text = text;
        this.font = new Font("Arial", Font.PLAIN, 22);
    }

    public Text(String text, Font font){
        this.text = text;
        this.font = font;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        text = text;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}
