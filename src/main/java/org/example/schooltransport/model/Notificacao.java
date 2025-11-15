package org.example.schooltransport.model;

import java.util.ArrayList;
import java.util.List;

public class Notificacao {

    private long pessoaId;
    private List<String> notificacoes = new ArrayList<>();

    public Notificacao(long pessoaId) {
        this.pessoaId = pessoaId;
        this.notificacoes = new ArrayList<>();
    }

    public long getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(long pessoaId) {
        this.pessoaId = pessoaId;
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
