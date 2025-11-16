package org.example.schooltransport.model;

import java.util.Date;

public class RegistroPresenca {

    private Aluno aluno;
    private boolean presente;
    private Date dataDoRegistro;

    public RegistroPresenca(Aluno aluno, boolean presente) {
        this.aluno = aluno;
        this.presente = presente;
        this.dataDoRegistro = new Date();
    }


    public Aluno getAluno() {
        return aluno;
    }

    public boolean isPresente() {
        return presente;
    }

    public Date getDataDoRegistro() {
        return dataDoRegistro;
    }
}