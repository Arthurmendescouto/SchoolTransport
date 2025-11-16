package org.example.schooltransport.data;

import org.example.schooltransport.model.Frequencia;
import java.util.List;

/**
 * Interface responsável por definir operações de persistência relacionadas às
 * frequências registradas no sistema.
 */
public interface FrequenciaRepositorio {

    /**
     * Salva um registro de frequência no repositório.
     *
     * @param f Objeto de frequência a ser salvo.
     * @return A frequência salva com ID atribuído.
     */
    Frequencia salvar(Frequencia f);


    /**
     * Lista todas as frequências associadas a um aluno específico.
     *
     * @param idAluno ID do aluno.
     * @return Lista de frequências do aluno.
     */
    List<Frequencia> listarPorAlunoId(Long idAluno);



    /**
     * Retorna todas as frequências armazenadas.
     *
     * @return Lista completa de frequências.
     */
    List<Frequencia> listarTodas();
}
