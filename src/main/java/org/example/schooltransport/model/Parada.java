package org.example.schooltransport.model;

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
    private Aluno aluno; // Referência ao aluno associado a esta parada

    public Parada(String nomeParada, String logradouro, String numero, String bairro, String cidade, String estado) {
        this.nomeParada = new SimpleStringProperty(nomeParada);
        this.logradouro = new SimpleStringProperty(logradouro);
        this.numero = new SimpleStringProperty(numero);
        this.bairro = new SimpleStringProperty(bairro);
        this.cidade = new SimpleStringProperty(cidade);
        this.estado = new SimpleStringProperty(estado);
        this.passada = new SimpleBooleanProperty(false); // Começa como não passada
        this.aluno = null; // Sem aluno associado por padrão
    }

    public String getNomeParada() { return nomeParada.get(); }
    public SimpleStringProperty nomeParadaProperty() { return nomeParada; }
    public void setNomeParada(String nomeParada) { this.nomeParada.set(nomeParada); }

    public String getLogradouro() { return logradouro.get(); }
    public SimpleStringProperty logradouroProperty() { return logradouro; }

    public String getNumero() { return numero.get(); }
    public SimpleStringProperty numeroProperty() { return numero; }

    public String getBairro() { return bairro.get(); }
    public SimpleStringProperty bairroProperty() { return bairro; }

    public String getCidade() { return cidade.get(); }
    public SimpleStringProperty cidadeProperty() { return cidade; }

    public String getEstado() { return estado.get(); }
    public SimpleStringProperty estadoProperty() { return estado; }

    // Propriedade booleana para o visto (Status)
    public boolean isPassada() { return passada.get(); }
    public SimpleBooleanProperty passadaProperty() { return passada; }
    public void setPassada(boolean passada) { this.passada.set(passada); }

    // Getter e setter para Aluno
    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    @Override
    public String toString() {
        // Formata o endereço completo para exibição em cadastrar e editar rota
        StringBuilder sb = new StringBuilder();
        if (getNomeParada() != null && !getNomeParada().isEmpty()) {
            sb.append(getNomeParada());
            sb.append(" - ");
        }
        sb.append(getLogradouro());
        if (getNumero() != null && !getNumero().isEmpty()) {
            sb.append(", ").append(getNumero());
        }
        if (getBairro() != null && !getBairro().isEmpty()) {
            sb.append(" - ").append(getBairro());
        }
        if (getCidade() != null && !getCidade().isEmpty()) {
            sb.append(" - ").append(getCidade());
        }
        if (getEstado() != null && !getEstado().isEmpty()) {
            sb.append("/").append(getEstado());
        }
        return sb.toString();
    }
}