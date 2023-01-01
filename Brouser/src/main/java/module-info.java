module com.example.brouser2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires opencsv;

    opens com.example.brouser2 to javafx.fxml;
    exports com.example.brouser2;
    exports com.example.brouser2.History;
    opens com.example.brouser2.History to javafx.fxml;
}