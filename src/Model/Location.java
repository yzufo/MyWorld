package Model;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cbhzhun on 2016/11/19.
 * Object of location
 */
public class Location {
    private HashMap<Integer, Image> locationMap; //save images of different directions
    private String name; // This location's name
    // save each directions' attributes.
    //forward: next location
    //forwardImage: next location's direction
    //isForward :this direction can go forward or not
    private HashMap<Integer, Integer> forward, forwardImage, isForward;
    private List<Item> items;//save items which have been put in this location
    private double X, Y; // this location's position in the map
    private int imageNumber; // How much directions in this location

    /**
     * init
     *
     * @param name location's name
     */
    public Location(String name) {
        locationMap = new HashMap<>();
        forward = new HashMap<>();
        forwardImage = new HashMap<>();
        isForward = new HashMap<>();
        items = new ArrayList<>();
        this.name = name;
    }

    //Getters and Setters
    public String getName() {
        return name;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void deleteItem(int i) {
        items.remove(i);
    }

    public List<Item> getItem() {
        return items;
    }

    public int getImageNumber() {
        return imageNumber;
    }

    public void setImageNumber(int imageNumber) {
        this.imageNumber = imageNumber;
    }

    public int getIsForward(int i) {
        return isForward.get(i);
    }

    public void setIsForward(int i, int isForward) {
        this.isForward.put(i, isForward);
    }

    public int getForwardImage(int i) {
        return forwardImage.get(i);
    }

    public void setForwardImage(int i, int j) {
        forwardImage.put(i, j);
    }

    public int getForward(int i) {
        return forward.get(i);
    }

    public void setForward(int i, int targetid) {
        this.forward.put(i, targetid);
    }

    public void setLocationMap(int i, Image image) {
        locationMap.put(i, image);
    }

    public Image getLocationMap(int i) {
        return locationMap.get(i);
    }
}
