package org.example.schooltransport.service;

import org.example.schooltransport.data.FrequenciaRepositorio;
import org.example.schooltransport.model.Aluno;
import org.example.schooltransport.model.Frequencia;

import java.time.LocalDate;
import java.util.List;


/**
 * Serviço responsável pela lógica de registro e consulta de frequências.
 * <p>
 * Atua como camada intermediária entre os controladores (UI) e o repositório,
 * garantindo melhor organização e desacoplamento do código.
 * </p>
 */
public class FrequenciaService {

     /** Repositório utilizado para armazenar e recuperar frequências. */
    private final FrequenciaRepositorio repo;


    
    /**
     * Construtor da classe FrequenciaService.
     *
     * @param repo Implementação de {@link FrequenciaRepositorio} que será utilizada.
     */
    public FrequenciaService(FrequenciaRepositorio repo) {
        this.repo = repo;
    }


    /**
     * Registra uma falta para o aluno informado.
     * <p>
     * Cria um novo objeto {@link Frequencia} com:
     * <ul>
     *     <li>O aluno informado</li>
     *     <li>A data atual ({@link LocalDate#now()})</li>
     *     <li>Presença marcada como {@code false}</li>
     * </ul>
     * </p>
     *
     * @param aluno Aluno que teve a falta registrada.
     * @return A frequência salva no repositório, já com ID atribuído.
     */
    public Frequencia registrarFalta(Aluno aluno) {
        Frequencia f = new Frequencia();
        f.setAluno(aluno);
        f.setData(LocalDate.now());
        f.setPresente(false);
        return repo.salvar(f);
    }


    /**
     * Lista todas as frequências registradas no sistema.
     *
     * @return Lista contendo todas as frequências armazenadas.
     */
    public List<Frequencia> listarTodas() {
        return repo.listarTodas();
    }
}
