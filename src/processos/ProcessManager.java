package processos;

import java.util.PriorityQueue;

/**
 * Gerenciador de Processos utilizando PriorityQueue para escalonamento
 */
public class ProcessManager {
    private PriorityQueue<Processo> filaProntos;
    private Processo processoEmExecucao;
    private int proximoId;
    private int relogio;
    private boolean preemptiva;
    private int quantum;
    
    public ProcessManager() {
        this.filaProntos = new PriorityQueue<>();
        this.processoEmExecucao = null;
        this.proximoId = 1;
        this.relogio = 0;
        this.preemptiva = false;
        this.quantum = 3; // Quantum padrão para escalonamento preemptivo
    }
    
    /**
     * Adiciona um novo processo à fila de prontos
     */
    public void adicionarProcesso(String nome, int prioridade, int tempoExec) {
        Processo processo = new Processo(proximoId++, nome, prioridade, tempoExec);
        filaProntos.offer(processo);
        System.out.println("Processo adicionado: " + processo);
    }
    
    /**
     * Simula uma unidade de tempo no sistema
     */
    public boolean executarUnidadeTempo() {
        // Se não há processo em execução, tenta buscar um da fila
        if (processoEmExecucao == null || processoEmExecucao.isFinalizado()) {
            // Se havia um processo finalizado, remover da execução
            if (processoEmExecucao != null && processoEmExecucao.isFinalizado()) {
                System.out.println("[" + relogio + "] Processo FINALIZADO: " + processoEmExecucao.getNome());
                processoEmExecucao = null;
            }
            
            // Buscar próximo processo da fila
            if (!filaProntos.isEmpty()) {
                processoEmExecucao = filaProntos.poll();
                System.out.println("[" + relogio + "] Processo INICIADO: " + processoEmExecucao.getNome());
            }
        }
        
        // Verificar preempção no escalonamento preemptivo
        if (preemptiva && processoEmExecucao != null && !processoEmExecucao.isFinalizado()) {
            int tempoUsado = processoEmExecucao.getTempoExec() - processoEmExecucao.getTempoRestante();
            if (tempoUsado > 0 && tempoUsado % quantum == 0) {
                // Preemprir processo e voltar à fila
                filaProntos.offer(processoEmExecucao);
                processoEmExecucao = filaProntos.poll();
                if (processoEmExecucao != null) {
                    System.out.println("[" + relogio + "] PREEMPÇÃO: Novo processo iniciado");
                }
            }
        }
        
        // Se há processo em execução, executa por uma unidade de tempo
        if (processoEmExecucao != null) {
            processoEmExecucao.executar();
            System.out.println("[" + relogio + "] EXECUTANDO: " + processoEmExecucao.toString());
        } else if (filaProntos.isEmpty()) {
            // Nenhum processo para executar
            System.out.println("[" + relogio + "] Sistema ocioso - nenhum processo na fila");
            return false; // Indica que a simulação pode terminar
        }
        
        relogio++;
        return true;
    }
    
    /**
     * Executa múltiplas unidades de tempo
     */
    public void executarSimulacao(int unidadesTempo) {
        System.out.println("\n=== INICIANDO SIMULAÇÃO (Escalonamento " + (preemptiva ? "PREEMPTIVO" : "NÃO-PREEMPTIVO") + ") ===");
        System.out.println("Executando por " + unidadesTempo + " unidades de tempo...\n");
        
        for (int i = 0; i < unidadesTempo; i++) {
            if (!executarUnidadeTempo()) {
                break;
            }
        }
        
        // Finalizar processo atual se ainda estiver em execução
        if (processoEmExecucao != null && !processoEmExecucao.isFinalizado()) {
            System.out.println("[" + relogio + "] Simulação interrompida. Processo em execução: " + processoEmExecucao.getNome());
        }
        
        System.out.println("\n=== SIMULAÇÃO CONCLUÍDA ===");
        mostrarStatus();
    }
    
    /**
     * Mostra o status atual do gerenciador
     */
    public void mostrarStatus() {
        System.out.println("\n--- Status do Gerenciador de Processos ---");
        System.out.println("Relógio do sistema: " + relogio);
        System.out.println("Processos na fila de prontos: " + filaProntos.size());
        
        if (processoEmExecucao != null && !processoEmExecucao.isFinalizado()) {
            System.out.println("Processo em execução: " + processoEmExecucao);
        } else {
            System.out.println("Nenhum processo em execução");
        }
        
        if (!filaProntos.isEmpty()) {
            System.out.println("\nFila de prontos:");
            PriorityQueue<Processo> copiaFila = new PriorityQueue<>(filaProntos);
            int posicao = 1;
            while (!copiaFila.isEmpty()) {
                System.out.println("  " + posicao++ + ". " + copiaFila.poll());
            }
        }
    }
    
    /**
     * Define o tipo de escalonamento
     */
    public void setPreemptiva(boolean preemptiva) {
        this.preemptiva = preemptiva;
        System.out.println("Escalonamento alterado para: " + (preemptiva ? "PREEMPTIVO" : "NÃO-PREEMPTIVO"));
    }
    
    /**
     * Limpa todos os processos
     */
    public void limparProcessos() {
        filaProntos.clear();
        processoEmExecucao = null;
        proximoId = 1;
        relogio = 0;
        System.out.println("Todos os processos foram removidos.");
    }
    
    /**
     * Retorna o relógio atual
     */
    public int getRelogio() {
        return relogio;
    }
    
    /**
     * Verifica se há processos na fila ou em execução
     */
    public boolean temProcessos() {
        return !filaProntos.isEmpty() || (processoEmExecucao != null && !processoEmExecucao.isFinalizado());
    }
}

