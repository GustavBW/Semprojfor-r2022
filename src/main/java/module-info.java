module GUI {
    requires javafx.controls;
    requires javafx.fxml;
    
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires javafx.graphics;
    requires org.jetbrains.annotations;
    requires java.desktop;

    opens dk.sdu.se_f22.productmodule.management.GUI to javafx.fxml;
    exports dk.sdu.se_f22.productmodule.management.GUI;
}