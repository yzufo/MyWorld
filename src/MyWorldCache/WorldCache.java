package MyWorldCache;

import Action.TextFlowOutput;
import Model.Item;
import Model.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cbhzhun on 2016/11/21.
 */
public class WorldCache {
    private HashMap<Integer, Location> allLocations;
    private List<Item> myItems;
    private Location currentLocation;
    private int currentFace, currentItemId, currentPickItemId, currentLocationId;

    public void deleteItem(int deletid){
        currentLocation.deleteItem(deletid);
    }
    public void add(int id,Item item){
        myItems.add(id,item);
    }
    public void remove(int deletid){
        myItems.remove(deletid);
    }

    public void addItem(int deletid){
        currentLocation.addItem(myItems.get(deletid));
    }
    public void replace(int id, Location location){
        allLocations.replace(id,location);
    }
    public HashMap<Integer, Location> getAllLocations() {
        return allLocations;
    }

    public void setAllLocations(HashMap<Integer, Location> allLocations) {
        this.allLocations = allLocations;
    }

    public List<Item> getMyItems() {
        return myItems;
    }

    public void setMyItems(List<Item> myItems) {
        this.myItems = myItems;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public int getCurrentFace() {
        return currentFace;
    }

    public void setCurrentFace(int currentFace) {
        this.currentFace = currentFace;
    }

    public int getCurrentItemId() {
        return currentItemId;
    }

    public void setCurrentItemId(int currentItemId) {
        this.currentItemId = currentItemId;
    }

    public int getCurrentPickItemId() {
        return currentPickItemId;
    }

    public void setCurrentPickItemId(int currentPickItemId) {
        this.currentPickItemId = currentPickItemId;
    }

    public int getCurrentLocationId() {
        return currentLocationId;
    }

    public void setCurrentLocationId(int currentLocationId) {
        this.currentLocationId = currentLocationId;
    }



    public void World(){
        allLocations = new HashMap<>();
        myItems = new ArrayList<>();
        currentLocation = new Location(null);
        currentFace = currentItemId = currentPickItemId = currentLocationId = -1;
    }
}
