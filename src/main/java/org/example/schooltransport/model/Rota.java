package org.example.schooltransport.model;
import java.util.ArrayList;
import org.example.schooltransport.model.Parada;

public class Rota {
    private Motorista motorista;
    private Veiculo onibus;
    private String turno;
    private ArrayList<Parada> paradas = new ArrayList<>();

    public Rota(Motorista motorista, Veiculo onibus, String turno, ArrayList<Parada> paradas) {
        this.motorista = motorista;
        this.onibus = onibus;
        this.turno = turno;
        this.paradas = paradas;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public Veiculo getOnibus() {
        return onibus;
    }

    public void setOnibus(Veiculo onibus) {
        this.onibus = onibus;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public ArrayList<Parada> getParadas() {
        return paradas;
    }

    public void setParadas(ArrayList<Parada> paradas) {
        this.paradas = paradas;
    }
}
