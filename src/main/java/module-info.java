module org.example.schooltransport {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;

    opens org.example.schooltransport to javafx.fxml;
    opens org.example.schooltransport.controllers to javafx.fxml;

    exports org.example.schooltransport;
    exports org.example.schooltransport.controllers;
}
