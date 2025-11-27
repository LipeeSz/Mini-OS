package impressao;

import java.util.PriorityQueue;

/**
 * Gerenciador da fila de impressão utilizando PriorityQueue
 */
public class PrintSpooler {
    private PriorityQueue<DocumentoImpressao> filaImpressao;
    private int proximoId;
    private int tempoTotalImpressao;
    
    public PrintSpooler() {
        this.filaImpressao = new PriorityQueue<>();
        this.proximoId = 1;
        this.tempoTotalImpressao = 0;
    }
    
    /**
     * Adiciona um documento à fila de impressão
     */
    public void adicionarDocumento(String arquivo, int tamanho, String prioridadeStr) {
        DocumentoImpressao.Prioridade prioridade;
        
        try {
            prioridade = DocumentoImpressao.Prioridade.valueOf(prioridadeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Prioridade inválida! Use: URGENTE, NORMAL ou BAIXO");
            return;
        }
        
        DocumentoImpressao documento = new DocumentoImpressao(
            proximoId++, arquivo, tamanho, prioridade
        );
        
        filaImpressao.offer(documento);
        System.out.println("Documento adicionado: " + documento);
    }
    
    /**
     * Processa a fila de impressão completa
     */
    public void processarFila() {
        if (filaImpressao.isEmpty()) {
            System.out.println("Fila de impressão vazia!");
            return;
        }
        
        System.out.println("\n=== PROCESSANDO FILA DE IMPRESSÃO ===");
        System.out.println("Total de documentos: " + filaImpressao.size());
        
        int tempoDecorrido = 0;
        int countUrgente = 0;
        int countNormal = 0;
        int countBaixo = 0;
        
        // Criar uma cópia da fila para processar
        PriorityQueue<DocumentoImpressao> filaTemp = new PriorityQueue<>(filaImpressao);
        
        while (!filaTemp.isEmpty()) {
            DocumentoImpressao doc = filaTemp.poll();
            
            System.out.println(String.format("[%d] IMPRIMINDO: %s", tempoDecorrido, doc));
            
            // Contar por prioridade
            switch (doc.getPrioridade()) {
                case URGENTE: countUrgente++; break;
                case NORMAL: countNormal++; break;
                case BAIXO: countBaixo++; break;
            }
            
            tempoDecorrido += doc.getTamanho();
            tempoTotalImpressao += doc.getTamanho();
        }
        
        System.out.println("\n=== IMPRESSÃO CONCLUÍDA ===");
        System.out.println("Tempo total de impressão: " + tempoTotalImpressao + " unidades");
        System.out.println("\nDocumentos por prioridade:");
        System.out.println("  Urgente: " + countUrgente);
        System.out.println("  Normal: " + countNormal);
        System.out.println("  Baixo: " + countBaixo);
        
        filaImpressao.clear();
    }
    
    /**
     * Imprime apenas o próximo documento
     */
    public void imprimirProximo() {
        if (filaImpressao.isEmpty()) {
            System.out.println("Fila de impressão vazia!");
            return;
        }
        
        DocumentoImpressao doc = filaImpressao.poll();
        System.out.println("Imprimindo: " + doc);
        tempoTotalImpressao += doc.getTamanho();
    }
    
    /**
     * Mostra a ordem de impressão sem processar
     */
    public void mostrarOrdemImpressao() {
        if (filaImpressao.isEmpty()) {
            System.out.println("Fila de impressão vazia!");
            return;
        }
        
        System.out.println("\n--- Ordem de Impressão ---");
        PriorityQueue<DocumentoImpressao> filaTemp = new PriorityQueue<>(filaImpressao);
        int posicao = 1;
        
        while (!filaTemp.isEmpty()) {
            System.out.println(posicao++ + ". " + filaTemp.poll());
        }
    }
    
    /**
     * Mostra estatísticas da fila
     */
    public void mostrarStatus() {
        System.out.println("\n--- Status da Fila de Impressão ---");
        System.out.println("Documentos na fila: " + filaImpressao.size());
        System.out.println("Tempo total de impressão: " + tempoTotalImpressao + " unidades");
        
        if (!filaImpressao.isEmpty()) {
            System.out.println("\nPróximo documento: " + filaImpressao.peek());
        }
    }
    
    /**
     * Limpa a fila de impressão
     */
    public void limparFila() {
        filaImpressao.clear();
        tempoTotalImpressao = 0;
        proximoId = 1;
        System.out.println("Fila de impressão limpa!");
    }
    
    /**
     * Verifica se há documentos na fila
     */
    public boolean temDocumentos() {
        return !filaImpressao.isEmpty();
    }
}

