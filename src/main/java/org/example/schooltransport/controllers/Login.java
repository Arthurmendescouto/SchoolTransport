package org.example.schooltransport.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Login {

    @FXML
    private TextField campoNomeParada; // Campo de e-mail

    @FXML
    private TextField campoCEP; // Campo de senha

    @FXML
    private Button btnEntrar;

    @FXML
    private void initialize() {
        // Chamado automaticamente quando o FXML é carregado
        configurarEventos();
    }

    private void configurarEventos() {
        btnEntrar.setOnAction(event -> realizarLogin());
    }

    private void realizarLogin() {
        String email = campoNomeParada.getText().trim();
        String senha = campoCEP.getText().trim();

        if (email.isEmpty() || senha.isEmpty()) {
            mostrarAlerta("Campos obrigatórios", "Por favor, preencha todos os campos.");
            return;
        }

        // Aqui você pode implementar a lógica real de autenticação (ex: consulta ao banco)
        if (email.equals("admin@email.com") && senha.equals("1234")) {
            mostrarAlerta("Login realizado", "Bem-vindo, administrador!");
            // TODO: Redirecionar para próxima tela
        } else {
            mostrarAlerta("Erro", "E-mail ou senha inválidos.");
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
