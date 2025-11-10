package org.example.schooltransport.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.schooltransport.data.Repositorio;
import org.example.schooltransport.model.Aluno;

public class CadastrarAlunoController {

    @FXML
    private TextField campoNome;

    @FXML
    private TextField campoCpf;

    @FXML
    private TextField campoResponsavel;

    @FXML
    private TextField campoContato;

    @FXML
    private TextField campoParada;

    @FXML
    private TextField campoEmail;

    @FXML
    private TextField campoSenha;

    @FXML
    private Label mensagemStatus;

    @FXML
    private void cadastrarAluno() {
        String nome = campoNome.getText();
        String cpf = campoCpf.getText();
        String responsavel = campoResponsavel.getText();
        String contato = campoContato.getText();
        String parada = campoParada.getText();
        String email = campoEmail.getText();
        String senha = campoSenha.getText();

        if (nome.isEmpty() || cpf.isEmpty() || responsavel.isEmpty() ||
                contato.isEmpty() || parada.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            mensagemStatus.setText("Preencha todos os campos!");
            return;
        }

        Aluno novoAluno = new Aluno(nome, cpf, responsavel, contato, parada, email, senha);
        Repositorio.getListaAluno().add(novoAluno);
        mensagemStatus.setText("Aluno cadastrado com sucesso!");

        limparCampos();
    }

    private void limparCampos() {
        campoNome.clear();
        campoCpf.clear();
        campoResponsavel.clear();
        campoContato.clear();
        campoParada.clear();
        campoEmail.clear();
        campoSenha.clear();
    }
}
