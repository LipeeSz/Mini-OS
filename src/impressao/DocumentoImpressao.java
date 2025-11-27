package impressao;

import java.util.Date;

/**
 * Classe que representa um documento na fila de impressão
 */
public class DocumentoImpressao implements Comparable<DocumentoImpressao> {
    private int id;
    private String arquivo;
    private int tamanho;              // Número de páginas
    private Prioridade prioridade;    // URGENTE, NORMAL, BAIXO
    private Date dataEnvio;
    
    public enum Prioridade {
        BAIXO(1),
        NORMAL(2),
        URGENTE(3);
        
        private final int valor;
        
        Prioridade(int valor) {
            this.valor = valor;
        }
        
        public int getValor() {
            return valor;
        }
    }
    
    public DocumentoImpressao(int id, String arquivo, int tamanho, Prioridade prioridade) {
        this.id = id;
        this.arquivo = arquivo;
        this.tamanho = tamanho;
        this.prioridade = prioridade;
        this.dataEnvio = new Date();
    }
    
    // Getters
    public int getId() {
        return id;
    }
    
    public String getArquivo() {
        return arquivo;
    }
    
    public int getTamanho() {
        return tamanho;
    }
    
    public Prioridade getPrioridade() {
        return prioridade;
    }
    
    public Date getDataEnvio() {
        return dataEnvio;
    }
    
    /**
     * Compara documentos por prioridade e por ordem de chegada
     * Maior prioridade = maior prioridade de impressão
     * Dentro da mesma prioridade, ordem FIFO
     */
    @Override
    public int compareTo(DocumentoImpressao outro) {
        // Compara por prioridade (ordem decrescente)
        int comparacaoPrioridade = Integer.compare(
            outro.prioridade.getValor(), 
            this.prioridade.getValor()
        );
        
        if (comparacaoPrioridade != 0) {
            return comparacaoPrioridade;
        }
        
        // Mesma prioridade: compara por data de envio (FIFO)
        return this.dataEnvio.compareTo(outro.dataEnvio);
    }
    
    @Override
    public String toString() {
        return String.format("Doc%d: %s [%s, %d páginas]", 
                           id, arquivo, prioridade, tamanho);
    }
}

