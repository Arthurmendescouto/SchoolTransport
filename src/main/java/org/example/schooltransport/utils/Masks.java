package org.example.schooltransport.utils;

import javafx.scene.control.TextField;

public class Masks {

    // Máscara para CPF
    public static void applyCPFMask(TextField textField) {
        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            String digits = newValue.replaceAll("[^0-9]", "");

            if (digits.length() > 11) {
                digits = digits.substring(0, 11);
            }

            StringBuilder cpf = new StringBuilder();

            for (int i = 0; i < digits.length(); i++) {
                if (i == 3 || i == 6) cpf.append('.');
                if (i == 9) cpf.append('-');
                cpf.append(digits.charAt(i));
            }

            textField.setText(cpf.toString());
            textField.positionCaret(cpf.length());
        });
    }

    // Máscara para contato (telefone)
    public static void applyPhoneMask(TextField textField) {
        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            String digits = newValue.replaceAll("[^0-9]", "");

            if (digits.length() > 11) {
                digits = digits.substring(0, 11);
            }

            StringBuilder phone = new StringBuilder();

            for (int i = 0; i < digits.length(); i++) {
                if (i == 0) phone.append("(");
                if (i == 2) phone.append(") ");
                if (i == 7) phone.append("-");
                phone.append(digits.charAt(i));
            }

            textField.setText(phone.toString());
            textField.positionCaret(phone.length());
        });
    }

    // Validação simples para email
    public static void applyEmailValidation(TextField textField) {
        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.contains("@")) {
                textField.setStyle("-fx-border-color: red;");
            } else {
                textField.setStyle("");
            }
        });
    }
}
