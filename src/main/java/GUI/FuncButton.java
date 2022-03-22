package GUI;

import javafx.scene.control.Button;

public class FuncButton {

    protected Button button;
    protected String text;

    public FuncButton(String text){
        this.text = text;
        this.button = new Button(text);

        button.setOnMouseClicked(e -> onClicked());
    }

    public void onClicked(){

    }

    public Button getButton(){
        return button;
    }

}
