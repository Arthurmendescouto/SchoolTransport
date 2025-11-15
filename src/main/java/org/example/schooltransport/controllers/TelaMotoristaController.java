package org.example.schooltransport.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.example.schooltransport.Cadastro;
import org.example.schooltransport.Parada;

import javafx.collections.ListChangeListener;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TelaMotoristaController implements Initializable {

    // Componentes FXML injetados
    @FXML private Button btnVoltar;
    @FXML private Label labelNomeParada;
    @FXML private HBox containerProximaParada;
    @FXML private Label labelInfoParada;
    @FXML private Button btnEntregue;

    // ✅ NOVOS COMPONENTES DO PROGRESSO
    @FXML private Label labelContagemTotal;
    @FXML private Label labelEntregues;
    @FXML private Label labelPendentes;
    @FXML private Pane barraProgressoPreenchida;
    @FXML private VBox containerProgresso; // Container principal do progresso
    private String telaDeOrigem;

    private Parada proximaParada;
    private int totalParadasIniciais = 0; // Armazena o total inicial para o cálculo percentual

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

        // 3. Chamadas iniciais
        exibirProximaParada();
        atualizarProgresso();
    }

    /**
     * Atualiza o cartão para exibir a próxima parada na lista.
     */
    private void exibirProximaParada() {
        ObservableList<Parada> dados = Cadastro.getInstance().getListaDeParadas();

        if (!dados.isEmpty()) {
            proximaParada = dados.get(0);

            // Exibe o nome da parada cadastrada
            labelInfoParada.setText(proximaParada.getNomeParada());

            containerProximaParada.setVisible(true);
            btnEntregue.setDisable(false);
            containerProximaParada.setDisable(false); // Garante que o clique funcione
        } else {
            proximaParada = null;
            labelInfoParada.setText("Nenhuma parada pendente.");
            btnEntregue.setDisable(true);
            // Manter o container habilitado para permitir o clique (navegar para lista vazia)
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
            // Verifica se barraProgressoPreenchida existe antes de usar
            if (barraProgressoPreenchida != null) {
                barraProgressoPreenchida.setPrefWidth(0);
            }
            return;
        }

        int pendentes = dados.size();
        int entregues = totalParadasIniciais - pendentes;

        // 1. Atualiza Labels de contagem (ex: 2/7)
        labelContagemTotal.setText(entregues + "/" + totalParadasIniciais);
        labelEntregues.setText(String.valueOf(entregues));
        labelPendentes.setText(String.valueOf(pendentes));

        // 2. Atualiza a barra percentual (se existir no FXML)
        if (barraProgressoPreenchida != null) {
            double progressoPercentual = (double) entregues / totalParadasIniciais;

            // Usa Platform.runLater para garantir que o cálculo de largura ocorra após o layout
            javafx.application.Platform.runLater(() -> {
                // Verifica novamente se ainda existe e se tem um pai
                if (barraProgressoPreenchida != null && barraProgressoPreenchida.getParent() != null) {
                    // Pega a largura do StackPane pai da barra preenchida
                    double larguraBase = barraProgressoPreenchida.getParent().getBoundsInLocal().getWidth();
                    double novaLargura = larguraBase * progressoPercentual;

                    barraProgressoPreenchida.setPrefWidth(novaLargura);
                }
            });
        }
    }

    // =========================================================
    // AÇÕES DE CLIQUE
    // =========================================================

    /**
     * Ação do HBox: Redireciona para a lista completa ao clicar no corpo do cartão.
     */
    @FXML
    private void handleAbrirListaParadas(MouseEvent event) {
        if (event.getTarget() instanceof Button) {
            return; // Ignora o clique se veio do botão "Entregue"
        }
        navegarDeTela((Node)event.getSource(), "listaParadas.fxml");
    }

    /**
     * Ação do Botão "Entregue": Remove o item da lista e avança para o próximo.
     */
    @FXML
    private void handleRemoverProximaParada(ActionEvent event) {
        if (proximaParada != null) {
            Cadastro.getInstance().removerParada(proximaParada);
            // O listener irá atualizar o progresso e a próxima parada
        }
    }

    /**
     * Ação do Botão "← Voltar".
     */
    @FXML
    private void voltar(ActionEvent event) {
        // Lógica para voltar à tela principal ou menu
        System.out.println("Voltando da Tela Motorista");
        navegarDeTela(event, "login.fxml");
    }

    // Método auxiliar de navegação
    private void navegarDeTela(Node sourceNode, String fxmlFile) {
        try {
            // Construímos um caminho absoluto a partir da raiz (note o "/" no início)
            String caminhoAbsoluto = "/org/example/schooltransport/" + fxmlFile;

            // Obtém o URL do recurso a partir do caminho absoluto
            URL resourceUrl = getClass().getResource(caminhoAbsoluto);

            if (resourceUrl == null) {
                // Se isso falhar agora, o nome do arquivo em 'fxmlFile' está errado
                System.err.println("FATAL: Não foi possível encontrar o FXML em: " + caminhoAbsoluto);
                System.err.println("Verifique se o nome do arquivo '" + fxmlFile + "' está digitado corretamente.");
                return;
            }

            // Carrega o FXML
            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Parent root = loader.load();

            // Obtém o Stage (janela)
            Stage stage = (Stage) sourceNode.getScene().getWindow();

            // Define a nova cena
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar o FXML: " + fxmlFile);
            System.err.println(">>> SE O ERRO FOR 'ClassNotFoundException', VOCÊ NÃO CORRIGIU O 'fx:controller' DENTRO DO FXML! <<<");
        }
    }

    // Sobrecarga do método de navegação para aceitar ActionEvent
    private void navegarDeTela(ActionEvent event, String fxmlFile) {
        navegarDeTela((Node) event.getSource(), fxmlFile);
    }

    private void setTelaDeOrigem(String telaDeOrigem) {
        this.telaDeOrigem = telaDeOrigem;
    }
}