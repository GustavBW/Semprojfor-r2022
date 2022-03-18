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
    private final List<Text> textBoxes;
    private final List<HBox> subContainers;
    private final HashMap<Text, ProductAttribute> textPattrMap;
    private final HashMap<ProductAttribute, Text> pattrTextMap;
    private final VBox container;

    public ProductGUI(Product product){
        this.product = product;
        this.textPattrMap = new HashMap<>();
        this.pattrTextMap = new HashMap<>();
        this.subContainers = new ArrayList<>();
        this.container = new VBox();
        this.textBoxes = generateGUI();
    }

    private ArrayList<Text> generateGUI(){
        if(!container.getChildren().isEmpty()){
            container.getChildren().clear();
        }

        //Title text
        container.getChildren().add(new Text(product.get(ProductAttribute.NAME)));

        ArrayList<Text> output = new ArrayList<>();
        //This makes the IN_STOCK display correctly for GUI purposes. It shouldn't change anything anywhere else. But for good measure this change is reverted later
        product.set(ProductAttribute.IN_STOCK, product.get(ProductAttribute.IN_STOCK).replaceAll(",","\n"));

        //Generates a table of Hboxes in VBoxes of all product attributes
        for(ProductAttribute pattr : ProductAttribute.values()){
            HBox subContainer = new HBox();

            Text attrText = new Text(product.get(pattr));
            Text attrNameText = new Text(pattr.alias);

            attrText.setDisable(true);
            attrNameText.setDisable(true);

            subContainer.getChildren().addAll(List.of(attrNameText,attrText));

            subContainers.add(subContainer);
            output.add(attrText);

            textPattrMap.put(attrText,pattr);
            pattrTextMap.put(pattr,attrText);
        }

        product.set(ProductAttribute.IN_STOCK, product.get(ProductAttribute.IN_STOCK).replaceAll("\n",","));
        container.getChildren().addAll(subContainers);
        return output;
    }

    public Node getContainerNode(){
        return container;
    }

    public boolean startEditMode(){
        isInEditMode = true;


        return isInEditMode;
    }
    public boolean stopEditMode(){
        isInEditMode = false;


        return isInEditMode;
    }

    private Product getModProduct(){
        Product output = new Product();

        for(ProductAttribute pattr : ProductAttribute.values()){
            output.set(pattr,pattrTextMap.get(pattr).getText());
        }

        return output;
    }
    public Product getProduct(){
        return product;
    }

}
