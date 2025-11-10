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
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListaParadaController implements Initializable {

    @FXML private VBox listaParadasContainer; // O novo contêiner para os itens dinâmicos
    @FXML private Button btnVoltar;

    private ObservableList<Parada> dados;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dados = Cadastro.getInstance().getListaDeParadas();

        dados.addListener((ListChangeListener<Parada>) change -> {
            construirListaItens();
        });

        construirListaItens();
    }

    /**
     * Constrói e exibe todos os itens da lista dinamicamente (os "cartões").
     */
    private void construirListaItens() {
        // ESSENCIAL: Limpar o contêiner antes de reconstruir a lista
        listaParadasContainer.getChildren().clear();

        if (dados.isEmpty()) {
            Label vazio = new Label("Nenhuma parada pendente. Rota concluída!");
            vazio.setStyle("-fx-font-size: 16px; -fx-padding: 20px;");
            listaParadasContainer.getChildren().add(vazio);
            return;
        }

        // Cria um HBox (o "cartão") para cada parada
        for (Parada parada : dados) {
            HBox itemBox = criarItemParada(parada);
            listaParadasContainer.getChildren().add(itemBox);
        }
    }

    /**
     * Cria o elemento visual (HBox) para uma única parada (o "cartão").
     */
    private HBox criarItemParada(Parada parada) {
        HBox itemBox = new HBox(10);

        // 1. Alinhamento e Estilo
        itemBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        itemBox.getStyleClass().add("parada-item");
        itemBox.setPadding(new Insets(10));
        HBox.setHgrow(itemBox, javafx.scene.layout.Priority.ALWAYS);

        // Label para Nome da Parada (e logradouro)
        Label nomeLabel = new Label(parada.getNomeParada() + " (" + parada.getLogradouro() + ")");
        nomeLabel.getStyleClass().add("parada-nome-texto");

        // 2. ELEMENTO PARA PREENCHER O ESPAÇO (Spacer)
        // Criamos um Pane invisível que se expande o máximo possível, empurrando o botão para a direita.
        javafx.scene.layout.Pane spacer = new javafx.scene.layout.Pane();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS); // Isso faz o espaçador crescer

        // Botão de remoção (Entregue)
        Button entregueBtn = new Button("Remover");
        entregueBtn.getStyleClass().add("btn-entregue-status");

        // Lógica de remoção ao clicar
        entregueBtn.setOnAction(e -> {
            Cadastro.getInstance().removerParada(parada);
        });

        // 3. Adiciona o Label, o Spacer e o Botão
        itemBox.getChildren().addAll(nomeLabel, spacer, entregueBtn);
        return itemBox;
    }

    @FXML
    private void voltar(ActionEvent event) {
        navegarDeTela(event, "cadastrarParada.fxml");
    }

    private void navegarDeTela(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar o arquivo FXML: " + fxmlFile);
        }
    }
}