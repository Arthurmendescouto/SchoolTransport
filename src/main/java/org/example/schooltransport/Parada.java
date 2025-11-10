package org.example.schooltransport;

// Usando javafx.beans para que a listaParadas.fxml possa observar as propriedades
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Parada {
    private final SimpleStringProperty nomeParada;
    private final SimpleStringProperty logradouro;
    private final SimpleStringProperty numero;
    private final SimpleStringProperty bairro;
    private final SimpleStringProperty cidade;
    private final SimpleStringProperty estado;
    private final SimpleBooleanProperty passada; // Para o visto (Status)

    public Parada(String nomeParada, String logradouro, String numero, String bairro, String cidade, String estado) {
        this.nomeParada = new SimpleStringProperty(nomeParada);
        this.logradouro = new SimpleStringProperty(logradouro);
        this.numero = new SimpleStringProperty(numero);
        this.bairro = new SimpleStringProperty(bairro);
        this.cidade = new SimpleStringProperty(cidade);
        this.estado = new SimpleStringProperty(estado);
        this.passada = new SimpleBooleanProperty(false); // Começa como não passada
    }

    // Getters e Setters para as propriedades (essenciais para TableView)
    public String getNomeParada() { return nomeParada.get(); }
    public SimpleStringProperty nomeParadaProperty() { return nomeParada; }
    public void setNomeParada(String nomeParada) { this.nomeParada.set(nomeParada); }

    public String getLogradouro() { return logradouro.get(); }
    public SimpleStringProperty logradouroProperty() { return logradouro; }

    // Propriedade booleana para o visto (Status)
    public boolean isPassada() { return passada.get(); }
    public SimpleBooleanProperty passadaProperty() { return passada; }
    public void setPassada(boolean passada) { this.passada.set(passada); }
}