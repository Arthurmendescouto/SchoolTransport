package org.example.schooltransport.controllers;

import java.io.IOException;
import java.net.URL;

import org.example.schooltransport.data.Repositorio;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller responsável pela tela de login.
 * Autentica usuários e gerencia sessão de acesso.
 */
public class LoginController {

    @FXML
    private TextField emailDigitado; // Campo de e-mail

    @FXML
    private TextField senhaDigitada; // Campo de senha

    @FXML
    private Button btnEntrar;

    @FXML
    private void initialize() {
        // Chamado automaticamente quando o FXML é carregado

    }

    // R = Responsável, A = Aluno, M = Motorista
    char tipoDeUsuario = 'X';

    @FXML
    private void realizarLogin(ActionEvent event) {
        String email = emailDigitado.getText().trim();
        String senha = senhaDigitada.getText().trim();


        if (email.isEmpty() || senha.isEmpty()) {
            mostrarAlerta("Campos obrigatórios", "Por favor, preencha todos os campos.");
            return;
        }

        if ((email.equals("admin@email.com") && senha.equals("1234"))
        || ((email.equals("administrador") && senha.equals("administrador")))) {
            mostrarAlerta("Login realizado", "Bem-vindo, administrador!");
            navegarDeTela(event, "painelAdministrador.fxml");
        } else if (verificaValidadeDosDadosDeLogin(email, senha)) {
            if (tipoDeUsuario == 'R')
                navegarDeTela(event, "painelResponsavel.fxml");
            if (tipoDeUsuario == 'A')
                navegarDeTela(event, "consultarRotaAluno.fxml");
            if (tipoDeUsuario == 'M') {
                navegarDeTela(event, "telaMotorista.fxml");
            }
        } else {
            mostrarAlerta("Erro", "E-mail ou senha inválidos.");
        }
    }
    private void navegarDeTela(ActionEvent event, String fxmlFile) {
        try {
            // Construímos um caminho absoluto a partir da raiz (note o "/" no início)
            String caminhoAbsoluto = "/org/example/schooltransport/" + fxmlFile;

            // Obtém o URL do recurso a partir do caminho absoluto
            URL resourceUrl = getClass().getResource(caminhoAbsoluto);

            if (resourceUrl == null) {
                // Se isso falhar agora, o nome do arquivo em 'fxmlFile' está errado
                System.err.println("FATAL: Não foi possível encontrar o FXML em: " + caminhoAbsoluto);
                System.err.println("Verifique se o nome do arquivo '" + fxmlFile + "' está digitado corretamente.");
                return;
            }

            // Carrega o FXML
            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Parent root = loader.load();

            // Obtém o Stage (janela)
            Node sourceNode = (Node) event.getSource();
            Stage stage = (Stage) sourceNode.getScene().getWindow();

            // Define a nova cena
            Scene scene = new Scene(root, 390, 700);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar o FXML: " + fxmlFile);
            System.err.println(">>> SE O ERRO FOR 'ClassNotFoundException', VOCÊ NÃO CORRIGIU O 'fx:controller' DENTRO DO FXML! <<<");
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private boolean verificaValidadeDosDadosDeLogin(String email, String senha) {
        boolean isValid = false;
        for (int i = 0; i < Repositorio.getListaAluno().size(); i++) {
            if (email.equals(Repositorio.getListaAluno().get(i).getEmail())
                    && senha.equals(Repositorio.getListaAluno().get(i).getSenha())) {
                isValid = true;
                tipoDeUsuario = 'A';
                // registra sessão para aluno
                Repositorio.setCurrentUserType('A');
                Repositorio.setCurrentUserCpf(Repositorio.getListaAluno().get(i).getCpf());
                break;
            }
        }
        for (int i = 0; i < Repositorio.getListaResponsavel().size(); i++) {
            if (email.equals(Repositorio.getListaResponsavel().get(i).getEmail())
                    && senha.equals(Repositorio.getListaResponsavel().get(i).getSenha())) {
                isValid = true;
                tipoDeUsuario = 'R';
                // registra sessão para responsável
                Repositorio.setCurrentUserType('R');
                Repositorio.setCurrentUserCpf(Repositorio.getListaResponsavel().get(i).getCpf());
                break;
            }
        }
        for (int i = 0; i < Repositorio.getListaMotorista().size(); i++) {
            if (email.equals(Repositorio.getListaMotorista().get(i).getEmail())
                    && senha.equals(Repositorio.getListaMotorista().get(i).getSenha())) {
                isValid = true;
                tipoDeUsuario = 'R';
                // registra sessão para responsável
                Repositorio.setCurrentUserType('R');
                Repositorio.setCurrentUserCpf(Repositorio.getListaMotorista().get(i).getCpf());
                break;
            }
        }


        return isValid;
    }
}
