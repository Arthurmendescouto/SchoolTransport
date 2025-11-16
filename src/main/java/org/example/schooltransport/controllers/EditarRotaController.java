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
 * Controller responsável pela tela de edição de rotas.
 * Permite modificar motorista, veículo, turno e paradas de uma rota existente.
 */
public class EditarRotaController implements Initializable {

    @FXML private ComboBox<Motorista> comboMotorista;
    @FXML private ComboBox<Veiculo> comboVeiculo;
    @FXML private ComboBox<Parada> comboParada;
    @FXML private ListView<Parada> listaParadasListView;
    @FXML private Label labelParadasCount;
    @FXML private ToggleGroup turnoGroup;
    @FXML private RadioButton radioMatutino;
    @FXML private RadioButton radioVespertino;
    @FXML private RadioButton radioNoturno;
    @FXML private Button botaoAdicionarParada;
    @FXML private Button botaoSalvar;
    @FXML private Button botaoVoltar;
    @FXML private Label mensagemStatus;

    private ObservableList<Parada> selectedParadas = FXCollections.observableArrayList();
    private Rota rotaEmEdicao;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboMotorista.getItems().clear();
        comboMotorista.getItems().addAll(Repositorio.getListaMotorista());

        comboVeiculo.getItems().clear();
        comboVeiculo.getItems().addAll(Repositorio.getListaVeiculo());

        comboParada.setItems(Repositorio.getListaParada());

        listaParadasListView.setItems(selectedParadas);

        // re-use same cell factory logic as cadastrar
        listaParadasListView.setCellFactory(lv -> new javafx.scene.control.ListCell<Parada>() {
            private final javafx.scene.layout.HBox container = new javafx.scene.layout.HBox(8);
            private final javafx.scene.control.Label label = new javafx.scene.control.Label();
            private final javafx.scene.control.Button removeBtn = new javafx.scene.control.Button("Remover");

            {
                container.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
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

        labelParadasCount.setText("Paradas: " + selectedParadas.size());
        selectedParadas.addListener((javafx.collections.ListChangeListener<Parada>) c -> {
            labelParadasCount.setText("Paradas: " + selectedParadas.size());
        });

        mensagemStatus.setText("");
    }

    /**
     * Define a rota a ser editada e preenche os campos com seus valores.
     * @param rota Rota a ser editada
     */
    public void setRota(Rota rota) {
        this.rotaEmEdicao = rota;
        // preenche campos com valores da rota
        if (rota.getMotorista() != null) comboMotorista.getSelectionModel().select(rota.getMotorista());
        if (rota.getOnibus() != null) comboVeiculo.getSelectionModel().select(rota.getOnibus());
        String t = rota.getTurno();
        if (t != null) {
            String tl = t.toLowerCase();
            // aceitar sinônimos (ex: "Manhã" -> Matutino, "Tarde" -> Vespertino, "Noite" -> Noturno)
            if (tl.contains("mat") || tl.contains("manh")) {
                turnoGroup.selectToggle(radioMatutino);
            } else if (tl.contains("vesp") || tl.contains("tard")) {
                turnoGroup.selectToggle(radioVespertino);
            } else if (tl.contains("not") || tl.contains("noit")) {
                turnoGroup.selectToggle(radioNoturno);
            }
        }

        selectedParadas.clear();
        if (rota.getParadas() != null) selectedParadas.addAll(rota.getParadas());
    }

    @FXML
    private void adicionarParada(ActionEvent event) {
        Parada p = comboParada.getSelectionModel().getSelectedItem();
        if (p == null) {
            mensagemStatus.setText("Escolha uma parada para adicionar.");
            return;
        }
        if (!selectedParadas.contains(p)) {
            selectedParadas.add(p);
            mensagemStatus.setText("");
            comboParada.getSelectionModel().clearSelection();
        } else {
            mensagemStatus.setText("Parada já adicionada.");
        }
    }

    @FXML
    private void salvarRota(ActionEvent event) {
        if (rotaEmEdicao == null) {
            mensagemStatus.setText("Erro interno: rota não carregada.");
            return;
        }

        Motorista motorista = comboMotorista.getSelectionModel().getSelectedItem();
        Veiculo veiculo = comboVeiculo.getSelectionModel().getSelectedItem();
        String turno = getSelectedTurno();

        if (motorista == null || veiculo == null || turno == null || turno.trim().isEmpty()) {
            mensagemStatus.setText("⚠️ Selecione motorista, veículo e o turno.");
            return;
        }

        rotaEmEdicao.setMotorista(motorista);
        rotaEmEdicao.setOnibus(veiculo);
        rotaEmEdicao.setTurno(turno);
        rotaEmEdicao.setParadas(new ArrayList<>(selectedParadas));

        mensagemStatus.setText("✅ Rota atualizada com sucesso!");
    }

    private String getSelectedTurno() {
        if (radioMatutino.isSelected()) return "Matutino";
        if (radioVespertino.isSelected()) return "Vespertino";
        if (radioNoturno.isSelected()) return "Noturno";
        return "";
    }

    @FXML
    private void voltar(ActionEvent event) {
        // Voltar diretamente ao painel do administrador
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
        }
    }
}
