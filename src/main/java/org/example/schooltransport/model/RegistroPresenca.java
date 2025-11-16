package org.example.schooltransport.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RegistroPresenca {
    private Aluno aluno;
    private LocalDate data;
    private boolean presente;

    /**
     * Construtor para criar um registro de presença.
     * 
     * @param aluno O aluno relacionado ao registro
     * @param data A data do registro
     * @param presente true se o aluno estava presente, false se estava ausente
     */
    public RegistroPresenca(Aluno aluno, LocalDate data, boolean presente) {
        this.aluno = aluno;
        this.data = data;
        this.presente = presente;
    }

    /**
     * Construtor que usa a data atual.
     * 
     * @param aluno O aluno relacionado ao registro
     * @param presente true se o aluno estava presente, false se estava ausente
     */
    public RegistroPresenca(Aluno aluno, boolean presente) {
        this(aluno, LocalDate.now(), presente);
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }

    /** Retorna a data formatada como string (dd/MM/yyyy).
     * @return Data formatada
     */
    public String getDataFormatada() {
        return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    /**Retorna o status formatado (Presente ou Ausente). 
     * @return Status formatado
     */
    public String getStatusFormatado() {
        return presente ? "Presente" : "Ausente";
    }
}

