package org.example.schooltransport.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class PainelAdministradorController implements Initializable {

    @FXML
    private Label mensagemStatus;

    @FXML
    private javafx.scene.control.Button botaoVoltarTopo;
    @FXML
    private javafx.scene.control.Button btnConsultarNotificacoes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // garante que o botão de topo tenha um handler se o onAction do FXML falhar por algum motivo
        if (botaoVoltarTopo != null) {
            botaoVoltarTopo.setOnAction(e -> voltar(e));
        }

        // Mostrar o botão de consultar notificações quando o usuário logado for um responsável
        try {
            if (btnConsultarNotificacoes != null) {
                boolean mostrar = (org.example.schooltransport.data.Repositorio.getCurrentUserType() == 'R');
                btnConsultarNotificacoes.setVisible(mostrar);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void voltar(ActionEvent event) {
        // Navega para a tela de login ou outra tela apropriada
        navegarDeTela(event, "login.fxml");
    }

    @FXML
    private void abrirCadastrarRota(ActionEvent event) {
        // Navega para a tela de cadastrar rota
        navegarDeTela(event, "cadastrarRota.fxml");
    }

    @FXML
    private void abrirCadastrarAluno(ActionEvent event) {
        navegarDeTela(event, "cadastrarAlunos.fxml");
    }

    @FXML
    private void abrirCadastrarMotorista(ActionEvent event) {
        navegarDeTela(event, "cadastrarMotorista.fxml");
    }

    @FXML
    private void abrirCadastrarResponsavel(ActionEvent event) {
        navegarDeTela(event, "cadastrarResponsavel.fxml");
    }

    @FXML
    private void abrirCadastrarVeiculo(ActionEvent event) {
        navegarDeTela(event, "cadastrarVeiculo.fxml");
    }

    @FXML
    private void abrirCadastrarParada(ActionEvent event) {
        // Navega para a tela de cadastrar parada
        navegarDeTela(event, "cadastrarParada.fxml");
    }

    @FXML
    private void abrirListarRotas(ActionEvent event) {
        // Navega para a tela que lista rotas (para selecionar uma a editar)
        navegarDeTela(event, "listarRotas.fxml");
    }

    @FXML
    private void abrirConsultarNotificacoes(ActionEvent event) {
        navegarDeTela(event, "consultarNotificacoes.fxml");
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

