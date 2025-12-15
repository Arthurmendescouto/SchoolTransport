package org.example.schooltransport.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.schooltransport.model.Parada;
import org.example.schooltransport.model.Ocorrencia;

public class Repositorio {

    // ================= PARADAS =================
    private static ObservableList<Parada> listaParadas =
            FXCollections.observableArrayList();

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

    static {


        org.example.schooltransport.model.Motorista m1 = new org.example.schooltransport.model.Motorista("Motorista Padrao", "motorista@email.com", "1234", "11122233344");
        listaMotorista.add(m1);
    }

    public static ObservableList<org.example.schooltransport.model.Aluno> getListaAluno() {
        return listaAluno;
    }
    public static ObservableList<org.example.schooltransport.model.Motorista> getListaMotorista() {
        return listaMotorista;
    }
    public static ObservableList<org.example.schooltransport.model.Responsavel> getListaResponsavel() {
        return listaResponsavel;
    }

    // ================= NOTIFICAÇÕES =================
    public static void adicionarNotificacaoParaCpf(String cpf, String mensagem) {
        System.out.println("[NOTIFICAÇÃO] Para " + cpf + ": " + mensagem);
        // Implementação real armazenaria em uma lista de notificações
    }
}
