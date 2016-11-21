package MyWorldController;

import Action.MainDataAction;
import Action.TextFlowOutput;
import Action.XMLReader;
import MainEntry.MyMainEntry;
import Model.Item;
import Model.World;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by cbhzhun on 2016/11/19.
 */
public class MainController implements Initializable{
    private MyMainEntry application;
   /* protected static HashMap<Integer, Location> allLocations;
    protected static List<Item> myItems;
    protected static Location currentLocation;
    protected static int currentFace, currentItemId, currentPickItemId, currentLocationId;
    private TextFlowOutput textFlowOutput;*/
    protected static World myWold;
    private TextFlowOutput textFlowOutput;

    @FXML private ScrollPane textContainer;
    @FXML private MenuItem putMenu, pickMenu;
    protected static XMLReader xmlReader;
    private MainDataAction mainDataAction;
    @FXML private HBox mainViewBox, itemsHbox,mapViewBox;
    @FXML private Button leftButton, forwardButton, rightButton;
    private ImageView mainImageView,mapImageView,naviImageView;
    @FXML private TextFlow textFlow;

    public void setApp(MyMainEntry application){
        this.application = application;
    }

    public void Initialise() {
        myWold = new World();
        xmlReader = new XMLReader();
       // allLocations = new HashMap<>();
       // myItems = new ArrayList<>();
        mainDataAction = new MainDataAction();
        textFlowOutput = new TextFlowOutput();

        mapImageView = new ImageView();
        mapImageView.setFitWidth(275);
        mapImageView.setFitHeight(200);
        mapImageView.setPreserveRatio(true);

        naviImageView = new ImageView();
        naviImageView.setFitWidth(30);
        naviImageView.setFitHeight(30);
        naviImageView.setPreserveRatio(true);

        mainImageView = new ImageView();
        mainImageView.setFitWidth(300);
        mainImageView.setFitHeight(400);
        mainImageView.setPreserveRatio(true);
        mainImageView.setId("999");

        leftButton.setDisable(true);
        rightButton.setDisable(true);
        forwardButton.setDisable(true);
        itemsHbox.setSpacing(9);

        textFlow.getChildren().addListener(
                (ListChangeListener<Node>) ((change) -> {
                    textFlow.requestLayout();
                    textContainer.layout();
                    textContainer.setVvalue(1.0f);
                }));
    }

    public void ImportFile() {
        Stage stage = new Stage();
        stage.setTitle("Choose XML File");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML", "*.xml"));
        File file = fileChooser.showOpenDialog(stage);
        Import(file);
    }

    public void Import(File file){
        try {
            mainDataAction.readXMLFile(file);
            leftButton.setDisable(false);
            rightButton.setDisable(false);
            mapImageView.setImage(xmlReader.getMap());
            naviImageView.setImage(xmlReader.getNaviArrow());
            showImage();
            showItem();
            showMap();
            checkForward();
        }catch (Exception e){
            e.printStackTrace();
            ThrowError();
        }
    }
    public void ThrowError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("Cannot read XML file!");
        alert.setContentText("Please make sure this XML file is valid！");
        alert.showAndWait();
    }
    public void PutItem() {
        textFlow.getChildren().add(textFlowOutput.outputPutItem());
        mainDataAction.PutItemAction();
        showItem();
        showImage();
    }

    public void PickItem() {
        textFlow.getChildren().add(textFlowOutput.outputPickItem());
        mainDataAction.PickItemAction();
        showImage();
        showItem();
    }

    public void showItem() {
        itemsHbox.getChildren().clear();
        myWold.getMyItems().forEach(item -> {
            myWold.setCurrentItemId(-1);
            ImageView imageView = new ImageView();
            imageView.setImage(item.getImage());
            imageView.setId(String.valueOf(item.getItemId()));
            imageView.setOnMouseClicked(event -> {
                myWold.setCurrentItemId(Integer.parseInt(imageView.getId()));
                imageView.setEffect(new DropShadow(20, Color.BLACK));
                textFlow.getChildren().add(textFlowOutput.outputSelectItem());
            });
            itemsHbox.getChildren().add(imageView);
        });
    }

    public void ClickItem() {
        itemsHbox.getChildren().forEach(node -> {
            if (Integer.parseInt(node.getId()) != myWold.getCurrentItemId()) {
                node.setEffect(new DropShadow(0, Color.BLACK));
            }
        });
    }

    public void showImage() {

        mainImageView.setImage(myWold.getCurrentLocation().getLocationMap(myWold.getCurrentFace()));// set location image as main background image
        mainViewBox.getChildren().clear();
        //Show items in the main viw, create a group to contain all items ImageView
        Group groupView = new Group();
        groupView.getChildren().add(mainImageView);
        List<Item> tmpItems = myWold.getCurrentLocation().getItem();
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
            myWold.setCurrentPickItemId(-1);
            if (event.getPickResult().getIntersectedNode().getId() != "999") {//if image is background image, no effect
                event.getPickResult().getIntersectedNode().setEffect(new InnerShadow(20, Color.BLACK));
                myWold.setCurrentPickItemId(Integer.parseInt(event.getPickResult().getIntersectedNode().getId()));
                textFlow.getChildren().add(textFlowOutput.outputSelectPickItem());
            }
        });
        mainViewBox.getChildren().add(groupView);
    }
    public void showMap(){
        mapViewBox.getChildren().clear();
        naviImageView.setRotate((360.0/myWold.getCurrentLocation().getImageNumber()) * (myWold.getCurrentFace()) );
        naviImageView.setX(myWold.getCurrentLocation().getX());
        naviImageView.setY(myWold.getCurrentLocation().getY());
        Group mapGroup = new Group(mapImageView,naviImageView);
        mapViewBox.getChildren().add(mapGroup);
    }
    public void ClickLeftButton() {
        myWold.setCurrentFace(myWold.getCurrentFace() - 1);
        if (myWold.getCurrentFace() == 0) myWold.setCurrentFace(myWold.getCurrentLocation().getImageNumber());
        showImage();
        showMap();
        checkForward();
        textFlow.getChildren().add(textFlowOutput.outputTurnLeft());
    }

    public void ClickRightButton() {
        myWold.setCurrentFace(myWold.getCurrentFace() % myWold.getCurrentLocation().getImageNumber() + 1 );
        showImage();
        showMap();
        checkForward();
        textFlow.getChildren().add(textFlowOutput.outputTurnRight());
    }

    public void ClickForwardButton() {
        myWold.replace(myWold.getCurrentLocationId(),myWold.getCurrentLocation());
       // allLocations.replace(currentLocationId, currentLocation);
        myWold.setCurrentLocationId(myWold.getCurrentLocation().getForward(myWold.getCurrentFace()));
        //currentLocationId = currentLocation.getForward(currentFace);
        myWold.setCurrentFace(myWold.getCurrentLocation().getForwardImage(myWold.getCurrentFace()));
       // currentFace = currentLocation.getForwardImage(currentFace);
        myWold.setCurrentLocation(myWold.getAllLocations().get(myWold.getCurrentLocationId()));
        //currentLocation = allLocations.get(currentLocationId);
        showImage();
        showMap();
        checkForward();
        textFlow.getChildren().add(textFlowOutput.moveToNewLocation());
    }
    public void checkForward(){
        //if this directing can go forward, make forward button visible
        if (myWold.getCurrentLocation().getIsForward(myWold.getCurrentFace()) == 1) {
            forwardButton.setDisable(false);
            textFlow.getChildren().add(textFlowOutput.outputForwardAvaiable());
        }
        else
            forwardButton.setDisable(true);
    }

    public void ClickItems() {
        if (myWold.getCurrentItemId() == -1)
            putMenu.setDisable(true);
        else
            putMenu.setDisable(false);
        if (myWold.getCurrentPickItemId() == -1)
            pickMenu.setDisable(true);
        else
            pickMenu.setDisable(false);
    }
    public void quitApplication(){
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
