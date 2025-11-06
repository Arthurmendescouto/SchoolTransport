module org.example.schooltransport {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.schooltransport to javafx.fxml;
    exports org.example.schooltransport;
}