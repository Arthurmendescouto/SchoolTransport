package org.example.schooltransport.model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * Representa um veículo utilizado no transporte escolar.
 */
public class Veiculo implements Serializable {
    private static final long serialVersionUID = 1L;
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
        salvarEmArquivo();
    }

    /**
     * Constrói um veículo sem salvar automaticamente no arquivo.
     * Usado para carregar dados do arquivo sem duplicação.
     * @param modelo Modelo/fabricante do veículo
     * @param placa Placa do veículo
     * @param capacidade Capacidade de passageiros
     * @param salvar Se false, não salva automaticamente no arquivo
     */
    public Veiculo (String modelo, String placa, int capacidade, boolean salvar){
        this.modelo = modelo;
        this.placa = placa;
        this.capacidade=capacidade;
        if (salvar) {
            salvarEmArquivo();
        }
    }

       private void salvarEmArquivo() {
        try (FileWriter fw = new FileWriter("listaVeiculos.txt", true);
            PrintWriter pw = new PrintWriter(fw)) {
            pw.println(this.getModelo() + "|" + this.getPlaca() + "|" + this.getCapacidade());
        } catch (IOException e) {
            System.out.println("Erro ao salvar veículo em arquivo: " + e.getMessage());
            e.printStackTrace();
        }
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
