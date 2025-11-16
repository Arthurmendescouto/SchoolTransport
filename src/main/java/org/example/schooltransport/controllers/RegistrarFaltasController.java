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


/**
 * Controlador responsável pela tela de registro de faltas dos alunos.
 * <p>
 * Permite ao motorista:
 * <ul>
 *     <li>Selecionar um aluno</li>
 *     <li>Registrar falta</li>
 *     <li>Visualizar a tabela de frequências registradas</li>
 * </ul>
 * Esta classe atua como ponte entre a interface gráfica e o serviço de frequência.
 */
public class RegistrarFaltasController {

    /** ComboBox contendo todos os alunos cadastrados no sistema. */
    @FXML private ComboBox<Aluno> comboAlunos;


    /** Tabela onde as frequências registradas são exibidas. */   
    @FXML private TableView<Frequencia> tabelaFrequencia;

    /** Coluna do ID da frequência. */
    @FXML private TableColumn<Frequencia, Long> colId;

    /** Coluna com o nome do aluno. */
    @FXML private TableColumn<Frequencia, String> colAluno;

    /** Coluna com a data da frequência. */
    @FXML private TableColumn<Frequencia, String> colData;

    /** Coluna indicando se o aluno esteve presente. */
    @FXML private TableColumn<Frequencia, String> colPresente;

    
    /** Serviço responsável pela lógica de registro e consulta de frequências. */
    private FrequenciaService service;


    /** Lista observável usada pela tabela. */
    private ObservableList<Frequencia> data = FXCollections.observableArrayList();


    /**
     * Método executado automaticamente ao carregar o FXML.
     * <p>
     * Inicializa o serviço, popula alunos no ComboBox,
     * define as colunas da tabela e carrega as frequências existentes.
     * </p>
     */
    @FXML
    public void initialize() {
        service = new FrequenciaService(new InMemoryFrequenciaRepositorio());

        // Preencher ComboBox com alunos (com proteção contra NPE)
        if (Repositorio.getListaAluno() != null) {
            comboAlunos.setItems(FXCollections.observableArrayList(Repositorio.getListaAluno()));
        } else {
            comboAlunos.setItems(FXCollections.observableArrayList());
        }

        // Configurar colunas com segurança contra valores nulos
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

    /**
     * Atualiza a tabela com todas as frequências armazenadas no sistema.
     * Utiliza o serviço para recuperar os dados.
     */
    private void carregarTabela() {
        data.setAll(service.listarTodas());
    }


    /**
     * Ação executada ao clicar no botão "Registrar Falta".
     * <p>
     * Verifica se um aluno foi selecionado e registra uma falta através do service.
     * Após o registro, recarrega a tabela e exibe uma mensagem de sucesso.
     * </p>
     */
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

    /**
     * Navega de volta para a tela do motorista.
     * Chamado pelo evento de clique no botão "Voltar".
     *
     * @param event Evento de clique do mouse.
     */
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
