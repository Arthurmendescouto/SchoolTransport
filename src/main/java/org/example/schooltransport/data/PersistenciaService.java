package org.example.schooltransport.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.example.schooltransport.model.Aluno;
import org.example.schooltransport.model.Responsavel;
import org.example.schooltransport.model.Veiculo;

/**
 * Serviço responsável por persistir e recuperar dados do sistema em arquivos de texto (.txt).
 * Carrega dados dos arquivos listaResponsaveis.txt, listaAlunos.txt e listaVeiculos.txt.
 */
public class PersistenciaService {

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
                    for (Responsavel resp : Repositorio.getListaResponsavel()) {
                        if (resp.getCpf().equals(cpf)) {
                            existe = true;
                            break;
                        }
                    }

                    if (!existe) {
                        Responsavel responsavel = new Responsavel(nome, cpf, contato, email, senha, false);
                        Repositorio.getListaResponsavel().add(responsavel);
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
                    for (Aluno aluno : Repositorio.getListaAluno()) {
                        if (aluno.getCpf().equals(cpf)) {
                            existe = true;
                            break;
                        }
                    }

                    if (!existe) {
                        Aluno aluno = new Aluno(nome, cpf, responsavel, contato, email, senha);
                        Repositorio.getListaAluno().add(aluno);
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
                    for (Veiculo veiculo : Repositorio.getListaVeiculo()) {
                        if (veiculo.getPlaca().equals(placa)) {
                            existe = true;
                            break;
                        }
                    }

                    if (!existe) {
                        Veiculo veiculo = new Veiculo(modelo, placa, capacidade, false);
                        Repositorio.getListaVeiculo().add(veiculo);
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

