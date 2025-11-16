package org.example.schooltransport;

import java.io.IOException;

import org.example.schooltransport.data.Repositorio;
import org.example.schooltransport.model.Aluno;
import org.example.schooltransport.model.Motorista;
import org.example.schooltransport.model.Responsavel;
import org.example.schooltransport.model.Rota;
import org.example.schooltransport.model.Veiculo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Carregar seed data antes de exibir a tela
        seedDataIfNeeded();
        
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 900);
        stage.setTitle("Sistema de Transporte Escolar");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    /**
     * Carrega dados de teste nos repositórios se estiverem vazios.
     */
    private static void seedDataIfNeeded() {
        // Seed Motoristas
        if (Repositorio.getListaMotorista().isEmpty()) {
            Repositorio.getListaMotorista().add(new Motorista("João Silva", "123.456.789-00", "(11) 98765-4321", "joao@email.com", "senha123", "1234567890"));
            Repositorio.getListaMotorista().add(new Motorista("Maria Santos", "987.654.321-00", "(11) 99876-5432", "maria@email.com", "senha123", "0987654321"));
            System.out.println("✅ Motoristas seed carregados");
        }
        
        // Seed Veículos
        if (Repositorio.getListaVeiculo().isEmpty()) {
            Repositorio.getListaVeiculo().add(new Veiculo("ABC-1234", "Ônibus Escolar", 45));
            Repositorio.getListaVeiculo().add(new Veiculo("XYZ-9876", "Van Escolar", 20));
            System.out.println("✅ Veículos seed carregados");
        }
        
        // Seed Paradas
        if (Repositorio.getListaParada().isEmpty()) {
            Parada parada1 = new Parada("Parada Centro", "Rua Principal", "100", "Centro", "São Paulo", "SP");
            Parada parada2 = new Parada("Parada Sul", "Avenida Sul", "200", "Zona Sul", "São Paulo", "SP");
            Parada parada3 = new Parada("Parada Norte", "Avenida Norte", "300", "Zona Norte", "São Paulo", "SP");
            
            Repositorio.getListaParada().add(parada1);
            Repositorio.getListaParada().add(parada2);
            Repositorio.getListaParada().add(parada3);
            System.out.println("✅ Paradas seed carregadas");
        }
        
        // Seed Alunos
        if (Repositorio.getListaAluno().isEmpty()) {
            Aluno aluno1 = new Aluno("Pedro Silva", "111.222.333-44", "123.456.789-00", "(11) 91111-1111", "pedro@email.com", "senha123");
            Aluno aluno2 = new Aluno("Ana Santos", "222.333.444-55", "987.654.321-00", "(11) 92222-2222", "ana@email.com", "senha123");
            Aluno aluno3 = new Aluno("Carlos Oliveira", "333.444.555-66", "123.456.789-00", "(11) 93333-3333", "carlos@email.com", "senha123");
            
            Repositorio.getListaAluno().add(aluno1);
            Repositorio.getListaAluno().add(aluno2);
            Repositorio.getListaAluno().add(aluno3);
            System.out.println("✅ Alunos seed carregados");
        }
        
        // Seed Responsáveis
        if (Repositorio.getListaResponsavel().isEmpty()) {
            Responsavel resp1 = new Responsavel("João Silva (Responsável)", "123.456.789-00", "(11) 91111-1111", "joao.resp@email.com", "senha123");
            Responsavel resp2 = new Responsavel("Maria Santos (Responsável)", "987.654.321-00", "(11) 92222-2222", "maria.resp@email.com", "senha123");
            
            Repositorio.getListaResponsavel().add(resp1);
            Repositorio.getListaResponsavel().add(resp2);
            System.out.println("✅ Responsáveis seed carregados");
        }
        
        // Seed Rotas
        if (Repositorio.getListaRota().isEmpty()) {
            ArrayList<Parada> paradasRota1 = new ArrayList<>();
            paradasRota1.add(Repositorio.getListaParada().get(0));
            paradasRota1.add(Repositorio.getListaParada().get(1));
            
            Rota rota1 = new Rota(
                Repositorio.getListaMotorista().get(0),
                Repositorio.getListaVeiculo().get(0),
                "Matutino",
                paradasRota1
            );
            
            Repositorio.getListaRota().add(rota1);
            System.out.println("✅ Rotas seed carregadas");
        }
        
        // Seed Notificações
        if (Repositorio.getListaNotificacao().isEmpty()) {
            Repositorio.adicionarNotificacaoParaCpf("123.456.789-00", "Bem-vindo ao sistema de transporte!");
            Repositorio.adicionarNotificacaoParaCpf("123.456.789-00", "Sua rota foi confirmada para hoje.");
            System.out.println("✅ Notificações seed carregadas");
        }
        
        System.out.println("🎉 Seed data inicializado com sucesso!");
    }
    
    public static void main(String[] args) {
        launch();
    }

}