package GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import productmanager.ProductManager;

import java.util.ArrayList;
import java.util.List;

public class ProductGUIManager {
    private ProductGUI currentGUI;
    private Pane hostPane;
    
    public ProductGUIManager(Pane prodGUIPane){
        hostPane = prodGUIPane;
    }
    
    public void onGUIChange(ProductGUI pGUI){
        if (currentGUI.isInEditMode()){
            //https://stackoverflow.com/questions/8309981/how-to-create-and-show-common-dialog-error-warning-confirmation-in-javafx-2
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You have unsaved changes!\nDo you want to save your changes?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
    
            if (alert.getResult() == ButtonType.YES) {
                currentGUI.saveChanges();
            } else if (alert.getResult() == ButtonType.NO) {
                currentGUI.cancelEditMode();
            } else {
                return;
            }
        }
        
        hostPane.getChildren().clear();
        hostPane.getChildren().add(pGUI.getGUI());
    }
}
