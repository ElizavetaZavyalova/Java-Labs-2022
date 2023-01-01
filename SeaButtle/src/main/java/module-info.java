module client.seabuttle {
    requires javafx.controls;
    requires javafx.fxml;


    opens client2 to javafx.fxml;
    exports client2;
    opens client1 to javafx.fxml;
    exports client1;
}