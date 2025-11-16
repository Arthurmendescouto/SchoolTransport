package org.example.schooltransport.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.schooltransport.data.InMemoryFrequenciaRepositorio;
import org.example.schooltransport.data.Repositorio;
import org.example.schooltransport.model.Aluno;
import org.example.schooltransport.model.Frequencia;
import org.example.schooltransport.service.FrequenciaService;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import javafx.scene.input.MouseEvent;

public class RegistrarFaltasController {

    @FXML private ComboBox<Aluno> comboAlunos;
    @FXML private TableView<Frequencia> tabelaFrequencia;
    @FXML private TableColumn<Frequencia, Long> colId;
    @FXML private TableColumn<Frequencia, String> colAluno;
    @FXML private TableColumn<Frequencia, String> colData;
    @FXML private TableColumn<Frequencia, String> colPresente;

    private FrequenciaService service;
    private ObservableList<Frequencia> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        service = new FrequenciaService(new InMemoryFrequenciaRepositorio());

        // proteger caso Repositorio.getListaAluno() seja null
        if (Repositorio.getListaAluno() != null) {
            comboAlunos.setItems(FXCollections.observableArrayList(Repositorio.getListaAluno()));
        } else {
            comboAlunos.setItems(FXCollections.observableArrayList());
        }

        // Configurar colunas com checks para evitar NPE
        colId.setCellValueFactory(cell -> {
            Frequencia f = cell.getValue();
            return new SimpleObjectProperty<>(f != null ? f.getId() : null);
        });

        colAluno.setCellValueFactory(cell -> {
            Frequencia f = cell.getValue();
            String nome = (f != null && f.getAluno() != null) ? f.getAluno().getNome() : "";
            return new SimpleStringProperty(nome);
        });

        colData.setCellValueFactory(cell -> {
            Frequencia f = cell.getValue();
            String dataStr = "";
            if (f != null && f.getData() != null) {
                dataStr = f.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
            return new SimpleStringProperty(dataStr);
        });

        colPresente.setCellValueFactory(cell -> {
            Frequencia f = cell.getValue();
            return new SimpleStringProperty((f != null && f.isPresente()) ? "Sim" : "Não");
        });

        tabelaFrequencia.setItems(data);

        carregarTabela();
    }

    private void carregarTabela() {
        data.setAll(service.listarTodas());
    }

    @FXML
    public void onRegistrarFalta() {
        Aluno aluno = comboAlunos.getValue();

        if (aluno == null) {
            new Alert(Alert.AlertType.WARNING, "Selecione um aluno!").showAndWait();
            return;
        }

        service.registrarFalta(aluno);
        carregarTabela();

        new Alert(Alert.AlertType.INFORMATION, "Falta registrada com sucesso!").showAndWait();
    }

    // método compatível com onMouseClicked do FXML
    @FXML
    private void voltar(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/example/schooltransport/telaMotorista.fxml")
            );
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) comboAlunos.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,
                    "Erro ao voltar para a tela anterior.").showAndWait();
        }
    }
}
