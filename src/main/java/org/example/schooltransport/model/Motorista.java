package org.example.schooltransport.model;

import java.io.Serializable;

/**
 * Classe Motorista — estende Pessoa e adiciona dados específicos do motorista.
 */
public class Motorista extends Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    private String cnh;

    /**
     * Cria um motorista com os dados informados.
     * @param nome Nome completo
     * @param cpf CPF
     * @param contato Telefone de contato
     * @param email E-mail de acesso
     * @param senha Senha de acesso
     * @param cnh Número da CNH
     */
    public Motorista(String nome, String cpf, String contato, String email, String senha, String cnh) {
        super(nome, cpf, contato, email, senha);
        this.cnh = cnh;
    }

    /**
     * Retorna a CNH do motorista.
     * @return CNH cadastrada
     */
    public String getCnh() {
        return cnh;
    }

    /**
     * Atualiza a CNH do motorista.
     * @param cnh Novo número de CNH
     */
    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    /**
     * Representação amigável para listagens e seletores.
     */
    @Override
    public String toString() {
        return nome != null ? nome : "Motorista";
    }
}
