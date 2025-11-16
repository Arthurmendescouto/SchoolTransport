package org.example.schooltransport.model;

/**
 * Classe Motorista — estende Pessoa e adiciona dados específicos do motorista.
 */
public class Motorista extends Pessoa {
    private String cnh;

    public Motorista(String nome, String cpf, String contato, String email, String senha, String cnh) {
        super(nome, cpf, contato, email, senha);
        this.cnh = cnh;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    @Override
    public String toString() {
        return nome != null ? nome : "Motorista";
    }
}
