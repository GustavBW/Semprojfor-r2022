package GUI;

public class CreateNewProductButton extends FuncButton{

    public CreateNewProductButton() {
        super("Create New");
    }

    @Override
    public void onClicked(){
        ProductGUI newProductGUI = new ProductGUI();

    }
}
