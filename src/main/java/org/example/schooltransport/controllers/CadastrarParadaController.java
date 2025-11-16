package org.example.schooltransport.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.example.schooltransport.Cadastro;
import org.example.schooltransport.model.Parada;
import org.example.schooltransport.data.Repositorio;
import org.example.schooltransport.model.Aluno;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller responsável pela tela de cadastro de paradas.
 * Gerencia a validação de campos e o cadastro de novas paradas no sistema.
 * 
 * @author Sistema de Transporte Escolar
 * @version 1.0
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
    @FXML private ComboBox<Aluno> comboAluno; // Novo: ComboBox para selecionar aluno
    @FXML private Label labelErro;

    /**
     * Inicializa o controller e configura o label de erro como invisível.
     */
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
            
            // Configurar a exibição: mostrar "Nome (CPF)"
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
            
            // Configurar o botão selecionado também mostra "Nome (CPF)"
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

    /**
     * Processa o cadastro de uma nova parada após validação dos campos.
     * Exibe mensagens de erro caso algum campo esteja inválido.
     * 
     * @param event Evento de ação do botão
     */
    @FXML
    private void concluirCadastro(ActionEvent event) {
        // Limpa mensagens de erro anteriores
        if (labelErro != null) {
            labelErro.setText("");
            labelErro.setVisible(false);
        }

        try {
            // Validação dos campos obrigatórios
            List<String> erros = new ArrayList<>();

            // Validação do aluno selecionado
            Aluno alunoSelecionado = comboAluno.getSelectionModel().getSelectedItem();
            if (alunoSelecionado == null) {
                erros.add("• Selecione um aluno para associar à parada");
            }

            // Validação do nome da parada
            String nomeParada = campoNomeParada.getText().trim();
            if (nomeParada.isEmpty()) {
                erros.add("• Nome da Parada é obrigatório");
            } else if (nomeParada.matches("^\\d+$")) {
                erros.add("• Nome não pode ser apenas números");
            }

            // Validação do CEP
            String cep = campoCEP.getText().trim();
            if (cep.isEmpty()) {
                erros.add("• CEP é obrigatório");
            } else if (!validarCEP(cep)) {
                erros.add("• CEP inválido (8 dígitos)");
            }

            // Validação do logradouro
            String logradouro = campoLogradouro.getText().trim();
            if (logradouro.isEmpty()) {
                erros.add("• Logradouro é obrigatório");
            }

            // Validação do número
            String numero = campoNumero.getText().trim();
            if (numero.isEmpty()) {
                erros.add("• Número é obrigatório");
            } else if (!numero.matches("^\\d+[A-Za-z]?$")) {
                erros.add("• Número inválido (apenas números)");
            }

            // Validação do bairro
            String bairro = campoBairro.getText().trim();
            if (bairro.isEmpty()) {
                erros.add("• Bairro é obrigatório");
            }

            // Validação da cidade
            String cidade = campoCidade.getText().trim();
            if (cidade.isEmpty()) {
                erros.add("• Cidade é obrigatória");
            } else if (cidade.matches("^\\d+$")) {
                erros.add("• Cidade não pode ser apenas números");
            }

            // Validação do estado
            String estado = campoEstado.getText().trim();
            if (estado.isEmpty()) {
                erros.add("• Estado é obrigatório");
            } else if (!validarEstado(estado)) {
                erros.add("• Estado inválido");
            }

            // Se houver erros, exibe e interrompe o cadastro
            if (!erros.isEmpty()) {
                String mensagemErro = String.join("\n", erros);
                mostrarErro(mensagemErro);
                return;
            }

            // Cria a nova parada
            Parada novaParada = new Parada(nomeParada, logradouro, numero, bairro, cidade, estado.toUpperCase());
            
            // Associa o aluno selecionado à parada
            novaParada.setAluno(alunoSelecionado);
            
            Cadastro.getInstance().adicionarParada(novaParada);
            System.out.println("Parada cadastrada com sucesso: " + nomeParada);
            System.out.println("Aluno associado: " + alunoSelecionado.getNome() + " (" + alunoSelecionado.getCpf() + ")");

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
     * 
     * @param cep CEP a ser validado
     * @return true se o CEP está em formato válido, false caso contrário
     */
    private boolean validarCEP(String cep) {
        // Remove caracteres não numéricos
        String cepLimpo = cep.replaceAll("[^0-9]", "");
        // CEP deve ter exatamente 8 dígitos
        return cepLimpo.length() == 8 && cepLimpo.matches("^\\d{8}$");
    }

    /**
     * Valida o formato do Estado (deve ser uma sigla de 2 letras).
     * 
     * @param estado Estado a ser validado
     * @return true se o estado está em formato válido, false caso contrário
     */
    private boolean validarEstado(String estado) {
        // Estado deve ter exatamente 2 letras
        return estado.length() == 2 && estado.matches("^[A-Za-z]{2}$");
    }

    /**
     * Exibe mensagem de erro na interface do usuário.
     * 
     * @param mensagem Mensagem de erro a ser exibida
     */
    private void mostrarErro(String mensagem) {
        if (labelErro != null) {
            labelErro.setText(mensagem);
            labelErro.setVisible(true);
        }
        System.err.println("Erro de validação: " + mensagem);
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