package org.example.schooltransport.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.example.schooltransport.Cadastro;
import org.example.schooltransport.Parada;
import org.example.schooltransport.data.Repositorio;
import org.example.schooltransport.model.Aluno;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TelaMotoristaController implements Initializable {

    @FXML private Button btnVoltar;
    @FXML private Label labelNomeParada;
    @FXML private HBox containerProximaParada;
    @FXML private Label labelInfoParada;
    @FXML private Button btnEntregue;
    @FXML private Label labelContagemTotal;
    @FXML private Label labelEntregues;
    @FXML private Label labelPendentes;
    @FXML private Pane barraProgressoPreenchida;
    @FXML private VBox containerProgresso;

    @FXML private StackPane stackPaneAlunos;
    @FXML private Label labelListaVazia;
    @FXML private ScrollPane scrollPaneAlunos;
    @FXML private VBox vboxListaAlunos;

    private String telaDeOrigem;
    private Parada proximaParada;
    private int totalParadasIniciais = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Parada> dados = Cadastro.getInstance().getListaDeParadas();
        totalParadasIniciais = dados.size();

        dados.addListener((ListChangeListener<Parada>) change -> {
            exibirProximaParada();
            atualizarProgresso();
        });

        exibirProximaParada();
        atualizarProgresso();
        carregarListaAlunos();
    }

    private void carregarListaAlunos() {
        vboxListaAlunos.getChildren().clear();
        ArrayList<Aluno> alunos = Repositorio.getListaAluno();

        if (alunos == null || alunos.isEmpty()) {
            labelListaVazia.setVisible(true);
            scrollPaneAlunos.setVisible(false);
        } else {
            labelListaVazia.setVisible(false);
            scrollPaneAlunos.setVisible(true);

            for (Aluno aluno : alunos) {
                HBox itemAluno = new HBox(10);
                itemAluno.setAlignment(Pos.CENTER_LEFT);
                itemAluno.getStyleClass().add("item-aluno");

                Label nomeAluno = new Label(aluno.getNome());
                HBox.setHgrow(nomeAluno, Priority.ALWAYS);
                nomeAluno.setMaxWidth(Double.MAX_VALUE);
                nomeAluno.getStyleClass().add("nome-aluno-item");

                CheckBox checkPresenca = new CheckBox("Presente");
                checkPresenca.getStyleClass().add("check-presenca");

                checkPresenca.setOnAction(event -> {
                    if (checkPresenca.isSelected()) {
                        System.out.println(aluno.getNome() + " marcado como presente.");
                    } else {
                        System.out.println(aluno.getNome() + " marcado como ausente.");
                    }
                });

                itemAluno.getChildren().addAll(nomeAluno, checkPresenca);
                vboxListaAlunos.getChildren().add(itemAluno);
            }
        }
    }

    private void exibirProximaParada() {
        ObservableList<Parada> dados = Cadastro.getInstance().getListaDeParadas();

        if (!dados.isEmpty()) {
            proximaParada = dados.get(0);
            labelInfoParada.setText(proximaParada.getNomeParada());

            containerProximaParada.setVisible(true);
            containerProximaParada.setDisable(false);

            // ✔ mostra botão
            btnEntregue.setVisible(true);
            btnEntregue.setManaged(true);
            btnEntregue.setDisable(false);

        } else {
            proximaParada = null;
            labelInfoParada.setText("Nenhuma parada pendente.");

            // ❌ botão não aparece e não ocupa espaço
            btnEntregue.setVisible(false);
            btnEntregue.setManaged(false);

            containerProximaParada.setDisable(true);
        }
    }

    private void atualizarProgresso() {
        ObservableList<Parada> dados = Cadastro.getInstance().getListaDeParadas();

        if (totalParadasIniciais == 0) {
            labelContagemTotal.setText("0/0");
            labelEntregues.setText("0");
            labelPendentes.setText("0");
            if (barraProgressoPreenchida != null) barraProgressoPreenchida.setPrefWidth(0);
            return;
        }

        int pendentes = dados.size();
        int entregues = totalParadasIniciais - pendentes;

        labelContagemTotal.setText(entregues + "/" + totalParadasIniciais);
        labelEntregues.setText(String.valueOf(entregues));
        labelPendentes.setText(String.valueOf(pendentes));

        if (barraProgressoPreenchida != null) {
            double progressoPercentual = (double) entregues / totalParadasIniciais;
            javafx.application.Platform.runLater(() -> {
                double larguraBase = barraProgressoPreenchida.getParent().getBoundsInLocal().getWidth();
                barraProgressoPreenchida.setPrefWidth(larguraBase * progressoPercentual);
            });
        }
    }

    @FXML
    private void handleAbrirListaParadas(MouseEvent event) {
        if (event.getTarget() instanceof Button) return;
        navegarDeTela((Node) event.getSource(), "listaParadas.fxml");
    }

    @FXML
    private void handleRemoverProximaParada(ActionEvent event) {
        if (proximaParada == null) {
            event.consume();
            return;
        }

        Cadastro.getInstance().removerParada(proximaParada);
        event.consume();
    }

    @FXML
    private void voltar(ActionEvent event) {
        navegarDeTela(event, "login.fxml");
    }

    private void navegarDeTela(Node sourceNode, String fxmlFile) {
        try {
            String caminhoAbsoluto = "/org/example/schooltransport/" + fxmlFile;
            URL resourceUrl = getClass().getResource(caminhoAbsoluto);

            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Parent root = loader.load();
            Stage stage = (Stage) sourceNode.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navegarDeTela(ActionEvent event, String fxmlFile) {
        navegarDeTela((Node) event.getSource(), fxmlFile);
    }

    private void setTelaDeOrigem(String telaDeOrigem) {
        this.telaDeOrigem = telaDeOrigem;
    }
}
