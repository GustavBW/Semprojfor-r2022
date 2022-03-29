package GUI;

public class ReloadGUIButton extends FuncButton{

    private final App app;

    public ReloadGUIButton(App app) {
        super("Reload");
        this.app = app;
    }

    @Override
    public void onClicked() {
        app.reloadProductButtons();
        System.out.println("Reloaded Product Buttons");
    }
}
