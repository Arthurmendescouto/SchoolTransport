package org.example.schooltransport.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.example.schooltransport.model.Parada;
import org.example.schooltransport.data.Repositorio;
import org.example.schooltransport.model.Motorista;
import org.example.schooltransport.model.Rota;
import org.example.schooltransport.model.Veiculo;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * Controller responsável pela tela de cadastro de rotas.
 * Permite associar motorista, veículo, turno e paradas para compor uma rota.
 */
public class CadastrarRotaController implements Initializable {

    @FXML private ComboBox<Motorista> comboMotorista;
    @FXML private ComboBox<Veiculo> comboVeiculo;
    @FXML private ComboBox<Parada> comboParada;
    @FXML private ListView<Parada> listaParadasListView;
    @FXML private javafx.scene.control.Label labelParadasCount;
    @FXML private ToggleGroup turnoGroup;
    @FXML private RadioButton radioMatutino;
    @FXML private RadioButton radioVespertino;
    @FXML private RadioButton radioNoturno;
    @FXML private Button botaoAdicionarParada;
    @FXML private Button botaoSalvar;
    @FXML private Button botaoVoltar;
    @FXML private Label mensagemStatus;

    private ObservableList<Parada> selectedParadas = FXCollections.observableArrayList();

    /**
     * Inicializa o controller configurando combos e lista de paradas.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Popular combos a partir do repositório
        comboMotorista.getItems().clear();
        comboMotorista.getItems().addAll(Repositorio.getListaMotorista());

        comboVeiculo.getItems().clear();
        comboVeiculo.getItems().addAll(Repositorio.getListaVeiculo());

        // Paradas do repositório (observável)
        comboParada.setItems(Repositorio.getListaParada());

        listaParadasListView.setItems(selectedParadas);

        // Cell factory para mostrar cada parada com um botão 'Remover'
        listaParadasListView.setCellFactory(lv -> new javafx.scene.control.ListCell<Parada>() {
            private final javafx.scene.layout.HBox container = new javafx.scene.layout.HBox(8);
            private final javafx.scene.control.Label label = new javafx.scene.control.Label();
            private final javafx.scene.control.Button removeBtn = new javafx.scene.control.Button("Remover");

            {
                container.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
                //adiciona estilização
                container.getStyleClass().add("parada-item");
                label.getStyleClass().add("parada-nome-texto");
                removeBtn.getStyleClass().add("btn-entregue-status");
                javafx.scene.layout.HBox.setHgrow(label, javafx.scene.layout.Priority.ALWAYS);
                label.setMaxWidth(Double.MAX_VALUE);

                removeBtn.setMinWidth(80);

                container.getChildren().addAll(label, removeBtn);
                removeBtn.setOnAction(e -> {
                    Parada item = getItem();
                    if (item != null) {
                        selectedParadas.remove(item);
                    }
                });
            }

            @Override
            protected void updateItem(Parada item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    label.setText(item.toString());
                    setGraphic(container);
                }
            }
        });

        // mantém o label de contagem atualizado
        labelParadasCount.setText("Paradas: " + selectedParadas.size());
        selectedParadas.addListener((javafx.collections.ListChangeListener<Parada>) c -> {
            labelParadasCount.setText("Paradas: " + selectedParadas.size());
        });

        mensagemStatus.setText("");
    }

    /**
     * Adiciona uma parada selecionada à lista de paradas da rota.
     * Valida se o veículo selecionado ainda tem capacidade.
     * @param event Evento da ação
     */
    @FXML
    private void adicionarParada(ActionEvent event) {
        Parada p = comboParada.getSelectionModel().getSelectedItem();
        if (p == null) {
            mensagemStatus.setText("Escolha uma parada para adicionar.");
            return;
        }

        // Verifica se veículo foi selecionado
        Veiculo veiculo = comboVeiculo.getSelectionModel().getSelectedItem();
        if (veiculo == null) {
            mensagemStatus.setText("⚠️ Selecione um veículo antes de adicionar paradas.");
            return;
        }

        // Verifica se o veículo atingiu sua capacidade
        if (selectedParadas.size() >= veiculo.getCapacidade()) {
            mensagemStatus.setText("⚠️ Capacidade do veículo atingida (" + veiculo.getCapacidade() + " paradas).");
            return;
        }

        if (!selectedParadas.contains(p)) {
            selectedParadas.add(p);
            mensagemStatus.setText("✓ Parada adicionada. Paradas: " + selectedParadas.size() + "/" + veiculo.getCapacidade());
            // Limpa seleção do combo para permitir adicionar novamente outra parada
            comboParada.getSelectionModel().clearSelection();
        } else {
            mensagemStatus.setText("Parada já adicionada.");
        }
    }

    /**
     * Salva a rota configurada no repositório.
     * @param event Evento da ação
     */
    @FXML
    private void salvarRota(ActionEvent event) {
        Motorista motorista = comboMotorista.getSelectionModel().getSelectedItem();
        Veiculo veiculo = comboVeiculo.getSelectionModel().getSelectedItem();
        String turno = getSelectedTurno();

        if (motorista == null || veiculo == null || turno == null || turno.trim().isEmpty()) {
            mensagemStatus.setText("⚠️ Selecione motorista, veículo e o turno.");
            return;
        }

        ArrayList<Parada> paradas = new ArrayList<>(selectedParadas);
        Rota rota = new Rota(motorista, veiculo, turno, paradas);

        // Adiciona ao repositório
        Repositorio.getListaRota().add(rota);

        mensagemStatus.setText("✅ Rota cadastrada com sucesso! Paradas: " + paradas.size());
        // Limpar seleção
        comboMotorista.getSelectionModel().clearSelection();
        comboVeiculo.getSelectionModel().clearSelection();
        comboParada.getSelectionModel().clearSelection();
        // limpar seleção dos radio buttons
        turnoGroup.selectToggle(null);
        selectedParadas.clear();
    }

    /**
     * Obtém o turno selecionado pelos radio buttons.
     * @return Turno selecionado ou string vazia
     */
    private String getSelectedTurno() {
        if (radioMatutino.isSelected()) return "Matutino";
        if (radioVespertino.isSelected()) return "Vespertino";
        if (radioNoturno.isSelected()) return "Noturno";
        return "";
    }

    @FXML
    private void voltarTela(ActionEvent event) {
        navegarDeTela(event, "painelAdministrador.fxml");
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
            System.err.println("Erro ao carregar o FXML: " + fxmlFile);
        }
    }
}
