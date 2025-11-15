package org.example.schooltransport.model;

public class Notificacao {
    private Pessoa pessoa;
    private String conteudo;

    public Notificacao(Pessoa pessoa, String conteudo) {
        this.pessoa = pessoa;
        this.conteudo = conteudo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}
