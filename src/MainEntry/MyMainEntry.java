package MainEntry;

import MyWorldController.FirstController;
import MyWorldController.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.InputStream;

/**
 * Created by cbhzhun on 2016/11/19.
 */
public class MyMainEntry extends Application{
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println(this.getClass());
        stage = primaryStage;
        stage.setTitle("Import File");
        loadFirstWindow();
        stage.show();
       /* FXMLLoader fxmlLoader = new FXMLLoader();
        String viewerFxml = "../MyWorld.fxml";
        AnchorPane page = fxmlLoader.load(this.getClass().getResource(viewerFxml).openStream());
        // Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(page);
        primaryStage.setScene(scene);
        MainController viewer = (MainController) fxmlLoader.getController();
        viewer.Initialise();*/
        //primaryStage.show();
    }

    public void loadFirstWindow(){
        try{
            FirstController firstController = (FirstController) replaceSceneContent("../MyWorldInit.fxml");
            firstController.setApp(this);
        }catch(Exception e){

        }
    }
    public void loadMainWindow(File file){
        try{
            MainController mainController = (MainController) replaceSceneContent("../MyWorld.fxml");
            mainController.Initialise();
            try {
                mainController.Import(file);
            }catch (Exception e){
                mainController.ThrowError();
                e.printStackTrace();
            }
            mainController.setApp(this);
        }catch(Exception e){

        }
    }
    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Pane page = loader.load(this.getClass().getResource(fxml).openStream());
        Scene scene = new Scene(page);
        stage.setScene(scene);
        return (Initializable) loader.getController();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
