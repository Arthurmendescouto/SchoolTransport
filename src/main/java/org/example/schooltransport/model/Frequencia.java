package org.example.schooltransport.model;

import java.time.LocalDate;

/**
 * Representa o registro de frequência de um aluno para um determinado dia.
 * Armazena informações sobre presença ou falta.
 */

public class Frequencia {
    private Long id;
    private Aluno aluno;
    private LocalDate data;
    private boolean presente;

    /**
     * Construtor padrão da classe Frequencia.
     * Utilizado principalmente por ferramentas de serialização/deserialização.
     */

    public Frequencia() {}


     /**
     * Construtor completo para criação de um registro de frequência.
     *
     * @param id        Identificador único da frequência.
     * @param aluno     Aluno associado ao registro.
     * @param data      Data do registro.
     * @param presente  Indica se o aluno esteve presente (true) ou ausente (false).
     */
    public Frequencia(Long id, Aluno aluno, LocalDate data, boolean presente) {
        this.id = id;
        this.aluno = aluno;
        this.data = data;
        this.presente = presente;
    }

    /**
     * Retorna o ID da frequência.
     *
     * @return identificador único da frequência.
     */
    public Long getId() { return id; }

    /**
     * Define o ID da frequência.
     *
     * @param id novo identificador da frequência.
     */
    public void setId(Long id) { this.id = id; }

    /**
     * Retorna o aluno associado ao registro.
     *
     * @return aluno desta frequência.
     */
    public Aluno getAluno() { return aluno; }


    /**
     * Define o aluno associado à frequência.
     *
     * @param aluno aluno a ser vinculado ao registro.
     */
    public void setAluno(Aluno aluno) { this.aluno = aluno; }

    /**
     * Retorna a data do registro de frequência.
     *
     * @return data da ocorrência.
     */
    public LocalDate getData() { return data; }

    /**
     * Define a data do registro de frequência.
     *
     * @param data nova data a ser atribuída.
     */
    public void setData(LocalDate data) { this.data = data; }

    /**
     * Indica se o aluno esteve presente.
     *
     * @return true se presente, false se ausente.
     */
    public boolean isPresente() { return presente; }


    /**
     * Define a presença do aluno.
     *
     * @param presente valor booleano indicando presença.
     */
    public void setPresente(boolean presente) { this.presente = presente; }
}
