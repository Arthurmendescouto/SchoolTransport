package org.example.schooltransport.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CadastrarVeiculoController {

    @FXML
    private TextField campoModeloOnibus;

    @FXML
    private TextField campoPlaca;

    @FXML
    private TextField campoCapacidade;

    @FXML
    private Button botaoCadastrar;

    @FXML
    private Button botaoVoltar;

    @FXML
    private Label mensagemStatus;

    @FXML
    private void initialize() {
        // Configurações iniciais
        mensagemStatus.setText("");
    }

    @FXML
    private void onCadastrar() {
        String modelo = campoModeloOnibus.getText();
        String placa = campoPlaca.getText();
        String capacidade = campoCapacidade.getText();

        if (modelo.isEmpty() || placa.isEmpty() || capacidade.isEmpty()) {
            mensagemStatus.setText("⚠️ Preencha todos os campos!");
            return;
        }

        // Exemplo de simulação de cadastro
        mensagemStatus.setText("✅ Veículo cadastrado: " + modelo + " (" + placa + ")");
        campoModeloOnibus.clear();
        campoPlaca.clear();
        campoCapacidade.clear();
    }

    @FXML
    private void onVoltar() {
        System.out.println("Voltando à tela anterior...");
        // Aqui você pode trocar a cena via Stage se desejar.
    }
}
