package org.example.schooltransport.controllers;

import org.example.schooltransport.model.Cadastro;
import org.example.schooltransport.model.Ocorrencia;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;

public class CadastrarOcorrenciaController {

    @FXML
    private TextArea txtDescricao;

    @FXML
    private void salvarOcorrencia() {

        String descricao = txtDescricao.getText();

        if (descricao == null || descricao.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("A descrição não pode estar vazia!");
            alert.show();
            return;
        }


        String autorCpf = org.example.schooltransport.data.Repositorio.getCurrentUserCpf();
        if (autorCpf == null) {
            autorCpf = "ANONIMO";
        }

        Ocorrencia ocorrencia = new Ocorrencia(descricao, autorCpf);

        Cadastro.getInstance().adicionarOcorrencia(ocorrencia);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Ocorrência registrada com sucesso! (Autor: " + autorCpf + ")");
        alert.show();

        txtDescricao.clear();
    }
}
