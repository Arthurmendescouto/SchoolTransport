package org.example.schooltransport.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList; // NOVO import
import java.util.ResourceBundle;

import org.example.schooltransport.Cadastro;
import org.example.schooltransport.Parada;
import org.example.schooltransport.data.Repositorio; // NOVO import
import org.example.schooltransport.model.Aluno; // NOVO import

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos; // NOVO import
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox; // NOVO import
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane; // NOVO import
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority; // NOVO import
import javafx.scene.layout.StackPane; // NOVO import
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TelaMotoristaController implements Initializable {

    // Componentes FXML (Paradas e Progresso - Existentes)
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

    // --- INÍCIO: NOVOS COMPONENTES FXML (Lista de Alunos) ---
    @FXML private StackPane stackPaneAlunos;
    @FXML private Label labelListaVazia;
    @FXML private ScrollPane scrollPaneAlunos;
    @FXML private VBox vboxListaAlunos;
    // --- FIM: NOVOS COMPONENTES FXML ---

    private String telaDeOrigem;
    private Parada proximaParada;
    private int totalParadasIniciais = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Parada> dados = Cadastro.getInstance().getListaDeParadas();

        // 1. Inicializa o total de paradas (deve ser feito apenas uma vez)
        totalParadasIniciais = dados.size();

        // 2. Listener para atualizar a UI quando a lista de paradas muda
        dados.addListener((ListChangeListener<Parada>) change -> {
            exibirProximaParada();
            atualizarProgresso();
        });

        // 3. Chamadas iniciais (Lógica de Paradas/Progresso)
        exibirProximaParada();
        atualizarProgresso();

        // 4. ✅ NOVO: Carregar a lista de alunos
        carregarListaAlunos();
    }

    // =========================================================
    // ✅ NOVO MÉTODO: LÓGICA DA LISTA DE ALUNOS
    // =========================================================

    /**
     * Carrega a lista de alunos do Repositório e preenche o VBox.
     * Mostra uma mensagem se a lista estiver vazia.
     */
    private void carregarListaAlunos() {
        vboxListaAlunos.getChildren().clear(); // Limpa a lista antes de adicionar

        // Pega a lista de alunos do seu repositório
        // (O mesmo repositório que CadastrarAlunoController usa)
        ArrayList<Aluno> alunos = Repositorio.getListaAluno();

        if (alunos == null || alunos.isEmpty()) {
            // Mostra a mensagem de lista vazia e esconde o scroll
            labelListaVazia.setVisible(true);
            scrollPaneAlunos.setVisible(false);
        } else {
            // Esconde a mensagem e mostra o scroll
            labelListaVazia.setVisible(false);
            scrollPaneAlunos.setVisible(true);

            // Cria um item para cada aluno
            for (Aluno aluno : alunos) {
                // Cria o HBox para o item
                HBox itemAluno = new HBox(10); // 10px de espaçamento
                itemAluno.setAlignment(Pos.CENTER_LEFT);
                itemAluno.getStyleClass().add("item-aluno"); // Aplica o estilo CSS

                // Cria o Label com o nome
                Label nomeAluno = new Label(aluno.getNome());
                HBox.setHgrow(nomeAluno, Priority.ALWAYS); // Faz o nome ocupar o espaço
                nomeAluno.setMaxWidth(Double.MAX_VALUE);
                nomeAluno.getStyleClass().add("nome-aluno-item"); // Estilo CSS

                // Cria o CheckBox
                CheckBox checkPresenca = new CheckBox("Presente");
                checkPresenca.getStyleClass().add("check-presenca"); // Estilo CSS

                // (Opcional) Adicionar lógica ao clicar no checkbox
                checkPresenca.setOnAction(event -> {
                    if (checkPresenca.isSelected()) {
                        System.out.println(aluno.getNome() + " marcado como presente.");
                        // Aqui você pode atualizar o status do aluno, contagem de progresso, etc.
                    } else {
                        System.out.println(aluno.getNome() + " marcado como ausente.");
                    }
                });

                // Adiciona o nome e o checkbox ao HBox
                itemAluno.getChildren().addAll(nomeAluno, checkPresenca);

                // Adiciona o HBox do aluno ao VBox principal da lista
                vboxListaAlunos.getChildren().add(itemAluno);
            }
        }
    }

    // =========================================================
    // MÉTODOS EXISTENTES: LÓGICA DE PARADAS E PROGRESSO
    // =========================================================

    /**
     * Atualiza o cartão para exibir a próxima parada na lista.
     */
    private void exibirProximaParada() {
        ObservableList<Parada> dados = Cadastro.getInstance().getListaDeParadas();

        if (!dados.isEmpty()) {
            proximaParada = dados.get(0);
            labelInfoParada.setText(proximaParada.getNomeParada());
            containerProximaParada.setVisible(true);
            btnEntregue.setDisable(false);
            containerProximaParada.setDisable(false);
        } else {
            proximaParada = null;
            labelInfoParada.setText("Nenhuma parada pendente.");
            btnEntregue.setDisable(true);
            containerProximaParada.setDisable(false);
        }
    }

    /**
     * Calcula e atualiza a barra de progresso, contadores (ativos/pendentes) e o texto de contagem (2/7).
     */
    private void atualizarProgresso() {
        ObservableList<Parada> dados = Cadastro.getInstance().getListaDeParadas();

        if (totalParadasIniciais == 0) {
            labelContagemTotal.setText("0/0");
            labelEntregues.setText("0");
            labelPendentes.setText("0");
            if (barraProgressoPreenchida != null) {
                barraProgressoPreenchida.setPrefWidth(0);
            }
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
                if (barraProgressoPreenchida != null && barraProgressoPreenchida.getParent() != null) {
                    double larguraBase = barraProgressoPreenchida.getParent().getBoundsInLocal().getWidth();
                    double novaLargura = larguraBase * progressoPercentual;
                    barraProgressoPreenchida.setPrefWidth(novaLargura);
                }
            });
        }
    }

    // =========================================================
    // AÇÕES DE CLIQUE E NAVEGAÇÃO (EXISTENTES)
    // =========================================================

    @FXML
    private void handleAbrirListaParadas(MouseEvent event) {
        if (event.getTarget() instanceof Button) {
            return;
        }
        navegarDeTela((Node)event.getSource(), "listaParadas.fxml");
    }

    @FXML
    private void handleRemoverProximaParada(ActionEvent event) {
        if (proximaParada != null) {
            Cadastro.getInstance().removerParada(proximaParada);
        }
    }

    @FXML
    private void voltar(ActionEvent event) {
        System.out.println("Voltando da Tela Motorista");
        navegarDeTela(event, "login.fxml");
    }

    private void navegarDeTela(Node sourceNode, String fxmlFile) {
        try {
            String caminhoAbsoluto = "/org/example/schooltransport/" + fxmlFile;
            URL resourceUrl = getClass().getResource(caminhoAbsoluto);

            if (resourceUrl == null) {
                System.err.println("FATAL: Não foi possível encontrar o FXML em: " + caminhoAbsoluto);
                return;
            }

            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Parent root = loader.load();
            Stage stage = (Stage) sourceNode.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar o FXML: " + fxmlFile);
        }
    }

    private void navegarDeTela(ActionEvent event, String fxmlFile) {
        navegarDeTela((Node) event.getSource(), fxmlFile);
    }

    private void setTelaDeOrigem(String telaDeOrigem) {
        this.telaDeOrigem = telaDeOrigem;
    }
}