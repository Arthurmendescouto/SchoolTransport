package org.example.schooltransport.model;

/**
 * Representa um veículo utilizado no transporte escolar.
 */
public class Veiculo {
    String modelo;
    String placa;
    int capacidade;

    /**
     * Constrói um veículo com as informações básicas.
     * @param modelo Modelo/fabricante do veículo
     * @param placa Placa do veículo
     * @param capacidade Capacidade de passageiros
     */
    public Veiculo (String modelo, String placa, int capacidade){
        this.modelo = modelo;
        this.placa = placa;
        this.capacidade=capacidade;
    }

    /**
     * Retorna o modelo do veículo.
     */
    public String getModelo(){
        return modelo;
    }

    /**
     * Retorna a placa do veículo.
     */
    public String getPlaca(){
        return placa;
    }

    /**
     * Retorna a capacidade de passageiros.
     */
    public int getCapacidade(){
        return capacidade;
    }

    /**
     * Atualiza o modelo do veículo.
     */
    public void setModelo(String modelo){
        this.modelo = modelo;
    }

    /**
     * Atualiza a placa do veículo.
     */
    public void setPlaca(String placa){
        this.placa = placa;
    }

    /**
     * Atualiza a capacidade de passageiros.
     */
    public void setCapacidade(int capacidade){
        this.capacidade = capacidade;
    }

    /**
     * Retorna uma representação curta para listagens.
     */
    @Override
    public String toString() {
        return modelo + " - " + placa;
    }
}
