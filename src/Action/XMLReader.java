package Action;

import Model.Item;
import Model.Location;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Created by cbhzhun on 2016/11/19.
 * This class use to read all data and save into cache and get data from cache
 */
public class XMLReader {
    private static Element rootElement;
    private static int isRemote;
    private static String myUrl;
    File xmlFile;

    private String mainUrl = mainUrl = "file:///" + System.getProperty("user.dir") + "/";

    /**
     * Use inputstream to save online file
     *
     * @param inputStream inputStream from remote
     * @return xmlfile
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * Get init Location When user just import this world
     *
     * @return location id
     * @throws Exception
     */
    public int getInitLocation() throws Exception {
        return Integer.parseInt(this.rootElement.element("CurrentLocation").getText());
    }

    /**
     * Get init direction When user just import this world
     *
     * @return direction id
     * @throws Exception
     */
    public int getCurrentFace() throws Exception {
        return Integer.parseInt(this.rootElement.element("CurrentFace").getText());
    }

    /**
     * init
     *
     * @param xmlFile
     */
    public void setXmlFile(File xmlFile) {
        this.xmlFile = xmlFile;
    }

    /**
     * Read items' Image and save into cache
     *
     * @return Item List
     * @throws Exception
     */
    public List<Item> readItems() throws Exception {
        List<Item> myItems = new ArrayList<>();
        int itemsNumber = Integer.parseInt(rootElement.element("ItemsNumber").getText());
        for (int i = 1; i <= itemsNumber; i++) {
            Item tmpItem = new Item();
            tmpItem.setImage(new Image(mainUrl + rootElement.element("Items").element("Item" + String.valueOf(i)).getText()));
            tmpItem.setInLocation(false);
            tmpItem.setItemId(i);
            myItems.add(tmpItem);
        }
        return myItems;
    }

    /**
     * If error has happened when read xmlfile.
     * show an alert to tell user
     */
    public void ThrowError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("Cannot read XML file!");
        alert.setContentText("Please make sure this XML file is valid！");
        alert.showAndWait();
    }

    /**
     * read map's image and save into cache
     *
     * @return map's Iamge
     * @throws Exception
     */
    public Image getMap() throws Exception {
        return new Image(mainUrl + rootElement.element("MapUrl").getText());
    }

    /**
     * read naviArrow's image and save into cache
     *
     * @return naviArrow's image
     * @throws Exception
     */
    public Image getNaviArrow() throws Exception {
        return new Image(mainUrl + rootElement.element("NaviImageUrl").getText());
    }

    /**
     * show a dialog to let user choose xml file to import
     *
     * @return xmlfile
     * @throws Exception
     */
    public File getXMLFileFromLocal() throws Exception {
        Stage stage = new Stage();
        stage.setTitle("Choose XML File");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        //Setting file type, make sure user can only choose .xml file
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML", "*.xml"));
        File file = fileChooser.showOpenDialog(stage);
        return file;
    }

    /**
     * show a dialog to let user input xml file (online url) to load xml file
     *
     * @return xmlfile
     * @throws Exception
     */
    public File getXMLFileFromRemote() throws Exception {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("XML File Url");
        dialog.setHeaderText("Please input the Url of your XML file.");
        dialog.setContentText("Url:");
        Optional result = dialog.showAndWait();
        String tmpfileUrl = "";
        if (result.isPresent()) {
            tmpfileUrl = result.get().toString();
        }
        if (tmpfileUrl.equals("")) return null;
        return getFileFromUrl(tmpfileUrl, "temp.xml");
    }

    /**
     * Create a connection to download xml file
     *
     * @param tmpUrl   file's url
     * @param filename tmp file's name
     * @return xml file
     * @throws Exception
     */
    public File getFileFromUrl(String tmpUrl, String filename) throws Exception {
        URL url = new URL(tmpUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.connect();
        InputStream inputStream = con.getInputStream();
        byte[] getData = readInputStream(inputStream);
        File file = new File(filename);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        inputStream.close();
        return file;
    }

    /**
     * Read all Location's information
     *
     * @return a hashmap which store all location's information
     * @throws Exception
     */
    public HashMap<Integer, Location> readLocations() throws Exception {
        HashMap<Integer, Location> myWorld = new HashMap<>();
        int locationNumber = Integer.parseInt(rootElement.element("LocationNumber").getText());
        for (int i = 1; i <= locationNumber; i++) {
            Element element = rootElement.element(String.format("Location%d", i));
            Location location = new Location(element.element("LocationName").getText());
            location.setX(Double.parseDouble(element.attributeValue("X")));
            location.setY(Double.parseDouble(element.attributeValue("Y")));
            int imageNumber = Integer.parseInt(element.element("ImagesNumber").getText());
            location.setHasPano(Boolean.parseBoolean(element.attributeValue("hasPane")));
            if(location.getHasPano())
                location.setLocationMapUrl(0,mainUrl + element.element("Pano").getText());
            location.setImageNumber(imageNumber);
            for (int j = 1; j <= imageNumber; j++) {
                Element elementImage = element.element(String.format("Images%d", j));
                //location.setLocationMap(j, new Image(mainUrl + elementImage.element("Url").getText()));
                location.setLocationMapUrl(j,mainUrl + elementImage.element("Url").getText());
                String isForward = elementImage.attributeValue("isForward");
                if (isForward.equals("1")) {
                    location.setIsForward(j, 1);
                    location.setForward(j, Integer.parseInt(elementImage.element("ForwardLocationId").getText()));
                    location.setForwardImage(j, Integer.parseInt(elementImage.element("ForwardImage").getText()));
                    if(location.getHasPano()){
                        location.setStartX(j,Double.parseDouble(elementImage.attributeValue("X")));
                        location.setRange(j,Double.parseDouble(elementImage.attributeValue("Range")));
                    }
                } else {
                    location.setIsForward(j, 0);
                }
            }
            myWorld.put(i, location);
        }
        return myWorld;
    }

    /**
     * Read xml file and transform to Document
     *
     * @throws Exception
     */
    public void read() throws Exception {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(xmlFile);
        this.rootElement = document.getRootElement();
        isRemote = Integer.parseInt(rootElement.element("isRemote").getText());
        if (isRemote == 1) {
            myUrl = rootElement.element("Url").getText();
            mainUrl = myUrl;
        }
    }
}
