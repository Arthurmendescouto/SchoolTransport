package org.example.schooltransport.controllers;

import java.io.IOException;

import org.example.schooltransport.data.Repositorio;
import org.example.schooltransport.model.Responsavel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastrarResponsavelController {
    
    @FXML private TextField campoNomeResponsavel;
    @FXML private TextField campoCPF;
    @FXML private TextField campoContato;
    @FXML private TextField campoEmail;
    @FXML private PasswordField campoSenha;
    @FXML private Label mensagemStatus;

    @FXML
    private void cadastrarResponsavel() {
        String nome = campoNomeResponsavel.getText();
        String cpf = campoCPF.getText();
        String contato = campoContato.getText();
        String email = campoEmail.getText();
        String senha = campoSenha.getText();

        if (nome.isEmpty() || cpf.isEmpty() || contato.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            mensagemStatus.setText("Preencha todos os campos!");
            return;
        }

        Responsavel responsavel = new Responsavel(nome, cpf, contato, email, senha);
        Repositorio.getListaResponsavel().add(responsavel);
        mensagemStatus.setText("Responsável cadastrado com sucesso!");
        System.out.println("Responsável cadastrado: " + responsavel.getNome());
        System.out.println(Repositorio.getListaResponsavel().size());
        limparCampos();
    }

    @FXML
    private void voltarTelaAnterior() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/schooltransport/consultarResponsavel.fxml"));
            Scene cena = new Scene(loader.load());
            Stage stage = (Stage) campoNomeResponsavel.getScene().getWindow();
            stage.setScene(cena);
            stage.show();
        } catch (IOException e) {
            mensagemStatus.setText("Erro ao voltar para a tela anterior.");
            e.printStackTrace();
        }
    }

    private void limparCampos() {
        campoNomeResponsavel.clear();
        campoCPF.clear();
        campoContato.clear();
        campoEmail.clear();
        campoSenha.clear();
    }
}
