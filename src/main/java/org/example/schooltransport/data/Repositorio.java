package org.example.schooltransport.data;

import java.util.ArrayList;

import org.example.schooltransport.Parada;
import org.example.schooltransport.model.Aluno;
import org.example.schooltransport.model.Veiculo;
import org.example.schooltransport.model.Responsavel;
import org.example.schooltransport.model.Rota;
import org.example.schooltransport.model.RegistroPresenca;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Repositorio {
    private static ArrayList<Aluno> listaAluno = new ArrayList<>();
    private static ArrayList<Responsavel> listaResponsavel = new ArrayList<>();
    private static ArrayList<Veiculo> listaVeiculo = new ArrayList<>();
    private static ArrayList<Rota> listaRota = new ArrayList<>();
    private static ObservableList<Parada> listaParada = FXCollections.observableArrayList();
    private static ArrayList<RegistroPresenca> listaRegistroPresenca = new ArrayList<>();

    //colocando elementos genéricos para testar  o regristro de presença do motorista
    static {
        // 👇 Responsável para testar o LOGIN (responsavel/responsavel) 👇
        Responsavel responsavelPadrao = new Responsavel("Responsável padrão", "", "", "responsavel", "responsavel");
        listaResponsavel.add(responsavelPadrao);

        // Alunos para a lista de presença (TelaMotorista)
        // Alguns alunos vinculados a outros responsáveis
        listaAluno.add(new Aluno("Filipe Alves", "111", "Maria Alves", "999", "Parada A", "filipe@mail.com", "123"));
        listaAluno.add(new Aluno("Ana Beatriz", "222", "Carlos Silva", "888", "Parada B", "ana@mail.com", "123"));
        listaAluno.add(new Aluno("Julio Cesar", "333", "Marta Lima", "777", "Parada C", "julio@mail.com", "123"));
        
        // Alunos vinculados ao "Responsável padrão" para testar a tela de faltas
        listaAluno.add(new Aluno("João Silva", "444", "Responsável padrão", "111", "Parada D", "joao@mail.com", "123"));
        listaAluno.add(new Aluno("Maria Santos", "555", "Responsável padrão", "222", "Parada E", "maria@mail.com", "123"));
    }

    public static ArrayList<Aluno> getListaAluno() {
        return listaAluno;
    }
    public static void setListaAluno(ArrayList<Aluno> listaAluno) {
        Repositorio.listaAluno = listaAluno;
    }
    public static ArrayList<Responsavel> getListaResponsavel() {
        return listaResponsavel;
    }
    public static void setListaResponsavel(ArrayList<Responsavel> listaResponsavel) {
        Repositorio.listaResponsavel = listaResponsavel;
    }
    public static ArrayList<Veiculo> getListaVeiculo() {
        return listaVeiculo;
    }
    public static void setListaVeiculo(ArrayList<Veiculo> listaVeiculo) {
        Repositorio.listaVeiculo = listaVeiculo;
    }
    public static ArrayList<Rota> getListaRota() {
        return listaRota;
    }
    public static void setListaRota(ArrayList<Rota> listaRota) {
        Repositorio.listaRota = listaRota;
    }
    
    public static ObservableList<Parada> getListaParada() {
        return listaParada;
    }
    
    public static void adicionarParada(Parada parada) {
        listaParada.add(parada);
    }
    
    public static void removerParada(Parada parada) {
        listaParada.remove(parada);
    }

    // Métodos para gerenciar registros de presença
    public static ArrayList<RegistroPresenca> getListaRegistroPresenca() {
        return listaRegistroPresenca;
    }

    public static void adicionarRegistroPresenca(RegistroPresenca registro) {
        listaRegistroPresenca.add(registro);
        System.out.println("DEBUG: Registro adicionado - Aluno: " + 
            (registro.getAluno() != null ? registro.getAluno().getNome() : "null") + 
            ", Presente: " + registro.isPresente() + 
            ", Total de registros: " + listaRegistroPresenca.size());
    }

    /**
     * Retorna todos os registros de faltas (ausências) de um aluno específico.
     * 
     * @param aluno O aluno para buscar as faltas
     * @return Lista de registros onde o aluno estava ausente
     */
    public static ArrayList<RegistroPresenca> getFaltasPorAluno(Aluno aluno) {
        ArrayList<RegistroPresenca> faltas = new ArrayList<>();
        if (aluno == null || aluno.getNome() == null) {
            return faltas;
        }
        String nomeAluno = aluno.getNome();
        for (RegistroPresenca registro : listaRegistroPresenca) {
            if (registro == null) continue;
            Aluno alunoRegistro = registro.getAluno();
            if (alunoRegistro == null || alunoRegistro.getNome() == null) continue;
            // Compara por nome do aluno, já que a classe Aluno não sobrescreve equals()
            if (alunoRegistro.getNome().equals(nomeAluno) && !registro.isPresente()) {
                faltas.add(registro);
            }
        }
        return faltas;
    }

    /**
     * Retorna todos os registros de faltas de alunos vinculados a um responsável.
     * 
     * @param nomeResponsavel Nome do responsável
     * @return Lista de registros de faltas dos alunos do responsável
     */
    public static ArrayList<RegistroPresenca> getFaltasPorResponsavel(String nomeResponsavel) {
        ArrayList<RegistroPresenca> faltas = new ArrayList<>();
        if (nomeResponsavel == null || nomeResponsavel.isEmpty()) {
            return faltas;
        }
        for (RegistroPresenca registro : listaRegistroPresenca) {
            if (registro == null) continue;
            Aluno aluno = registro.getAluno();
            if (aluno == null || aluno.getResponsavel() == null) continue;
            if (aluno.getResponsavel().equals(nomeResponsavel) && !registro.isPresente()) {
                faltas.add(registro);
            }
        }
        return faltas;
    }

    /**
     * Retorna todos os alunos vinculados a um responsável.
     * 
     * @param nomeResponsavel Nome do responsável
     * @return Lista de alunos do responsável
     */
    public static ArrayList<Aluno> getAlunosPorResponsavel(String nomeResponsavel) {
        ArrayList<Aluno> alunos = new ArrayList<>();
        if (nomeResponsavel == null || nomeResponsavel.isEmpty()) {
            return alunos;
        }
        for (Aluno aluno : listaAluno) {
            if (aluno == null || aluno.getResponsavel() == null) continue;
            if (aluno.getResponsavel().equals(nomeResponsavel)) {
                alunos.add(aluno);
            }
        }
        return alunos;
    }

    /**
     * Retorna todas as faltas de todos os alunos (para administradores).
     * 
     * @return Lista de todos os registros de faltas
     */
    public static ArrayList<RegistroPresenca> getTodasFaltas() {
        ArrayList<RegistroPresenca> faltas = new ArrayList<>();
        for (RegistroPresenca registro : listaRegistroPresenca) {
            if (registro == null) continue;
            if (!registro.isPresente()) {
                faltas.add(registro);
            }
        }
        return faltas;
    }

    /**
     * Retorna todos os alunos cadastrados no sistema.
     * 
     * @return Lista de todos os alunos
     */
    public static ArrayList<Aluno> getTodosAlunos() {
        return new ArrayList<>(listaAluno);
    }

}
