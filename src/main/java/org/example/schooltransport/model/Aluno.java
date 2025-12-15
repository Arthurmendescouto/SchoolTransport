package org.example.schooltransport.model;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 * Representa um aluno e a referência ao seu responsável (CPF do responsável).
 */
public class Aluno extends Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    private String responsavel;

    /**
     * Constrói um aluno.
     * @param nome Nome do aluno
     * @param cpf CPF do aluno
     * @param responsavel CPF do responsável
     * @param contato Telefone de contato
     * @param email E-mail de acesso
     * @param senha Senha de acesso
     */
    public Aluno(String nome, String cpf, String responsavel, String contato, String email, String senha) {
        super(nome, cpf, contato, email, senha);
        this.responsavel = responsavel;
        salvarEmArquivo();
    }

    /**
     * Constrói um aluno sem salvar automaticamente no arquivo.
     * Usado para carregar dados do arquivo sem duplicação.
     * @param nome Nome do aluno
     * @param cpf CPF do aluno
     * @param responsavel CPF do responsável
     * @param contato Telefone de contato
     * @param email E-mail de acesso
     * @param senha Senha de acesso
     * @param salvar Se false, não salva automaticamente no arquivo
     */
    public Aluno(String nome, String cpf, String responsavel, String contato, String email, String senha, boolean salvar) {
        super(nome, cpf, contato, email, senha);
        this.responsavel = responsavel;
        if (salvar) {
            salvarEmArquivo();
        }
    }

    private void salvarEmArquivo() {
        try (FileWriter fw = new FileWriter("listaAlunos.txt", true);
            PrintWriter pw = new PrintWriter(fw)) {
            pw.println(this.getNome() + "|" + this.getCpf() + "|" + this.getResponsavel() + "|" + this.getContato() + "|" + this.getEmail() + "|" + this.getSenha());
        } catch (IOException e) {
            System.out.println("Erro ao salvar aluno em arquivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** @return CPF do responsável vinculado */
    public String getResponsavel() {
        return responsavel;
    }

    /** Define o CPF do responsável vinculado */
    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    @Override
    public String toString() {
        return this.getNome(); // ou: nome + " (" + cpf + ")"
    }

}
