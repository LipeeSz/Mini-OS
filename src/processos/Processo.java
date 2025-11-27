package processos;

/**
 * Classe que representa um processo no sistema operacional
 */
public class Processo implements Comparable<Processo> {
    private int id;
    private String nome;
    private int prioridade;    // Maior valor = maior prioridade
    private int tempoExec;     // Tempo necessário para terminar
    private int tempoRestante; // Tempo restante de execução
    
    public Processo(int id, String nome, int prioridade, int tempoExec) {
        this.id = id;
        this.nome = nome;
        this.prioridade = prioridade;
        this.tempoExec = tempoExec;
        this.tempoRestante = tempoExec;
    }
    
    // Getters
    public int getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public int getPrioridade() {
        return prioridade;
    }
    
    public int getTempoExec() {
        return tempoExec;
    }
    
    public int getTempoRestante() {
        return tempoRestante;
    }
    
    public void setTempoRestante(int tempoRestante) {
        this.tempoRestante = tempoRestante;
    }
    
    public boolean isFinalizado() {
        return tempoRestante <= 0;
    }
    
    /**
     * Executa o processo por uma unidade de tempo
     */
    public void executar() {
        if (tempoRestante > 0) {
            tempoRestante--;
        }
    }
    
    /**
     * Compara processos por prioridade (maior prioridade primeiro)
     * Para PriorityQueue: menor valor = maior prioridade (invertido)
     */
    @Override
    public int compareTo(Processo outro) {
        // Ordenação decrescente por prioridade (maior valor = maior prioridade)
        // Para PriorityQueue, invertemos para que o maior valor tenha precedência
        return Integer.compare(outro.prioridade, this.prioridade);
    }
    
    @Override
    public String toString() {
        return String.format("P%d: %s [Prioridade: %d, Tempo: %d/%d]", 
                           id, nome, prioridade, tempoRestante, tempoExec);
    }
}

