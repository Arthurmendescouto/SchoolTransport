package org.example.schooltransport.model;

public class Aluno extends Pessoa {
    private String responsavel;
    private String parada;

    public Aluno(String nome, String cpf, String responsavel, String contato, String parada, String email, String senha) {
        super(nome, cpf, contato, email, senha);
        this.responsavel = responsavel;
        this.parada = parada;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getParada() {
        return parada;
    }

    public void setParada(String parada) {
        this.parada = parada;
    }
}
