package arquivos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Sistema de arquivos simples utilizando estrutura em árvore
 */
public class FileSystem {
    private NoSistemaArquivos raiz;
    private NoSistemaArquivos diretorioAtual;
    
    public FileSystem() {
        this.raiz = new NoSistemaArquivos("/", true, null);
        this.diretorioAtual = raiz;
    }
    
    /**
     * Cria um diretório
     * mkdir <nome>
     */
    public void mkdir(String nome) {
        // Verifica se já existe
        if (diretorioAtual.buscarFilho(nome) != null) {
            System.out.println("Erro: '" + nome + "' já existe!");
            return;
        }
        
        NoSistemaArquivos novoDir = new NoSistemaArquivos(nome, true, diretorioAtual);
        diretorioAtual.adicionarFilho(novoDir);
        System.out.println("Diretório '" + nome + "' criado.");
    }
    
    /**
     * Cria um arquivo
     * touch <nome>
     */
    public void touch(String nome) {
        // Verifica se já existe
        if (diretorioAtual.buscarFilho(nome) != null) {
            System.out.println("Erro: '" + nome + "' já existe!");
            return;
        }
        
        NoSistemaArquivos novoArquivo = new NoSistemaArquivos(nome, false, diretorioAtual);
        novoArquivo.setTamanho(100); // Tamanho padrão
        diretorioAtual.adicionarFilho(novoArquivo);
        System.out.println("Arquivo '" + nome + "' criado.");
    }
    
    /**
     * Lista os conteúdos do diretório atual
     * ls
     */
    public void ls() {
        System.out.println("\nConteúdo de: " + diretorioAtual.getCaminho());
        
        if (diretorioAtual.getFilhos().isEmpty()) {
            System.out.println("(vazio)");
        } else {
            List<NoSistemaArquivos> filhos = new ArrayList<>(diretorioAtual.getFilhos());
            Collections.sort(filhos, (a, b) -> {
                // Diretórios primeiro
                if (a.ehDiretorio() && !b.ehDiretorio()) return -1;
                if (!a.ehDiretorio() && b.ehDiretorio()) return 1;
                // Depois ordena por nome
                return a.getNome().compareTo(b.getNome());
            });
            
            for (NoSistemaArquivos filho : filhos) {
                System.out.println("  " + filho.toString());
            }
        }
    }
    
    /**
     * Navega para um diretório
     * cd <pasta>
     */
    public void cd(String nome) {
        if (nome.equals("..")) {
            // Volta para o pai
            if (diretorioAtual.getPai() != null) {
                diretorioAtual = diretorioAtual.getPai();
                System.out.println("Diretório atual: " + diretorioAtual.getCaminho());
            } else {
                System.out.println("Já está na raiz!");
            }
        } else if (nome.equals("/")) {
            // Vai para a raiz
            diretorioAtual = raiz;
            System.out.println("Diretório atual: /");
        } else {
            // Navega para um filho
            NoSistemaArquivos filho = diretorioAtual.buscarFilho(nome);
            if (filho == null) {
                System.out.println("Erro: '" + nome + "' não encontrado!");
            } else if (!filho.ehDiretorio()) {
                System.out.println("Erro: '" + nome + "' não é um diretório!");
            } else {
                diretorioAtual = filho;
                System.out.println("Diretório atual: " + diretorioAtual.getCaminho());
            }
        }
    }
    
    /**
     * Remove um arquivo ou diretório
     * rm <nome>
     */
    public void rm(String nome) {
        NoSistemaArquivos filho = diretorioAtual.buscarFilho(nome);
        if (filho == null) {
            System.out.println("Erro: '" + nome + "' não encontrado!");
            return;
        }
        
        if (filho.ehDiretorio() && !filho.getFilhos().isEmpty()) {
            System.out.println("Erro: Diretório '" + nome + "' não está vazio!");
            return;
        }
        
        diretorioAtual.removerFilho(filho);
        if (filho.ehDiretorio()) {
            System.out.println("Diretório '" + nome + "' removido.");
        } else {
            System.out.println("Arquivo '" + nome + "' removido.");
        }
    }
    
    /**
     * Exibe a estrutura em árvore
     * tree
     */
    public void tree() {
        System.out.println("\n=== Estrutura do Sistema de Arquivos ===");
        imprimirArvore(raiz, "", true);
    }
    
    /**
     * Método auxiliar recursivo para imprimir a árvore
     */
    private void imprimirArvore(NoSistemaArquivos no, String indentacao, boolean ehUltimo) {
        if (no == null) return;
        
        // Imprime o nó atual
        System.out.print(indentacao);
        if (!no.equals(raiz)) {
            System.out.print(ehUltimo ? "└── " : "├── ");
        }
        System.out.println(no.toString());
        
        // Imprime os filhos
        List<NoSistemaArquivos> filhos = no.getFilhos();
        for (int i = 0; i < filhos.size(); i++) {
            boolean ehUltimoFilho = (i == filhos.size() - 1);
            String novaIndentacao = indentacao + (ehUltimo ? "    " : "│   ");
            imprimirArvore(filhos.get(i), novaIndentacao, ehUltimoFilho);
        }
    }
    
    /**
     * Mostra o diretório atual
     */
    public void mostrarDiretorioAtual() {
        System.out.println("Diretório atual: " + diretorioAtual.getCaminho());
    }
}

