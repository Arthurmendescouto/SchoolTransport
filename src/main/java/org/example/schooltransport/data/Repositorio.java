package org.example.schooltransport.data;

import java.util.ArrayList;
import java.util.LinkedList;

import org.example.schooltransport.Parada;
import org.example.schooltransport.model.Aluno;
import org.example.schooltransport.model.Motorista;
import org.example.schooltransport.model.Veiculo;
import org.example.schooltransport.model.Responsavel;
import org.example.schooltransport.model.Rota;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Repositorio {
    private static ArrayList<Aluno> listaAluno = new ArrayList<>();
    private static ArrayList<Responsavel> listaResponsavel = new ArrayList<>();
    private static ArrayList<Veiculo> listaVeiculo = new ArrayList<>();
    private static ArrayList<Motorista> listaMotorista = new ArrayList<>();
    private static LinkedList<Rota> listaRota = new LinkedList<>();
    private static ObservableList<Parada> listaParada = FXCollections.observableArrayList();
    
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
    
    public static ObservableList<Parada> getListaParada() {
        return listaParada;
    }
    
    public static void adicionarParada(Parada parada) {
        listaParada.add(parada);
    }
    
    public static void removerParada(Parada parada) {
        listaParada.remove(parada);
    }

}
