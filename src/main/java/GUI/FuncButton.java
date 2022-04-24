package GUI;

import javafx.scene.control.Button;

public abstract class FuncButton{

    protected Button button;
    protected String text;

    public FuncButton(String text){
        this.text = text;
        this.button = new Button(text);

        button.setOnMouseClicked(e -> onClicked());
        button.setPrefWidth(App.dim.getX() * 0.15);
        button.setPrefHeight(App.dim.getY() * 0.1);
    }

    public abstract void onClicked();

    public Button getButton(){
        return button;
    }

}
