package Action;

import MyWorldController.MainController;
import javafx.scene.text.Text;
import sun.applet.Main;

/**
 * Created by cbhzhun on 2016/11/21.
 */
public class TextFlowOutput extends MainController {
    public void TextFlowOutputController(){

    }
    public Text outputTurnLeft(){
        return new Text("\n\nTurned left to " + currentLocation.getName() + currentFace);
    }
    public Text outputTurnRight(){
        return new Text("\n\nTurned right to " + currentLocation.getName() + currentFace);
    }
    public Text outputForwardAvaiable(){
        return new Text("\n\nMove forward is avaiable!");
    }
    public Text moveToNewLocation(){
        return new Text("\n\n=====================================\n" +
                "Moved to "+ currentLocation.getName() +
                "\n Facing to " + currentLocation.getName() + currentFace);
    }
    public Text outputSelectItem(){
        return new Text("\n\nSelected item" + currentItemId + ". Ready to put");
    }
    public Text outputSelectPickItem(){
        return new Text("\n\nSelected item" + currentPickItemId + ". Ready to pick");
    }
    public Text outputPutItem(){
        return new Text("\n\nItem" + currentItemId + "has been put in the " + currentLocation.getName());
    }
    public Text outputPickItem(){
        return new Text("\n\nItem" + currentPickItemId + "has been picked form the "+ currentLocation.getName());
    }
}
