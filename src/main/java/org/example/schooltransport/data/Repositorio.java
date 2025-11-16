package org.example.schooltransport.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.example.schooltransport.model.Notificacao;

import org.example.schooltransport.model.Parada;
import org.example.schooltransport.model.Aluno;
import org.example.schooltransport.model.Motorista;
import org.example.schooltransport.model.Veiculo;
import org.example.schooltransport.model.Responsavel;
import org.example.schooltransport.model.Rota;
import org.example.schooltransport.model.RegistroPresenca;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Repositório em memória para entidades do sistema e informações de sessão.
 * Fornece listas estáticas para uso pela aplicação JavaFX.
 */
public class Repositorio {
    private static ArrayList<Aluno> listaAluno = new ArrayList<>();
    private static ArrayList<Responsavel> listaResponsavel = new ArrayList<>();
    private static ArrayList<Veiculo> listaVeiculo = new ArrayList<>();
    private static ArrayList<Motorista> listaMotorista = new ArrayList<>();
    private static LinkedList<Rota> listaRota = new LinkedList<>();
    private static List<Notificacao> listaNotificacao = new ArrayList<>();
    private static ObservableList<Parada> listaParada = FXCollections.observableArrayList();
    private static ArrayList<RegistroPresenca> listaRegistroPresenca = new ArrayList<>();
    // pequeno armazenamento de sessão: CPF e tipo do usuário logado
    private static String currentUserCpf = null;
    private static char currentUserType = 'X';
    
    // Bloco estático de inicialização - cria usuário padrão de motorista
    static {
        inicializarMotoristaPadrao();
    }
    
    /**
     * Inicializa um motorista padrão no sistema.
     * Email: motorista@email.com
     * Senha: 1234
     */
    private static void inicializarMotoristaPadrao() {
        // Verifica se já existe um motorista com esse email para evitar duplicatas
        boolean jaExiste = false;
        for (Motorista m : listaMotorista) {
            if (m != null && "motorista@email.com".equals(m.getEmail())) {
                jaExiste = true;
                break;
            }
        }
        
        // Se não existe, cria o motorista padrão
        if (!jaExiste) {
            Motorista motoristaPadrao = new Motorista(
                "Motorista Padrão",
                "000.000.000-00",
                "(00) 00000-0000",
                "motorista@email.com",
                "1234",
                "12345678901"
            );
            listaMotorista.add(motoristaPadrao);
            System.out.println("Motorista padrão criado: " + motoristaPadrao.getEmail());
        }
    }
    
    /** Retorna a lista de alunos. */
    public static ArrayList<Aluno> getListaAluno() {
        return listaAluno;
    }
    /** Substitui a lista de alunos. */
    public static void setListaAluno(ArrayList<Aluno> listaAluno) {
        Repositorio.listaAluno = listaAluno;
    }
    /** Retorna a lista de responsáveis. */
    public static ArrayList<Responsavel> getListaResponsavel() {
        return listaResponsavel;
    }
    /** Substitui a lista de responsáveis. */
    public static void setListaResponsavel(ArrayList<Responsavel> listaResponsavel) {
        Repositorio.listaResponsavel = listaResponsavel;
    }
    /** Retorna a lista de veículos. */
    public static ArrayList<Veiculo> getListaVeiculo() {
        return listaVeiculo;
    }
    /** Substitui a lista de veículos. */
    public static void setListaVeiculo(ArrayList<Veiculo> listaVeiculo) {
        Repositorio.listaVeiculo = listaVeiculo;
    }
    /** Retorna a lista de motoristas. */
    public static ArrayList<org.example.schooltransport.model.Motorista> getListaMotorista() {
        return listaMotorista;
    }
    /** Substitui a lista de motoristas. */
    public static void setListaMotorista(ArrayList<org.example.schooltransport.model.Motorista> listaMotorista) {
        Repositorio.listaMotorista = listaMotorista;
    }
    /** Retorna a lista de rotas. */
    public static LinkedList<Rota> getListaRota() {
        return listaRota;
    }
    /** Substitui a lista de rotas. */
    public static void setListaRota(LinkedList<Rota> listaRota) {
        Repositorio.listaRota = listaRota;
    }

    // Notificações
    /** Retorna a lista de notificações por CPF. */
    public static List<Notificacao> getListaNotificacao() {
        return listaNotificacao;
    }

    /**
     * Busca um registro de notificações pelo CPF.
     * @param cpf CPF do titular
     * @return objeto de notificações ou null se não encontrado
     */
    public static Notificacao getNotificacaoPorCpf(String cpf) {
        if (cpf == null) return null;
        for (Notificacao n : listaNotificacao) {
            if (cpf.equals(n.getPessoaCPF())) return n;
        }
        return null;
    }

    /**
     * Adiciona uma notificação de texto a um CPF, criando o agrupador caso necessário.
     */
    public static void adicionarNotificacaoParaCpf(String cpf, String conteudo) {
        if (cpf == null || conteudo == null) return;
        Notificacao n = getNotificacaoPorCpf(cpf);
        if (n == null) {
            n = new Notificacao(cpf);
            listaNotificacao.add(n);
        }
        n.adicionarNotificacao(conteudo);
    }

    /** Remove uma notificação de texto de um CPF. */
    public static void removerNotificacaoParaCpf(String cpf, String conteudo) {
        Notificacao n = getNotificacaoPorCpf(cpf);
        if (n != null) {
            n.removerNotificacao(conteudo);
        }
    }
    
    /** Retorna a lista observável de paradas. */
    public static ObservableList<Parada> getListaParada() {
        return listaParada;
    }
    
    /** Adiciona uma parada à lista observável. */
    public static void adicionarParada(Parada parada) {
        listaParada.add(parada);
    }
    
    /** Remove uma parada da lista observável. */
    public static void removerParada(Parada parada) {
        listaParada.remove(parada);
    }

    // Sessão (usuário logado)
    /** Retorna o CPF do usuário logado (sessão). */
    public static String getCurrentUserCpf() {
        return currentUserCpf;
    }

    /** Define o CPF do usuário logado (sessão). */
    public static void setCurrentUserCpf(String cpf) {
        currentUserCpf = cpf;
    }

    /** Retorna o tipo do usuário logado (A/R/M). */
    public static char getCurrentUserType() {
        return currentUserType;
    }

    /** Define o tipo do usuário logado (A/R/M). */
    public static void setCurrentUserType(char tipo) {
        currentUserType = tipo;
    }

    /** Limpa informações de sessão atual. */
    public static void clearSession() {
        currentUserCpf = null;
        currentUserType = 'X';
    }

    // Registros de Presença
    /** Retorna a lista de registros de presença. */
    public static ArrayList<RegistroPresenca> getListaRegistroPresenca() {
        return listaRegistroPresenca;
    }

    /** Adiciona um registro de presença à lista. */
    public static void adicionarRegistroPresenca(RegistroPresenca registro) {
        if (registro != null) {
            listaRegistroPresenca.add(registro);
        }
    }

    /**
     * Retorna todos os alunos do sistema.
     * @return Lista de todos os alunos
     */
    public static ArrayList<Aluno> getTodosAlunos() {
        return new ArrayList<>(listaAluno);
    }

    /**
     * Busca alunos vinculados a um responsável pelo CPF ou nome.
     * @param identificadorResponsavel CPF ou nome do responsável
     * @return Lista de alunos vinculados ao responsável
     */
    public static ArrayList<Aluno> getAlunosPorResponsavel(String identificadorResponsavel) {
        ArrayList<Aluno> alunos = new ArrayList<>();
        if (identificadorResponsavel == null || identificadorResponsavel.isEmpty()) {
            return alunos;
        }

        // Busca por CPF primeiro
        for (Aluno aluno : listaAluno) {
            if (aluno != null && identificadorResponsavel.equals(aluno.getResponsavel())) {
                alunos.add(aluno);
            }
        }

        // Se não encontrou por CPF, tenta buscar por nome do responsável
        if (alunos.isEmpty()) {
            for (Responsavel responsavel : listaResponsavel) {
                if (responsavel != null && identificadorResponsavel.equals(responsavel.getNome())) {
                    String cpfResponsavel = responsavel.getCpf();
                    for (Aluno aluno : listaAluno) {
                        if (aluno != null && cpfResponsavel.equals(aluno.getResponsavel())) {
                            alunos.add(aluno);
                        }
                    }
                    break;
                }
            }
        }

        return alunos;
    }

    /**
     * Retorna todas as faltas (registros onde presente = false) do sistema.
     * @return Lista de todas as faltas
     */
    public static ArrayList<RegistroPresenca> getTodasFaltas() {
        ArrayList<RegistroPresenca> faltas = new ArrayList<>();
        for (RegistroPresenca registro : listaRegistroPresenca) {
            if (registro != null && !registro.isPresente()) {
                faltas.add(registro);
            }
        }
        return faltas;
    }

    /**
     * Busca faltas de um aluno específico.
     * @param aluno O aluno
     * @return Lista de faltas do aluno (registros onde presente = false)
     */
    public static ArrayList<RegistroPresenca> getFaltasPorAluno(Aluno aluno) {
        ArrayList<RegistroPresenca> faltas = new ArrayList<>();
        if (aluno == null) {
            return faltas;
        }

        for (RegistroPresenca registro : listaRegistroPresenca) {
            if (registro != null && 
                registro.getAluno() != null && 
                aluno.getCpf().equals(registro.getAluno().getCpf()) &&
                !registro.isPresente()) {
                faltas.add(registro);
            }
        }
        return faltas;
    }

    /**
     * Busca faltas de todos os alunos vinculados a um responsável.
     * @param identificadorResponsavel CPF ou nome do responsável
     * @return Lista de faltas dos alunos do responsável
     */
    public static ArrayList<RegistroPresenca> getFaltasPorResponsavel(String identificadorResponsavel) {
        ArrayList<RegistroPresenca> faltas = new ArrayList<>();
        if (identificadorResponsavel == null || identificadorResponsavel.isEmpty()) {
            return faltas;
        }

        // Busca os alunos do responsável
        ArrayList<Aluno> alunos = getAlunosPorResponsavel(identificadorResponsavel);
        
        // Para cada aluno, busca suas faltas
        for (Aluno aluno : alunos) {
            faltas.addAll(getFaltasPorAluno(aluno));
        }

        return faltas;
    }

}
