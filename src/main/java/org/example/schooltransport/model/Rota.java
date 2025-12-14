package org.example.schooltransport.model;
import java.io.Serializable;
import java.util.ArrayList;
import org.example.schooltransport.model.Parada;

/**
 * Descreve uma rota de transporte contendo motorista, veículo, turno e paradas.
 */
public class Rota implements Serializable {
    private static final long serialVersionUID = 1L;
    private Motorista motorista;
    private Veiculo onibus;
    private String turno;
    private ArrayList<Parada> paradas = new ArrayList<>();

    /**
     * Constrói uma rota completa.
     * @param motorista Motorista responsável
     * @param onibus Veículo utilizado
     * @param turno Turno (ex.: Manhã, Tarde)
     * @param paradas Lista de paradas na ordem prevista
     */
    public Rota(Motorista motorista, Veiculo onibus, String turno, ArrayList<Parada> paradas) {
        this.motorista = motorista;
        this.onibus = onibus;
        this.turno = turno;
        this.paradas = paradas;
    }

    /** Retorna o motorista da rota. */
    public Motorista getMotorista() {
        return motorista;
    }

    /** Define o motorista da rota. */
    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    /** Retorna o veículo (ônibus) da rota. */
    public Veiculo getOnibus() {
        return onibus;
    }

    /** Define o veículo (ônibus) da rota. */
    public void setOnibus(Veiculo onibus) {
        this.onibus = onibus;
    }

    /** Retorna o turno da rota. */
    public String getTurno() {
        return turno;
    }

    /** Atualiza o turno da rota. */
    public void setTurno(String turno) {
        this.turno = turno;
    }

    /** Obtém a lista de paradas. */
    public ArrayList<Parada> getParadas() {
        return paradas;
    }

    /** Define a lista de paradas. */
    public void setParadas(ArrayList<Parada> paradas) {
        this.paradas = paradas;
    }
}
