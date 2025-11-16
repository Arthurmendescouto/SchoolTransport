package org.example.schooltransport.service;

import org.example.schooltransport.data.FrequenciaRepositorio;
import org.example.schooltransport.model.Aluno;
import org.example.schooltransport.model.Frequencia;

import java.time.LocalDate;
import java.util.List;

public class FrequenciaService {

    private final FrequenciaRepositorio repo;

    public FrequenciaService(FrequenciaRepositorio repo) {
        this.repo = repo;
    }

    public Frequencia registrarFalta(Aluno aluno) {
        Frequencia f = new Frequencia();
        f.setAluno(aluno);
        f.setData(LocalDate.now());
        f.setPresente(false);
        return repo.salvar(f);
    }

    public List<Frequencia> listarTodas() {
        return repo.listarTodas();
    }
}
