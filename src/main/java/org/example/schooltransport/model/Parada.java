package org.example.schooltransport.model;

// Usando javafx.beans para que a listaParadas.fxml possa observar as propriedades
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Representa um ponto de parada na rota, com endereço e aluno associado.
 * Fornece propriedades observáveis para integração com JavaFX.
 */
public class Parada {
    private final SimpleStringProperty nomeParada;
    private final SimpleStringProperty logradouro;
    private final SimpleStringProperty numero;
    private final SimpleStringProperty bairro;
    private final SimpleStringProperty cidade;
    private final SimpleStringProperty estado;
    private final SimpleBooleanProperty passada; // Para o visto (Status)
    private Aluno aluno; // Referência ao aluno associado a esta parada

    /**
     * Cria uma nova parada com endereço completo.
     */
    public Parada(String nomeParada, String logradouro, String numero, String bairro, String cidade, String estado) {
        this.nomeParada = new SimpleStringProperty(nomeParada);
        this.logradouro = new SimpleStringProperty(logradouro);
        this.numero = new SimpleStringProperty(numero);
        this.bairro = new SimpleStringProperty(bairro);
        this.cidade = new SimpleStringProperty(cidade);
        this.estado = new SimpleStringProperty(estado);
        this.passada = new SimpleBooleanProperty(false); // Começa como não passada
        this.aluno = null; // Sem aluno associado por padrão
        this.horarioPrevisto = new SimpleStringProperty(null);

    }

    /** Nome amigável da parada. */
    public String getNomeParada() { return nomeParada.get(); }
    /** Propriedade observável do nome da parada. */
    public SimpleStringProperty nomeParadaProperty() { return nomeParada; }
    /** Atualiza o nome da parada. */
    public void setNomeParada(String nomeParada) { this.nomeParada.set(nomeParada); }

    /** Logradouro do endereço. */
    public String getLogradouro() { return logradouro.get(); }
    /** Propriedade observável do logradouro. */
    public SimpleStringProperty logradouroProperty() { return logradouro; }

    /** Número do endereço. */
    public String getNumero() { return numero.get(); }
    /** Propriedade observável do número. */
    public SimpleStringProperty numeroProperty() { return numero; }

    /** Bairro do endereço. */
    public String getBairro() { return bairro.get(); }
    /** Propriedade observável do bairro. */
    public SimpleStringProperty bairroProperty() { return bairro; }

    /** Cidade do endereço. */
    public String getCidade() { return cidade.get(); }
    /** Propriedade observável da cidade. */
    public SimpleStringProperty cidadeProperty() { return cidade; }

    /** Estado (UF) do endereço. */
    public String getEstado() { return estado.get(); }
    /** Propriedade observável do estado. */
    public SimpleStringProperty estadoProperty() { return estado; }


    /** Retorna o horário previsto desta parada no formato HH:mm. */
    public String getHorarioPrevisto() {return horarioPrevisto.get();}

    /** Propriedade observável do horário previsto (para TableView e bindings). */
    public SimpleStringProperty horarioPrevistoProperty() {return horarioPrevisto;}

/** Define o horário previsto desta parada. */
public void setHorarioPrevisto(String horarioPrevisto) {
    this.horarioPrevisto.set(horarioPrevisto);
}


    //
    private final SimpleStringProperty horarioPrevisto;

    /** Indica se a parada já foi realizada (visto). */
    public boolean isPassada() { return passada.get(); }
    /** Propriedade observável do status de passada. */
    public SimpleBooleanProperty passadaProperty() { return passada; }
    /** Atualiza o status de passada. */
    public void setPassada(boolean passada) { this.passada.set(passada); }

    /** Retorna o aluno associado a esta parada. */
    public Aluno getAluno() {
        return aluno;
    }

    /** Define o aluno associado a esta parada. */
    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    /**
     * Representação detalhada do endereço para exibição em telas.
     */
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
        if (getHorarioPrevisto() != null && !getHorarioPrevisto().isEmpty()) {
            sb.append(" (").append(getHorarioPrevisto()).append(")");
        }

        return sb.toString();
    }
}