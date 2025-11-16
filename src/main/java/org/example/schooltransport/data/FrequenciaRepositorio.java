package org.example.schooltransport.data;

import org.example.schooltransport.model.Frequencia;
import java.util.List;

public interface FrequenciaRepositorio {
    Frequencia salvar(Frequencia f);
    List<Frequencia> listarPorAlunoId(Long idAluno);
    List<Frequencia> listarTodas();
}
