// Filipe Alves Sousa Julio

package org.example.schooltransport.controllers;

import java.io.IOException;

import org.example.schooltransport.data.Repositorio;
import org.example.schooltransport.model.Veiculo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

/**
 * Controller responsável pela tela de cadastro de veículos.
 * Gerencia a validação e inserção de novos veículos no sistema.
 */
public class CadastrarVeiculoController {

    @FXML private TextField campoModeloOnibus;
    @FXML private TextField campoPlaca;
    @FXML private TextField campoCapacidade;
    @FXML private Label mensagemStatus;

    /**
     * Inicializa o controller configurando formatação do campo de capacidade.
     */
    @FXML
    private void initialize() {
        campoCapacidade.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        }));
    }

    /**
     * Processa o cadastro de um novo veículo.
     */
    @FXML
    private void CadastrarVeiculo() {
        String modelo = campoModeloOnibus.getText();
        String placa = campoPlaca.getText();
        String capacidadeTexto = campoCapacidade.getText();

        if (modelo.isEmpty() || placa.isEmpty() || capacidadeTexto.isEmpty()) {
            mensagemStatus.setText("Preencha todos os campos!");
            return;
        }

        try {
            int capacidade = Integer.parseInt(capacidadeTexto);
            Veiculo veiculo = new Veiculo(modelo, placa, capacidade);
            Repositorio.getListaVeiculo().add(veiculo);

            mensagemStatus.setText("Veículo cadastrado: " + modelo + " (" + placa + ")");
            System.out.println("Cadastrado novo veículo!");
            System.out.println("Total: " + Repositorio.getListaVeiculo().size());

            limparCampos();

        } catch (NumberFormatException e) {
            mensagemStatus.setText("Capacidade inválida! Digite apenas números.");
        }
    }

    /**
     * Retorna ao painel do administrador.
     */
    @FXML
    private void voltarTelaAnterior() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/schooltransport/painelAdministrador.fxml"));
            Scene cena = new Scene(loader.load());
            Stage stage = (Stage) campoModeloOnibus.getScene().getWindow();
            stage.setScene(cena);
            stage.show();
        } catch (IOException e) {
            mensagemStatus.setText("Erro ao voltar para o painel do administrador.");
            e.printStackTrace();
        }
    }

    /**
     * Limpa todos os campos do formulário.
     */
    private void limparCampos() {
        campoModeloOnibus.clear();
        campoPlaca.clear();
        campoCapacidade.clear();
    }
}
