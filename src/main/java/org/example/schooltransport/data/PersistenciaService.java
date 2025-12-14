package org.example.schooltransport.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.example.schooltransport.model.Aluno;
import org.example.schooltransport.model.Motorista;
import org.example.schooltransport.model.Notificacao;
import org.example.schooltransport.model.Parada;
import org.example.schooltransport.model.RegistroPresenca;
import org.example.schooltransport.model.Responsavel;
import org.example.schooltransport.model.Rota;
import org.example.schooltransport.model.Veiculo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Serviço responsável por persistir e recuperar dados do sistema em arquivos externos.
 * Utiliza serialização de objetos Java para armazenar os dados.
 */
public class PersistenciaService {
    
    private static final String DIRETORIO_DADOS = "dados";
    private static final String ARQUIVO_ALUNOS = DIRETORIO_DADOS + File.separator + "alunos.dat";
    private static final String ARQUIVO_RESPONSAVEIS = DIRETORIO_DADOS + File.separator + "responsaveis.dat";
    private static final String ARQUIVO_VEICULOS = DIRETORIO_DADOS + File.separator + "veiculos.dat";
    private static final String ARQUIVO_MOTORISTAS = DIRETORIO_DADOS + File.separator + "motoristas.dat";
    private static final String ARQUIVO_ROTAS = DIRETORIO_DADOS + File.separator + "rotas.dat";
    private static final String ARQUIVO_PARADAS = DIRETORIO_DADOS + File.separator + "paradas.dat";
    private static final String ARQUIVO_NOTIFICACOES = DIRETORIO_DADOS + File.separator + "notificacoes.dat";
    private static final String ARQUIVO_PRESENCAS = DIRETORIO_DADOS + File.separator + "presencas.dat";

    /**
     * Salva todos os dados do repositório em arquivos externos.
     * Cria o diretório de dados se não existir.
     */
    public static void salvarDados() {
        try {
            // Criar diretório se não existir
            File diretorio = new File(DIRETORIO_DADOS);
            if (!diretorio.exists()) {
                diretorio.mkdirs();
            }

            // Salvar cada lista
            salvarLista(ARQUIVO_ALUNOS, Repositorio.getListaAluno());
            salvarLista(ARQUIVO_RESPONSAVEIS, Repositorio.getListaResponsavel());
            salvarLista(ARQUIVO_VEICULOS, Repositorio.getListaVeiculo());
            salvarLista(ARQUIVO_MOTORISTAS, Repositorio.getListaMotorista());
            salvarLista(ARQUIVO_ROTAS, Repositorio.getListaRota());
            salvarLista(ARQUIVO_NOTIFICACOES, Repositorio.getListaNotificacao());
            salvarLista(ARQUIVO_PRESENCAS, Repositorio.getListaDePresenca());
            
            // Salvar paradas (ObservableList precisa ser convertida)
            List<Parada> paradasList = new ArrayList<>(Repositorio.getListaParada());
            salvarLista(ARQUIVO_PARADAS, paradasList);

            System.out.println("Dados salvos com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carrega todos os dados dos arquivos externos para o repositório.
     * Se os arquivos não existirem, mantém as listas vazias.
     */
    @SuppressWarnings("unchecked")
    public static void carregarDados() {
        try {
            // Carregar cada lista
            ArrayList<Aluno> alunos = (ArrayList<Aluno>) carregarLista(ARQUIVO_ALUNOS);
            if (alunos != null) {
                Repositorio.setListaAluno(alunos);
            }

            ArrayList<Responsavel> responsaveis = (ArrayList<Responsavel>) carregarLista(ARQUIVO_RESPONSAVEIS);
            if (responsaveis != null) {
                Repositorio.setListaResponsavel(responsaveis);
            }

            ArrayList<Veiculo> veiculos = (ArrayList<Veiculo>) carregarLista(ARQUIVO_VEICULOS);
            if (veiculos != null) {
                Repositorio.setListaVeiculo(veiculos);
            }

            ArrayList<Motorista> motoristas = (ArrayList<Motorista>) carregarLista(ARQUIVO_MOTORISTAS);
            if (motoristas != null) {
                Repositorio.setListaMotorista(motoristas);
            }

            LinkedList<Rota> rotas = (LinkedList<Rota>) carregarLista(ARQUIVO_ROTAS);
            if (rotas != null) {
                Repositorio.setListaRota(rotas);
            }

            List<Notificacao> notificacoes = (List<Notificacao>) carregarLista(ARQUIVO_NOTIFICACOES);
            if (notificacoes != null) {
                // Limpar lista atual e adicionar as carregadas
                Repositorio.getListaNotificacao().clear();
                Repositorio.getListaNotificacao().addAll(notificacoes);
            }

            ArrayList<RegistroPresenca> presencas = (ArrayList<RegistroPresenca>) carregarLista(ARQUIVO_PRESENCAS);
            if (presencas != null) {
                // Limpar lista atual e adicionar as carregadas
                Repositorio.getListaDePresenca().clear();
                Repositorio.getListaDePresenca().addAll(presencas);
            }

            // Carregar paradas e converter para ObservableList
            List<Parada> paradasList = (List<Parada>) carregarLista(ARQUIVO_PARADAS);
            if (paradasList != null) {
                ObservableList<Parada> paradasObservable = FXCollections.observableArrayList(paradasList);
                Repositorio.getListaParada().clear();
                Repositorio.getListaParada().addAll(paradasObservable);
            }

            System.out.println("Dados carregados com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Salva uma lista em um arquivo usando serialização.
     */
    private static void salvarLista(String arquivo, List<?> lista) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(lista);
        }
    }

    /**
     * Carrega uma lista de um arquivo usando deserialização.
     * Retorna null se o arquivo não existir.
     */
    private static Object carregarLista(String arquivo) {
        File file = new File(arquivo);
        if (!file.exists()) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar arquivo " + arquivo + ": " + e.getMessage());
            return null;
        }
    }
}

