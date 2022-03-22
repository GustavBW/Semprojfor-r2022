package GUI;

import javafx.scene.control.Button;
import productmanager.Product;
import productmanager.ProductAttribute;

/*
It seems to me that all the functionality of this class is already found is the pGUI class.
Should these pB's just call the same methods as found in the pGUI class?
*/

public class ProductButton extends FuncButton {

    private final ProductGUIManager prodGUIManager;
    private final Product product;
    private final ProductGUI prodGUI;
    private final Button button;

    public ProductButton(Product p, ProductGUIManager pGUIM) {
        super(p.get(ProductAttribute.NAME));
        this.prodGUIManager = pGUIM;
        this.product = p;

        prodGUI = new ProductGUI(product);
        button = new Button(product.toString());
        //Make the button
        //Make ProductGUI
        button.setOnMouseClicked(e -> onClicked());
    }

    @Override
    public void onClicked() {
        prodGUIManager.onGUIChange(prodGUI);
    }
}
