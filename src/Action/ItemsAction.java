package Action;

import Model.Item;
import MyWorldController.MainController;

/**
 * Created by cbhzhun on 2016/11/20.
 */
public class ItemsAction extends MainController {
    public ItemsAction(){
        currentItemId = currentPickItemId = -1;
    }
    public void PutItemAction(){
        int deletid = -1;
        for(int i=0;i<myItems.size();i++)
            if(myItems.get(i).getItemId() == currentItemId)
            {
                deletid = i;
                break;
            }
        if(deletid != -1){
            currentLocation.addItem(myItems.get(deletid));
            myItems.remove(deletid);
        }
    }
    public void PickItemAction(){
        int deletid = -1;
        for(int i=0;i<currentLocation.getItem().size();i++)
            if(currentLocation.getItem().get(i).getItemId() == currentPickItemId){
                deletid = i;
            }
        if(deletid != -1){
            Item item  = currentLocation.getItem().get(deletid);
            myItems.add(item.getItemId()-1,item);
            currentLocation.deleteItem(deletid);
        }
    }
}
