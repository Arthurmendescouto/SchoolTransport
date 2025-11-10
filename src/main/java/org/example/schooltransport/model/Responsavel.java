package org.example.schooltransport.model;

public class Responsavel extends Pessoa{
    String nome;
    String cpf;
    String contato;

    public Responsavel (String nome, String cpf, String contato) {
        super(nome, cpf, contato);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

}
