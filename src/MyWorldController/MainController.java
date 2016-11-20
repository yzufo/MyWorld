package MyWorldController;

import Action.XMLReader;
import Model.Item;
import Model.Location;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cbhzhun on 2016/11/19.
 */
public class MainController {
    protected static HashMap<Integer, Location> myWorld;
    protected static List<Item> myItems;
    protected static Location currentLocation;
    protected static int currentFace, currentItemId, currentPickItemId, currentLocationId;
    @FXML
    private MenuItem putMenu, pickMenu;
    XMLReader xmlReader;
    ItemsController itemsController;
    @FXML
    private HBox mainViewBox, itemsHbox;
    @FXML
    private Button leftButton, forwardButton, rightButton;
    private ImageView mainImageView;

    public void Initialise() {
        xmlReader = new XMLReader();
        myWorld = new HashMap<>();
        myItems = new ArrayList<>();
        itemsController = new ItemsController();

        mainImageView = new ImageView();
        mainImageView.setFitWidth(300);
        mainImageView.setFitHeight(400);
        mainImageView.setPreserveRatio(true);
        mainImageView.setId("999");

        leftButton.setDisable(true);
        rightButton.setDisable(true);
        forwardButton.setDisable(true);
        itemsHbox.setSpacing(9);

        //  mainImageView = new ImageView();
        //   mainImageView.setImage(new Image("huaji.jpg"));
    }

    public void ImportFile(ActionEvent event) {
        Stage stage = new Stage();
        stage.setTitle("Choose XML File");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML", "*.xml"));
        File file = fileChooser.showOpenDialog(stage);
        System.out.println(file);
        xmlReader.setXmlFile(file);
        xmlReader.read();
        myWorld = xmlReader.readLocations();
        myItems = xmlReader.readItems();
        currentLocationId = xmlReader.getInitLocation();
        currentLocation = myWorld.get(currentLocationId);
        currentFace = xmlReader.getCurrentFace();
        leftButton.setDisable(false);
        rightButton.setDisable(false);
        showImage();
        showItem();
    }

    public void PutItem() {
        itemsController.PutItemAction();
        showItem();
        showImage();
    }

    public void PickItem() {
        itemsController.PickItemAction();
        showImage();
        showItem();
    }

    public void showItem() {
        itemsHbox.getChildren().clear();
        myItems.forEach(item -> {
            currentItemId = -1;
            ImageView imageView = new ImageView();
            imageView.setImage(item.getImage());
            imageView.setId(String.valueOf(item.getItemId()));
            imageView.setOnMouseClicked(event -> {
                currentItemId = Integer.parseInt(imageView.getId());
                imageView.setEffect(new DropShadow(20, Color.BLACK));
            });
            itemsHbox.getChildren().add(imageView);
        });
    }

    public void ClickItem() {
        itemsHbox.getChildren().forEach(node -> {
            if (Integer.parseInt(node.getId()) != currentItemId) {
                node.setEffect(new DropShadow(0, Color.BLACK));
            }
        });
    }

    public void showImage() {

        mainImageView.setImage(currentLocation.getLocationMap(currentFace));// set location image as main background image
        //if this directing can go forward, make forward button visible
        if (currentLocation.getIsForward(currentFace) == 1)
            forwardButton.setDisable(false);
        else forwardButton.setDisable(true);
        mainViewBox.getChildren().clear();
        //Show items in the main viw, create a group to contain all items ImageView
        Group groupView = new Group();
        groupView.getChildren().add(mainImageView);
        List<Item> tmpItems = currentLocation.getItem();
        //put all items into the group
        for (int i = 0; i < tmpItems.size(); i++) {
            ImageView tmpImageView = new ImageView(tmpItems.get(i).getImage());
            tmpImageView.setFitWidth(64);
            tmpImageView.setFitHeight(64);
            tmpImageView.setY(i * 64);
            tmpImageView.setId(String.valueOf(tmpItems.get(i).getItemId()));
            groupView.getChildren().add(tmpImageView);
        }
        //set effect of clicking item
        groupView.setOnMouseClicked(event -> {
            groupView.getChildren().forEach(node -> {
                node.setEffect(null);
            });
            currentPickItemId = -1;
            if (event.getPickResult().getIntersectedNode().getId() != "999") {//if image is background image, no effect
                event.getPickResult().getIntersectedNode().setEffect(new InnerShadow(20, Color.BLACK));
                currentPickItemId = Integer.parseInt(event.getPickResult().getIntersectedNode().getId());
            }
        });
        mainViewBox.getChildren().add(groupView);
    }

    public void ClickLeftButton(ActionEvent event) {
        currentFace -= 1;
        if (currentFace == 0) currentFace = currentLocation.getImageNumber();
        showImage();
    }

    public void ClickRightButton(ActionEvent event) {
        currentFace = currentFace % currentLocation.getImageNumber() + 1;
        showImage();
    }

    public void ClickForwardButton(ActionEvent event) {
        myWorld.replace(currentLocationId, currentLocation);
        currentLocationId = currentLocation.getForward(currentFace);
        currentFace = currentLocation.getForwardImage(currentFace);
        currentLocation = myWorld.get(currentLocationId);
        showImage();
    }

    public void ClickItems() {
        if (currentItemId == -1)
            putMenu.setDisable(true);
        else
            putMenu.setDisable(false);
        if (currentPickItemId == -1)
            pickMenu.setDisable(true);
        else
            pickMenu.setDisable(false);
    }
}
