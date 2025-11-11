// filipe alves sousa julio
package org.example.schooltransport.controllers;

import java.io.IOException;

import org.example.schooltransport.data.Repositorio;
import org.example.schooltransport.model.Aluno;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastrarAlunoController {

    @FXML private TextField campoNome;
    @FXML private TextField campoCpf;
    @FXML private TextField campoResponsavel;
    @FXML private TextField campoContato;
    @FXML private TextField campoParada;
    @FXML private TextField campoEmail;
    @FXML private PasswordField campoSenha;
    @FXML private Label mensagemStatus;

    @FXML
    private void cadastrarAluno() {
        try {
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

            // estou criando um novo aluno e inserindo na minha ed
            Aluno novoAluno = new Aluno(nome, cpf, responsavel, contato, parada, email, senha);
            Repositorio.getListaAluno().add(novoAluno);
            mensagemStatus.setText("Aluno cadastrado com sucesso!");
            limparCampos();

        //inseri também tratamento para excessões
        } catch (Exception e) {
            mensagemStatus.setText("Erro ao cadastrar: " + e.getMessage());
        }
    }

    @FXML
    private void voltarListaAlunos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/schooltransport/consultarAlunos.fxml"));
            Scene cena = new Scene(loader.load());
            Stage stage = (Stage) campoNome.getScene().getWindow();
            stage.setScene(cena);
            stage.show();
        } catch (IOException e) {
            mensagemStatus.setText("Erro ao voltar para a lista de alunos.");
            e.printStackTrace();
        }
    }

    // estou usando para limpar os campos depois do cadastro
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
