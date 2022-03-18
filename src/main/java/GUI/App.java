package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        Scene scene = new Scene(pane, 1280,720);

        stage.setScene(scene);
        stage.setTitle("PIM-1 GUI");
        stage.show();
        stage.requestFocus();
    }

    public static void main(String[] args) {
        launch();
    }
}
