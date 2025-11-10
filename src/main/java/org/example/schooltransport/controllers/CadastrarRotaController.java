package org.example.schooltransport.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CadastrarRotaController {

    @FXML
    private TextField campoMotorista;

    @FXML
    private TextField campoOnibus;

    @FXML
    private TextField campoTurno;

    @FXML
    private VBox listaParadasContainer;

    @FXML
    private Button botaoAdicionarParada;

    @FXML
    private Button botaoSalvar;

    @FXML
    private Button botaoVoltar;

    @FXML
    private Label mensagemStatus;

    @FXML
    private void initialize() {
        mensagemStatus.setText("");
    }

    @FXML
    private void adicionarParada() {
        TextField novaParada = new TextField();
        novaParada.setPromptText("Nome da parada");
        novaParada.getStyleClass().add("form-field");
        listaParadasContainer.getChildren().add(novaParada);
    }

    @FXML
    private void salvarRota() {
        String motorista = campoMotorista.getText();
        String onibus = campoOnibus.getText();
        String turno = campoTurno.getText();

        if (motorista.isEmpty() || onibus.isEmpty() || turno.isEmpty()) {
            mensagemStatus.setText("⚠️ Preencha todos os campos principais!");
            return;
        }

        int qtdParadas = listaParadasContainer.getChildren().size();
        mensagemStatus.setText("✅ Rota cadastrada com sucesso! Paradas: " + qtdParadas);
    }

    @FXML
    private void voltarTela() {
        System.out.println("Voltando à tela anterior...");
    }
}
