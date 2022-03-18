package GUI;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import productmanager.Product;
import productmanager.ProductAttribute;

import java.util.ArrayList;

public class ProductGUI {

    private final Product product;
    private final ArrayList<Text> textBoxes;
    private final VBox container;

    public ProductGUI(Product product){
        this.product = product;
        textBoxes = generateGUI();

        container = new VBox();
        container.getChildren().addAll(textBoxes);
    }

    private ArrayList<Text> generateGUI(){
        ArrayList<Text> output = new ArrayList<>();

        for(ProductAttribute pattr : ProductAttribute.values()){
            Text newText = new Text(product.get(pattr));
            newText.setDisable(true);
            output.add(newText);
        }

        return output;
    }

    public Node getContainer(){
        return container;
    }



}
