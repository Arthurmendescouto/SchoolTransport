package org.example.schooltransport.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import org.example.schooltransport.data.Repositorio;
import org.example.schooltransport.model.Aluno;
import org.example.schooltransport.model.Parada;

import java.io.IOException;

/**
 * Controlador da tela "Horários das Paradas" destinada aos responsáveis.
 */
public class HorariosParadaController {

    @FXML private ComboBox<Aluno> comboAlunos;
    @FXML private TableView<Parada> tabelaParadas;
    @FXML private TableColumn<Parada, String> colParada;
    @FXML private TableColumn<Parada, String> colHorario;
    @FXML private Button btnVoltar;               // <-- precisa existir e corresponder ao fx:id

    private final ObservableList<Parada> listaParadas = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        if (Repositorio.getListaParada() != null) {
            listaParadas.setAll(Repositorio.getListaParada());
        }

        if (Repositorio.getListaAluno() != null) {
            comboAlunos.setItems(FXCollections.observableArrayList(Repositorio.getListaAluno()));
        } else {
            comboAlunos.setItems(FXCollections.observableArrayList());
        }

        colParada.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(
                cell.getValue() != null && cell.getValue().getNomeParada() != null ? cell.getValue().getNomeParada() : ""));

        colHorario.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(
                cell.getValue() != null && cell.getValue().getHorarioPrevisto() != null
                        ? cell.getValue().getHorarioPrevisto() : "Não informado"));

        tabelaParadas.setItems(listaParadas);

        comboAlunos.setOnAction(evt -> atualizarTabelaParaAluno(comboAlunos.getValue()));
    }

    private void atualizarTabelaParaAluno(Aluno aluno) {
        if (aluno == null) {
            if (Repositorio.getListaParada() != null) listaParadas.setAll(Repositorio.getListaParada());
            return;
        }

        String paradaDoAluno = null;
        try {
            paradaDoAluno = (String) aluno.getClass().getMethod("getParada").invoke(aluno);
        } catch (Exception e1) {
            try {
                paradaDoAluno = (String) aluno.getClass().getMethod("getNomeParada").invoke(aluno);
            } catch (Exception e2) {
                paradaDoAluno = null;
            }
        }

        if (paradaDoAluno == null || paradaDoAluno.isEmpty()) {
            if (Repositorio.getListaParada() != null) listaParadas.setAll(Repositorio.getListaParada());
            return;
        }

        ObservableList<Parada> ordenada = FXCollections.observableArrayList();

        if (Repositorio.getListaParada() != null) {
            for (Parada p : Repositorio.getListaParada()) {
                if (p.getNomeParada() != null && p.getNomeParada().equalsIgnoreCase(paradaDoAluno)) {
                    ordenada.add(p);
                }
            }
            for (Parada p : Repositorio.getListaParada()) {
                if (!(p.getNomeParada() != null && p.getNomeParada().equalsIgnoreCase(paradaDoAluno))) {
                    ordenada.add(p);
                }
            }
        }

        listaParadas.setAll(ordenada);
        if (!ordenada.isEmpty()) {
            tabelaParadas.getSelectionModel().select(0);
            tabelaParadas.scrollTo(0);
        }
    }

    /**
     * Volta para o painel do responsável.
     */
    @FXML
    private void voltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/schooltransport/painelResponsavel.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao voltar para a tela anterior.").showAndWait();
        }
    }
}
