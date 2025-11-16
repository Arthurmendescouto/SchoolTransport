// Filipe Alves Sousa Julio

package org.example.schooltransport.controllers;

import java.io.IOException;

import org.example.schooltransport.data.Repositorio;
import org.example.schooltransport.model.Aluno;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastrarAlunoController {

    @FXML private TextField campoNome;
    @FXML private TextField campoCpf;
    @FXML private TextField campoResponsavel;
    @FXML private TextField campoContato;
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
            String email = campoEmail.getText();
            String senha = campoSenha.getText();

            if (nome.isEmpty() || cpf.isEmpty() || responsavel.isEmpty() ||
                    contato.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                mensagemStatus.setText("Preencha todos os campos!");
                return;
            }

            // Criar novo aluno sem parada (parada será associada via Parada.setAluno())
            Aluno novoAluno = new Aluno(nome, cpf, responsavel, contato, email, senha);
            Repositorio.getListaAluno().add(novoAluno);
            mensagemStatus.setText("Aluno cadastrado com sucesso!");
            limparCampos();
            System.out.println("Aluno cadastrado: " + novoAluno.getNome());

        //inseri também tratamento para excessões
        } catch (Exception e) {
            mensagemStatus.setText("Erro ao cadastrar: " + e.getMessage());
        }
    }

    @FXML
    private void voltarTelaAnterior() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/schooltransport/painelAdministrador.fxml"));
            Parent root = loader.load();
            Scene cena = new Scene(root, 390, 700);
            Stage stage = (Stage) campoNome.getScene().getWindow();
            stage.setScene(cena);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            mensagemStatus.setText("Erro ao voltar para o painel do administrador.");
            e.printStackTrace();
        }
    }

    private void limparCampos() {
        campoNome.clear();
        campoCpf.clear();
        campoResponsavel.clear();
        campoContato.clear();
        campoEmail.clear();
        campoSenha.clear();
    }
}
