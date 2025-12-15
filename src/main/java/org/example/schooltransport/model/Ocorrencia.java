package org.example.schooltransport.model;

import java.time.LocalDateTime;

public class Ocorrencia {

    private String descricao;
    private LocalDateTime dataHora;
    private String autorCpf;

    public Ocorrencia(String descricao, String autorCpf) {
        this.descricao = descricao;
        this.autorCpf = autorCpf;
        this.dataHora = LocalDateTime.now();
    }

    public Ocorrencia(String descricao) {
         this(descricao, "ANONIMO");
    }

    public String getAutorCpf() {
        return autorCpf;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}
