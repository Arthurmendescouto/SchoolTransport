package org.example.schooltransport.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.example.schooltransport.data.Repositorio;
import org.example.schooltransport.model.Aluno;
import org.example.schooltransport.model.RegistroPresenca;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller para a tela de consulta de faltas do administrador, ela exibe todas as faltas de todos os alunos do sistema
 */
public class ConsultarFaltasAdminController implements Initializable {

    @FXML
    private Button btnVoltar;

    @FXML
    private Label labelTitulo;

    @FXML
    private Label labelTotalFaltas;

    @FXML
    private Label labelListaVazia;

    @FXML
    private ScrollPane scrollPaneFaltas;

    @FXML
    private VBox vboxListaFaltas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Aguarda um pouco para garantir que todos os componentes FXML foram inicializados
            javafx.application.Platform.runLater(() -> {
                carregarTodasFaltas();
            });
        } catch (Exception e) {
            System.err.println("Erro no initialize: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carrega e exibe todas as faltas de todos os alunos do sistema.
     */
    private void carregarTodasFaltas() {
        try {
            // Verifica se os componentes foram inicializados
            if (vboxListaFaltas == null || labelTotalFaltas == null || labelListaVazia == null || scrollPaneFaltas == null) {
                System.err.println("DEBUG: Componentes FXML não foram inicializados corretamente");
                return;
            }
            
            vboxListaFaltas.getChildren().clear();

            // Busca todas as faltas do sistema
            ArrayList<RegistroPresenca> todasFaltas = Repositorio.getTodasFaltas();
            System.out.println("DEBUG: Total de faltas no sistema: " + todasFaltas.size());

            // Busca todos os alunos
            ArrayList<Aluno> todosAlunos = Repositorio.getTodosAlunos();
            System.out.println("DEBUG: Total de alunos no sistema: " + todosAlunos.size());

            if (todasFaltas.isEmpty()) {
                labelListaVazia.setText("Nenhuma falta registrada até o momento.\n\nAs faltas aparecerão aqui quando o motorista marcar alunos como ausentes.");
                labelListaVazia.setVisible(true);
                scrollPaneFaltas.setVisible(false);
                labelTotalFaltas.setText("Total de faltas: 0");
                return;
            }

            // Conta total de faltas
            int totalFaltas = todasFaltas.size();
            labelTotalFaltas.setText("Total de faltas: " + totalFaltas);

            // Agrupa faltas por aluno usando um mapa
            Map<String, ArrayList<RegistroPresenca>> faltasPorAluno = new HashMap<>();
            
            for (RegistroPresenca falta : todasFaltas) {
                if (falta == null || falta.getAluno() == null) continue;
                String nomeAluno = falta.getAluno().getNome();
                faltasPorAluno.computeIfAbsent(nomeAluno, k -> new ArrayList<>()).add(falta);
            }

            // Exibe as faltas
            labelListaVazia.setVisible(false);
            scrollPaneFaltas.setVisible(true);

            // Cria cards para cada aluno que tem faltas
            for (Aluno aluno : todosAlunos) {
                if (aluno == null) continue;
                String nomeAluno = aluno.getNome();
                ArrayList<RegistroPresenca> faltasAluno = faltasPorAluno.get(nomeAluno);
                
                if (faltasAluno != null && !faltasAluno.isEmpty()) {
                    // Cria um card para cada aluno com suas faltas
                    VBox cardAluno = criarCardAluno(aluno, faltasAluno);
                    vboxListaFaltas.getChildren().add(cardAluno);
                }
            }

            // não tendo cards criados, mostra mensagem
            if (vboxListaFaltas.getChildren().isEmpty()) {
                labelListaVazia.setText("Nenhuma falta encontrada para exibir.");
                labelListaVazia.setVisible(true);
                scrollPaneFaltas.setVisible(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("DEBUG: Erro ao carregar faltas: " + e.getMessage());
            labelListaVazia.setText("Erro ao carregar faltas: " + e.getMessage() + "\n\nVerifique o console para mais detalhes.");
            labelListaVazia.setVisible(true);
            scrollPaneFaltas.setVisible(false);
            labelTotalFaltas.setText("Total de faltas: 0");
        }
    }

    /**
     * Cria um card visual para exibir as faltas de um aluno.
     * 
     * @param aluno O aluno
     * @param faltas Lista de faltas do aluno
     * @return VBox com o card do aluno
     */
    private VBox criarCardAluno(Aluno aluno, ArrayList<RegistroPresenca> faltas) {
        VBox card = new VBox(8);
        card.setAlignment(Pos.TOP_LEFT);
        card.getStyleClass().add("card-falta");
        card.setPadding(new Insets(15));
        card.setSpacing(8);

        // Nome do aluno
        Label labelNomeAluno = new Label("Aluno: " + aluno.getNome());
        labelNomeAluno.getStyleClass().add("nome-aluno-falta");
        card.getChildren().add(labelNomeAluno);

        // Responsável do aluno
        Label labelResponsavel = new Label("Responsável: " + (aluno.getResponsavel() != null ? aluno.getResponsavel() : "Não informado"));
        labelResponsavel.getStyleClass().add("label-campo");
        labelResponsavel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666666;");
        card.getChildren().add(labelResponsavel);

        // Quantidade de faltas
        Label labelQtdFaltas = new Label("Faltas: " + faltas.size());
        labelQtdFaltas.getStyleClass().add("qtd-faltas");
        card.getChildren().add(labelQtdFaltas);

        // Lista de datas das faltas
        VBox vboxDatas = new VBox(5);
        vboxDatas.setPadding(new Insets(5, 0, 0, 10));
        
        for (RegistroPresenca falta : faltas) {
            if (falta == null) continue;
            HBox itemData = new HBox(10);
            itemData.setAlignment(Pos.CENTER_LEFT);
            
            String dataFormatada = "Data não disponível";
            try {
                if (falta.getData() != null) {
                    dataFormatada = falta.getDataFormatada();
                }
            } catch (Exception e) {
                System.err.println("Erro ao formatar data: " + e.getMessage());
            }
            
            Label labelData = new Label("• " + dataFormatada);
            labelData.getStyleClass().add("data-falta");
            
            itemData.getChildren().add(labelData);
            vboxDatas.getChildren().add(itemData);
        }
        
        card.getChildren().add(vboxDatas);

        return card;
    }

    @FXML
    private void voltar(ActionEvent event) {
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

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar o FXML: " + fxmlFile);
        }
    }
}

