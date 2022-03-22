module GUI {
    requires javafx.controls;
    requires javafx.fxml;
    
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires javafx.graphics;

    opens GUI to javafx.fxml;
    exports GUI;
}