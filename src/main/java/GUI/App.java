package GUI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
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

    //public static ProductManager productManager = new ProductManager("resources/productsForApp.json");
    private static Scene mainScene;
    private static Stage mainStage;
    public static Point2D dim = new Point2D(1280,720);
    public static ProductGUIManager prodGUIManager;
    private static final JsonCache cacheInstance = JsonCache.getInstance();

    @Override
    public void start(Stage stage) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIapplication.fxml"));

        mainScene = new Scene(loader.load(), 1280, 720);

        mainStage = stage;
        mainStage.setTitle("PIM-1 GUI");
        mainStage.setResizable(false);
        mainStage.setScene(mainScene);
        mainStage.show();

    }

    private void addProductButtons() {
        List<Product> products = cacheInstance.get();

        for (Product p : products) {
            ProductButton pB = new ProductButton(p, prodGUIManager);
            btnVbox.getChildren().add(pB.getButton());
        }
    }

    private void addFuncButtons() {
        btnHbox.getChildren().addAll(List.of(
                new ReloadGUIButton(this).getButton(),
                new CreateNewProductButton().getButton(),
                new LoadFileButton().getButton()
        ));
        //Add all the FuncButtons here
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prodGUIManager = new ProductGUIManager(productPane);
        addProductButtons();
        addFuncButtons();
        cacheInstance.setDestroyOnExit(true);
    }

    public void reloadProductButtons(){
        btnVbox.getChildren().clear();
        addProductButtons();
    }
    public static void loadFileFrom(String path){
        try {
            cacheInstance.add(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static JsonCache getCache(){
        return cacheInstance;
    }

    public synchronized void stop(){
        System.exit(69);
    }

    public static void main(String[] args) {
        launch();
    }

    public static Scene getMainScene(){
        return mainScene == null ? mainStage.getScene() : mainScene;
    }

}
