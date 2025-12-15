package org.example.schooltransport.model;

import javafx.collections.ObservableList;
import org.example.schooltransport.data.Repositorio;

public class Cadastro {

    private static Cadastro instance;

    private Cadastro() {
    }

    public static Cadastro getInstance() {
        if (instance == null) {
            instance = new Cadastro();
        }
        return instance;
    }

    // ================= PARADAS =================
    public void adicionarParada(Parada parada) {
        Repositorio.adicionarParada(parada);
    }

    public void removerParada(Parada parada) {
        Repositorio.removerParada(parada);
    }

    public ObservableList<Parada> getListaDeParadas() {
        return Repositorio.getListaParada();
    }

    // ================= OCORRÊNCIAS =================
    public void adicionarOcorrencia(Ocorrencia ocorrencia) {
        Repositorio.adicionarOcorrencia(ocorrencia);
    }

    public ObservableList<Ocorrencia> getListaOcorrencias() {
        return Repositorio.getListaOcorrencias();
    }
}
