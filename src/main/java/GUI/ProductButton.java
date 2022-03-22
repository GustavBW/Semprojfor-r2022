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

    public ProductButton(Product p, ProductGUIManager pGUIM) {
        super(p.get(ProductAttribute.NAME));
        this.prodGUIManager = pGUIM;
        this.product = p;

        prodGUI = new ProductGUI(product);
    }

    @Override
    public void onClicked() {
        prodGUIManager.onGUIChange(prodGUI);
    }
}
