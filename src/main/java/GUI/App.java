package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.stage.Stage;
import productmanager.ProductManager;

import java.io.IOException;
import java.net.URL;

public class App extends Application {

    public static ProductManager productManager = new ProductManager("resources/productsForApp.json");
    public static Point2D dim = new Point2D(1280,720);
    private ProductGUIManager prodGUIManager;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIapplication.fxml"));
        Scene scene = new Scene(loader.load(), 1280, 720);
        stage.setTitle("PIM-1 GUI");
        stage.setScene(scene);
        stage.show();


            //Scene scene = new Scene(content);
            //stage.setScene(scene);
            //stage.show();


        /*Pane mainPane = new Pane();
        Pane prodGUIPane = new Pane();

        addProductButtons(mainPane);
        addFuncButtons(mainPane);

        //prodGUIManager = new ProductGUIManager(prodGUIPane);
        mainPane.getChildren().add(prodGUIPane);
        Scene scene = new Scene(mainPane, dim.getX(),dim.getY());

        stage.setScene(scene);
        stage.setTitle("PIM-1 GUI");
        stage.show();
        stage.requestFocus();
    }

    private void addFuncButtons(Pane node) {
        HBox hbox = new HBox();


        //Add all the FuncButtons here


        node.getChildren().add(hbox);
    }

    private void addProductButtons(Pane node) {
        VBox vbox = new VBox();
        ArrayList<Product> products = productManager.getAllProducts();

        for(Product p : products){
            //ProductButton pB = new ProductButton(p, prodGUIManager);
            //vbox.getChildren().add(
            //        pB.getButton()
            //);
        }

        node.getChildren().add(vbox);
         */
    }

    public static void main(String[] args) {
        launch();
    }
}
