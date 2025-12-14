package org.example.schooltransport.model;

import java.io.Serializable;

/**
 * Representa um aluno e a referência ao seu responsável (CPF do responsável).
 */
public class Aluno extends Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    private String responsavel;

    /**
     * Constrói um aluno.
     * @param nome Nome do aluno
     * @param cpf CPF do aluno
     * @param responsavel CPF do responsável
     * @param contato Telefone de contato
     * @param email E-mail de acesso
     * @param senha Senha de acesso
     */
    public Aluno(String nome, String cpf, String responsavel, String contato, String email, String senha) {
        super(nome, cpf, contato, email, senha);
        this.responsavel = responsavel;
    }

    /** @return CPF do responsável vinculado */
    public String getResponsavel() {
        return responsavel;
    }

    /** Define o CPF do responsável vinculado */
    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    @Override
    public String toString() {
        return this.getNome(); // ou: nome + " (" + cpf + ")"
    }

}
