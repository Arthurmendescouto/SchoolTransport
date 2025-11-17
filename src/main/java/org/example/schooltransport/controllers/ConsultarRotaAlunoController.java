package org.example.schooltransport.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.schooltransport.model.Parada;
import org.example.schooltransport.data.Repositorio;

import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;

/**
 * Controller responsável pela tela de consulta de rota para alunos.
 * Exibe informações sobre a próxima parada, quantidade de alunos e turno.
 */
public class ConsultarRotaAlunoController {
    private String turnoDaRotaValido = "";
    @FXML
    private Label proximaParada;
    @FXML
    private Label quantidadeDeAlunos;
    @FXML
    private Label quantidadeDeParadas;
    @FXML
    private Label turnoDaRota;
    @FXML
    private void voltar(ActionEvent event) {navegarDeTela(event, "login.fxml");}
    @FXML
    public void initialize() {
        if (consultarProximaParada(Repositorio.getListaParada()) == null) {
            proximaParada.setText("Ainda não há paradas por aqui...");
        } else {
            proximaParada.setText(consultarProximaParada(Repositorio.getListaParada()));
        };
        quantidadeDeAlunos.setText(String.valueOf(Repositorio.getListaAluno().size()));
        quantidadeDeParadas.setText(String.valueOf(Repositorio.getListaParada().size()));
        if (turnoIsValid())
            turnoDaRota.setText(turnoDaRotaValido);
    }

    private String consultarProximaParada(ObservableList<Parada> listaParadas) {
        String auxVar = "";
        for (Parada parada : listaParadas) {
            if (!parada.isPassada())
                auxVar = parada.getNomeParada();
        }
        return auxVar;
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
    private boolean turnoIsValid() {
        try {
            turnoDaRotaValido = String.valueOf(Repositorio.getListaRota().getFirst().getTurno());
            return true;
        } catch (NullPointerException e) {
            turnoDaRotaValido = "-";
        } catch (NoSuchElementException e) {
            turnoDaRotaValido = "-";
        }
        return false;
    }

}
