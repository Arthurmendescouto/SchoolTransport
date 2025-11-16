package org.example.schooltransport.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.example.schooltransport.model.Rota;
import org.example.schooltransport.data.Repositorio;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * Controller responsável pela tela de listagem de rotas.
 * Exibe rotas cadastradas e permite navegar para edição.
 */
public class ListaRotasController implements Initializable {

    @FXML private ListView<Rota> listaRotasListView;
    @FXML private Label mensagemStatus;

    private ObservableList<Rota> rotasObs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rotasObs = FXCollections.observableArrayList(Repositorio.getListaRota());
        listaRotasListView.setItems(rotasObs);

        listaRotasListView.setCellFactory(lv -> new javafx.scene.control.ListCell<Rota>() {
            @Override
            protected void updateItem(Rota item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    String motorista = item.getMotorista() != null ? item.getMotorista().toString() : "(sem motorista)";
                    String onibus = item.getOnibus() != null ? item.getOnibus().toString() : "(sem ônibus)";
                    String turno = item.getTurno() != null ? item.getTurno() : "";
                    setText(String.format("%s — %s (%s)", motorista, onibus, turno));
                }
            }
        });
    }

    @FXML
    private void voltar(ActionEvent event) {
        navegarDeTela(event, "painelAdministrador.fxml");
    }

    @FXML
    private void abrirEditarRota(ActionEvent event) {
        Rota rota = listaRotasListView.getSelectionModel().getSelectedItem();
        if (rota == null) {
            mensagemStatus.setText("Selecione uma rota para editar.");
            return;
        }

        try {
            String caminho = "/org/example/schooltransport/editarRota.fxml";
            URL resourceUrl = getClass().getResource(caminho);
            if (resourceUrl == null) {
                System.err.println("FATAL: editarRota.fxml não encontrado em: " + caminho);
                return;
            }
            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Parent root = loader.load();

            // Passa a rota para o controller de edição
            EditarRotaController controller = loader.getController();
            controller.setRota(rota);

            Node sourceNode = (Node) event.getSource();
            Stage stage = (Stage) sourceNode.getScene().getWindow();
            Scene scene = new Scene(root, 390, 700);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            mensagemStatus.setText("Erro ao abrir editor de rota.");
        }
    }

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
            Node sourceNode = (Node) event.getSource();
            Stage stage = (Stage) sourceNode.getScene().getWindow();
            Scene scene = new Scene(root, 390, 700);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
