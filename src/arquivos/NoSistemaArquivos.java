package arquivos;

import java.util.ArrayList;
import java.util.List;

/**
 * Nó da árvore que representa um diretório ou arquivo
 */
public class NoSistemaArquivos {
    private String nome;
    private boolean ehDiretorio;
    private NoSistemaArquivos pai;
    private List<NoSistemaArquivos> filhos;
    private int tamanho;              // Tamanho em bytes (apenas para arquivos)
    
    public NoSistemaArquivos(String nome, boolean ehDiretorio, NoSistemaArquivos pai) {
        this.nome = nome;
        this.ehDiretorio = ehDiretorio;
        this.pai = pai;
        this.filhos = new ArrayList<>();
        this.tamanho = 0;
    }
    
    // Getters
    public String getNome() {
        return nome;
    }
    
    public boolean ehDiretorio() {
        return ehDiretorio;
    }
    
    public NoSistemaArquivos getPai() {
        return pai;
    }
    
    public List<NoSistemaArquivos> getFilhos() {
        return filhos;
    }
    
    public int getTamanho() {
        return tamanho;
    }
    
    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }
    
    /**
     * Adiciona um filho ao nó
     */
    public void adicionarFilho(NoSistemaArquivos filho) {
        filhos.add(filho);
    }
    
    /**
     * Remove um filho do nó
     */
    public boolean removerFilho(NoSistemaArquivos filho) {
        return filhos.remove(filho);
    }
    
    /**
     * Busca um filho pelo nome
     */
    public NoSistemaArquivos buscarFilho(String nome) {
        for (NoSistemaArquivos filho : filhos) {
            if (filho.getNome().equals(nome)) {
                return filho;
            }
        }
        return null;
    }
    
    /**
     * Retorna o caminho completo do nó
     */
    public String getCaminho() {
        if (pai == null) {
            return nome.equals("/") ? "/" : "/" + nome;
        }
        return pai.getCaminho() + (pai.getNome().equals("/") ? "" : "/") + nome;
    }
    
    @Override
    public String toString() {
        if (ehDiretorio) {
            return nome + "/";
        }
        return nome + " (" + tamanho + " bytes)";
    }
}

