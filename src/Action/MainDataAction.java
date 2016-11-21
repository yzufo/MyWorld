package Action;

import Model.Item;
import MyWorldController.MainController;

import java.io.File;

/**
 * Created by cbhzhun on 2016/11/20.
 */
public class MainDataAction extends MainController {
    public MainDataAction(){
        myWold.setCurrentItemId(-1);
        myWold.setCurrentPickItemId(-1);
       // currentItemId = currentPickItemId = -1;
    }
    public void PutItemAction(){
        int deletid = -1;
        for(int i=0;i<myWold.getMyItems().size();i++)
            if(myWold.getMyItems().get(i).getItemId() == myWold.getCurrentItemId())
            {
                deletid = i;
                break;
            }
        if(deletid != -1){
            myWold.addItem(deletid);
           // currentLocation.addItem(myItems.get(deletid));
            myWold.remove(deletid);
          //  myItems.remove(deletid);
        }
    }
    public void PickItemAction(){
        int deletid = -1;
        for(int i=0;i<myWold.getCurrentLocation().getItem().size();i++)
            if(myWold.getCurrentLocation().getItem().get(i).getItemId() == myWold.getCurrentPickItemId()){
                deletid = i;
            }
        if(deletid != -1){
            Item item  = myWold.getCurrentLocation().getItem().get(deletid);
            myWold.add(item.getItemId()-1,item);
           // myItems.add(item.getItemId()-1,item);
            myWold.deleteItem(deletid);
           // currentLocation.deleteItem(deletid);
        }
    }
    public void readXMLFile(File file) throws Exception{
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
