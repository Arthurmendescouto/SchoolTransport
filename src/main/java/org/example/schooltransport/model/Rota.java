package org.example.schooltransport.model;

import java.util.ArrayList;

public class Rota {
    String motorista;
    String onibus;
    String turno;
    ArrayList<String> paradas = new ArrayList<>();

    public Rota(String motorista, String onibus, String turno, ArrayList<String> paradas) {
        this.motorista = motorista;
        this.onibus = onibus;
        this.turno = turno;
        this.paradas = paradas;
    }

    public String getMotorista() {
        return motorista;
    }

    public void setMotorista(String motorista) {
        this.motorista = motorista;
    }

    public String getOnibus() {
        return onibus;
    }

    public void setOnibus(String onibus) {
        this.onibus = onibus;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public ArrayList<String> getParadas() {
        return paradas;
    }

    public void setParadas(ArrayList<String> paradas) {
        this.paradas = paradas;
    }

    
    
}
