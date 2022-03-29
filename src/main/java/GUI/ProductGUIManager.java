package GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;

import java.util.Optional;

public class ProductGUIManager {
    private ProductGUI currentGUI; //To access what product is currently shown
    private Pane hostPane; //The pane that we attach the ProductGUI
    
    public ProductGUIManager(Pane prodGUIPane){
        hostPane = prodGUIPane;
    }
    
    public void onGUIChange(ProductGUI pGUI){
        //If the new product is the same as the one being shown we don't need to change anything
        if (pGUI == currentGUI) {
            return;
        }
        
        //If there is currently a product that is being edited we need to warn the user
        if (currentGUI != null && currentGUI.isInEditMode()) {
            //https://stackoverflow.com/questions/8309981/how-to-create-and-show-common-dialog-error-warning-confirmation-in-javafx-2
            //Here we create a popup dialog designed for alerting the user. We add a custom array of buttons to give the user more options
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You have unsaved changes!\nDo you want to save your changes?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            //I am not certain what this does
            alert.initModality(Modality.WINDOW_MODAL);
            alert.initOwner(hostPane.getScene().getWindow());
            //Shows the popup dialog and pauses the program until an answer is given
            Optional<ButtonType> result = alert.showAndWait();
    
            //Check the result from the user
            if (result.get() == ButtonType.YES) { //If the user says yes we save their changes, cancelling edit mode in the process
                currentGUI.saveChanges();
            } else if (result.get() == ButtonType.NO) { //If the user says no we cancel edit mode, deleting their changes in the process
                currentGUI.cancelEditMode();
            } else { //Cancel the action of changing product if the user either pressed cancel or closed the dialog box without giving an answer
                return;
            }
        }
        
        hostPane.getChildren().clear(); //Remove previous product
        hostPane.getChildren().add(pGUI.getGUI()); //Add the new product
        currentGUI = pGUI; //Save the new gui for later reference
    }
    
    public void clearGUI(){
        hostPane.getChildren().clear(); //Remove current product
    }
}
