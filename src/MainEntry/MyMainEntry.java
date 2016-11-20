package MainEntry;

import MyWorldController.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Created by cbhzhun on 2016/11/19.
 */
public class MyMainEntry extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        String viewerFxml = "../MyWorld.fxml";
        AnchorPane page = fxmlLoader.load(this.getClass().getResource(viewerFxml).openStream());
        // Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(page);
        primaryStage.setScene(scene);
        MainController viewer = (MainController) fxmlLoader.getController();
        viewer.Initialise();
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
