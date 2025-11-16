package org.example.schooltransport;

import java.io.IOException;

import org.example.schooltransport.data.Repositorio;
import org.example.schooltransport.model.Aluno;
import org.example.schooltransport.model.Motorista;
import org.example.schooltransport.model.Parada;
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
        
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 390, 700);
        stage.setTitle("Sistema de Transporte Escolar");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch();
    }

}