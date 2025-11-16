package org.example.schooltransport.model;

import java.time.LocalDate;

public class Frequencia {
    private Long id;
    private Aluno aluno;
    private LocalDate data;
    private boolean presente;

    public Frequencia() {}

    public Frequencia(Long id, Aluno aluno, LocalDate data, boolean presente) {
        this.id = id;
        this.aluno = aluno;
        this.data = data;
        this.presente = presente;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public boolean isPresente() { return presente; }
    public void setPresente(boolean presente) { this.presente = presente; }
}
