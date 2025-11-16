package org.example.schooltransport.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.example.schooltransport.data.Repositorio;
<<<<<<< Updated upstream
import org.example.schooltransport.model.Aluno;
import org.example.schooltransport.model.RegistroPresenca;
import org.example.schooltransport.model.Responsavel;
=======
import org.example.schooltransport.data.InMemoryFrequenciaRepositorio;
import org.example.schooltransport.model.Aluno;
import org.example.schooltransport.model.Responsavel;
import org.example.schooltransport.model.Frequencia;
import java.time.format.DateTimeFormatter;
>>>>>>> Stashed changes

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
<<<<<<< Updated upstream

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Se o email ainda não foi definido pelo LoginController,
        // tenta usar o primeiro responsável como fallback
=======
    private InMemoryFrequenciaRepositorio frequenciaRepo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializa o repositório de frequências
        frequenciaRepo = new InMemoryFrequenciaRepositorio();
        
        // Se o email ainda não foi definido pelo LoginController,
        // tenta usar o CPF da sessão ou o primeiro responsável como fallback
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
     * Por enquanto, usa o primeiro responsável da lista.
     * Em produção, isso deveria vir de uma sessão de autenticação.
     */
    private void identificarResponsavelLogado() {
        ArrayList<Responsavel> responsaveis = Repositorio.getListaResponsavel();
        if (!responsaveis.isEmpty()) {
            // Por enquanto usa o primeiro responsável, mas aqui tem que mudar para o LoginController ou de uma sessão
=======
     * Tenta usar o CPF da sessão para encontrar o responsável.
     * Se não encontrar, usa o primeiro responsável da lista como fallback.
     */
    private void identificarResponsavelLogado() {
        String cpfLogado = Repositorio.getCurrentUserCpf();
        if (cpfLogado != null && !cpfLogado.isEmpty()) {
            // Busca responsável pelo CPF da sessão
            for (Responsavel r : Repositorio.getListaResponsavel()) {
                if (r != null && cpfLogado.equals(r.getCpf())) {
                    emailResponsavelLogado = r.getEmail();
                    return;
                }
            }
        }
        
        // Fallback: usa o primeiro responsável da lista
        ArrayList<Responsavel> responsaveis = Repositorio.getListaResponsavel();
        if (!responsaveis.isEmpty()) {
>>>>>>> Stashed changes
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

            System.out.println("DEBUG: Responsável encontrado: " + responsavel.getNome() + " (CPF: " + responsavel.getCpf() + ")");

            // Busca todos os alunos do responsável usando o CPF
<<<<<<< Updated upstream
            ArrayList<Aluno> alunos = Repositorio.getAlunosPorResponsavel(responsavel.getCpf());
=======
            ArrayList<Aluno> alunos = getAlunosPorResponsavel(responsavel.getCpf());
>>>>>>> Stashed changes
            System.out.println("DEBUG: Alunos encontrados: " + alunos.size());
            for (Aluno a : alunos) {
                System.out.println("  - " + a.getNome() + " (Responsável CPF: " + a.getResponsavel() + ")");
            }
            
            if (alunos.isEmpty()) {
                labelListaVazia.setText("Você não possui alunos cadastrados.\n\nPara vincular alunos, cadastre-os informando seu CPF como responsável: " + responsavel.getCpf());
                labelListaVazia.setVisible(true);
                scrollPaneFaltas.setVisible(false);
                labelTotalFaltas.setText("Total de faltas: 0");
                return;
            }

            // Busca todas as faltas dos alunos do responsável usando o CPF
<<<<<<< Updated upstream
            ArrayList<RegistroPresenca> faltas = Repositorio.getFaltasPorResponsavel(responsavel.getCpf());
=======
            ArrayList<Frequencia> faltas = getFaltasPorResponsavel(responsavel.getCpf());
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
                ArrayList<RegistroPresenca> faltasAluno = Repositorio.getFaltasPorAluno(aluno);
=======
                ArrayList<Frequencia> faltasAluno = getFaltasPorAluno(aluno);
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
    private VBox criarCardAluno(Aluno aluno, ArrayList<RegistroPresenca> faltas) {
=======
    private VBox criarCardAluno(Aluno aluno, ArrayList<Frequencia> faltas) {
>>>>>>> Stashed changes
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
        
<<<<<<< Updated upstream
        for (RegistroPresenca falta : faltas) {
=======
        for (Frequencia falta : faltas) {
>>>>>>> Stashed changes
            if (falta == null) continue;
            HBox itemData = new HBox(10);
            itemData.setAlignment(Pos.CENTER_LEFT);
            
            String dataFormatada = "Data não disponível";
            try {
                if (falta.getData() != null) {
<<<<<<< Updated upstream
                    dataFormatada = falta.getDataFormatada();
=======
                    dataFormatada = falta.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
            if (responsavel.getEmail().equals(email)) {
=======
            if (responsavel != null && responsavel.getEmail() != null && responsavel.getEmail().equals(email)) {
>>>>>>> Stashed changes
                return responsavel;
            }
        }
        return null;
    }

    /**
<<<<<<< Updated upstream
=======
     * Busca alunos vinculados a um responsável pelo CPF.
     * 
     * @param cpfResponsavel CPF do responsável
     * @return Lista de alunos vinculados ao responsável
     */
    private ArrayList<Aluno> getAlunosPorResponsavel(String cpfResponsavel) {
        ArrayList<Aluno> alunos = new ArrayList<>();
        if (cpfResponsavel == null || cpfResponsavel.isEmpty()) {
            return alunos;
        }

        // Busca alunos pelo CPF do responsável
        for (Aluno aluno : Repositorio.getListaAluno()) {
            if (aluno != null && cpfResponsavel.equals(aluno.getResponsavel())) {
                alunos.add(aluno);
            }
        }

        return alunos;
    }

    /**
     * Busca faltas de um aluno específico (frequências onde presente = false).
     * 
     * @param aluno O aluno
     * @return Lista de faltas do aluno
     */
    private ArrayList<Frequencia> getFaltasPorAluno(Aluno aluno) {
        ArrayList<Frequencia> faltas = new ArrayList<>();
        if (aluno == null || frequenciaRepo == null) {
            return faltas;
        }

        // Busca frequências do aluno pelo CPF
        java.util.List<Frequencia> frequencias = frequenciaRepo.listarPorAlunoCpf(aluno.getCpf());
        
        // Filtra apenas as faltas (presente = false)
        for (Frequencia freq : frequencias) {
            if (freq != null && !freq.isPresente()) {
                faltas.add(freq);
            }
        }

        return faltas;
    }

    /**
     * Busca faltas de todos os alunos vinculados a um responsável.
     * 
     * @param cpfResponsavel CPF do responsável
     * @return Lista de faltas dos alunos do responsável
     */
    private ArrayList<Frequencia> getFaltasPorResponsavel(String cpfResponsavel) {
        ArrayList<Frequencia> faltas = new ArrayList<>();
        if (cpfResponsavel == null || cpfResponsavel.isEmpty()) {
            return faltas;
        }

        // Busca os alunos do responsável
        ArrayList<Aluno> alunos = getAlunosPorResponsavel(cpfResponsavel);
        
        // Para cada aluno, busca suas faltas
        for (Aluno aluno : alunos) {
            faltas.addAll(getFaltasPorAluno(aluno));
        }

        return faltas;
    }

    /**
>>>>>>> Stashed changes
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

