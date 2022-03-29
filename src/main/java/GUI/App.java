package GUI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import productmanager.Product;
import productmanager.ProductManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class App extends Application implements Initializable
{
    @FXML
    private HBox btnHbox;
    @FXML
    private VBox btnVbox;
    @FXML
    private Pane productPane;

    public static ProductManager productManager = new ProductManager("resources/productsForApp.json");
    public static Point2D dim = new Point2D(1280,720);
    public static ProductGUIManager prodGUIManager;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIapplication.fxml"));

        Scene scene = new Scene(loader.load(), 1280, 720);
        stage.setTitle("PIM-1 GUI");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    private void addProductButtons() {
        ArrayList<Product> products = productManager.getAllProducts();

        for (Product p : products) {
            ProductButton pB = new ProductButton(p, prodGUIManager);
            btnVbox.getChildren().add(pB.getButton());
        }
    }

    private void addFuncButtons() {
        btnHbox.getChildren().addAll(List.of(
                new ReloadGUIButton(this).getButton(),
                new CreateNewProductButton().getButton()

        ));
        //Add all the FuncButtons here
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prodGUIManager = new ProductGUIManager(productPane);
        addProductButtons();
        addFuncButtons();
    }

    public void reloadProductButtons(){
        btnVbox.getChildren().clear();
        addProductButtons();
    }

    public synchronized void stop(){
        System.exit(69);
    }

    public static void main(String[] args) {
        launch();
    }


}
