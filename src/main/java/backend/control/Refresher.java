package backend.control;

import java.util.Collection;

/**
 * Created by fernando on 6/19/15.
 */
public class Refresher extends Thread {

    final int DELAY = 3000;

    @Override
    public void run() {

        Collection <Mapping> mappingCollection = Controller.getMappingStore().getMappingsList().values();

        for (Mapping mapping : mappingCollection){
            mapping.refresh();
        }

        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
