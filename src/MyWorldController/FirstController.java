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

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.HttpURLConnection;
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
    public void ClickImportButton() throws Exception{
        Stage stage = new Stage();
        stage.setTitle("Choose XML File");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML", "*.xml"));
        File file = fileChooser.showOpenDialog(stage);
        application.loadMainWindow(file);
    }
    public void importFromRemote() throws Exception{
        URL url = new URL("http://45.32.181.45/Source/MyWorld/MyWorld.xml");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.connect();
        InputStream inputStream = con.getInputStream();
        byte[] getData = readInputStream(inputStream);
        File file = new File("temp.xml");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        inputStream.close();
        application.loadMainWindow(file);
    }
    public void ClickQuitButton(){
        System.exit(1);
    }
    public void FirstController(MyMainEntry application){

    }
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
