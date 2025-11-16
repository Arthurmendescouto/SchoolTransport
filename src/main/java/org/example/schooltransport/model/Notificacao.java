package org.example.schooltransport.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo simples de Notificação associado a um CPF (pessoaCPF).
 */
public class Notificacao {
    private String pessoaCPF;
    private List<String> notificacoes = new ArrayList<>();

    public Notificacao(String pessoaCPF) {
        this.pessoaCPF = pessoaCPF;
        this.notificacoes = new ArrayList<>();
    }

    public String getPessoaCPF() {
        return pessoaCPF;
    }

    public void setPessoaCPF(String pessoaCPF) {
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

    public void removerNotificacao(String conteudo) {
        this.notificacoes.remove(conteudo);
    }
}
