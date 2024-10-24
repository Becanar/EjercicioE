module org.example.ejercicioe {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.ejercicioe to javafx.fxml;
    exports org.example.ejercicioe;
}