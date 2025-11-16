package org.example.schooltransport.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo simples de Notificação associado a um CPF (pessoaCPF).
 */
public class Notificacao {
    private String pessoaCPF;
    private List<String> notificacoes = new ArrayList<>();

    /**
     * Cria um agrupador de notificações vinculado a um CPF.
     * @param pessoaCPF CPF da pessoa titular das notificações
     */
    public Notificacao(String pessoaCPF) {
        this.pessoaCPF = pessoaCPF;
        this.notificacoes = new ArrayList<>();
    }

    /** Retorna o CPF vinculado. */
    public String getPessoaCPF() {
        return pessoaCPF;
    }

    /** Atualiza o CPF vinculado. */
    public void setPessoaCPF(String pessoaCPF) {
        this.pessoaCPF = pessoaCPF;
    }

    /** Lista as mensagens de notificação. */
    public List<String> getNotificacoes() {
        return notificacoes;
    }

    /** Substitui a lista de notificações. */
    public void setNotificacoes(List<String> notificacoes) {
        this.notificacoes = notificacoes;
    }

    /** Adiciona uma notificação de texto. */
    public void adicionarNotificacao(String conteudo) {
        this.notificacoes.add(conteudo);
    }

    /** Remove uma notificação de texto. */
    public void removerNotificacao(String conteudo) {
        this.notificacoes.remove(conteudo);
    }
}
