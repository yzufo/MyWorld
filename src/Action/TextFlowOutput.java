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
        return new Text("\n\nTurned left to " + myWold.getCurrentLocation().getName() + myWold.getCurrentFace());
    }
    public Text outputTurnRight(){
        return new Text("\n\nTurned right to " + myWold.getCurrentLocation().getName() + myWold.getCurrentFace());
    }
    public Text outputForwardAvaiable(){
        return new Text("\n\nMove forward is avaiable!");
    }
    public Text moveToNewLocation(){
        return new Text("\n\n=====================================\n" +
                "Moved to "+ myWold.getCurrentLocation().getName() +
                "\n Facing to " + myWold.getCurrentLocation().getName() + myWold.getCurrentFace());
    }
    public Text outputSelectItem(){
        return new Text("\n\nSelected item" + myWold.getCurrentItemId() + ". Ready to put");
    }
    public Text outputSelectPickItem(){
        return new Text("\n\nSelected item" + myWold.getCurrentPickItemId() + ". Ready to pick");
    }
    public Text outputPutItem(){
        return new Text("\n\nItem" + myWold.getCurrentItemId() + "has been put in the " + myWold.getCurrentLocation().getName());
    }
    public Text outputPickItem(){
        return new Text("\n\nItem" + myWold.getCurrentPickItemId() + "has been picked form the "+ myWold.getCurrentLocation().getName());
    }
}
