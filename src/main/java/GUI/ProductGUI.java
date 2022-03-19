package GUI;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import productmanager.Product;
import productmanager.ProductAttribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductGUI {

    private Product product;
    private boolean isInEditMode = false;
    private final List<Text> editables;
    private final List<HBox> subContainers;
    private final HashMap<Text, ProductAttribute> textPattrMap;
    private final HashMap<ProductAttribute, Text> pattrTextMap;
    private final VBox container;
    private final Button editButton;
    private final Button saveButton;
    private final Button cancelButton;

    public ProductGUI(Product product){
        this.product = product;
        this.textPattrMap = new HashMap<>();
        this.pattrTextMap = new HashMap<>();
        this.subContainers = new ArrayList<>();
        this.container = new VBox();
        this.editables = generateGUI();
        this.editButton = new Button("Edit");
        this.saveButton = new Button("Save");
        this.cancelButton = new Button("Cancel");

        editButton.setOnMouseClicked(e -> startEditMode());
        saveButton.setDisable(true);
        saveButton.setVisible(false);
        saveButton.setOnMouseClicked(e -> saveChanges());
        cancelButton.setVisible(false);
        cancelButton.setDisable(true);
        cancelButton.setOnMouseClicked(e -> cancelEditMode());
    }

    private ArrayList<Text> generateGUI(){
        //Resetting the container
        container.getChildren().clear();

        //Title text
        Text titleText = new Text(product.get(ProductAttribute.NAME));
        titleText.setDisable(true);
        HBox topBox = new HBox(titleText,editButton, saveButton, cancelButton);
        container.getChildren().add(topBox);

        ArrayList<Text> output = new ArrayList<>();
        //This makes the IN_STOCK display correctly for GUI purposes. It shouldn't change anything anywhere else. But for good measure this change is reverted later
        product.set(ProductAttribute.IN_STOCK, product.get(ProductAttribute.IN_STOCK).replaceAll(",","\n"));

        //Generates a table of Hboxes in VBoxes of all product attributes, however, ignore the first since that is the UUID which must not be changed
        for(ProductAttribute pattr : ProductAttribute.values()){

                HBox subContainer = new HBox();

                Text attrText = new Text(product.get(pattr));
                Text attrNameText = new Text(pattr.alias);

                attrText.setDisable(true);
                attrNameText.setDisable(true);

                subContainer.getChildren().addAll(List.of(attrNameText, attrText));

                subContainers.add(subContainer);


                textPattrMap.put(attrText, pattr);
                pattrTextMap.put(pattr, attrText);

            if(pattr != ProductAttribute.UUID) {
                output.add(attrText);
            }
        }

        product.set(ProductAttribute.IN_STOCK, product.get(ProductAttribute.IN_STOCK).replaceAll("\n",","));
        container.getChildren().addAll(subContainers);
        return output;
    }

    public Node getContainerNode(){
        return container;
    }

    public boolean startEditMode(){
        System.out.println("Editing Product " + product.get(ProductAttribute.UUID));
        isInEditMode = true;

        unlockAll();

        saveButton.setDisable(false);
        saveButton.setVisible(true);
        cancelButton.setDisable(false);
        cancelButton.setVisible(true);

        return isInEditMode;
    }

    private void unlockAll(){
        for(Text t : editables){
            t.setDisable(false);
        }
    }
    private void lockAll(){
        for(Text t : editables){
            t.setDisable(true);
        }
    }

    public boolean saveChanges(){
        System.out.println("Saved Changes to Product " + product.get(ProductAttribute.UUID));
        isInEditMode = false;

        lockAll();

        saveButton.setDisable(true);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        cancelButton.setDisable(true);

        App.productManager.update(product.get(ProductAttribute.UUID),getModProduct());
        product = getModProduct();
        generateGUI();

        return isInEditMode;
    }
    public boolean cancelEditMode(){
        System.out.println("Cancelled Editing Product " + product.get(ProductAttribute.UUID));
        isInEditMode = false;

        lockAll();
        generateGUI();

        return !isInEditMode;
    }

    private Product getModProduct(){
        Product output = new Product();

        for(ProductAttribute pattr : ProductAttribute.values()){
            output.set(pattr,pattrTextMap.get(pattr).getText());
        }
        output.setLocations(new ArrayList<>(List.of(pattrTextMap.get(ProductAttribute.IN_STOCK).getText().split(","))));

        return output;
    }
    public Product getProduct(){
        return product;
    }

}
