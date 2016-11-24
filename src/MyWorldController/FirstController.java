package MyWorldController;

import MainEntry.MyMainEntry;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import Action.XMLReader;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by cbhzhun on 2016/11/21.
 * This is fistPane's Controller
 */
public class FirstController implements Initializable{
    private MyMainEntry application;
    private XMLReader xmlReader = new XMLReader();

    @FXML private Button firstPageButton1,firstPageButton2;

    /**
     * init buttons' EventHandlers
     * @param application let Main stage show this pane
     */
    public void setApp(MyMainEntry application){
        this.application = application;
        //Set EventHandler of OnMouseEntryEvent and OnMouseExitEvent
        //Show and hide DropShadow when mouse entry and exit
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

    /**
     * Implement mouse click Import Button's event
     * Form file Chooser
     */
    public void ClickImportButton() {
        File file;
        try{
            file = xmlReader.getXMLFileFromLocal();
            if(file == null) {
                return;
            }
            application.loadMainWindow(file);
        }catch (Exception e){
            e.printStackTrace();
            xmlReader.ThrowError();
            return;
        }
    }

    /**
     * Implement mouse click ImportFromRemote Button's event
     * Load from internet
     * @throws Exception
     */
    public void importFromRemote() throws Exception{
        File file;
        try {
            file = xmlReader.getXMLFileFromRemote();
            if(file == null){
                return;
            }
            application.loadMainWindow(file);
        }catch (Exception e){
            e.printStackTrace();
            xmlReader.ThrowError();
            return;
        }
    }
    /**
     * Implement mouse click Quit Button's event
     * Exit application
     */
    public void ClickQuitButton(){
        System.exit(1);
    }

    /**
     * Init
     */
    public void FirstController(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
