package GUI;

import javafx.scene.control.Button;

/*
It seems to me that all the functionality of this class is already found is the pGUI class.
Should these pB's just call the same methods as found in the pGUI class?
*/

public class ProductButton {
    private Button editButton;
    private Button saveButton;
    private Button cancelButton;

    public void EditButton (Button editButton) {
        this.editButton = editButton;
    }
    public void SaveButton (Button saveButton) {
        this.saveButton = saveButton;
    }
    public void CancelButton (Button cancelButton) {
        this.cancelButton = cancelButton;
    }
}
