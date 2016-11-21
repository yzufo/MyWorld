package Action;

import Model.Item;
import Model.Location;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cbhzhun on 2016/11/19.
 */
public class XMLReader{
    private static Element rootElement;
    File xmlFile;

    public int getInitLocation() throws Exception{
        return Integer.parseInt(this.rootElement.element("CurrentLocation").getText());
    }

    public int getCurrentFace() throws Exception{
        return Integer.parseInt(this.rootElement.element("CurrentFace").getText());
    }

    public void setXmlFile(File xmlFile) {
        this.xmlFile = xmlFile;
    }

    public List<Item> readItems() throws Exception{
        List<Item> myItems = new ArrayList<>();
        int itemsNumber = Integer.parseInt(rootElement.element("ItemsNumber").getText());
        for (int i = 1; i <= itemsNumber; i++) {
            Item tmpItem = new Item();
            tmpItem.setImage(new Image(rootElement.element("Items").element("Item" + String.valueOf(i)).getText()));
            tmpItem.setInLocation(false);
            tmpItem.setItemId(i);
            myItems.add(tmpItem);
        }
        return myItems;
    }

    public Image getMap() throws Exception {
        return new Image(rootElement.element("MapUrl").getText());
    }

    public Image getNaviArrow() throws Exception{
        return new Image(rootElement.element("NaviImageUrl").getText());
    }

    public HashMap<Integer, Location> readLocations() throws Exception{
        HashMap<Integer, Location> myWorld = new HashMap<>();
            int locationNumber = Integer.parseInt(rootElement.element("LocationNumber").getText());
            for (int i = 1; i <= locationNumber; i++) {
                Element element = rootElement.element(String.format("Location%d", i));
                Location location = new Location(element.element("LocationName").getText());
                location.setX(Double.parseDouble(element.attributeValue("X")));
                location.setY(Double.parseDouble(element.attributeValue("Y")));
                int imageNumber = Integer.parseInt(element.element("ImagesNumber").getText());
                location.setImageNumber(imageNumber);
                for (int j = 1; j <= imageNumber; j++) {
                    Element elementImage = element.element(String.format("Images%d", j));
                    location.setLocationMap(j, new Image(elementImage.element("Url").getText()));

                    String isForward = elementImage.attributeValue("isForward");
                    if (isForward.equals("1")) {
                        location.setIsForward(j, 1);
                        location.setForward(j, Integer.parseInt(elementImage.element("ForwardLocationId").getText()));
                        location.setForwardImage(j, Integer.parseInt(elementImage.element("ForwardImage").getText()));
                    } else {
                        location.setIsForward(j, 0);
                    }
                }
                myWorld.put(i, location);
            }

        return myWorld;
    }

    public void read() throws Exception{
            //InputStream inputStream = new FileInputStream(xmlFile);
            //创建SAXReader读取器，专门用于读取xml
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(xmlFile);
            this.rootElement = document.getRootElement();
    }
}
