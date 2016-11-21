package MyWorldController;

import MainEntry.MyMainEntry;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by cbhzhun on 2016/11/21.
 */
public class FirstController implements Initializable{
    private MyMainEntry application;

    public void setApp(MyMainEntry application){
        this.application = application;
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
