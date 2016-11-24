package MainEntry;

import Action.XMLReader;
import MyWorldController.FirstController;
import MyWorldController.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.util.Properties;

/**
 * Created by cbhzhun on 2016/11/19.
 * This is Application's main method
 */
public class MyMainEntry extends Application {
    public static Stage stage;
    private String mainUrl;
    private Properties properties = new Properties();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainUrl = System.getProperty("user.dir") + "/"; // Get absolute path
        properties.load(this.getClass().getResource("/MyWorld.properties").openStream());
        primaryStage.setResizable(false);
        stage = primaryStage;
        stage.setTitle("Import File");
        stage.initStyle(StageStyle.UNDECORATED);
        loadFirstWindow();
        stage.show();
    }

    /**
     * Init and show first pane which let user to chose import from local, remote or quit.
     */
    public void loadFirstWindow() {
        try {
            FirstController firstController = (FirstController) replaceSceneContent("/MyWorldInit.fxml");
            firstController.setApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Pass xmlfile to mainPane's controller and show mainPane
     *
     * @param file xmlfile
     */
    public void loadMainWindow(File file) {
        try {
            MainController mainController = (MainController) replaceSceneContent("/MyWorld.fxml");
            mainController.Initialise();
            try {
                mainController.Import(file); // read XMLFile and save data into cache
            } catch (Exception e) {
                e.printStackTrace();
                loadFirstWindow();
            }
            mainController.setApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * replaceSecenContent (from firstPane to mainPane), load new fxml file
     *
     * @param fxml path of fxml
     * @return
     * @throws Exception
     */
    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Pane page = loader.load(this.getClass().getResource(fxml).openStream());
        Scene scene = new Scene(page);
        String cssFile = properties.get("WordName").toString();
        String isRemote = properties.get("isRemote").toString();
        if (isRemote.equals("1")) {     // Use different ways to get css file depends on it's location(local or remote)
            File file = new XMLReader().getFileFromUrl(properties.get("Url").toString(), "tmp.css");
            scene.getStylesheets().add("file:///" + mainUrl + file.getPath());
        } else {
            scene.getStylesheets().add("file:///" + mainUrl + "Source/" + cssFile + "/" + cssFile + ".css");
        }
        stage.setScene(scene);
        return (Initializable) loader.getController();
    }
}
