package org.example.schooltransport;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RegistrarParadaController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Registrar rota!");
    }
}
