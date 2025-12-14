package org.example.schooltransport.model;

import java.io.Serializable;
import java.util.Date;

public class RegistroPresenca implements Serializable {
    private static final long serialVersionUID = 1L;

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