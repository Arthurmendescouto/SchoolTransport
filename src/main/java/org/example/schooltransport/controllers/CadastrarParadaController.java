package org.example.schooltransport.controllers;

import java.io.IOException;
import java.net.URL; // IMPORTANTE

import org.example.schooltransport.Cadastro;
import org.example.schooltransport.Parada;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastrarParadaController {

    @FXML private TextField campoNomeParada;
    @FXML private TextField campoCEP;
    @FXML private TextField campoLogradouro;
    @FXML private TextField campoNumero;
    @FXML private TextField campoBairro;
    @FXML private TextField campoComplemento;
    @FXML private TextField campoCidade;
    @FXML private TextField campoEstado;

    @FXML
    private void concluirCadastro(ActionEvent event) {
        String nomeParada = campoNomeParada.getText();
        String logradouro = campoLogradouro.getText();
        String cidade = campoCidade.getText();

        if (nomeParada.isEmpty() || logradouro.isEmpty() || cidade.isEmpty()) {
            System.err.println("Erro: Preencha os campos obrigatórios (Nome, Logradouro, Cidade).");
            return;
        }

        Parada novaParada = new Parada(nomeParada, logradouro, campoNumero.getText(), campoBairro.getText(), cidade, campoEstado.getText());
        Cadastro.getInstance().adicionarParada(novaParada);
        System.out.println("Parada cadastrada com sucesso: " + nomeParada);

        // 4. Navegar para a lista de paradas
        navegarDeTela(event, "listaParadas.fxml");
    }

    @FXML
    private void voltar(ActionEvent event) {
        // Navega para o painel de administrador
        navegarDeTela(event, "painelAdministrador.fxml");
    }

    @FXML
    private void abrirTelaMotorista(ActionEvent event) {
        // Navega para a tela do motorista
        navegarDeTela(event, "telaMotorista.fxml");
    }

    /**
     * ✅ MÉTODO DE NAVEGAÇÃO CORRIGIDO (Versão 3: Caminho Absoluto)
     *
     * Esta é a forma mais robusta. Ele procura o FXML a partir da
     * raiz do seu 'resources' (classpath).
     *
     * Com base nos seus prints, o caminho completo para seus FXMLs é:
     * /org/example/schooltransport/ [nome-do-arquivo.fxml]
     *
     */
    private void navegarDeTela(ActionEvent event, String fxmlFile) {
        try {
            // **A CORREÇÃO ESTÁ AQUI:**
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
            Parent root = loader.load(); // O erro ClassNotFoundException ainda pode acontecer aqui

            // Obtém o Stage (janela)
            Node sourceNode = (Node) event.getSource();
            Stage stage = (Stage) sourceNode.getScene().getWindow();

            // Define a nova cena
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) { // IOException "pega" o LoadException
            e.printStackTrace();
            System.err.println("Erro ao carregar o FXML: " + fxmlFile);
            System.err.println(">>> SE O ERRO FOR 'ClassNotFoundException', VOCÊ NÃO CORRIGIU O 'fx:controller' DENTRO DO FXML! <<<");
        }
    }
}