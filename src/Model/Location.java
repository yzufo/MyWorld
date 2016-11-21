package Model;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cbhzhun on 2016/11/19.
 */
public class Location {
    private HashMap<Integer,Image> locationMap;
    private String name;
    private HashMap<Integer,Integer> forward,forwardImage,isForward;
    private List<Item> items;
    private double X,Y;
    private int imageNumber;


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

    public void addItem(Item item){
        items.add(item);
    }
    public void deleteItem(int i){
        items.remove(i);
    }
    public List<Item> getItem(){
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

    public void setIsForward(int i,int isForward) {
        this.isForward.put(i,isForward);
    }
    public int getForwardImage(int i){
        return forwardImage.get(i);
    }
    public void setForwardImage(int i,int j){
        forwardImage.put(i,j);
    }
    public int getForward(int i) {
        return forward.get(i);
    }

    public void setForward(int i,int targetid) {
        this.forward.put(i,targetid);
    }

    public Location(String name){
        locationMap = new HashMap<>();
        forward = new HashMap<>();
        forwardImage = new HashMap<>();
        isForward = new HashMap<>();
        items = new ArrayList<>();
        this.name = name;
    }
    public void setLocationMap(int i,Image image){
        locationMap.put(i,image);
    }
    public Image getLocationMap(int i){
        return locationMap.get(i);
    }
}
