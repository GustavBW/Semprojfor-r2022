package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import productmanager.ProductManager;

public class App extends Application {

    public static ProductManager productManager = new ProductManager("resources/productsForApp.json");

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
