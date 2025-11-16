package org.example.schooltransport.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.example.schooltransport.data.Repositorio;
import org.example.schooltransport.model.Notificacao;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * Controller responsável pela tela de consulta de notificações.
 * Exibe notificações dos alunos vinculados ao responsável logado.
 */
public class ConsultarNotificacoesController implements Initializable {

    @FXML private Button removerBtn;
    @FXML private Button voltarBtn;
    @FXML private ListView<String> listViewNotificacoes;
    @FXML private Label mensagemStatus;

    // Guarda a origem de cada item exibido: par[0]=cpfAluno, par[1]=mensagem
    private java.util.List<java.util.Map.Entry<String,String>> origem = new java.util.ArrayList<>();

    /**
     * Inicializa o controller carregando notificações dos alunos do responsável.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listViewNotificacoes.setItems(FXCollections.observableArrayList());
        mensagemStatus.setText("");

        // Apenas para responsável: carrega notificações dos seus alunos automaticamente
        try {
            if (Repositorio.getCurrentUserType() != 'R') {
                mensagemStatus.setText("Apenas responsáveis podem ver notificações neste painel.");
                return;
            }
            String cpfResponsavel = Repositorio.getCurrentUserCpf();
            if (cpfResponsavel == null || cpfResponsavel.trim().isEmpty()) {
                mensagemStatus.setText("Erro: CPF do responsável não encontrado na sessão.");
                return;
            }

            // Buscar alunos vinculados a esse responsável
            java.util.List<String> exibicao = new java.util.ArrayList<>();
            origem.clear();
            for (org.example.schooltransport.model.Aluno a : Repositorio.getListaAluno()) {
                if (cpfResponsavel.equals(a.getResponsavel())) {
                    String cpfAluno = a.getCpf();
                    Notificacao n = Repositorio.getNotificacaoPorCpf(cpfAluno);
                    if (n != null && !n.getNotificacoes().isEmpty()) {
                        // ordem mais recente primeiro
                        java.util.List<String> msgs = new java.util.ArrayList<>(n.getNotificacoes());
                        java.util.Collections.reverse(msgs);
                        for (String m : msgs) {
                            String label = a.getNome() + " - " + m;
                            exibicao.add(label);
                            origem.add(new java.util.AbstractMap.SimpleEntry<>(cpfAluno, m));
                        }
                    }
                }
            }

            if (exibicao.isEmpty()) {
                mensagemStatus.setText("Nenhuma notificação encontrada para seus alunos.");
                listViewNotificacoes.setItems(FXCollections.observableArrayList());
            } else {
                mensagemStatus.setText("Mostrando notificações (mais recentes primeiro)");
                listViewNotificacoes.setItems(FXCollections.observableArrayList(exibicao));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            mensagemStatus.setText("Erro ao carregar notificações.");
        }
    }

    /**
     * Remove a notificação selecionada da lista.
     * @param event Evento da ação
     */
    @FXML
    private void removerSelecionado(ActionEvent event) {
        String selecionado = listViewNotificacoes.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mensagemStatus.setText("Selecione uma notificação para remover.");
            return;
        }
        int idx = listViewNotificacoes.getSelectionModel().getSelectedIndex();
        if (idx < 0 || idx >= origem.size()) {
            mensagemStatus.setText("Índice inválido para remoção.");
            return;
        }
        java.util.Map.Entry<String,String> par = origem.get(idx);
        String cpfAluno = par.getKey();
        String mensagem = par.getValue();
        Repositorio.removerNotificacaoParaCpf(cpfAluno, mensagem);

        // remover do arrays locais e atualizar a exibição
        origem.remove(idx);
        javafx.collections.ObservableList<String> items = listViewNotificacoes.getItems();
        items.remove(idx);
        listViewNotificacoes.setItems(items);
        mensagemStatus.setText("Notificação removida.");
    }

    @FXML
    private void voltarTela(ActionEvent event) {
        try {
            String fxmlDestino = (Repositorio.getCurrentUserType() == 'R') ? "painelResponsavel.fxml" : "painelAdministrador.fxml";
            String caminhoAbsoluto = "/org/example/schooltransport/" + fxmlDestino;
            URL resourceUrl = getClass().getResource(caminhoAbsoluto);
            if (resourceUrl == null) {
                System.err.println("FATAL: Não foi possível encontrar o FXML em: " + caminhoAbsoluto);
                return;
            }
            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Parent root = loader.load();

            Node sourceNode = (Node) event.getSource();
            Stage stage = (Stage) sourceNode.getScene().getWindow();
            Scene scene = new Scene(root, 390, 700);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
