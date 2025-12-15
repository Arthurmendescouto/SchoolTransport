package org.example.schooltransport.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.schooltransport.model.Aluno;
import org.example.schooltransport.model.Ocorrencia;
import org.example.schooltransport.model.Parada;
import org.example.schooltransport.model.Responsavel;
import org.example.schooltransport.model.Veiculo;

public class Repositorio {

    // ================= PARADAS =================
    private static ObservableList<Parada> listaParadas =
            FXCollections.observableArrayList();
    private static Object org;

    public static void adicionarParada(Parada parada) {
        listaParadas.add(parada);
    }

    public static void removerParada(Parada parada) {
        listaParadas.remove(parada);
    }

    public static ObservableList<Parada> getListaParada() {
        return listaParadas;
    }

    // ================= OCORRÊNCIAS =================
    private static ObservableList<Ocorrencia> listaOcorrencias =
            FXCollections.observableArrayList();

    public static void adicionarOcorrencia(Ocorrencia ocorrencia) {
        listaOcorrencias.add(ocorrencia);
    }

    public static ObservableList<Ocorrencia> getListaOcorrencias() {
        return listaOcorrencias;
    }
    // ================= SESSÃO =================
    private static char currentUserType = 'X';
    private static String currentUserCpf = null;

    public static void setCurrentUserType(char type) {
        currentUserType = type;
    }
    public static char getCurrentUserType() {
        return currentUserType;
    }
    public static void setCurrentUserCpf(String cpf) {
        currentUserCpf = cpf;
    }
    public static String getCurrentUserCpf() {
        return currentUserCpf;
    }

    // ================= LISTAS DE USUÁRIOS (MOCK) =================
    // Necessárias para LoginController e TelaMotoristaController funcionarem
    private static ObservableList<org.example.schooltransport.model.Aluno> listaAluno = FXCollections.observableArrayList();
    private static ObservableList<org.example.schooltransport.model.Motorista> listaMotorista = FXCollections.observableArrayList();
    private static ObservableList<org.example.schooltransport.model.Responsavel> listaResponsavel = FXCollections.observableArrayList();

    public static ObservableList<org.example.schooltransport.model.Aluno> getListaAluno() {
        return listaAluno;
    }
    public static ObservableList<org.example.schooltransport.model.Motorista> getListaMotorista() {
        return listaMotorista;
    }
    public static ObservableList<org.example.schooltransport.model.Responsavel> getListaResponsavel() {
        return listaResponsavel;
    }

    // ================= VEÍCULOS =================
private static ObservableList<org.example.schooltransport.model.Veiculo> listaVeiculo =
        FXCollections.observableArrayList();

// ================= ROTAS =================
private static ObservableList<org.example.schooltransport.model.Rota> listaRota =
        FXCollections.observableArrayList();

// ================= NOTIFICAÇÕES =================
private static ObservableList<org.example.schooltransport.model.Notificacao> listaNotificacao =
        FXCollections.observableArrayList();

// ================= PRESENÇA =================
private static ObservableList<org.example.schooltransport.model.RegistroPresenca> listaDePresenca =
        FXCollections.observableArrayList();
public static ObservableList<org.example.schooltransport.model.Veiculo> getListaVeiculo() {
    return listaVeiculo;
}

public static ObservableList<org.example.schooltransport.model.Rota> getListaRota() {
    return listaRota;
}

public static ObservableList<org.example.schooltransport.model.Notificacao> getListaNotificacao() {
    return listaNotificacao;
}

public static ObservableList<org.example.schooltransport.model.RegistroPresenca> getListaDePresenca() {
    return listaDePresenca;
}

    public static void adicionarNotificacaoParaCpf(String cpfAluno, String msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // ================= PERSISTÊNCIA =================
    
    /**
     * Salva todos os dados do repositório em arquivos de texto.
     * Os dados são salvos automaticamente quando os objetos são criados.
     * Este método está vazio pois os arquivos .txt são atualizados diretamente pelos construtores.
     */
    public static void salvarDados() {
        // Os dados são salvos automaticamente nos arquivos .txt quando os objetos são criados
        // através dos métodos salvarEmArquivo() nas classes Responsavel, Aluno e Veiculo
        System.out.println("Dados já estão salvos nos arquivos de texto.");
    }

    /**
     * Carrega todos os dados dos arquivos de texto (.txt) para o repositório.
     * Se os arquivos não existirem, mantém as listas vazias.
     */
    public static void carregarDados() {
        try {
            // Carregar responsáveis dos arquivos .txt
            carregarResponsaveisDeTexto();

            // Carregar alunos dos arquivos .txt (depois dos responsáveis, pois alunos dependem deles)
            carregarAlunosDeTexto();

            // Carregar veículos dos arquivos .txt
            carregarVeiculosDeTexto();

            System.out.println("Dados carregados dos arquivos de texto com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carrega responsáveis do arquivo de texto listaResponsaveis.txt.
     * Formato: nome|cpf|contato|email|senha
     */
    private static void carregarResponsaveisDeTexto() {
        File arquivo = new File("listaResponsaveis.txt");
        if (!arquivo.exists()) {
            System.out.println("Arquivo listaResponsaveis.txt não encontrado.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) {
                    continue;
                }

                String[] partes = linha.split("\\|");
                if (partes.length == 5) {
                    String nome = partes[0].trim();
                    String cpf = partes[1].trim();
                    String contato = partes[2].trim();
                    String email = partes[3].trim();
                    String senha = partes[4].trim();

                    // Verificar se o responsável já existe (por CPF)
                    boolean existe = false;
                    for (Responsavel resp : getListaResponsavel()) {
                        if (resp.getCpf().equals(cpf)) {
                            existe = true;
                            break;
                        }
                    }

                    if (!existe) {
                        Responsavel responsavel = new Responsavel(nome, cpf, contato, email, senha, false);
                        getListaResponsavel().add(responsavel);
                    }
                } else {
                    System.err.println("Linha inválida em listaResponsaveis.txt: " + linha);
                }
            }
            System.out.println("Responsáveis carregados do arquivo de texto.");
        } catch (IOException e) {
            System.err.println("Erro ao ler listaResponsaveis.txt: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carrega alunos do arquivo de texto listaAlunos.txt.
     * Formato: nome|cpf|responsavel|contato|email|senha
     */
    private static void carregarAlunosDeTexto() {
        File arquivo = new File("listaAlunos.txt");
        if (!arquivo.exists()) {
            System.out.println("Arquivo listaAlunos.txt não encontrado.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) {
                    continue;
                }

                String[] partes = linha.split("\\|");
                if (partes.length == 6) {
                    String nome = partes[0].trim();
                    String cpf = partes[1].trim();
                    String responsavel = partes[2].trim();
                    String contato = partes[3].trim();
                    String email = partes[4].trim();
                    String senha = partes[5].trim();

                    // Verificar se o aluno já existe (por CPF)
                    boolean existe = false;
                    for (Aluno aluno : getListaAluno()) {
                        if (aluno.getCpf().equals(cpf)) {
                            existe = true;
                            break;
                        }
                    }

                    if (!existe) {
                        Aluno aluno = new Aluno(nome, cpf, responsavel, contato, email, senha, false);
                        getListaAluno().add(aluno);
                    }
                } else {
                    System.err.println("Linha inválida em listaAlunos.txt: " + linha);
                }
            }
            System.out.println("Alunos carregados do arquivo de texto.");
        } catch (IOException e) {
            System.err.println("Erro ao ler listaAlunos.txt: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carrega veículos do arquivo de texto listaVeiculos.txt.
     * Formato: modelo|placa|capacidade
     */
    private static void carregarVeiculosDeTexto() {
        File arquivo = new File("listaVeiculos.txt");
        if (!arquivo.exists()) {
            System.out.println("Arquivo listaVeiculos.txt não encontrado.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) {
                    continue;
                }

                String[] partes = linha.split("\\|");
                if (partes.length == 3) {
                    String modelo = partes[0].trim();
                    String placa = partes[1].trim();
                    int capacidade;
                    try {
                        capacidade = Integer.parseInt(partes[2].trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Capacidade inválida na linha: " + linha);
                        continue;
                    }

                    // Verificar se o veículo já existe (por placa)
                    boolean existe = false;
                    for (Veiculo veiculo : getListaVeiculo()) {
                        if (veiculo.getPlaca().equals(placa)) {
                            existe = true;
                            break;
                        }
                    }

                    if (!existe) {
                        Veiculo veiculo = new Veiculo(modelo, placa, capacidade, false);
                        getListaVeiculo().add(veiculo);
                    }
                } else {
                    System.err.println("Linha inválida em listaVeiculos.txt: " + linha);
                }
            }
            System.out.println("Veículos carregados do arquivo de texto.");
        } catch (IOException e) {
            System.err.println("Erro ao ler listaVeiculos.txt: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}
