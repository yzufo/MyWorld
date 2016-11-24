package Action;

import Model.Item;
import MyWorldController.MainController;

import java.io.File;

/**
 * Created by cbhzhun on 2016/11/20.
 * This class use to update cache
 */
public class MainDataAction extends MainController {
    /**
     * Init
     */
    public MainDataAction() {
        //set these variable as-1 means user don't choose anything
        myWold.setCurrentItemId(-1);
        myWold.setCurrentPickItemId(-1);
        // currentItemId = currentPickItemId = -1;
    }

    /**
     * Update cache about items
     * save item which want to put into current location's cache and delete from items' cache
     */
    public void PutItemAction() {
        int deletid = -1;
        for (int i = 0; i < myWold.getMyItems().size(); i++)
            if (myWold.getMyItems().get(i).getItemId() == myWold.getCurrentItemId()) {
                deletid = i;
                break;
            }
        if (deletid != -1) {
            myWold.addItem(deletid);
            // currentLocation.addItem(myItems.get(deletid));
            myWold.remove(deletid);
            //  myItems.remove(deletid);
        }
    }

    /**
     * Update cache about items
     * save item which want to put into items' cache and delete from current location's cache
     */
    public void PickItemAction() {
        int deletid = -1;
        for (int i = 0; i < myWold.getCurrentLocation().getItem().size(); i++)
            if (myWold.getCurrentLocation().getItem().get(i).getItemId() == myWold.getCurrentPickItemId()) {
                deletid = i;
            }
        if (deletid != -1) {
            Item item = myWold.getCurrentLocation().getItem().get(deletid);
            myWold.add(item.getItemId() - 1, item);
            // myItems.add(item.getItemId()-1,item);
            myWold.deleteItem(deletid);
            // currentLocation.deleteItem(deletid);
        }
    }

    /**
     * Read all data and save them into cache
     *
     * @param file xmlfile
     * @throws Exception
     */
    public void readXMLFile(File file) throws Exception {
        xmlReader.setXmlFile(file);
        xmlReader.read();
        myWold.setAllLocations(xmlReader.readLocations());
        // allLocations = xmlReader.readLocations();
        myWold.setMyItems(xmlReader.readItems());
        // myItems = xmlReader.readItems();
        myWold.setCurrentLocationId(xmlReader.getInitLocation());
        //currentLocationId = xmlReader.getInitLocation();
        myWold.setCurrentLocation(myWold.getAllLocations().get(myWold.getCurrentLocationId()));
        //currentLocation = allLocations.get(currentLocationId);
        myWold.setCurrentFace(xmlReader.getCurrentFace());
        //currentFace = xmlReader.getCurrentFace();
    }
}
