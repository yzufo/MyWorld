package Model;

import javafx.scene.image.Image;

/**
 * Created by cbhzhun on 2016/11/20.
 * Object of item
 */
public class Item {
    private Image image;
    private int itemId;
    private boolean isInLocation;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public boolean isInLocation() {
        return isInLocation;
    }

    public void setInLocation(boolean inLocation) {
        isInLocation = inLocation;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }


    public void Item() {

    }
}
