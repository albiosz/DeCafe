module com.example.decafe {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires java.desktop;


    opens com.example.decafe to javafx.fxml;
    exports com.example.decafe;
    exports com.example.decafe.util;
    opens com.example.decafe.util to javafx.fxml;
    exports com.example.decafe.exception;
    opens com.example.decafe.exception to javafx.fxml;
}