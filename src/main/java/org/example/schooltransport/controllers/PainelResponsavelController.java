package org.example.schooltransport.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller responsável pelo painel do responsável.
 * Oferece acesso à consulta de notificações dos alunos sob sua responsabilidade.
 */
public class PainelResponsavelController implements Initializable {

    @FXML
    private javafx.scene.control.Button botaoVoltarTopo;

    /**
     * Inicializa o controller configurando o botão de voltar.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (botaoVoltarTopo != null) {
            botaoVoltarTopo.setOnAction(e -> voltar(e));
        }
    }

    /**
     * Retorna à tela de login.
     */
    @FXML
    private void voltar(ActionEvent event) {
        navegarDeTela(event, "login.fxml");
    }

    /**
     * Abre a tela de consulta de notificações dos alunos.
     */
    @FXML
    private void abrirConsultarNotificacoes(ActionEvent event) {
        navegarDeTela(event, "consultarNotificacoes.fxml");
    }

    /**
     * Método auxiliar para navegação entre telas.
     * @param event Evento de ação do botão
     * @param fxmlFile Nome do arquivo FXML de destino
     */
    private void navegarDeTela(ActionEvent event, String fxmlFile) {
        try {
            String caminhoAbsoluto = "/org/example/schooltransport/" + fxmlFile;
            URL resourceUrl = getClass().getResource(caminhoAbsoluto);
            if (resourceUrl == null) {
                System.err.println("FATAL: Não foi possível encontrar o FXML em: " + caminhoAbsoluto);
                return;
            }
            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 390, 700);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
