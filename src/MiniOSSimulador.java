import processos.ProcessManager;
import impressao.PrintSpooler;
import arquivos.FileSystem;
import memoria.MemoryManager;
import java.util.Scanner;
import java.io.IOException;

/**
 * Classe principal do Mini Sistema Operacional
 * Contém o menu principal e a interface de usuário
 */
public class MiniOSSimulador {
    private ProcessManager gerenciadorProcessos;
    private PrintSpooler filaImpressao;
    private FileSystem sistemaArquivos;
    private MemoryManager gerenciadorMemoria;
    private Scanner scanner;
    private boolean limparConsole;
    
    public MiniOSSimulador() {
        this.gerenciadorProcessos = new ProcessManager();
        this.filaImpressao = new PrintSpooler();
        this.sistemaArquivos = new FileSystem();
        this.gerenciadorMemoria = new MemoryManager(100);
        this.scanner = new Scanner(System.in);
        this.limparConsole = true; // Por padrão, limpa o console
    }
    
    /**
     * Limpa o console
     */
    private void limparConsole() {

            for (int i = 0; i < 25; i++) {
                System.out.println();
            }
        
    }
    
    public static void main(String[] args) {
        MiniOSSimulador sistema = new MiniOSSimulador();
        sistema.exibirMenuPrincipal();
    }
    
    /**
     * Exibe o menu principal do sistema
     */
    public void exibirMenuPrincipal() {
        int opcao;
        
        do {
            System.out.println("\n========================================");
            System.out.println("    MINI SISTEMA OPERACIONAL v1.0");
            System.out.println("========================================");
            System.out.println("1. Gerenciador de Processos");
            System.out.println("2. Fila de Impressão");
            System.out.println("3. Sistema de Arquivos");
            System.out.println("4. Gerenciador de Memória");
            System.out.println("5. Limpar console");
            System.out.println("0. Sair");
            System.out.println("========================================");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                
                switch (opcao) {
                    case 1:
                        limparConsole();
                        menuGerenciadorProcessos();
                        break;
                    case 2:
                        limparConsole();
                        menuFilaImpressao();
                        break;
                    case 3:
                        limparConsole();
                        menuSistemaArquivos();
                        break;
                    case 4:
                        limparConsole();
                        menuGerenciadorMemoria();
                        break;
                    case 5:
                        limparConsole();
                        break;
                    case 0:
                        System.out.println("\nSaindo do sistema...");
                        break;
                    default:
                        System.out.println("\nOpção inválida! Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nEntrada inválida! Digite um número.");
                opcao = -1;
            }
            
        } while (opcao != 0);
        
        scanner.close();
    }
    
    /**
     * Menu do Gerenciador de Processos
     */
    private void menuGerenciadorProcessos() {
        int opcao;
        
        do {
            System.out.println("\n========= GERENCIADOR DE PROCESSOS =========");
            System.out.println("1. Adicionar processo");
            System.out.println("2. Executar unidade de tempo");
            System.out.println("3. Executar simulação (múltiplas unidades)");
            System.out.println("4. Mostrar status");
            System.out.println("5. Limpar processos");
            System.out.println("6. Alterar escalonamento (Não-Preemptivo/Preemptivo)");
            System.out.println("0. Voltar ao menu principal");
            System.out.println("============================================");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                
                switch (opcao) {
                    case 1:
                        adicionarProcesso();
                        break;
                    case 2:
                        executarUnidadeTempo();
                        break;
                    case 3:
                        executarSimulacao();
                        break;
                    case 4:
                        gerenciadorProcessos.mostrarStatus();
                        break;
                    case 5:
                        gerenciadorProcessos.limparProcessos();
                        break;
                    case 6:
                        alterarEscalonamento();
                        break;
                    case 0:
                        System.out.println("\nRetornando ao menu principal...");
                        break;
                    default:
                        System.out.println("\nOpção inválida! Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nEntrada inválida! Digite um número.");
                opcao = -1;
            }
            
        } while (opcao != 0);
    }
    
    /**
     * Adiciona um novo processo ao gerenciador
     */
    private void adicionarProcesso() {
        try {
            System.out.print("Nome do processo: ");
            String nome = scanner.nextLine();
            
            System.out.print("Prioridade (maior valor = maior prioridade): ");
            int prioridade = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Tempo de execução (unidades): ");
            int tempoExec = Integer.parseInt(scanner.nextLine());
            
            gerenciadorProcessos.adicionarProcesso(nome, prioridade, tempoExec);
            
        } catch (NumberFormatException e) {
            System.out.println("Erro: Por favor, insira valores numéricos válidos.");
        }
    }
    
    /**
     * Executa uma unidade de tempo
     */
    private void executarUnidadeTempo() {
        if (gerenciadorProcessos.temProcessos()) {
            gerenciadorProcessos.executarUnidadeTempo();
        } else {
            System.out.println("Nenhum processo para executar. Adicione processos primeiro.");
        }
    }
    
    /**
     * Executa uma simulação completa
     */
    private void executarSimulacao() {
        try {
            System.out.print("Quantas unidades de tempo executar? ");
            int unidades = Integer.parseInt(scanner.nextLine());
            
            if (unidades <= 0) {
                System.out.println("Erro: O número deve ser maior que zero.");
                return;
            }
            
            gerenciadorProcessos.executarSimulacao(unidades);
            
        } catch (NumberFormatException e) {
            System.out.println("Erro: Por favor, insira um número válido.");
        }
    }
    
    /**
     * Altera o tipo de escalonamento
     */
    private void alterarEscalonamento() {
        System.out.println("\nEscalonamento atual: NÃO-PREEMPTIVO (padrão)");
        System.out.println("Mudança para PREEMPTIVO ainda não implementada.");
        System.out.println("O sistema continuará usando escalonamento NÃO-PREEMPTIVO.");
    }
    
    /**
     * Menu da Fila de Impressão
     */
    private void menuFilaImpressao() {
        int opcao;
        
        do {
            System.out.println("\n========= FILA DE IMPRESSÃO =========");
            System.out.println("1. Adicionar documento");
            System.out.println("2. Processar fila completa");
            System.out.println("3. Imprimir próximo documento");
            System.out.println("4. Mostrar ordem de impressão");
            System.out.println("5. Mostrar status");
            System.out.println("6. Limpar fila");
            System.out.println("0. Voltar ao menu principal");
            System.out.println("=====================================");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                
                switch (opcao) {
                    case 1:
                        adicionarDocumento();
                        break;
                    case 2:
                        filaImpressao.processarFila();
                        break;
                    case 3:
                        filaImpressao.imprimirProximo();
                        break;
                    case 4:
                        filaImpressao.mostrarOrdemImpressao();
                        break;
                    case 5:
                        filaImpressao.mostrarStatus();
                        break;
                    case 6:
                        filaImpressao.limparFila();
                        break;
                    case 0:
                        System.out.println("\nRetornando ao menu principal...");
                        break;
                    default:
                        System.out.println("\nOpção inválida! Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nEntrada inválida! Digite um número.");
                opcao = -1;
            }
            
        } while (opcao != 0);
    }
    
    private void adicionarDocumento() {
        try {
            System.out.print("Nome do arquivo: ");
            String arquivo = scanner.nextLine();
            
            System.out.print("Número de páginas: ");
            int tamanho = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Prioridade (URGENTE, NORMAL, BAIXO): ");
            String prioridade = scanner.nextLine();
            
            filaImpressao.adicionarDocumento(arquivo, tamanho, prioridade);
            
        } catch (NumberFormatException e) {
            System.out.println("Erro: Por favor, insira valores numéricos válidos.");
        }
    }
    
    /**
     * Menu do Sistema de Arquivos
     */
    private void menuSistemaArquivos() {
        int opcao;
        
        do {
            System.out.println("\n========= SISTEMA DE ARQUIVOS =========");
            System.out.println("1. mkdir <nome> - Criar diretório");
            System.out.println("2. touch <nome> - Criar arquivo");
            System.out.println("3. ls - Listar conteúdo");
            System.out.println("4. cd <nome> - Navegar diretório");
            System.out.println("5. rm <nome> - Remover arquivo/diretório");
            System.out.println("6. tree - Exibir árvore completa");
            System.out.println("7. pwd - Mostrar diretório atual");
            System.out.println("0. Voltar ao menu principal");
            System.out.println("=======================================");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                
                switch (opcao) {
                    case 1:
                        criarDiretorio();
                        break;
                    case 2:
                        criarArquivo();
                        break;
                    case 3:
                        sistemaArquivos.ls();
                        break;
                    case 4:
                        navegarDiretorio();
                        break;
                    case 5:
                        removerArquivoDiretorio();
                        break;
                    case 6:
                        sistemaArquivos.tree();
                        break;
                    case 7:
                        sistemaArquivos.mostrarDiretorioAtual();
                        break;
                    case 0:
                        System.out.println("\nRetornando ao menu principal...");
                        break;
                    default:
                        System.out.println("\nOpção inválida! Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nEntrada inválida! Digite um número.");
                opcao = -1;
            }
            
        } while (opcao != 0);
    }
    
    private void criarDiretorio() {
        System.out.print("Nome do diretório: ");
        String nome = scanner.nextLine();
        sistemaArquivos.mkdir(nome);
    }
    
    private void criarArquivo() {
        System.out.print("Nome do arquivo: ");
        String nome = scanner.nextLine();
        sistemaArquivos.touch(nome);
    }
    
    private void navegarDiretorio() {
        System.out.print("Diretório de destino (ou '..' para voltar, '/' para raiz): ");
        String nome = scanner.nextLine();
        sistemaArquivos.cd(nome);
    }
    
    private void removerArquivoDiretorio() {
        System.out.print("Nome do arquivo/diretório: ");
        String nome = scanner.nextLine();
        sistemaArquivos.rm(nome);
    }
    
    /**
     * Menu do Gerenciador de Memória
     */
    private void menuGerenciadorMemoria() {
        int opcao;
        
        do {
            System.out.println("\n========= GERENCIADOR DE MEMÓRIA =========");
            System.out.println("1. malloc <tamanho> - Alocar memória");
            System.out.println("2. free <id> - Liberar memória");
            System.out.println("3. show - Mostrar estado da memória");
            System.out.println("4. status - Mostrar estatísticas");
            System.out.println("5. clear - Limpar toda a memória");
            System.out.println("0. Voltar ao menu principal");
            System.out.println("===========================================");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                
                switch (opcao) {
                    case 1:
                        alocarMemoria();
                        break;
                    case 2:
                        liberarMemoria();
                        break;
                    case 3:
                        gerenciadorMemoria.show();
                        break;
                    case 4:
                        gerenciadorMemoria.mostrarStatus();
                        break;
                    case 5:
                        gerenciadorMemoria.limparMemoria();
                        break;
                    case 0:
                        System.out.println("\nRetornando ao menu principal...");
                        break;
                    default:
                        System.out.println("\nOpção inválida! Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nEntrada inválida! Digite um número.");
                opcao = -1;
            }
            
        } while (opcao != 0);
    }
    
    private void alocarMemoria() {
        try {
            System.out.print("Tamanho a alocar: ");
            int tamanho = Integer.parseInt(scanner.nextLine());
            gerenciadorMemoria.malloc(tamanho);
        } catch (NumberFormatException e) {
            System.out.println("Erro: Por favor, insira um número válido.");
        }
    }
    
    private void liberarMemoria() {
        try {
            System.out.print("ID do processo a liberar: ");
            int id = Integer.parseInt(scanner.nextLine());
            gerenciadorMemoria.free(id);
        } catch (NumberFormatException e) {
            System.out.println("Erro: Por favor, insira um número válido.");
        }
    }
}