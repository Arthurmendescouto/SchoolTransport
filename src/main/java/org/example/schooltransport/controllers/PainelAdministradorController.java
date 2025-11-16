package org.example.schooltransport.controllers;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PainelAdministradorController {

    @FXML
    private TextField campoNomeParada;
    @FXML
    private TextField campoCEP;
    @FXML
    private TextField campoLogradouro;
    @FXML
    private TextField campoBairro;
    @FXML
    private TextField campoComplemento;
    @FXML
    private Label mensagemStatus;

    @FXML
    private void voltar(ActionEvent event) {
        // Navega para a tela de login ou outra tela apropriada
        navegarDeTela(event, "login.fxml");
    }

    @FXML
    private void consultarFaltas(ActionEvent event) {
        try {
            // Navega para a tela de consulta de faltas do administrador
            System.out.println("DEBUG: Tentando navegar para consultarFaltasAdmin.fxml");
            navegarDeTela(event, "consultarFaltasAdmin.fxml");
        } catch (Exception e) {
            System.err.println("Erro ao navegar para tela de faltas: " + e.getMessage());
            e.printStackTrace();
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
            System.out.println("DEBUG: Carregando FXML de: " + resourceUrl);
            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Parent root = loader.load();
            System.out.println("DEBUG: FXML carregado com sucesso");

            // Obtém o Stage (janela)
            Node sourceNode = (Node) event.getSource();
            Stage stage = (Stage) sourceNode.getScene().getWindow();

            // Define a nova cena
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar o FXML: " + fxmlFile);
            System.err.println("Tipo do erro: " + e.getClass().getName());
            System.err.println("Mensagem: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Causa: " + e.getCause().getClass().getName());
                System.err.println("Mensagem da causa: " + e.getCause().getMessage());
            }
            System.err.println(">>> SE O ERRO FOR 'ClassNotFoundException', RECOMPILE O PROJETO! <<<");
        }
    }
}

