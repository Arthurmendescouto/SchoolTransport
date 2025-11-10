package org.example.schooltransport.controllers;

import java.io.IOException;

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

    // Campos FXML injetados (fx:id)
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
        // 1. Coletar e Validar Dados
        String nomeParada = campoNomeParada.getText();
        String logradouro = campoLogradouro.getText();
        String numero = campoNumero.getText();
        String bairro = campoBairro.getText();
        String cidade = campoCidade.getText();
        String estado = campoEstado.getText();

        // Simples validação para campos essenciais
        if (nomeParada.isEmpty() || logradouro.isEmpty() || cidade.isEmpty()) {
            // Em uma aplicação real, você mostraria um Alerta/Label de erro
            System.err.println("Erro: Preencha os campos obrigatórios (Nome, Logradouro, Cidade).");
            return;
        }

        // 2. Criar o objeto Parada
        Parada novaParada = new Parada(nomeParada, logradouro, numero, bairro, cidade, estado);

        // 3. Adicionar a Parada à lista global (Classe Cadastro)
        Cadastro.getInstance().adicionarParada(novaParada);
        System.out.println("Parada cadastrada com sucesso: " + nomeParada);

        // 4. Redirecionar para listaParadas.fxml
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("listaParadas.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            // Opcional: manter o mesmo tamanho da janela
            // stage.setWidth(stage.getWidth());
            // stage.setHeight(stage.getHeight());

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar listaParadas.fxml");
        }
    }

    @FXML
    private void voltar(ActionEvent event) {
        // Implemente a lógica para voltar à tela anterior (ex: Tela Principal)
    }

}