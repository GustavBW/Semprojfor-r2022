package dk.sdu.se_f22.productmodule.management.GUI;

import dk.sdu.se_f22.productmodule.management.domain_persistance.BaseProduct;
import dk.sdu.se_f22.productmodule.management.domain_persistance.ProductAttribute;

/*
It seems to me that all the functionality of this class is already found is the pGUI class.
Should these pB's just call the same methods as found in the pGUI class?
*/

public class ProductButton extends FuncButton {

    private final ProductGUIManager prodGUIManager;
    private final BaseProduct baseProduct;
    private final ProductGUI prodGUI;

    public ProductButton(BaseProduct p, ProductGUIManager pGUIM) {
        super(p.get(ProductAttribute.NAME));
        this.prodGUIManager = pGUIM;
        this.baseProduct = p;

        button.setPrefWidth(App.dim.getX() * 0.3);
        button.setPrefHeight(App.dim.getY() * 0.1);

        prodGUI = new ProductGUI(baseProduct);
    }

    @Override
    public void onClicked() {
        prodGUIManager.onGUIChange(prodGUI);
    }
}
