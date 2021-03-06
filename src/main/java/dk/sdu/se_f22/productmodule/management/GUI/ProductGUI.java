package dk.sdu.se_f22.productmodule.management.GUI;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import dk.sdu.se_f22.productmodule.management.domain_persistance.BaseProduct;
import dk.sdu.se_f22.productmodule.management.domain_persistance.ProductAttribute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ProductGUI {

    private BaseProduct baseProduct;    //The BaseProduct displayed through this GUI
    private boolean isInEditMode = false; //Boolean tracking if the GUI is in edit mode
    private boolean creatingNotEditing = false; //Boolean tracking whether to update an existing baseProduct or make a make a new one for Saving (.saveChanges())
    private final List<TextArea> editables; //Attributes of the BaseProduct that the user can edit.
    private final List<HBox> subContainers; //Pairs of an attribute name (Text obj) and the attribute value (Text obj)
    private final HashMap<TextArea, ProductAttribute> textPattrMap; //A way to get what ProductAttribute a text field displayes
    private final HashMap<ProductAttribute, TextArea> pattrTextMap; //A way to get a text field using a ProductAttribute
    private final VBox container;   //The entire page is one large container. Might be changed to something else later, but this is the entire UI.
    private final Button editButton;    //Button to enter edit mode
    private final Button saveButton;    //Button to save changes and exit edit mode
    private final Button cancelButton;  //Button to exit edit mode.
    private final Button deleteButton;
    private static final Point2D cDim = new Point2D(App.dim.getX() * 0.85, App.dim.getY() * 0.95);

    public ProductGUI(BaseProduct baseProduct){
        this.baseProduct = baseProduct;
        this.textPattrMap = new HashMap<>();
        this.pattrTextMap = new HashMap<>();
        this.subContainers = new ArrayList<>();
        this.container = new VBox();
        this.editables = new ArrayList<>();
        this.editButton = new Button("Edit");
        this.saveButton = new Button("Save");
        this.cancelButton = new Button("Cancel");
        this.deleteButton = new Button("Delete");

        generateGUI();
        setButtons();
    }

    public ProductGUI(){
        this(getBlankProduct());
        creatingNotEditing = true;
        startEditMode();
    }

    private void setButtons(){
        editButton.setOnMouseClicked(e -> startEditMode());
        editButton.setPrefWidth(cDim.getX() / 4.0);
        deleteButton.setOnMouseClicked(e -> deleteProduct());
        deleteButton.setPrefWidth(cDim.getX() / 4.0);
        saveButton.setDisable(true);
        saveButton.setPrefWidth(cDim.getX() / 4.0);
        saveButton.setOnMouseClicked(e -> saveChanges());
        saveButton.setStyle("-fx-background-color:#59c26f");
        saveButton.setFont(Font.font("40"));
        cancelButton.setDisable(true);
        cancelButton.setPrefWidth(cDim.getX() / 4.0);
        cancelButton.setOnMouseClicked(e -> cancelEditMode());
        cancelButton.setStyle("-fx-background-color:#d76868");
    }

    private static BaseProduct getBlankProduct() {
        BaseProduct blankBaseProduct = new BaseProduct();

        blankBaseProduct.set(ProductAttribute.UUID, "e.g. ea6954c2-64ec-4a65-b1a5-d614907e8b65");
        blankBaseProduct.set(ProductAttribute.AVERAGE_USER_REVIEW, "e.g. 4.523");
        blankBaseProduct.set(ProductAttribute.IN_STOCK, "e.g. K??benhavn,H??rsholm,Vejle...");
        blankBaseProduct.set(ProductAttribute.EAN, "e.g. 1122334455667");
        blankBaseProduct.set(ProductAttribute.PRICE, "e.g. 1875.95");
        blankBaseProduct.set(ProductAttribute.PUBLISHED_DATE, "yyyy-mm-dd");
        blankBaseProduct.set(ProductAttribute.EXPIRATION_DATE, "yyyy-mm-dd");
        blankBaseProduct.set(ProductAttribute.CATEGORY, "e.g. 'Laptops'");
        blankBaseProduct.set(ProductAttribute.NAME, "");
        blankBaseProduct.set(ProductAttribute.DESCRIPTION, "");
        blankBaseProduct.set(ProductAttribute.WEIGHT, "in KG");
        blankBaseProduct.set(ProductAttribute.SIZE, "e.g. length by width by height");
        blankBaseProduct.set(ProductAttribute.CLOCKSPEED, "e.g. 4.5GHz");

        return blankBaseProduct;
    }

    private List<TextArea> generateGUI(){
        //Resetting the container
        container.getChildren().clear();
        subContainers.clear();

        //Title text
        TextArea titleText = new TextArea(baseProduct.get(ProductAttribute.NAME));
        titleText.setEditable(false);
        titleText.setWrapText(true);

        titleText.setFont(Font.font("Verdana",20));
        titleText.setPrefWidth(cDim.getX());
        titleText.setPrefHeight(cDim.getY() * 0.015);

        HBox topBox = new HBox(titleText);
        container.getChildren().add(topBox);
        HBox subTopBox = new HBox(editButton, saveButton, cancelButton,deleteButton);
        container.getChildren().add(subTopBox);

        List<TextArea> output = new ArrayList<>();
        //This makes the IN_STOCK display correctly for GUI purposes. It shouldn't change anything anywhere else. But for good measure this change is reverted later
        //baseProduct.set(ProductAttribute.IN_STOCK, baseProduct.get(ProductAttribute.IN_STOCK).replaceAll(",","\n"));

        //Generates a table of Hboxes in VBoxes of all baseProduct attributes, however, ignore the first since that is the UUID which must not be changed
        for(ProductAttribute pattr : ProductAttribute.values()){

            HBox subContainer = new HBox();
            subContainer.setPrefWidth(cDim.getX());
            subContainer.setPrefHeight((cDim.getY() - (topBox.getPrefHeight() + subTopBox.getPrefHeight())) * (1.00 / (ProductAttribute.values().length + 2)));
            
            String attribute;
            TextArea attrText;
            if ((attribute = baseProduct.get(pattr)) != null) {
                attrText = new TextArea(attribute);
            } else {
                attrText = new TextArea();
            }
            attrText.setPrefWidth(subContainer.getPrefWidth() * 0.8);
            attrText.setPrefHeight(subContainer.getPrefHeight());
            attrText.setWrapText(true);

            TextField attrNameText = new TextField(pattr.alias);
            attrNameText.setPrefWidth(subContainer.getPrefWidth() * 0.2);

            //The user should only be able to make changes in the fields when edit mode is started. This way it's easier to make sure nothing goes wrong.
            attrText.setEditable(false);
            attrNameText.setEditable(false);

            subContainer.getChildren().addAll(List.of(attrNameText, attrText));

            subContainers.add(subContainer);

            textPattrMap.put(attrText, pattr);
            pattrTextMap.put(pattr, attrText);


            output.add(attrText);

        }

        baseProduct.set(ProductAttribute.IN_STOCK, baseProduct.get(ProductAttribute.IN_STOCK).replaceAll("\n",","));
        container.getChildren().addAll(subContainers);
        editables.addAll(output);
        return output;
    }

    public Node getGUI(){
        ScrollPane asScrollPane = new ScrollPane(container);
        return asScrollPane;
    }

    public boolean deleteProduct(){
        //https://stackoverflow.com/questions/8309981/how-to-create-and-show-common-dialog-error-warning-confirmation-in-javafx-2
        //Here we create a popup dialog designed for alerting the user. We add a custom array of buttons to give the user more options
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete this entry? \n It will be gone forever. \n (a very long time)", ButtonType.YES, ButtonType.NO);
        //I am not certain what this does
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(App.getMainScene().getWindow());
        //Shows the popup dialog and pauses the program until an answer is given
        Optional<ButtonType> result = alert.showAndWait();

        //Check the result from the user
        if (result.get() == ButtonType.YES) {
            //App.productManager.remove(baseProduct.get(ProductAttribute.UUID));
            try {
                App.getCache().remove(baseProduct);
            } catch (IOException e) {
                e.printStackTrace();
            }
            App.prodGUIManager.clearGUI();
        } else {
            return false;
        }

        return true;
    }

    public boolean startEditMode(){
        System.out.println("Editing BaseProduct " + baseProduct.get(ProductAttribute.UUID));
        isInEditMode = true;

        unlockAll();

        editButton.setDisable(true);
        deleteButton.setDisable(true);
        saveButton.setDisable(false);
        cancelButton.setDisable(false);

        return isInEditMode;
    }

    private void unlockAll(){
        //This unlocks all the text fields that are editable. Meaning the user can change their values now.
        for(TextArea t : editables){
            t.setEditable(true);
        }
    }
    private void lockAll(){
        //This locks all the text fields that are editable. Meaning the user cannot make changes in their values.
        for(TextArea t : editables){
            t.setEditable(false);
        }
    }

    public boolean saveChanges(){
        System.out.println("Saved Changes to BaseProduct " + baseProduct.get(ProductAttribute.UUID));
        isInEditMode = false;

        //Step 0: Lock all fields so no changes can be made. Also disable the Save and Cancel Buttons.
        lockAll();

        editButton.setDisable(false);
        deleteButton.setDisable(false);
        saveButton.setDisable(true);
        cancelButton.setDisable(true);

        //Step 1: Read all information in the text fields and make a new baseProduct from this.
        BaseProduct modProd = getModProduct();
        //Step 2: Update the baseProduct to equal this new one. (Their UUID's will always be the same, but this is just to make sure the right baseProduct is overridden)
        if(creatingNotEditing){
            try {
                App.getCache().add(modProd);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //App.productManager.create(modProd);
            creatingNotEditing = false;
        }else {
            try {
                App.getCache().remove(baseProduct);
                App.getCache().add(modProd);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //App.productManager.update(baseProduct.get(ProductAttribute.UUID), modProd);
        }
        //Step 3: Change what baseProduct this GUI is using.
        baseProduct = modProd;
        //Step 4: Remake the GUI using the new changed baseProduct.
        generateGUI();

        return isInEditMode;
    }
    public boolean cancelEditMode(){
        System.out.println("Cancelled Editing BaseProduct " + baseProduct.get(ProductAttribute.UUID));
        isInEditMode = false;

        lockAll();

        editButton.setDisable(false);
        deleteButton.setDisable(false);
        saveButton.setDisable(true);
        cancelButton.setDisable(true);

        generateGUI();

        return !isInEditMode;
    }

    private BaseProduct getModProduct(){
        BaseProduct output = new BaseProduct();

        //Using the HashMap to pull out all the information stored in the text fields.
        for(ProductAttribute pattr : ProductAttribute.values()){
            output.set(pattr,pattrTextMap.get(pattr).getText());
        }
        //Then being a little creative when filling in the .setLocation string list.
        output.setLocations(new ArrayList<>(List.of(pattrTextMap.get(ProductAttribute.IN_STOCK).getText().split(","))));

        return output;
    }
    public BaseProduct getProduct(){
        return baseProduct;
    }
    public boolean isInEditMode(){return isInEditMode;}

}
