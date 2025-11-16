package org.example.schooltransport.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller responsável pelo painel principal do administrador.
 * Oferece acesso a todas as funcionalidades de cadastro e consulta do sistema.
 */
public class PainelAdministradorController implements Initializable {

    @FXML
    private Label mensagemStatus;

    @FXML
    private javafx.scene.control.Button botaoVoltarTopo;
    @FXML
    private javafx.scene.control.Button btnConsultarNotificacoes;

    /**
     * Inicializa o controller configurando botões e visibilidade conforme tipo de usuário.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // garante que o botão de topo tenha um handler se o onAction do FXML falhar por algum motivo
        if (botaoVoltarTopo != null) {
            botaoVoltarTopo.setOnAction(e -> voltar(e));
        }

        // Mostrar o botão de consultar notificações quando o usuário logado for um responsável
        try {
            if (btnConsultarNotificacoes != null) {
                boolean mostrar = (org.example.schooltransport.data.Repositorio.getCurrentUserType() == 'R');
                btnConsultarNotificacoes.setVisible(mostrar);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Retorna à tela de login.
     */
    @FXML
    private void voltar(ActionEvent event) {
        // Navega para a tela de login ou outra tela apropriada
        navegarDeTela(event, "login.fxml");
    }

    @FXML
    private void consultarFaltas(ActionEvent event) {
        try {
            // Navega para a tela de consulta de faltas do administrador
            System.out.println("DEBUG: Tentando navegar para consultarFaltasAdmin.fxml");
            navegarDeTela(event, "consultarFaltasAdmin.fxml");
        } catch (Exception e) {
            System.err.println("Erro ao navegar para tela de faltas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Abre a tela de cadastro de rotas.
     */
    @FXML
    private void abrirCadastrarRota(ActionEvent event) {
        // Navega para a tela de cadastrar rota
        navegarDeTela(event, "cadastrarRota.fxml");
    }

    /**
     * Abre a tela de cadastro de alunos.
     */
    @FXML
    private void abrirCadastrarAluno(ActionEvent event) {
        navegarDeTela(event, "cadastrarAlunos.fxml");
    }

    /**
     * Abre a tela de cadastro de motoristas.
     */
    @FXML
    private void abrirCadastrarMotorista(ActionEvent event) {
        navegarDeTela(event, "cadastrarMotorista.fxml");
    }

    /**
     * Abre a tela de cadastro de responsáveis.
     */
    @FXML
    private void abrirCadastrarResponsavel(ActionEvent event) {
        navegarDeTela(event, "cadastrarResponsavel.fxml");
    }

    /**
     * Abre a tela de cadastro de veículos.
     */
    @FXML
    private void abrirCadastrarVeiculo(ActionEvent event) {
        navegarDeTela(event, "cadastrarVeiculo.fxml");
    }

    /**
     * Abre a tela de cadastro de paradas.
     */
    @FXML
    private void abrirCadastrarParada(ActionEvent event) {
        // Navega para a tela de cadastrar parada
        navegarDeTela(event, "cadastrarParada.fxml");
    }

    /**
     * Abre a tela de listagem de rotas.
     */
    @FXML
    private void abrirListarRotas(ActionEvent event) {
        // Navega para a tela que lista rotas (para selecionar uma a editar)
        navegarDeTela(event, "listarRotas.fxml");
    }

    /**
     * Abre a tela de consulta de notificações.
     */
    @FXML
    private void abrirConsultarNotificacoes(ActionEvent event) {
        navegarDeTela(event, "consultarNotificacoes.fxml");
    }

    /**
     * Método auxiliar para navegação entre telas.
     * @param event Evento de ação do botão
     * @param fxmlFile Nome do arquivo FXML de destino
     */
    private void navegarDeTela(ActionEvent event, String fxmlFile) {
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
            System.out.println("DEBUG: Carregando FXML de: " + resourceUrl);
            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Parent root = loader.load();
            System.out.println("DEBUG: FXML carregado com sucesso");

            // Obtém o Stage (janela)
            Node sourceNode = (Node) event.getSource();
            Stage stage = (Stage) sourceNode.getScene().getWindow();

            // Define a nova cena com tamanho fixo e desabilita redimensionamento
            Scene scene = new Scene(root, 390, 700);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar o FXML: " + fxmlFile);
            System.err.println("Tipo do erro: " + e.getClass().getName());
            System.err.println("Mensagem: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Causa: " + e.getCause().getClass().getName());
                System.err.println("Mensagem da causa: " + e.getCause().getMessage());
            }
            System.err.println(">>> SE O ERRO FOR 'ClassNotFoundException', RECOMPILE O PROJETO! <<<");
        }
    }
}
