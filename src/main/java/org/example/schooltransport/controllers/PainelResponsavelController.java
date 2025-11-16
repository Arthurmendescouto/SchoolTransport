package org.example.schooltransport.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PainelResponsavelController implements Initializable {

    @FXML
    private javafx.scene.control.Button botaoVoltarTopo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (botaoVoltarTopo != null) {
            botaoVoltarTopo.setOnAction(e -> voltar(e));
        }
    }

    @FXML
    private void voltar(ActionEvent event) {
        navegarDeTela(event, "login.fxml");
    }

    @FXML
    private void abrirConsultarNotificacoes(ActionEvent event) {
        navegarDeTela(event, "consultarNotificacoes.fxml");
    }

    private void navegarDeTela(ActionEvent event, String fxmlFile) {
        try {
            String caminhoAbsoluto = "/org/example/schooltransport/" + fxmlFile;
            URL resourceUrl = getClass().getResource(caminhoAbsoluto);
            if (resourceUrl == null) {
                System.err.println("FATAL: Não foi possível encontrar o FXML em: " + caminhoAbsoluto);
                return;
            }
            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
