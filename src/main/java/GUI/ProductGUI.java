package GUI;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import productmanager.Product;
import productmanager.ProductAttribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductGUI {

    private final Product product;
    private boolean isInEditMode = false;
    private final ArrayList<Text> textBoxes;
    private final List<HBox> subContainers;
    private final HashMap<Text, ProductAttribute> textPattrMap;
    private final VBox container;

    public ProductGUI(Product product){
        this.product = product;
        this.textPattrMap = new HashMap<>();
        this.subContainers = new ArrayList<>();
        textBoxes = generateGUI();

        container = new VBox();
        container.getChildren().addAll(textBoxes);
    }

    private ArrayList<Text> generateGUI(){
        ArrayList<Text> output = new ArrayList<>();

        for(ProductAttribute pattr : ProductAttribute.values()){
            HBox newHBox = new HBox();
            Text newText = new Text(product.get(pattr));
            newText.setDisable(true);
            output.add(newText);

            textPattrMap.put(newText,pattr);
        }

        return output;
    }

    public Node getContainer(){
        return container;
    }

    public boolean startEditMode(){



        return isInEditMode;
    }

}
