package org.example.schooltransport;

import org.example.schooltransport.data.Repositorio;
import org.example.schooltransport.model.Parada;

import javafx.collections.ObservableList;

public class Cadastro {

    private static Cadastro instance;

    private Cadastro() {}

    public static Cadastro getInstance() {
        if (instance == null) {
            instance = new Cadastro();
        }
        return instance;
    }

    public void adicionarParada(Parada parada) {
        Repositorio.adicionarParada(parada);
    }

    public void removerParada(Parada parada){
        Repositorio.removerParada(parada);
    }

    public ObservableList<Parada> getListaDeParadas() {
        return Repositorio.getListaParada();
    }
}