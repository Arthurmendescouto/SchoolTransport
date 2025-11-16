package org.example.schooltransport.controllers;

import java.io.IOException;

import org.example.schooltransport.data.Repositorio;
import org.example.schooltransport.model.Aluno;
import org.example.schooltransport.model.Responsavel;
import org.example.schooltransport.utils.Masks;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastrarAlunoController {

    @FXML private TextField campoNome;
    @FXML private TextField campoCpf;
    @FXML private ComboBox<Responsavel> campoResponsavel;
    @FXML private TextField campoContato;
    @FXML private TextField campoEmail;
    @FXML private PasswordField campoSenha;
    @FXML private Label mensagemStatus;

    @FXML
    public void initialize() {
        carregarResponsaveisNoComboBox();
        Masks.applyCPFMask(campoCpf);
        Masks.applyPhoneMask(campoContato);
        Masks.applyEmailValidation(campoEmail);

    }

    private void carregarResponsaveisNoComboBox() {
        if (Repositorio.getListaResponsavel().isEmpty()) {
            campoResponsavel.setDisable(true);
            campoResponsavel.setPromptText("Lista de responsáveis vazia");
        } else {
            campoResponsavel.setDisable(false);
            campoResponsavel.getItems().addAll(Repositorio.getListaResponsavel());

            campoResponsavel.setCellFactory(list -> new javafx.scene.control.ListCell<>() {
                @Override
                protected void updateItem(Responsavel resp, boolean empty) {
                    super.updateItem(resp, empty);
                    setText(empty || resp == null ? null : resp.getNome());
                }
            });

            campoResponsavel.setButtonCell(new javafx.scene.control.ListCell<>() {
                @Override
                protected void updateItem(Responsavel resp, boolean empty) {
                    super.updateItem(resp, empty);
                    setText(empty || resp == null ? null : resp.getNome());
                }
            });
        }
    }

    @FXML
    private void cadastrarAluno() {
        try {
            String nome = campoNome.getText();
            String cpf = campoCpf.getText();
            Responsavel responsavelSelecionado = campoResponsavel.getValue();
            String contato = campoContato.getText();
            String email = campoEmail.getText();
            String senha = campoSenha.getText();

            if (nome.isEmpty() || cpf.isEmpty() || responsavelSelecionado == null ||
                contato.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                mensagemStatus.setText("Preencha todos os campos!");
                return;
            }

            Aluno novoAluno = new Aluno(
                nome,
                cpf,
                responsavelSelecionado.getNome(),
                contato,
                email,
                senha
            );

            Repositorio.getListaAluno().add(novoAluno);
            mensagemStatus.setText("Aluno cadastrado com sucesso!");
            limparCampos();

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
        campoResponsavel.setValue(null);
        campoContato.clear();
        campoEmail.clear();
        campoSenha.clear();
    }
}
