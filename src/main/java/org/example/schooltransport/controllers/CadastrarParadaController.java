package org.example.schooltransport.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.example.schooltransport.model.Cadastro;
import org.example.schooltransport.model.Parada;
import org.example.schooltransport.data.Repositorio;
import org.example.schooltransport.model.Aluno;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    @FXML private ComboBox<Aluno> comboAluno;
    @FXML private Label labelErro;

    @FXML
    private void initialize() {
        if (labelErro != null) {
            labelErro.setVisible(false);
            labelErro.setText("");
        }

        // Carregar alunos no ComboBox
        if (comboAluno != null) {
            comboAluno.getItems().clear();
            comboAluno.getItems().addAll(Repositorio.getListaAluno());

            comboAluno.setCellFactory(param -> new javafx.scene.control.ListCell<Aluno>() {
                @Override
                protected void updateItem(Aluno item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNome() + " (" + item.getCpf() + ")");
                    }
                }
            });

            comboAluno.setButtonCell(new javafx.scene.control.ListCell<Aluno>() {
                @Override
                protected void updateItem(Aluno item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText("Selecione um aluno...");
                    } else {
                        setText(item.getNome() + " (" + item.getCpf() + ")");
                    }
                }
            });
        }
    }

    @FXML
    private void concluirCadastro(ActionEvent event) {
        if (labelErro != null) {
            labelErro.setText("");
            labelErro.setVisible(false);
        }

        try {
            List<String> erros = new ArrayList<>();

            // 1. Validação do Aluno (Obrigatório)
            Aluno alunoSelecionado = comboAluno.getSelectionModel().getSelectedItem();
            if (alunoSelecionado == null) {
                erros.add("• É obrigatório selecionar um aluno");
            }

            // 2. Validação do Nome da Parada (Obrigatório)
            String nomeParada = campoNomeParada.getText().trim();
            if (nomeParada.isEmpty()) {
                erros.add("• Nome da Parada é obrigatório");
            }

            // 3. Validação do CEP (Numérico Obrigatório)
            String cep = campoCEP.getText().trim();
            if (cep.isEmpty()) {
                erros.add("• CEP é obrigatório");
            } else if (!validarCEP(cep)) {
                erros.add("• CEP inválido (deve ter 8 dígitos, ex: 12345-678)");
            }

            // 4. Validação do Logradouro (Obrigatório)
            String logradouro = campoLogradouro.getText().trim();
            if (logradouro.isEmpty()) {
                erros.add("• Logradouro é obrigatório");
            }

            // 5. Validação do Número (Numérico Obrigatório)
            String numero = campoNumero.getText().trim();
            if (numero.isEmpty()) {
                erros.add("• Número é obrigatório");
            } else if (!numero.matches("^\\d+[A-Za-z]?$")) { // Permite "123" ou "123A"
                erros.add("• Número inválido (use apenas números, ex: 123 ou 123A)");
            }

            // 6. Bairro, Cidade e Estado (Não têm mais validação de formato ou obrigatoriedade)
            String bairro = campoBairro.getText().trim();
            String cidade = campoCidade.getText().trim();
            String estado = campoEstado.getText().trim();

            if (!erros.isEmpty()) {
                String mensagemErro = String.join("\n", erros);
                mostrarErro(mensagemErro);
                return;
            }

            String complemento = campoComplemento.getText().trim();

            Parada novaParada = new Parada(nomeParada, logradouro, numero, bairro, cidade, estado.toUpperCase());
            novaParada.setAluno(alunoSelecionado);
            Cadastro.getInstance().adicionarParada(novaParada);

            System.out.println("Parada cadastrada com sucesso: " + nomeParada);
            System.out.println("Aluno associado: " + alunoSelecionado.getNome() + " (" + alunoSelecionado.getCpf() + ")");

            navegarDeTela(event, "listaParadas.fxml");

        } catch (Exception e) {
            String mensagemErro = "Erro inesperado ao cadastrar parada: " + e.getMessage();
            mostrarErro(mensagemErro);
            e.printStackTrace();
        }
    }

    private boolean validarCEP(String cep) {
        String cepLimpo = cep.replaceAll("[^0-9]", "");
        return cepLimpo.length() == 8 && cepLimpo.matches("^\\d{8}$");
    }

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
     * MÉTODO DE NAVEGAÇÃO CORRIGIDO
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

            // Se estivermos indo para a lista de paradas, indique a origem para o controller
            if ("listaParadas.fxml".equals(fxmlFile)) {
                try {
                    Object controller = loader.getController();
                    if (controller instanceof org.example.schooltransport.controllers.ListaParadaController) {
                        ((org.example.schooltransport.controllers.ListaParadaController) controller).setTelaDeOrigem("cadastrarParada");
                    }
                } catch (Exception ex) {
                    // Não fatal: apenas seguimos sem setar a origem
                }
            }

            Node sourceNode = (Node) event.getSource();
            Stage stage = (Stage) sourceNode.getScene().getWindow();

            stage.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar o FXML: " + fxmlFile);
        }
    }
}