module org.example.schooltransport {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;

    opens org.example.schooltransport to javafx.fxml;
    opens org.example.schooltransport.controllers to javafx.fxml;
    opens org.example.schooltransport.model to javafx.base;
    opens org.example.schooltransport.data to javafx.base;

    exports org.example.schooltransport;
}
