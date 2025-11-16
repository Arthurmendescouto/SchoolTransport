package org.example.schooltransport.model;

public class Aluno extends Pessoa {
    private String responsavel;

    public Aluno(String nome, String cpf, String responsavel, String contato, String email, String senha) {
        super(nome, cpf, contato, email, senha);
        this.responsavel = responsavel;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }
}
