package org.example.schooltransport.data;

import org.example.schooltransport.model.Frequencia;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


/**
 * Implementação em memória do repositório de frequências.
 * <p>
 * Os dados são armazenados apenas durante a execução da aplicação,
 * sem persistência em banco de dados ou arquivo.
 * </p>
 */
public class InMemoryFrequenciaRepositorio implements FrequenciaRepositorio {


    /** Lista interna que armazena os registros de frequência. */
    private final List<Frequencia> banco = new ArrayList<>();

    /** Gerador automático de IDs sequenciais. */
    private final AtomicLong seq = new AtomicLong(1);


    /**
     * Salva uma nova frequência no repositório.
     * <p>
     * Se a frequência não possuir ID, um valor sequencial será atribuído automaticamente.
     * </p>
     *
     * @param f Frequência a ser salva.
     * @return Frequência salva, com ID definido.
     */
    @Override
    public Frequencia salvar(Frequencia f) {
        if (f.getId() == null) f.setId(seq.getAndIncrement());
        banco.add(f);
        return f;
    }

   
    /**
     * Lista todas as frequências associadas a um aluno, buscando pelo ID.
     * <p>
     * Nota: No modelo atual do sistema, o aluno não possui um campo ID,
     * portanto este método devolve a lista completa.
     * Está implementado apenas para cumprir o contrato da interface.
     * </p>
     *
     * @param idAluno ID do aluno.
     * @return Lista de frequências do aluno (ou toda a lista se não houver ID).
     */
    @Override
    public List<Frequencia> listarPorAlunoId(Long idAluno) {
        return listarTodas();
    }

    
    /**
     * Lista frequências buscando pelo CPF do aluno.
     * <p>
     * Esse método é realmente funcional, pois o modelo Aluno possui CPF.
     * </p>
     *
     * @param cpfAluno CPF do aluno.
     * @return Lista contendo todos os registros de frequência desse aluno.
     */
    public List<Frequencia> listarPorAlunoCpf(String cpfAluno) {
        if (cpfAluno == null) return new ArrayList<>();
        return banco.stream()
                .filter(fr -> fr.getAluno() != null
                        && fr.getAluno().getCpf() != null
                        && fr.getAluno().getCpf().equals(cpfAluno))
                .collect(Collectors.toList());
    }


    /**
     * Lista todas as frequências registradas no repositório.
     *
     * @return Lista completa de frequências.
     */
    @Override
    public List<Frequencia> listarTodas() {
        return new ArrayList<>(banco);
    }
}
