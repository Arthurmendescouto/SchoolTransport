package org.example.schooltransport.model;

import java.io.Serializable;

/**
 * Representa o responsável (guardião) por um ou mais alunos.
 */
public class Responsavel extends Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Constrói um responsável.
     * @param nome Nome do responsável
     * @param cpf CPF do responsável
     * @param contato Telefone de contato
     * @param email E-mail de acesso
     * @param senha Senha de acesso
     */
    public Responsavel (String nome, String cpf, String contato, String email, String senha) {
        super(nome, cpf, contato, email, senha);
    }

}
