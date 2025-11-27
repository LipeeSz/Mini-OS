package memoria;

/**
 * Classe que representa um bloco de memória
 */
public class BlocoMemoria {
    private int inicio;
    private int tamanho;
    private boolean livre;
    private int id;  // Identificador do processo (0 se livre)
    
    public BlocoMemoria(int inicio, int tamanho, boolean livre, int id) {
        this.inicio = inicio;
        this.tamanho = tamanho;
        this.livre = livre;
        this.id = id;
    }
    
    // Getters
    public int getInicio() {
        return inicio;
    }
    
    public int getTamanho() {
        return tamanho;
    }
    
    public boolean isLivre() {
        return livre;
    }
    
    public int getId() {
        return id;
    }
    
    // Setters
    public void setInicio(int inicio) {
        this.inicio = inicio;
    }
    
    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }
    
    public void setLivre(boolean livre) {
        this.livre = livre;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Retorna o fim do bloco
     */
    public int getFim() {
        return inicio + tamanho - 1;
    }
    
    /**
     * Verifica se dois blocos são adjacentes
     */
    public boolean isAdjacente(BlocoMemoria outro) {
        return this.fim() + 1 == outro.getInicio() || outro.getFim() + 1 == this.getInicio();
    }
    
    @Override
    public String toString() {
        if (livre) {
            return String.format("[%d-%d: LIVRE]", inicio, getFim());
        }
        return String.format("[%d-%d: PID%d]", inicio, getFim(), id);
    }
    
    private int fim() {
        return inicio + tamanho - 1;
    }
}

