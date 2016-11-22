package MyWorldController;

import MainEntry.MyMainEntry;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by cbhzhun on 2016/11/21.
 */
public class FirstController implements Initializable{
    private MyMainEntry application;

    @FXML private Button firstPageButton1,firstPageButton2;
    public void setApp(MyMainEntry application){
        this.application = application;
        firstPageButton1.setOnMouseEntered(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                firstPageButton1.setEffect(new DropShadow(10, Color.AQUA));
                firstPageButton1.requestFocus();
            }
        });
        firstPageButton1.setOnMouseExited(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                firstPageButton1.setEffect(null);
            }
        });
        firstPageButton2.setOnMouseEntered(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                firstPageButton2.setEffect(new DropShadow(10, Color.AQUA));
                firstPageButton2.requestFocus();
            }
        });
        firstPageButton2.setOnMouseExited(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                firstPageButton2.setEffect(null);
            }
        });
    }
    @FXML
    public void ClickImportButton(){
        Stage stage = new Stage();
        stage.setTitle("Choose XML File");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML", "*.xml"));
        File file = fileChooser.showOpenDialog(stage);
        application.loadMainWindow(file);
    }

    public void ClickQuitButton(){
        System.exit(1);
    }
    public void FirstController(MyMainEntry application){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
