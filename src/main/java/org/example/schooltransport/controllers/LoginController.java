package org.example.schooltransport.controllers;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField campoNomeParada; // Email
    @FXML
    private TextField campoCEP; // Senha

    @FXML
    private void entrar(ActionEvent event) {
        String email = campoNomeParada.getText();
        String senha = campoCEP.getText();

        if (email.isEmpty() || senha.isEmpty()) {
            System.err.println("Preencha email e senha!");
            return;
        }

        // TODO: Implementar validação de login
        // Por enquanto, navega para o painel de administrador
        navegarDeTela(event, "painelAdministrador.fxml");
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
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar o FXML: " + fxmlFile);
            System.err.println(">>> SE O ERRO FOR 'ClassNotFoundException', VOCÊ NÃO CORRIGIU O 'fx:controller' DENTRO DO FXML! <<<");
        }
    }
}

