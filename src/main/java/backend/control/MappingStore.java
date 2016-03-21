package backend.control;

import backend.persistence.DBConnector;

import java.util.HashMap;

/**
 * Created by fernando on 6/19/15.
 */
public class MappingStore {

    private HashMap<String,Mapping> mappingsList;

    MappingStore(){
        mappingsList = new HashMap<String,Mapping>();
    }

    public void addMapping(String key, Mapping mapping){
        mappingsList.put(key,mapping);
        DBConnector.saveMapping(key, mapping);
    }

    public Mapping getMapping(String key){
        return mappingsList.get(key);
    }

    public HashMap<String, Mapping> getMappingsList(){
        return mappingsList;
    }

    public void loadMappings(){
        DBConnector.loadMappings(mappingsList);
    }


}
