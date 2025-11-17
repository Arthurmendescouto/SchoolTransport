package org.example.schooltransport.model;

/**
 * Representa uma pessoa do sistema com dados básicos de identificação e contato.
 */
public class Pessoa {
    protected String nome;
    protected String cpf;
    protected String contato;
    protected String email;
    protected String senha;

    /**
     * Constrói uma pessoa.
     * @param nome Nome completo
     * @param cpf CPF da pessoa
     * @param contato Telefone ou meio de contato
     * @param email E-mail para login/contato
     * @param senha Senha de acesso
     */
    public Pessoa(String nome, String cpf, String contato, String email, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.contato = contato;
        this.email = email;
        this.senha = senha;
    }

    // Getters e Setters
    /** @return nome da pessoa */
    public String getNome() { return nome; }
    /** Define o nome da pessoa */
    public void setNome(String nome) { this.nome = nome; }

    /** @return CPF da pessoa */
    public String getCpf() { return cpf; }
    /** Define o CPF da pessoa */
    public void setCpf(String cpf) { this.cpf = cpf; }

    /** @return contato (telefone) */
    public String getContato() { return contato; }
    /** Define o contato (telefone) */
    public void setContato(String contato) { this.contato = contato; }

    /** @return e-mail */
    public String getEmail() { return email; }
    /** Define o e-mail */
    public void setEmail(String email) { this.email = email; }

    /** @return senha */
    public String getSenha() { return senha; }
    /** Define a senha */
    public void setSenha(String senha) { this.senha = senha; }


}
