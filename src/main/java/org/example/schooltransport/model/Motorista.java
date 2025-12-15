package org.example.schooltransport.model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * Classe Motorista — estende Pessoa e adiciona dados específicos do motorista.
 */
public class Motorista extends Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    private String cnh;

    /**
     * Cria um motorista com os dados informados.
     * @param nome Nome completo
     * @param cpf CPF
     * @param contato Telefone de contato
     * @param email E-mail de acesso
     * @param senha Senha de acesso
     * @param cnh Número da CNH
     */
    public Motorista(String nome, String cpf, String contato, String email, String senha, String cnh) {
        this(nome, cpf, contato, email, senha, cnh, true);
    }

    /**
     * Cria um motorista permitindo escolher se deve persistir imediatamente.
     * Útil ao carregar dados do arquivo para evitar duplicações.
     */
    public Motorista(String nome, String cpf, String contato, String email, String senha, String cnh, boolean salvar) {
        super(nome, cpf, contato, email, senha);
        this.cnh = cnh;
        if (salvar) {
            salvarEmArquivo();
        }
    }

       private void salvarEmArquivo() {
        try (FileWriter fw = new FileWriter("listaMotoristas.txt", true);
            PrintWriter pw = new PrintWriter(fw)) {
            pw.println(this.getNome() + "|" + this.getCpf() + "|" + this.getContato() + "|" + this.getEmail() + "|" + this.getSenha() + "|" + this.getCnh());
        } catch (IOException e) {
            System.out.println("Erro ao salvar motorista em arquivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Retorna a CNH do motorista.
     * @return CNH cadastrada
     */
    public String getCnh() {
        return cnh;
    }

    /**
     * Atualiza a CNH do motorista.
     * @param cnh Novo número de CNH
     */
    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    /**
     * Representação amigável para listagens e seletores.
     */
    @Override
    public String toString() {
        return nome != null ? nome : "Motorista";
    }
}
