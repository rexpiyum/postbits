package backend.control;

/**
 * Created by fernando on 6/19/15.
 */
public class Controller {

    private static MappingStore mappingStore;
    private static DeviceStore deviceStore;

    public void startRefresher(){
        new Refresher().start();
    }

    public Controller(){

        mappingStore = new MappingStore();
        deviceStore = new DeviceStore();

    }

    public static MappingStore getMappingStore() {
        return mappingStore;
    }

    public static DeviceStore getDeviceStore(){
        return deviceStore;
    }
}
