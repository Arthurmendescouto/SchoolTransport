package org.example.schooltransport;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cadastro {

    private static final ObservableList<Parada> listaDeParadas = FXCollections.observableArrayList();

    private static Cadastro instance;

    private Cadastro() {}

    public static Cadastro getInstance() {
        if (instance == null) {
            instance = new Cadastro();
        }
        return instance;
    }

    public void adicionarParada(Parada parada) {
        listaDeParadas.add(parada);
    }

    public ObservableList<Parada> getListaDeParadas() {
        return listaDeParadas;
    }
}