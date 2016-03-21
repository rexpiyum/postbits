package backend.control;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fernando on 6/19/15.
 */
public class ImageCreator {

    final static int IMAGE_SIZE = 5824;
    final static int IMAGE_WIDTH = 255;
    final static int IMAGE_HEIGHT = 176;

    public static byte[] createImageFromText(Text text, String id) {
        List<Text> textList = new ArrayList<Text>();
        textList.add(text);
        return createImageFromText(textList, id);
    }

    public static byte[] createImageFromText(List<Text> textList, String id) {

        BufferedImage image = new BufferedImage(264, 176, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        int height = 0;
        for (Text text : textList) {
            g2d.setFont(text.getFont());
            FontMetrics fontMetrics = g2d.getFontMetrics();
            String[] words = text.getText().split(" ");
            String line = words[0];

            for (int i = 1; i < words.length; i++) {
                System.out.println(fontMetrics.stringWidth(line + words[i]));
                if (fontMetrics.stringWidth(line + words[i]) > IMAGE_WIDTH) {
                    System.out.println(line);
                    //g2d.drawString(line,0,height+fontMetrics.getAscent());
                    height += fontMetrics.getHeight();
                    line = words[i];
                } else {
                    line += " " + words[i];
                }
            }
            //g2d.drawString(line,0,height+fontMetrics.getAscent());
            height += fontMetrics.getHeight() + 2;

        }

        if (height < IMAGE_HEIGHT) {
            height = (int) Math.floor(((IMAGE_HEIGHT - height) / 2));
        } else {
            height = 0;
        }
        for (Text text : textList) {
            g2d.setFont(text.getFont());
            FontMetrics fontMetrics = g2d.getFontMetrics();
            String[] words = text.getText().split(" ");
            String line = words[0];

            for (int i = 1; i < words.length; i++) {
                System.out.println(fontMetrics.stringWidth(line + words[i]));
                if (fontMetrics.stringWidth(line + words[i]) > IMAGE_WIDTH) {
                    System.out.println(line);
                    g2d.drawString(line, 0, height + fontMetrics.getAscent());
                    height += fontMetrics.getHeight();
                    line = words[i];
                } else {
                    line += " " + words[i];
                }
            }
            g2d.drawString(line, 0, height + fontMetrics.getAscent());
            height += fontMetrics.getHeight() + 2;

        }
        if (id != null) {
            drawDeviceID(g2d, id);
        }
        g2d.dispose();

        return getWIFImage(image);

    }

    public static byte[] createImageFromImage(BufferedImage srcImage, String id) {

        int width = 264;
        int height = 176;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = img.createGraphics();

//        BufferedImage srcImage = null;
//        try {
//            srcImage = ImageIO.read(new File("repository/deployment/server/webapps/cloudDrop_backend/image.jpg"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        g2d.drawImage(srcImage, 0, 0, width, height, 0, 0, srcImage.getWidth(), srcImage.getHeight(), null);
        if (id != null) {
            drawDeviceID(g2d, id);
        }
        g2d.dispose();

        DataBuffer data = img.getRaster().getDataBuffer();
        for (int i = 0; i < data.getSize(); i++) {
            int index;
            if (data.getElem(i) >= 200) {
                data.setElem(i, 0);
           }else if ((data.getElem(i) >= 160)) {
                if (((((i - i % 264) / 264) % 6) == 0) && ((i % 6) == 0)) {

                    data.setElem(i, 1);

                }else if (((((i - i % 264) / 264) % 6) == 3) && ((i % 6) == 3)) {

                    data.setElem(i, 1);

                }else {
                    data.setElem(i, 0);
                }
            }else if ((data.getElem(i) >= 120)) {
                if (((((i - i % 264) / 264) % 4) == 0) && ((i % 4) == 0)) {

                    data.setElem(i, 1);

                }else if (((((i - i % 264) / 264) % 4) == 2) && ((i % 4) == 2)) {

                    data.setElem(i, 1);

                }else {
                    data.setElem(i, 0);
                }
            }else if ((data.getElem(i) >= 50)) {
                if (((((i - i % 264) / 264) % 2) == 0) && ((i % 2) == 0)) {

                        data.setElem(i, 1);

                }else if (((((i - i % 264) / 264) % 2) == 1) && ((i % 2) == 1)) {

                    data.setElem(i, 1);

                }else {
                    data.setElem(i, 0);
                }
            } else {
                data.setElem(i, 1);

            }
        }
        return getWIFImage(img);

    }

    private static void drawDeviceID(Graphics2D g2d, String id) {
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int height = fontMetrics.getHeight() + 4;
        int width = fontMetrics.stringWidth(id) + 4;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(264 - width, 176 - height, width, height);
        g2d.setColor(Color.BLACK);
        g2d.drawString(id, 264 - width + 2, 176 - 2);
    }

    private static byte[] getWIFImage(BufferedImage image) {

        byte[] WIFData = new byte[IMAGE_SIZE];
        String binaryString = "";
        WIFData[0] = (byte) Integer.parseInt("10110000", 2);
        WIFData[1] = (byte) Integer.parseInt("00000000", 2);
        WIFData[2] = (byte) Integer.parseInt("00001000", 2);
        WIFData[3] = (byte) Integer.parseInt("00000001", 2);

        //this is to rectify the mirroring issue of the display
        DataBuffer data = image.getRaster().getDataBuffer();
        int[] dataCopy = new int[data.getSize()];
        for (int i = 0; i < data.getSize(); i++) {
            dataCopy[i] = data.getElem(i);
        }

        for (int i = 0; i < data.getSize(); i++) {
            int index = i + (263) - 2 * (i % 264);
            int value = dataCopy[data.getSize() - 1 - i];
            //System.out.println(i+":"+index + ":" + (data.getSize()-1-i));
            data.setElem(index, value);
        }
        for (int i = 0; i < data.getSize(); i++) {
            int index;
            if (data.getElem(i) != 0) {
                binaryString = "1" + binaryString;
            } else {
                binaryString = "0" + binaryString;
            }
            if ((i + 1) % 8 == 0) {
                index = 3 + ((i + 1) / 8);
                //binaryString = binaryString.substring(0,7)+"1";
                WIFData[index] = (byte) Integer.parseInt(binaryString, 2);
                //WIFData[index] = Byte.parseByte(binaryString,2);
                binaryString = "";

            }
        }
        //WIFData[IMAGE_SIZE - 1] = 100;
        return WIFData;

    }
}
