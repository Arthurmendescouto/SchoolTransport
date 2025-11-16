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
import org.example.schooltransport.model.RegistroPresenca; // 1. NOVA IMPORTAÇÃO

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Repositorio {
    private static ArrayList<Aluno> listaAluno = new ArrayList<>();
    private static ArrayList<Responsavel> listaResponsavel = new ArrayList<>();
    private static ArrayList<Veiculo> listaVeiculo = new ArrayList<>();
    private static ArrayList<Motorista> listaMotorista = new ArrayList<>();
    private static LinkedList<Rota> listaRota = new LinkedList<>();
    private static List<Notificacao> listaNotificacao = new ArrayList<>();
    private static ObservableList<Parada> listaParada = FXCollections.observableArrayList();

    // 2. NOVA LISTA DE PRESENÇA
    private static ArrayList<RegistroPresenca> listaDePresenca = new ArrayList<>();

    // pequeno armazenamento de sessão: CPF e tipo do usuário logado
    private static String currentUserCpf = null;
    private static char currentUserType = 'X';

    public static ArrayList<Aluno> getListaAluno() {
        return listaAluno;
    }
    public static void setListaAluno(ArrayList<Aluno> listaAluno) {
        Repositorio.listaAluno = listaAluno;
    }
    public static ArrayList<Responsavel> getListaResponsavel() {
        return listaResponsavel;
    }
    public static void setListaResponsavel(ArrayList<Responsavel> listaResponsavel) {
        Repositorio.listaResponsavel = listaResponsavel;
    }
    public static ArrayList<Veiculo> getListaVeiculo() {
        return listaVeiculo;
    }
    public static void setListaVeiculo(ArrayList<Veiculo> listaVeiculo) {
        Repositorio.listaVeiculo = listaVeiculo;
    }
    public static ArrayList<org.example.schooltransport.model.Motorista> getListaMotorista() {
        return listaMotorista;
    }
    public static void setListaMotorista(ArrayList<org.example.schooltransport.model.Motorista> listaMotorista) {
        Repositorio.listaMotorista = listaMotorista;
    }
    public static LinkedList<Rota> getListaRota() {
        return listaRota;
    }
    public static void setListaRota(LinkedList<Rota> listaRota) {
        Repositorio.listaRota = listaRota;
    }

    public static void adicionarRegistroPresenca(RegistroPresenca registro) {
        listaDePresenca.add(registro);
    }

    public static ArrayList<RegistroPresenca> getListaDePresenca() {
        return listaDePresenca;
    }
    // =============================================

    // Notificações
    public static List<Notificacao> getListaNotificacao() {
        return listaNotificacao;
    }

    public static Notificacao getNotificacaoPorCpf(String cpf) {
        if (cpf == null) return null;
        for (Notificacao n : listaNotificacao) {
            if (cpf.equals(n.getPessoaCPF())) return n;
        }
        return null;
    }

    public static void adicionarNotificacaoParaCpf(String cpf, String conteudo) {
        if (cpf == null || conteudo == null) return;
        Notificacao n = getNotificacaoPorCpf(cpf);
        if (n == null) {
            n = new Notificacao(cpf);
            listaNotificacao.add(n);
        }
        n.adicionarNotificacao(conteudo);
    }

    public static void removerNotificacaoParaCpf(String cpf, String conteudo) {
        Notificacao n = getNotificacaoPorCpf(cpf);
        if (n != null) {
            n.removerNotificacao(conteudo);
        }
    }

    public static ObservableList<Parada> getListaParada() {
        return listaParada;
    }

    public static void adicionarParada(Parada parada) {
        listaParada.add(parada);
    }

    public static void removerParada(Parada parada) {
        listaParada.remove(parada);
    }

    // Sessão (usuário logado)
    public static String getCurrentUserCpf() {
        return currentUserCpf;
    }

    public static void setCurrentUserCpf(String cpf) {
        currentUserCpf = cpf;
    }

    public static char getCurrentUserType() {
        return currentUserType;
    }

    public static void setCurrentUserType(char tipo) {
        currentUserType = tipo;
    }

    public static void clearSession() {

        currentUserCpf = null;
        currentUserType = 'X';
    }

}