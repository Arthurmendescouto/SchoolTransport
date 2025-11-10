package org.example.schooltransport.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegistrarResponsavelController {

    @FXML
    private TextField campoNomeResponsavel;

    @FXML
    private TextField campoCPF;

    @FXML
    private TextField campoContato;

    @FXML
    private Label mensagemStatus;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnVoltar;

    @FXML
    private void initialize() {
        mensagemStatus.setText("");
        btnCadastrar.setOnAction(e -> cadastrarResponsavel());
        btnVoltar.setOnAction(e -> voltarTelaAnterior());
    }

    private void cadastrarResponsavel() {
        String nome = campoNomeResponsavel.getText();
        String cpf = campoCPF.getText();
        String contato = campoContato.getText();

        if (nome.isEmpty() || cpf.isEmpty() || contato.isEmpty()) {
            mensagemStatus.setText("Preencha todos os campos!");
            return;
        }

        // Aqui você pode salvar no banco, etc.
        mensagemStatus.setText("Responsável cadastrado com sucesso!");
    }

    private void voltarTelaAnterior() {
        System.out.println("Voltando à tela anterior...");
        // Aqui você pode implementar a navegação de retorno (carregar outro FXML, por exemplo)
    }
}
