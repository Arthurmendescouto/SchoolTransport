package org.example.schooltransport.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegistrarAlunoController {

    @FXML private TextField campoNomeAluno;
    @FXML private TextField campoCpf;
    @FXML private TextField campoResponsavel;
    @FXML private TextField campoContato;
    @FXML private TextField campoPontoParada;
    @FXML private Button botaoConcluir;
    @FXML private Label mensagemStatus;

    @FXML
    private void initialize() {
        botaoConcluir.setOnAction(event -> salvarAluno());
    }

    private void salvarAluno() {
        String nome = campoNomeAluno.getText();
        String cpf = campoCpf.getText();
        String responsavel = campoResponsavel.getText();
        String contato = campoContato.getText();
        String parada = campoPontoParada.getText();

        if (nome.isEmpty() || cpf.isEmpty()) {
            mensagemStatus.setText("Preencha os campos obrigatórios!");
            return;
        }

        mensagemStatus.setText("Aluno cadastrado com sucesso!");
        System.out.println("Aluno: " + nome + " | CPF: " + cpf);
        System.out.println("Responsável: " + responsavel + " | Contato: " + contato);
        System.out.println("Ponto de parada: " + parada);
    }
}
