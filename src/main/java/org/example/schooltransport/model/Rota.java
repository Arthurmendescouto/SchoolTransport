package org.example.schooltransport.model;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
        salvarEmArquivo();
    }

       private void salvarEmArquivo() {
        try (FileWriter fw = new FileWriter("listaRotas.txt", true);
            PrintWriter pw = new PrintWriter(fw)) {
            pw.println(this.getMotorista() + "|" + this.getOnibus() + "|" + this.getTurno());
        } catch (IOException e) {
            System.out.println("Erro ao salvar rota em arquivo: " + e.getMessage());
            e.printStackTrace();
        }
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

    /**
     * Verifica se a rota pode adicionar mais paradas baseado na capacidade do veículo.
     * @return true se ainda há espaço no veículo, false caso contrário
     */
    public boolean podeAdicionarParada() {
        if (onibus == null) {
            return false;
        }
        return paradas.size() < onibus.getCapacidade();
    }

    /**
     * Retorna a quantidade de paradas atualmente na rota.
     * @return número de paradas
     */
    public int getQuantidadeParadas() {
        return paradas.size();
    }

    /**
     * Retorna o espaço disponível no veículo.
     * @return capacidade restante do veículo
     */
    public int getEspacoDisponivel() {
        if (onibus == null) {
            return 0;
        }
        return onibus.getCapacidade() - paradas.size();
    }
    
    /**
     * Retorna a informação de capacidade do veículo.
     * @return capacidade total do veículo
     */
    public int getCapacidadeVeiculo() {
        if (onibus == null) {
            return 0;
        }
        return onibus.getCapacidade();
    }
    
    /**
     * Obtém uma descrição do status da capacidade.
     * @return string descrevendo paradas/capacidade
     */
    public String getStatusCapacidade() {
        return getQuantidadeParadas() + "/" + getCapacidadeVeiculo();
    }
}
