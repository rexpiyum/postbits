package backend.control;

import backend.service.Content;
import backend.service.DeviceFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fernando on 6/23/15.
 */
public class ListMapping extends Mapping {

    private ArrayList<String> listItems;

    public ListMapping(String address){
        super(MappingType.LIST_MAPPING,address);
        setListItems(new ArrayList<String>());

    }

    @Override
    public boolean  refresh() {
        return true;
    }

    @Override
    public void createImage() {
        List<Text> textList = new ArrayList<Text>();
        for(String s: getListItems()){
            textList.add(new Text(s));
        }
        String id = String.valueOf(DeviceFactory.getDeviceID(getAddress()));
        setData(ImageCreator.createImageFromText(textList,id));
    }

    @Override
    public Content getText() {

        Content content = new Content();
        content.setTexts(listItems.toArray(new String[listItems.size()]));
        return content;
    }

    public ArrayList<String> getListItems(){
        return listItems;
    }

    public void addListItem(String item){
        if(item != null){
            getListItems().add(item);
        }
    }

    public void setListItems(ArrayList<String> listItems) {
        this.listItems = listItems;
    }
}
