package memoria;

import java.util.LinkedList;

/**
 * Gerenciador de memória utilizando Lista Encadeada
 * Implementa estratégia First Fit para alocação
 */
public class MemoryManager {
    private LinkedList<BlocoMemoria> memoria;
    private int tamanhoMemoria;
    private int proximoId;
    
    public MemoryManager(int tamanhoMemoria) {
        this.tamanhoMemoria = tamanhoMemoria;
        this.proximoId = 1;
        this.memoria = new LinkedList<>();
        
        // Inicializa toda a memória como livre
        this.memoria.add(new BlocoMemoria(0, tamanhoMemoria, true, 0));
    }
    
    /**
     * Aloca memória usando estratégia First Fit
     * malloc(tamanho)
     */
    public int malloc(int tamanho) {
        if (tamanho <= 0) {
            System.out.println("Erro: Tamanho deve ser maior que zero!");
            return 0;
        }
        
        // Busca o primeiro bloco livre que cabe
        for (int i = 0; i < memoria.size(); i++) {
            BlocoMemoria bloco = memoria.get(i);
            
            if (bloco.isLivre() && bloco.getTamanho() >= tamanho) {
                int id = proximoId++;
                
                if (bloco.getTamanho() == tamanho) {
                    // Usa o bloco inteiro
                    bloco.setLivre(false);
                    bloco.setId(id);
                } else {
                    // Divide o bloco
                    int novoInicio = bloco.getInicio() + tamanho;
                    int novoTamanho = bloco.getTamanho() - tamanho;
                    
                    bloco.setTamanho(tamanho);
                    bloco.setLivre(false);
                    bloco.setId(id);
                    
                    // Insere o bloco livre restante após o alocado
                    BlocoMemoria blocoLivre = new BlocoMemoria(novoInicio, novoTamanho, true, 0);
                    memoria.add(i + 1, blocoLivre);
                }
                
                System.out.println("Memória alocada: " + tamanho + " unidades para PID" + id);
                return id;
            }
        }
        
        System.out.println("Erro: Memória insuficiente para alocar " + tamanho + " unidades!");
        return 0;
    }
    
    /**
     * Libera memória ocupada por um processo
     * free(id)
     */
    public void free(int id) {
        if (id <= 0) {
            System.out.println("Erro: ID inválido!");
            return;
        }
        
        // Busca o bloco ocupado pelo processo
        BlocoMemoria blocoParaLiberar = null;
        int indice = -1;
        
        for (int i = 0; i < memoria.size(); i++) {
            BlocoMemoria bloco = memoria.get(i);
            if (!bloco.isLivre() && bloco.getId() == id) {
                blocoParaLiberar = bloco;
                indice = i;
                break;
            }
        }
        
        if (blocoParaLiberar == null) {
            System.out.println("Erro: Bloco com ID " + id + " não encontrado!");
            return;
        }
        
        // Marca como livre
        blocoParaLiberar.setLivre(true);
        blocoParaLiberar.setId(0);
        
        // Tenta fazer coalescing com blocos adjacentes
        coalescing(indice);
        
        System.out.println("Memória liberada: PID" + id);
    }
    
    /**
     * Faz coalescing de blocos livres adjacentes
     */
    private void coalescing(int indice) {
        if (indice < 0 || indice >= memoria.size()) return;
        
        boolean houveCoalescing = true;
        
        while (houveCoalescing && memoria.size() > 1) {
            houveCoalescing = false;
            BlocoMemoria atual = memoria.get(indice);
            
            // Verifica se há bloco adjacente à direita
            if (indice + 1 < memoria.size()) {
                BlocoMemoria proximo = memoria.get(indice + 1);
                if (proximo.isLivre() && atual.isLivre()) {
                    int novoFim = atual.getFim() + proximo.getTamanho();
                    atual.setTamanho(novoFim - atual.getInicio() + 1);
                    memoria.remove(indice + 1);
                    houveCoalescing = true;
                }
            }
            
            // Verifica se há bloco adjacente à esquerda
            if (indice > 0) {
                BlocoMemoria anterior = memoria.get(indice - 1);
                if (anterior.isLivre() && atual.isLivre()) {
                    int novoTamanho = anterior.getTamanho() + atual.getTamanho();
                    anterior.setTamanho(novoTamanho);
                    memoria.remove(indice);
                    indice--; // Ajusta o índice após remoção
                    houveCoalescing = true;
                }
            }
        }
    }
    
    /**
     * Exibe o estado da memória
     * show()
     */
    public void show() {
        System.out.println("\n=== ESTADO DA MEMÓRIA ===");
        System.out.print("Visualização: ");
        for (BlocoMemoria bloco : memoria) {
            if (bloco.isLivre()) {
                System.out.print("[]");
            } else {
                System.out.print("[####]");
            }
        }
        
        System.out.println("\n\nDetalhamento:");
        for (BlocoMemoria bloco : memoria) {
            System.out.println("  " + bloco.toString());
        }
        
        int espacoLivre = 0;
        int espacoOcupado = 0;
        
        for (BlocoMemoria bloco : memoria) {
            if (bloco.isLivre()) {
                espacoLivre += bloco.getTamanho();
            } else {
                espacoOcupado += bloco.getTamanho();
            }
        }
        
        System.out.println("\nLivre: " + espacoLivre + " / Ocupado: " + espacoOcupado + " / Total: " + tamanhoMemoria);
    }
    
    /**
     * Mostra estatísticas
     */
    public void mostrarStatus() {
        int espacoLivre = 0;
        int espacoOcupado = 0;
        int blocosLivres = 0;
        int blocosOcupados = 0;
        
        for (BlocoMemoria bloco : memoria) {
            if (bloco.isLivre()) {
                espacoLivre += bloco.getTamanho();
                blocosLivres++;
            } else {
                espacoOcupado += bloco.getTamanho();
                blocosOcupados++;
            }
        }
        
        System.out.println("\n--- Status do Gerenciador de Memória ---");
        System.out.println("Tamanho total: " + tamanhoMemoria + " unidades");
        System.out.println("Espaço livre: " + espacoLivre + " (" + (espacoLivre * 100 / tamanhoMemoria) + "%)");
        System.out.println("Espaço ocupado: " + espacoOcupado + " (" + (espacoOcupado * 100 / tamanhoMemoria) + "%)");
        System.out.println("Blocos livres: " + blocosLivres);
        System.out.println("Blocos ocupados: " + blocosOcupados);
    }
    
    /**
     * Limpa toda a memória
     */
    public void limparMemoria() {
        memoria.clear();
        memoria.add(new BlocoMemoria(0, tamanhoMemoria, true, 0));
        proximoId = 1;
        System.out.println("Memória limpa!");
    }
}

