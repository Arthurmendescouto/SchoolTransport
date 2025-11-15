package org.example.schooltransport.model;

import java.util.ArrayList;
import java.util.List;

public class Notificacao {

    private String pessoaCPF;
    private List<String> notificacoes = new ArrayList<>();

    public Notificacao(long pessoaId) {
        this.pessoaId = pessoaId;
        this.notificacoes = new ArrayList<>();
    }

    public String getPessoaId() {
        return pessoaCPF;
    }

    public void setPessoaId(long pessoaId) {
        this.pessoaCPF = pessoaCPF;
    }

    public List<String> getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(List<String> notificacoes) {
        this.notificacoes = notificacoes;
    }

    public void adicionarNotificacao(String conteudo) {
        this.notificacoes.add(conteudo);
    }
}
