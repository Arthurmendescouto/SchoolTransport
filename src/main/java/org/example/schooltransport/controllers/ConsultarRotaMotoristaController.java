package org.example.schooltransport.controllers;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.example.schooltransport.Parada;

import java.util.ArrayList;

public class ConsultarRotaMotoristaController {
    @FXML
    private VBox contentVBox;

    @FXML
    private Button botaoVoltar;

    public void renderizarLista(ArrayList<Parada> listaParadas) {

        contentVBox.getChildren().clear();

        for (int i = 0; i < listaParadas.size(); i++) {
            Parada data = listaParadas.get(i);

            HBox linha = criarLinha(data);
            contentVBox.getChildren().add(linha);

            if (i < listaParadas.size() - 1) {
                Region separador = criarSeparador();
                contentVBox.getChildren().add(separador);
            }
        }
    }

    private HBox criarLinha(Parada dados) {
        double totalWidth = 297;
        double sectionWidth = totalWidth / 3;

        // PRIMEIRA ÁREA
        HBox leftBox = new HBox();
        leftBox.setAlignment(Pos.CENTER_LEFT);
        leftBox.setPrefWidth(sectionWidth);
        //Essas linhas abaixo devem estar preenchidas com os valores corretos
        // Label leftLabel = new Label(dados.getLeftText());
        //leftBox.getChildren().add(leftLabel);

        // SEGUNDA ÁREA
        HBox centerBox = new HBox();
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPrefWidth(sectionWidth);
        //Essas linhas abaixo devem estar preenchida com os valores corretos
        //Label centerLabel = new Label(dados.getCenterText());
        //centerBox.getChildren().add(centerLabel);

        // TERCEIRA ÁREA
        HBox rightBox = new HBox();
        rightBox.setAlignment(Pos.CENTER_RIGHT);
        rightBox.setPrefWidth(sectionWidth);

        try {
            ImageView icon = new ImageView(new Image(imagemStatusRotaPath(dados.passadaProperty())));
            icon.setFitHeight(25);
            icon.setFitWidth(25);
            icon.setPreserveRatio(true);
            rightBox.getChildren().add(icon);
        } catch (Exception e) {
            System.out.println("Erro ao carregar imagem");
        }

        HBox hbox = new HBox(leftBox, centerBox, rightBox);
        hbox.setPrefSize(totalWidth, 56);
        hbox.getStyleClass().add("form-field");

        return hbox;
    }

    private Region criarSeparador() {
        Region region = new Region();
        region.setPrefHeight(10);

        return region;
    }
    private String imagemStatusRotaPath(SimpleBooleanProperty jaPassou) {
        String tempVar = "";
        if (jaPassou.get())
            tempVar = "";
        else
            tempVar = "";
        return tempVar;
    }
}

