package GUI;

import javax.swing.*;

public class LoadFileButton extends FuncButton {
    private FileChooser fileChooser;
    public LoadFileButton() {
        super("Load");
        fileChooser = new FileChooser();
    }

    @Override
    public void onClicked() {
    fileChooser.open();
    }
}
