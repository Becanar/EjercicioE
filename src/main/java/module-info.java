module org.example.ejercicioe {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.ejercicioe to javafx.fxml;
    exports org.example.ejercicioe.model;
    exports org.example.ejercicioe.app;
    opens org.example.ejercicioe.app to javafx.fxml;
}