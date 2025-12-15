package org.example.schooltransport.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.scene.Scene;
import org.example.schooltransport.model.Cadastro;
import org.example.schooltransport.model.Parada;
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

/**
 * Controller responsável pela tela do motorista.
 * Exibe próxima parada, progresso da rota e lista de alunos com presença.
 */
public class TelaMotoristaController implements Initializable {

    // --- Componentes FXML existentes ---
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

    @FXML private Button btnReportarPresenca;

    // --- Variáveis de Instância ---
    private String telaDeOrigem; // <-- ADICIONADA DE VOLTA AQUI
    private Parada proximaParada;
    private int totalParadasIniciais = 0;
    private HashMap<CheckBox, Aluno> mapaDePresenca = new HashMap<>();

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

    /**
     * Carrega a lista de alunos com checkbox de presença.
     */
    private void carregarListaAlunos() {
        vboxListaAlunos.getChildren().clear();
        mapaDePresenca.clear();
        ArrayList<Aluno> alunos = Repositorio.getListaAluno();

        if (alunos == null || alunos.isEmpty()) {
            labelListaVazia.setVisible(true);
            scrollPaneAlunos.setVisible(false);
            btnReportarPresenca.setDisable(true);
        } else {
            labelListaVazia.setVisible(false);
            scrollPaneAlunos.setVisible(true);
            btnReportarPresenca.setDisable(false);

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

                mapaDePresenca.put(checkPresenca, aluno);

                checkPresenca.setOnAction(event -> {
                    if (checkPresenca.isSelected()) {
                        System.out.println(aluno.getNome() + " marcado como presente.");
                    } else {
                        System.out.println(aluno.getNome() + " marcado como ausente.");
                        try {
                            String cpfAluno = aluno.getCpf();
                            if (cpfAluno != null && !cpfAluno.isEmpty()) {
                                String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                                String nomeParadaAtual = (proximaParada != null && proximaParada.getNomeParada() != null)
                                        ? (" na parada: " + proximaParada.getNomeParada())
                                        : "";
                                String msg = "Falta registrada em " + dataHora + nomeParadaAtual + ".";
                                Repositorio.adicionarNotificacaoParaCpf(cpfAluno, msg);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });

                itemAluno.getChildren().addAll(nomeAluno, checkPresenca);
                vboxListaAlunos.getChildren().add(itemAluno);
            }
        }
    }

    /**
     * Exibe a próxima parada pendente ou mensagem quando não houver paradas.
     */
    private void exibirProximaParada() {
        ObservableList<Parada> dados = Cadastro.getInstance().getListaDeParadas();

        if (!dados.isEmpty()) {
            proximaParada = dados.get(0);
            labelInfoParada.setText(proximaParada.getNomeParada());
            containerProximaParada.setVisible(true);
            containerProximaParada.setDisable(false);
            btnEntregue.setVisible(true);
            btnEntregue.setManaged(true);
            btnEntregue.setDisable(false);
        } else {
            proximaParada = null;
            labelInfoParada.setText("Nenhuma parada pendente.");
            btnEntregue.setVisible(false);
            btnEntregue.setManaged(false);
            containerProximaParada.setDisable(false);
        }
    }

    /**
     * Atualiza a barra de progresso e contadores de paradas entregues/pendentes.
     */
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
    private void handleReportarPresenca(ActionEvent event) {
        if (mapaDePresenca.isEmpty()) {
            System.out.println("Nenhum aluno para reportar.");
            return;
        }

        System.out.println("--- Relatório de Presença Enviado ---");

        for (Map.Entry<CheckBox, Aluno> entry : mapaDePresenca.entrySet()) {

            Aluno aluno = entry.getValue();
            boolean estaPresente = entry.getKey().isSelected();
            String status = estaPresente ? "Presente" : "Ausente";
            String mensagem = "Relatório de presença: " + aluno.getNome() + " foi marcado(a) como " + status + ".";

            if (aluno.getCpf() != null && !aluno.getCpf().isEmpty()) {
                Repositorio.adicionarNotificacaoParaCpf(aluno.getCpf(), mensagem);
                System.out.println("Notificação enviada para CPF " + aluno.getCpf() + ": " + mensagem);
            } else {
                System.err.println("Aluno " + aluno.getNome() + " sem CPF, notificação falhou.");
            }
        }

        btnReportarPresenca.setDisable(true);
        btnReportarPresenca.setText("Relatório Enviado");
    }

    @FXML
    private void handleAbrirListaParadas(MouseEvent event) {
        Node target = (Node) event.getTarget();
        while (target != null && target != containerProximaParada) {
            if (target == btnEntregue) {
                return;
            }
            target = target.getParent();
        }
        navegarDeTela((Node) event.getSource(), "listaParadas.fxml");
    }

    @FXML
    private void abrirRegistrarFaltas(ActionEvent event) {
        // usa o vbox já injetado como fonte para pegar a Stage via navegarDeTela
        navegarDeTela(vboxListaAlunos, "registrarFaltas.fxml");
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

            // Se estivermos abrindo a lista de paradas a partir da tela do motorista,
            // avise o controller da origem para que o botão 'voltar' retorne corretamente.
            if ("listaParadas.fxml".equals(fxmlFile)) {
                try {
                    Object controller = loader.getController();
                    if (controller instanceof org.example.schooltransport.controllers.ListaParadaController) {
                        ((org.example.schooltransport.controllers.ListaParadaController) controller).setTelaDeOrigem("telaMotorista");
                    }
                } catch (Exception ex) {
                    // Não fatal — continuar sem setar origem
                }
            }

            Stage stage = (Stage) sourceNode.getScene().getWindow();

            // Substitui a cena atual por uma nova com tamanho fixo
            Scene scene = new Scene(root, 390, 700);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navegarDeTela(ActionEvent event, String fxmlFile) {
        navegarDeTela((Node) event.getSource(), fxmlFile);
    }

    // Este é o método que estava causando o erro
    private void setTelaDeOrigem(String telaDeOrigem) {
        this.telaDeOrigem = telaDeOrigem;
    }
}
