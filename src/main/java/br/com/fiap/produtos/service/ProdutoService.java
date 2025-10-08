package br.com.fiap.produtos.service;

import br.com.fiap.produtos.model.Categoria;
import br.com.fiap.produtos.model.Produto;
import br.com.fiap.produtos.repository.CategoriaRepository;
import br.com.fiap.produtos.repository.ProdutoRepository;

// Usando el nombre completo de BigDecimal para evitar ambigüedades
import java.util.List;
import java.util.Optional;

public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    public ProdutoService() {
        this.produtoRepository = new ProdutoRepository();
        this.categoriaRepository = new CategoriaRepository();
    }

    public Produto cadastrarProduto(String nome, String descricao, java.math.BigDecimal preco, String nomeCategoria, Integer quantidadeEmEstoque) {
        // Validação básica
        if (quantidadeEmEstoque == null || quantidadeEmEstoque < 0) {
            throw new IllegalArgumentException("A quantidade em estoque não pode ser negativa");
        }
        
        // Verifica se a categoria já existe
        Optional<Categoria> categoriaOpt = categoriaRepository.findByNome(nomeCategoria);
        Categoria categoria;
        
        if (categoriaOpt.isPresent()) {
            categoria = categoriaOpt.get();
        } else {
            // Se a categoria não existe, cria uma nova
            categoria = new Categoria();
            categoria.setNome(nomeCategoria);
            categoria = categoriaRepository.save(categoria);
        }
        
        // Cria e salva o produto
        Produto produto = Produto.builder()
                .nome(nome)
                .descricao(descricao)
                .preco(preco)
                .categoria(categoria)
                .quantidadeEmEstoque(quantidadeEmEstoque)
                .build();
                
        return produtoRepository.save(produto);
    }

    public List<Produto> listarTodosProdutos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public void removerProduto(Long id) {
        produtoRepository.findById(id).ifPresent(produtoRepository::delete);
    }

    public List<Produto> buscarProdutosPorCategoria(String nomeCategoria) {
        return produtoRepository.findByCategoria(nomeCategoria);
    }

    public List<Produto> buscarProdutosPorPrecoMaximo(java.math.BigDecimal precoMaximo) {
        if (precoMaximo == null) {
            throw new IllegalArgumentException("O preço máximo não pode ser nulo");
        }
        return produtoRepository.findByPrecoMenorQue(precoMaximo);
    }

public Produto atualizarProduto(Long id, String nome, String descricao, java.math.BigDecimal preco, String nomeCategoria) {
        Optional<Produto> produtoOpt = produtoRepository.findById(id);
        if (produtoOpt.isEmpty()) {
            throw new IllegalArgumentException("Produto não encontrado com o ID: " + id);
        }
        
        Produto produto = produtoOpt.get();
        
        // Atualiza os campos básicos
        if (nome != null && !nome.isBlank()) {
            produto.setNome(nome);
        }
        
        if (descricao != null) {
            produto.setDescricao(descricao);
        }
        
        if (preco != null && preco.compareTo(java.math.BigDecimal.ZERO) > 0) {
            produto.setPreco(preco);
        }
        
        // Atualiza a categoria se fornecida
        if (nomeCategoria != null && !nomeCategoria.isBlank()) {
            Optional<Categoria> categoriaOpt = categoriaRepository.findByNome(nomeCategoria);
            Categoria categoria;
            
            if (categoriaOpt.isPresent()) {
                categoria = categoriaOpt.get();
            } else {
                categoria = new Categoria();
                categoria.setNome(nomeCategoria);
                categoria = categoriaRepository.save(categoria);
            }
            
            produto.setCategoria(categoria);
        }
        
        return produtoRepository.save(produto);
    }
}
