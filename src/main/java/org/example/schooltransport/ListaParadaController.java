package org.example.schooltransport;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ListaParadaController implements Initializable {

    @FXML private TableView<Parada> tabelaParadas;
    @FXML private TableColumn<Parada, String> colunaParada;
    // Coluna booleana (visto/Status)
    @FXML private TableColumn<Parada, Boolean> colunaStatus;

    // Este método é chamado automaticamente após o FXML ser carregado
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 1. Configurar as colunas para buscar as propriedades do objeto Parada
        // "logradouroProperty" é o nome da SimpleStringProperty que criamos no Parada.java
        colunaParada.setCellValueFactory(new PropertyValueFactory<>("logradouro"));

        // "passadaProperty" é o nome da SimpleBooleanProperty para o status
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("passada"));

        // 2. Obter a lista de dados da nossa classe Singleton
        ObservableList<Parada> dados = Cadastro.getInstance().getListaDeParadas();

        // 3. Vincular a lista de dados à TableView
        tabelaParadas.setItems(dados);

    }

    @FXML
    private void voltar(ActionEvent event) {
        navegarDeTela(event, "cadastrarParada.fxml");
    }

    /**
     * Método auxiliar para gerenciar a troca de tela (reutilização de código).
     */
    private void navegarDeTela(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar o arquivo FXML: " + fxmlFile);
        }
    }
}