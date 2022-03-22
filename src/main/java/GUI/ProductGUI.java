package GUI;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import productmanager.Product;
import productmanager.ProductAttribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductGUI {

    private Product product;    //The Product displayed through this GUI
    private boolean isInEditMode = false; //Boolean tracking if the GUI is in edit mode
    private boolean creatingNotEditing = false; //Boolean tracking whether to update an existing product or make a make a new one for Saving (.saveChanges())
    private final List<TextField> editables; //Attributes of the Product that the user can edit.
    private final List<HBox> subContainers; //Pairs of an attribute name (Text obj) and the attribute value (Text obj)
    private final HashMap<TextField, ProductAttribute> textPattrMap; //A way to get what ProductAttribute a text field displayes
    private final HashMap<ProductAttribute, TextField> pattrTextMap; //A way to get a text field using a ProductAttribute
    private final VBox container;   //The entire page is one large container. Might be changed to something else later, but this is the entire UI.
    private final Button editButton;    //Button to enter edit mode
    private final Button saveButton;    //Button to save changes and exit edit mode
    private final Button cancelButton;  //Button to exit edit mode.

    public ProductGUI(Product product){
        this.product = product;
        this.textPattrMap = new HashMap<>();
        this.pattrTextMap = new HashMap<>();
        this.subContainers = new ArrayList<>();
        this.container = new VBox();
        this.editables = new ArrayList<>();
        this.editButton = new Button("Edit");
        this.saveButton = new Button("Save");
        this.cancelButton = new Button("Cancel");

        generateGUI();

        editButton.setOnMouseClicked(e -> startEditMode());
        saveButton.setDisable(true);
        saveButton.setVisible(false);
        saveButton.setOnMouseClicked(e -> saveChanges());
        cancelButton.setVisible(false);
        cancelButton.setDisable(true);
        cancelButton.setOnMouseClicked(e -> cancelEditMode());

    }

    public ProductGUI(){
        this(getBlankProduct());
        creatingNotEditing = true;
        startEditMode();
    }

    private static Product getBlankProduct() {
        Product blankProduct = new Product();

        blankProduct.set(ProductAttribute.UUID, "e.g. ea6954c2-64ec-4a65-b1a5-d614907e8b65");
        blankProduct.set(ProductAttribute.ID, "e.g. 25");
        blankProduct.set(ProductAttribute.AVERAGE_USER_REVIEW, "e.g. 4.523");
        blankProduct.set(ProductAttribute.IN_STOCK, "e.g. København,Hørsholm,Vejle...");
        blankProduct.set(ProductAttribute.EAN, "e.g. 1122334455667");
        blankProduct.set(ProductAttribute.PRICE, "e.g. 1875.95");
        blankProduct.set(ProductAttribute.PUBLISHED_DATE, "dd/mm/yyyy");
        blankProduct.set(ProductAttribute.EXPIRATION_DATE, "dd/mm/yyyy");
        blankProduct.set(ProductAttribute.CATEGORY, "e.g. 'Laptops'");
        blankProduct.set(ProductAttribute.NAME, "");
        blankProduct.set(ProductAttribute.DESCRIPTION, "");
        blankProduct.set(ProductAttribute.WEIGHT, "in KG");
        blankProduct.set(ProductAttribute.SIZE, "e.g. length by width by height");
        blankProduct.set(ProductAttribute.CLOCKSPEED, "e.g. 4.5GHz");

        return blankProduct;
    }

    private ArrayList<TextField> generateGUI(){
        //Resetting the container
        container.getChildren().clear();
        subContainers.clear();

        //Title text
        Text titleText = new Text(product.get(ProductAttribute.NAME));
        titleText.setDisable(true);
        HBox topBox = new HBox(titleText,editButton, saveButton, cancelButton);
        container.getChildren().add(topBox);

        ArrayList<TextField> output = new ArrayList<>();
        //This makes the IN_STOCK display correctly for GUI purposes. It shouldn't change anything anywhere else. But for good measure this change is reverted later
        //product.set(ProductAttribute.IN_STOCK, product.get(ProductAttribute.IN_STOCK).replaceAll(",","\n"));

        //Generates a table of Hboxes in VBoxes of all product attributes, however, ignore the first since that is the UUID which must not be changed
        for(ProductAttribute pattr : ProductAttribute.values()){

            HBox subContainer = new HBox();
            subContainer.setPrefWidth(App.dim.getX() * 0.85);
            subContainer.setPrefWidth(App.dim.getY() * 0.95);

            TextField attrText = new TextField(product.get(pattr));
            attrText.setPrefWidth(subContainer.getPrefWidth() * 0.8);
            attrText.setPrefHeight(subContainer.getPrefHeight() * (1.00 / ProductAttribute.values().length));

            TextField attrNameText = new TextField(pattr.alias);
            attrNameText.setPrefWidth(subContainer.getPrefWidth() * 0.2);

            //The user should only be able to make changes in the fields when edit mode is started. This way it's easier to make sure nothing goes wrong.
            attrText.setEditable(false);
            attrNameText.setEditable(false);

            subContainer.getChildren().addAll(List.of(attrNameText, attrText));

            subContainers.add(subContainer);

            textPattrMap.put(attrText, pattr);
            pattrTextMap.put(pattr, attrText);

            if(pattr != ProductAttribute.UUID) {
                //The UUID shouldn't ever change. Thus it now can't.
                output.add(attrText);
            }
        }

        product.set(ProductAttribute.IN_STOCK, product.get(ProductAttribute.IN_STOCK).replaceAll("\n",","));
        container.getChildren().addAll(subContainers);
        editables.addAll(output);
        return output;
    }

    public Node getGUI(){
        return container;
    }

    public boolean startEditMode(){
        System.out.println("Editing Product " + product.get(ProductAttribute.UUID));
        isInEditMode = true;

        unlockAll();

        editButton.setDisable(true);
        editButton.setVisible(false);
        saveButton.setDisable(false);
        saveButton.setVisible(true);
        cancelButton.setDisable(false);
        cancelButton.setVisible(true);

        return isInEditMode;
    }

    private void unlockAll(){
        //This unlocks all the text fields that are editable. Meaning the user can change their values now.
        for(TextField t : editables){
            t.setEditable(true);
        }
    }
    private void lockAll(){
        //This locks all the text fields that are editable. Meaning the user cannot make changes in their values.
        for(TextField t : editables){
            t.setEditable(false);
        }
    }

    public boolean saveChanges(){
        System.out.println("Saved Changes to Product " + product.get(ProductAttribute.UUID));
        isInEditMode = false;

        //Step 0: Lock all fields so no changes can be made. Also disable the Save and Cancel Buttons.
        lockAll();

        editButton.setDisable(false);
        editButton.setVisible(true);
        saveButton.setDisable(true);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        cancelButton.setDisable(true);

        //Step 1: Read all information in the text fields and make a new product from this.
        Product modProd = getModProduct();
        //Step 2: Update the product to equal this new one. (Their UUID's will always be the same, but this is just to make sure the right product is overridden)
        if(creatingNotEditing){
            App.productManager.create(modProd);
            creatingNotEditing = false;
        }else {
            App.productManager.update(product.get(ProductAttribute.UUID), modProd);
        }
        //Step 3: Change what product this GUI is using.
        product = modProd;
        //Step 4: Remake the GUI using the new changed product.
        generateGUI();

        return isInEditMode;
    }
    public boolean cancelEditMode(){
        System.out.println("Cancelled Editing Product " + product.get(ProductAttribute.UUID));
        isInEditMode = false;

        lockAll();

        editButton.setDisable(false);
        editButton.setVisible(true);
        saveButton.setDisable(true);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        cancelButton.setDisable(true);

        generateGUI();

        return !isInEditMode;
    }

    private Product getModProduct(){
        Product output = new Product();

        //Using the HashMap to pull out all the information stored in the text fields.
        for(ProductAttribute pattr : ProductAttribute.values()){
            output.set(pattr,pattrTextMap.get(pattr).getText());
        }
        //Then being a little creative when filling in the .setLocation string list.
        output.setLocations(new ArrayList<>(List.of(pattrTextMap.get(ProductAttribute.IN_STOCK).getText().split(","))));

        return output;
    }
    public Product getProduct(){
        return product;
    }
    public boolean isInEditMode(){return isInEditMode;}

}
