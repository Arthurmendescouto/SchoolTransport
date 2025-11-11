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

public class CadastrarMotoristaController {

    @FXML
    private TextField campoNomeParada;
    @FXML
    private TextField campoCEP;
    @FXML
    private TextField campoBairro;
    @FXML
    private Label mensagemStatus;

    @FXML
    private void voltar(ActionEvent event) {
        // Navega para o painel de administrador
        navegarDeTela(event, "painelAdministrador.fxml");
    }

    @FXML
    private void cadastrar(ActionEvent event) {
        String nome = campoNomeParada.getText();
        String cpf = campoCEP.getText();
        String contato = campoBairro.getText();

        if (nome.isEmpty() || cpf.isEmpty() || contato.isEmpty()) {
            mensagemStatus.setText("Preencha todos os campos!");
            return;
        }

        // TODO: Implementar cadastro de motorista quando o modelo estiver pronto
        mensagemStatus.setText("Motorista cadastrado com sucesso!");
        
        // Limpar campos
        campoNomeParada.clear();
        campoCEP.clear();
        campoBairro.clear();
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

