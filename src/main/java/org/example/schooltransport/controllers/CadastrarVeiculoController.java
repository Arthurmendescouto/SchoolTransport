// Filipe Alves Sousa Julio

package org.example.schooltransport.controllers;

import java.io.IOException;

import org.example.schooltransport.data.Repositorio;
import org.example.schooltransport.model.Veiculo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
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

    private final int CAPACIDADE_MAXIMA = 50; // ajuste aqui como desejar

    @FXML
    private void initialize() {

        // Capacidade -> somente números
        campoCapacidade.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        }));

        // Máscara e validação da placa Mercosul: AAA-0A00
        campoPlaca.setTextFormatter(new TextFormatter<String>(change -> {
            String novo = change.getControlNewText().toUpperCase();

            // Remove caracteres inválidos
            novo = novo.replaceAll("[^A-Z0-9-]", "");

            // Aplica máscara automaticamente
            if (novo.length() > 3 && novo.charAt(3) != '-') {
                novo = novo.substring(0, 3) + "-" + novo.substring(3);
            }

            // Limita no máximo 8 caracteres (AAA-0A00)
            if (novo.length() > 8)
                return null;

            change.setText(novo);
            change.setRange(0, change.getControlText().length());
            return change;
        }));
    }

    @FXML
    private void CadastrarVeiculo() {

        String modelo = campoModeloOnibus.getText();
        String placa = campoPlaca.getText();
        String capacidadeTexto = campoCapacidade.getText();

        if (modelo.isEmpty() || placa.isEmpty() || capacidadeTexto.isEmpty()) {
            mensagemStatus.setText("Preencha todos os campos!");
            return;
        }

        // Validação da placa: AAA-0A00
        if (!placa.matches("^[A-Z]{3}-\\d[A-Z]\\d{2}$")) {
            mensagemStatus.setText("Placa inválida! Use o padrão Mercosul: AAA-0A00");
            return;
        }

        try {
            int capacidade = Integer.parseInt(capacidadeTexto);

            // verifica capacidade máxima
            if (capacidade <= 0 || capacidade > CAPACIDADE_MAXIMA) {
                mensagemStatus.setText("Capacidade inválida! Máximo permitido: " + CAPACIDADE_MAXIMA);
                return;
            }

            Veiculo veiculo = new Veiculo(modelo, placa, capacidade);
            Repositorio.getListaVeiculo().add(veiculo);

            mensagemStatus.setText("Veículo cadastrado: " + modelo + " (" + placa + ")");
            System.out.println("Novo veículo cadastrado!");
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
            Parent root = loader.load();
            Scene cena = new Scene(root, 390, 700);
            Stage stage = (Stage) campoModeloOnibus.getScene().getWindow();
            stage.setScene(cena);
            stage.setResizable(false);
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
