package GUI;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import productmanager.Product;
import productmanager.ProductManager;

import java.util.ArrayList;

public class App extends Application {

    public static ProductManager productManager = new ProductManager("resources/productsForApp.json");
    public static Point2D dim = new Point2D(1280,720);
    public static ProductGUIManager prodGUIManager;

    @Override
    public void start(Stage stage) throws Exception {
        Pane mainPane = new Pane();
        Pane prodGUIPane = new Pane();
        prodGUIManager = new ProductGUIManager(prodGUIPane);

        addProductButtons(mainPane);
        addFuncButtons(mainPane);

        mainPane.getChildren().add(prodGUIPane);
        Scene scene = new Scene(mainPane, dim.getX(),dim.getY());

        stage.setScene(scene);
        stage.setTitle("PIM-1 GUI");
        stage.show();
        stage.requestFocus();
        stage.setOnCloseRequest(e -> stop());
    }

    private void addFuncButtons(Pane node) {
        HBox hbox = new HBox();

        hbox.getChildren().add(new CreateNewProductButton().getButton());
        //Add all the FuncButtons here


        node.getChildren().add(hbox);
    }

    private void addProductButtons(Pane node) {
        VBox vbox = new VBox();
        ArrayList<Product> products = productManager.getAllProducts();

        for(Product p : products){
            ProductButton pB = new ProductButton(p, prodGUIManager);
            vbox.getChildren().add(pB.getButton());
        }

        node.getChildren().add(vbox);
    }

    public synchronized void stop(){
        System.exit(69);
    }

    public static void main(String[] args) {
        launch();
    }
}
