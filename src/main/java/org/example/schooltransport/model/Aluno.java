package org.example.schooltransport.model;

public class Aluno extends  Pessoa {
    String nome;
    String cpf;
    String responsavel;
    String contato;
    String parada;
    String email;
    String senha;
    int nivel;

    public Aluno (String nome, String cpf, String responsavel, String contato, String parada, String email, String senha, int nivel){
        super(nome, cpf, contato, email, senha, nivel);
        this.responsavel=responsavel;
        this.parada=parada;
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

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getParada() {
        return parada;
    }

    public void setParada(String parada) {
        this.parada = parada;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    
    
}
