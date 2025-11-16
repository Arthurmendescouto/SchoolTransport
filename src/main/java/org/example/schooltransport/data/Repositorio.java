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
    // pequeno armazenamento de sessão: CPF e tipo do usuário logado
    private static String currentUserCpf = null;
    private static char currentUserType = 'X';
    
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

}
