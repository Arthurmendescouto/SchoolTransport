package org.example.schooltransport.data;

import org.example.schooltransport.model.Frequencia;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InMemoryFrequenciaRepositorio implements FrequenciaRepositorio {

    private final List<Frequencia> banco = new ArrayList<>();
    private final AtomicLong seq = new AtomicLong(1);

    @Override
    public Frequencia salvar(Frequencia f) {
        if (f.getId() == null) f.setId(seq.getAndIncrement());
        banco.add(f);
        return f;
    }

   
    @Override
    public List<Frequencia> listarPorAlunoId(Long idAluno) {
        return listarTodas();
    }

    /**
     * Método útil para buscar frequências por CPF do aluno (identificador
     * que existe na classe Aluno do projeto)
     */
    public List<Frequencia> listarPorAlunoCpf(String cpfAluno) {
        if (cpfAluno == null) return new ArrayList<>();
        return banco.stream()
                .filter(fr -> fr.getAluno() != null
                        && fr.getAluno().getCpf() != null
                        && fr.getAluno().getCpf().equals(cpfAluno))
                .collect(Collectors.toList());
    }

    @Override
    public List<Frequencia> listarTodas() {
        return new ArrayList<>(banco);
    }
}
