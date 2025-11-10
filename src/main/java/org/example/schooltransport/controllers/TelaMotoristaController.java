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
import javafx.stage.Stage;

public class TelaMotoristaController implements Initializable {

    // Componentes FXML injetados
    @FXML private Button btnVoltar;
    @FXML private Label labelNomeParada; // Label "Próxima parada:"
    @FXML private HBox containerProximaParada; // O HBox do cartão
    @FXML private Label labelInfoParada; // O Label que mostra o nome da parada (Ex: 1 (3))
    @FXML private Button btnEntregue; // Botão do cartão

    private Parada proximaParada; // Referência à parada atual

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Parada> dados = Cadastro.getInstance().getListaDeParadas();

        // Listener para atualizar a UI quando a lista de paradas muda
        dados.addListener((ListChangeListener<Parada>) change -> {
            exibirProximaParada();
        });

        exibirProximaParada();
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
        } else {
            proximaParada = null;
            labelInfoParada.setText("Nenhuma parada pendente.");
            btnEntregue.setDisable(true);
            // Opcional: Desabilitar o clique no container vazio
            containerProximaParada.setDisable(true);
        }
    }

    // =========================================================
    // AÇÕES DE CLIQUE
    // =========================================================

    /**
     * ✅ Ação do HBox: Redireciona para a lista completa ao clicar no corpo do cartão.
     */
    @FXML
    private void handleAbrirListaParadas(MouseEvent event) {
        // Ignora cliques que vieram do botão "Entregue" para evitar ação dupla/indevida
        if (event.getTarget() instanceof Button) {
            return;
        }
        navegarDeTela((Node)event.getSource(), "listaParadas.fxml");
    }

    /**
     * Ação do Botão "Entregue": Remove o item da lista e avança para o próximo.
     */
    @FXML
    private void handleRemoverProximaParada(ActionEvent event) {
        if (proximaParada != null) {
            // Remove a parada atual da lista
            Cadastro.getInstance().removerParada(proximaParada);
            // O listener já cuidará da atualização da UI.
        }
    }

    /**
     * Ação do Botão "← Voltar".
     */
    @FXML
    private void voltar(ActionEvent event) {
        // Lógica para voltar à tela principal ou menu
        System.out.println("Voltando da Tela Motorista");
        // Exemplo: navegarDeTela(event, "menuPrincipal.fxml");
    }

    // Método auxiliar de navegação
    private void navegarDeTela(Node sourceNode, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            Stage stage = (Stage) sourceNode.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar o arquivo FXML: " + fxmlFile);
        }
    }

    // Sobrecarga do método de navegação para aceitar ActionEvent (para o botão voltar)
    private void navegarDeTela(ActionEvent event, String fxmlFile) {
        navegarDeTela((Node) event.getSource(), fxmlFile);
    }
}