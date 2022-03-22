package GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

public class ProductGUIManager {
    private ProductGUI currentGUI;
    private Pane hostPane;
    
    public ProductGUIManager(Pane prodGUIPane){
        hostPane = prodGUIPane;
    }
    
    public void onGUIChange(ProductGUI pGUI){
        if (currentGUI != null && currentGUI.isInEditMode()){
            //https://stackoverflow.com/questions/8309981/how-to-create-and-show-common-dialog-error-warning-confirmation-in-javafx-2
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You have unsaved changes!\nDo you want to save your changes?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.initModality(Modality.WINDOW_MODAL);
            alert.initOwner(hostPane.getScene().getWindow());
            Optional<ButtonType> result = alert.showAndWait();
    
            if (result.get() == ButtonType.YES) {
                currentGUI.saveChanges();
            } else if (result.get() == ButtonType.NO) {
                currentGUI.cancelEditMode();
            } else {
                return;
            }
        }
        
        hostPane.getChildren().clear();
        hostPane.getChildren().add(pGUI.getGUI());
        currentGUI = pGUI;
    }
}
