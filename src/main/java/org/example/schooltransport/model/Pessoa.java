package org.example.schooltransport.model;

public class Pessoa {
    protected String nome;
    protected String cpf;
    protected String contato;
    protected String email;
    protected String senha;

    public Pessoa(String nome, String cpf, String contato, String email, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.contato = contato;
        this.email = email;
        this.senha = senha;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getContato() { return contato; }
    public void setContato(String contato) { this.contato = contato; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }


}
