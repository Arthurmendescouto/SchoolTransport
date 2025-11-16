package org.example.schooltransport.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.example.schooltransport.data.Repositorio;
import org.example.schooltransport.model.Aluno;
import org.example.schooltransport.model.RegistroPresenca;
import org.example.schooltransport.model.Responsavel;

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
 * Controller para a tela de consulta de faltas do responsável.
 * Exibe todas as faltas dos alunos vinculados ao responsável logado.
 */
public class ConsultarFaltasController implements Initializable {

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

    private String emailResponsavelLogado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Se o email ainda não foi definido pelo LoginController,
        // tenta usar o primeiro responsável como fallback
        if (emailResponsavelLogado == null || emailResponsavelLogado.isEmpty()) {
            identificarResponsavelLogado();
        }
        // Só carrega as faltas se tiver um email válido
        if (emailResponsavelLogado != null && !emailResponsavelLogado.isEmpty()) {
            carregarFaltas();
        }
    }

    /**
     * Identifica qual responsável está logado.
     * Por enquanto, usa o primeiro responsável da lista.
     * Em produção, isso deveria vir de uma sessão de autenticação.
     */
    private void identificarResponsavelLogado() {
        ArrayList<Responsavel> responsaveis = Repositorio.getListaResponsavel();
        if (!responsaveis.isEmpty()) {
            // Por enquanto usa o primeiro responsável, mas aqui tem que mudar para o LoginController ou de uma sessão
            emailResponsavelLogado = responsaveis.get(0).getEmail();
        }
    }

    /*Carrega e exibe todas as faltas dos alunos vinculados ao responsável*/
    private void carregarFaltas() {
        try {
            vboxListaFaltas.getChildren().clear();

            System.out.println("DEBUG: Email responsável logado: " + emailResponsavelLogado);

            // Busca o responsável pelo email
            Responsavel responsavel = buscarResponsavelPorEmail(emailResponsavelLogado);
            
            if (responsavel == null) {
                System.err.println("DEBUG: Responsável não encontrado para email: " + emailResponsavelLogado);
                labelListaVazia.setText("Responsável não encontrado.");
                labelListaVazia.setVisible(true);
                scrollPaneFaltas.setVisible(false);
                labelTotalFaltas.setText("Total de faltas: 0");
                return;
            }

            System.out.println("DEBUG: Responsável encontrado: " + responsavel.getNome());

            // Busca todos os alunos do responsável
            ArrayList<Aluno> alunos = Repositorio.getAlunosPorResponsavel(responsavel.getNome());
            System.out.println("DEBUG: Alunos encontrados: " + alunos.size());
            for (Aluno a : alunos) {
                System.out.println("  - " + a.getNome() + " (Responsável: " + a.getResponsavel() + ")");
            }
            
            if (alunos.isEmpty()) {
                labelListaVazia.setText("Você não possui alunos cadastrados.\n\nPara vincular alunos, cadastre-os informando seu nome como responsável: " + responsavel.getNome());
                labelListaVazia.setVisible(true);
                scrollPaneFaltas.setVisible(false);
                labelTotalFaltas.setText("Total de faltas: 0");
                return;
            }

            // Busca todas as faltas dos alunos do responsável
            ArrayList<RegistroPresenca> faltas = Repositorio.getFaltasPorResponsavel(responsavel.getNome());
            System.out.println("DEBUG: Total de faltas encontradas: " + faltas.size());

            // Conta total de faltas
            int totalFaltas = faltas.size();
            labelTotalFaltas.setText("Total de faltas: " + totalFaltas);

            if (faltas.isEmpty()) {
                labelListaVazia.setText("Nenhuma falta registrada até o momento.\n\nAs faltas aparecerão aqui quando o motorista marcar alunos como ausentes.");
                labelListaVazia.setVisible(true);
                scrollPaneFaltas.setVisible(false);
                return;
            }

            // Exibe as faltas
            labelListaVazia.setVisible(false);
            scrollPaneFaltas.setVisible(true);

            // Agrupa faltas por aluno para melhor visualização
            for (Aluno aluno : alunos) {
                ArrayList<RegistroPresenca> faltasAluno = Repositorio.getFaltasPorAluno(aluno);
                System.out.println("DEBUG: Faltas do aluno " + aluno.getNome() + ": " + faltasAluno.size());
                
                if (!faltasAluno.isEmpty()) {
                    // Cria um card para cada aluno com suas faltas
                    VBox cardAluno = criarCardAluno(aluno, faltasAluno);
                    vboxListaFaltas.getChildren().add(cardAluno);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("DEBUG: Erro ao carregar faltas: " + e.getMessage());
            e.printStackTrace();
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
        VBox card = new VBox(10);
        card.setAlignment(Pos.TOP_LEFT);
        card.getStyleClass().add("card-falta");
        card.setPadding(new Insets(15));
        card.setSpacing(8);

        // Nome do aluno
        Label labelNomeAluno = new Label("Aluno: " + aluno.getNome());
        labelNomeAluno.getStyleClass().add("nome-aluno-falta");
        card.getChildren().add(labelNomeAluno);

        // Quantidade de faltas
        Label labelQtdFaltas = new Label("Faltas: " + faltas.size());
        labelQtdFaltas.getStyleClass().add("qtd-faltas");
        card.getChildren().add(labelQtdFaltas);

        // Lista de datas das faltas
        VBox vboxDatas = new VBox(5);
        vboxDatas.setPadding(new Insets(5, 0, 0, 15));
        
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

    /**
     * Busca um responsável pelo email.
     * 
     * @param email Email do responsável
     * @return Responsável encontrado ou null
     */
    private Responsavel buscarResponsavelPorEmail(String email) {
        if (email == null) return null;
        
        for (Responsavel responsavel : Repositorio.getListaResponsavel()) {
            if (responsavel.getEmail().equals(email)) {
                return responsavel;
            }
        }
        return null;
    }

    /**
     * Define o email do responsável logado.
     * Este método pode ser chamado pelo LoginController para passar o responsável logado.
     * 
     * @param email Email do responsável logado
     */
    public void setEmailResponsavelLogado(String email) {
        this.emailResponsavelLogado = email;
        carregarFaltas();
    }

    @FXML
    private void voltar(ActionEvent event) {
        navegarDeTela(event, "login.fxml");
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

