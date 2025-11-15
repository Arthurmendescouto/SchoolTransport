package org.example.schooltransport.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.example.schooltransport.Cadastro;
import org.example.schooltransport.Parada;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller responsável pela tela de cadastro de paradas.
 * Gerencia a validação de campos e o cadastro de novas paradas no sistema.
 * * @author Sistema de Transporte Escolar
 * @version 1.1 (Validação flexível)
 */
public class CadastrarParadaController {

    @FXML private TextField campoNomeParada;
    @FXML private TextField campoCEP;
    @FXML private TextField campoLogradouro;
    @FXML private TextField campoNumero;
    @FXML private TextField campoBairro;
    @FXML private TextField campoComplemento;
    @FXML private TextField campoCidade;
    @FXML private TextField campoEstado;
    @FXML private Label labelErro;

    @FXML
    private void initialize() {
        if (labelErro != null) {
            labelErro.setVisible(false);
            labelErro.setText("");
        }
    }

    /**
     * Processa o cadastro de uma nova parada após validação dos campos.
     * A validação foi tornada mais flexível para Estado e Número.
     * * @param event Evento de ação do botão
     */
    @FXML
    private void concluirCadastro(ActionEvent event) {
        if (labelErro != null) {
            labelErro.setText("");
            labelErro.setVisible(false);
        }

        try {
            List<String> erros = new ArrayList<>();

            // 1. Validação do nome da parada (Mantido)
            String nomeParada = campoNomeParada.getText().trim();
            if (nomeParada.isEmpty()) {
                erros.add("• Nome da Parada é obrigatório");
            } else if (nomeParada.matches("^\\d+$")) {
                erros.add("• Nome da Parada não pode ser apenas números");
            }

            // 2. Validação do CEP (Mantido, é um padrão técnico)
            String cep = campoCEP.getText().trim();
            if (cep.isEmpty()) {
                erros.add("• CEP é obrigatório");
            } else if (!validarCEP(cep)) {
                erros.add("• CEP inválido (deve ter 8 dígitos, ex: 12345-678)");
            }

            // 3. Validação do logradouro (Mantido)
            String logradouro = campoLogradouro.getText().trim();
            if (logradouro.isEmpty()) {
                erros.add("• Logradouro é obrigatório");
            }

            // 4. Validação do número (AGORA MAIS FLEXÍVEL)
            String numero = campoNumero.getText().trim();
            if (numero.isEmpty()) {
                // Apenas checa se está vazio. Permite "S/N", "Lote 10", "123A", etc.
                erros.add("• Número é obrigatório (ex: 123, S/N, Lote 10)");
            }

            // 5. Validação do bairro (Mantido)
            String bairro = campoBairro.getText().trim();
            if (bairro.isEmpty()) {
                erros.add("• Bairro é obrigatório");
            }

            // 6. Validação da cidade (Mantido)
            String cidade = campoCidade.getText().trim();
            if (cidade.isEmpty()) {
                erros.add("• Cidade é obrigatória");
            } else if (cidade.matches("^\\d+$")) {
                erros.add("• Cidade não pode ser apenas números");
            }

            // 7. Validação do estado (AGORA MAIS FLEXÍVEL)
            String estado = campoEstado.getText().trim();
            if (estado.isEmpty()) {
                erros.add("• Estado é obrigatório");
            } else if (estado.matches("^\\d+$")) { // Não pode ser só número
                erros.add("• Estado não pode ser apenas números");
            } else if (estado.length() < 2) { // Tem que ter no mínimo 2 chars (para "BA")
                erros.add("• Estado inválido (mínimo 2 caracteres, ex: BA ou Bahia)");
            }

            // Se houver erros, exibe e interrompe o cadastro
            if (!erros.isEmpty()) {
                String mensagemErro = String.join("\n", erros);
                mostrarErro(mensagemErro);
                return;
            }

            // Cria a nova parada
            String complemento = campoComplemento.getText().trim();
            // A função toUpperCase() já lida bem com "Bahia" (vira "BAHIA") ou "ba" (vira "BA")
            Parada novaParada = new Parada(nomeParada, logradouro, numero, bairro, cidade, estado.toUpperCase());
            Cadastro.getInstance().adicionarParada(novaParada);
            System.out.println("Parada cadastrada com sucesso: " + nomeParada);

            // Navegar para a lista de paradas
            navegarDeTela(event, "listaParadas.fxml");

        } catch (Exception e) {
            // Tratamento de exceções gerais
            String mensagemErro = "Erro inesperado ao cadastrar parada: " + e.getMessage();
            mostrarErro(mensagemErro);
            e.printStackTrace();
        }
    }

    /**
     * Valida o formato do CEP (deve conter 8 dígitos numéricos).
     * Este método é mantido pois CEP é um padrão técnico.
     * * @param cep CEP a ser validado
     * @return true se o CEP está em formato válido, false caso contrário
     */
    private boolean validarCEP(String cep) {
        // Remove caracteres não numéricos (como '-')
        String cepLimpo = cep.replaceAll("[^0-9]", "");
        // CEP deve ter exatamente 8 dígitos
        return cepLimpo.length() == 8 && cepLimpo.matches("^\\d{8}$");
    }

    /**
     * (Método validarEstado foi removido pois a lógica agora é mais flexível
     * e está dentro de concluirCadastro)
     */

    /**
     * Exibe mensagem de erro na interface do usuário.
     * * @param mensagem Mensagem de erro a ser exibida
     */
    private void mostrarErro(String mensagem) {
        if (labelErro != null) {
            labelErro.setText(mensagem);
            labelErro.setVisible(true);
        }
        System.err.println("Erro de validação: " + mensagem.replace("\n", " | "));
    }

    @FXML
    private void voltar(ActionEvent event) {
        navegarDeTela(event, "painelAdministrador.fxml");
    }

    @FXML
    private void abrirTelaMotorista(ActionEvent event) {
        navegarDeTela(event, "telaMotorista.fxml");
    }

    /**
     * Método de Navegação Padrão (Caminho Absoluto)
     */
    private void navegarDeTela(ActionEvent event, String fxmlFile) {
        try {
            String caminhoAbsoluto = "/org/example/schooltransport/" + fxmlFile;
            URL resourceUrl = getClass().getResource(caminhoAbsoluto);

            if (resourceUrl == null) {
                System.err.println("FATAL: Não foi possível encontrar o FXML em: " + caminhoAbsoluto);
                System.err.println("Verifique se o nome do arquivo '" + fxmlFile + "' está digitado corretamente.");
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
            System.err.println(">>> SE O ERRO FOR 'ClassNotFoundException', VOCÊ NÃO CORRIGIU O 'fx:controller' DENTRO DO FXML! <<<");
        }
    }
}