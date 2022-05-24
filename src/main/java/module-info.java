module GUI {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.jetbrains.annotations;
    requires java.desktop;

    opens dk.sdu.se_f22 to javafx.fxml;
    opens dk.sdu.se_f22.productmodule.management.GUI to javafx.fxml;
    
    exports dk.sdu.se_f22;
    exports dk.sdu.se_f22.productmodule.management.GUI;
}