package org.example.schooltransport.data;

import java.util.ArrayList;

import org.example.schooltransport.Parada;
import org.example.schooltransport.model.Aluno;
import org.example.schooltransport.model.Veiculo;
import org.example.schooltransport.model.Responsavel;
import org.example.schooltransport.model.Rota;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Repositorio {
    private static ArrayList<Aluno> listaAluno = new ArrayList<>();
    private static ArrayList<Responsavel> listaResponsavel = new ArrayList<>();
    private static ArrayList<Veiculo> listaVeiculo = new ArrayList<>();
    private static ArrayList<Rota> listaRota = new ArrayList<>();
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
    public static ArrayList<Rota> getListaRota() {
        return listaRota;
    }
    public static void setListaRota(ArrayList<Rota> listaRota) {
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
