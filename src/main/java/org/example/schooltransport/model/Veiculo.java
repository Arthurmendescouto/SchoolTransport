package org.example.schooltransport.model;

public class Veiculo {
    String modelo;
    String placa;
    int capacidade;

    public Veiculo (String modelo, String placa, int capacidade){
        this.modelo = modelo;
        this.placa = placa;
        this.capacidade=capacidade;
    }

    public String getModelo(){
        return modelo;
    }

    public String getPlaca(){
        return placa;
    }

    public int getCapacidade(){
        return capacidade;
    }

    public void setModelo(String modelo){
        this.modelo = modelo;
    }

    public void setPlaca(String placa){
        this.placa = placa;
    }

    public void setCapacidade(int capacidade){
        this.capacidade = capacidade;
    }
}
