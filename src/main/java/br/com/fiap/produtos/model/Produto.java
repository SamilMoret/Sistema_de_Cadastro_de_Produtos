package br.com.fiap.produtos.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataDeCadastro;

    @Column(name = "quantidade_estoque", nullable = false)
    private Integer quantidadeEmEstoque;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @PrePersist
    protected void onCreate() {
        this.dataDeCadastro = LocalDateTime.now();
    }

    @Override
    public String toString () {
        return String.format (
                "Produto{id=%d, nome='%s', descricao='%s', preco=%.2f, dataDeCadastro=%s, quantidadeEmEstoque=%d, categoria='%s'}",
                id,
                nome,
                descricao != null ? descricao : "",
                preco,
                dataDeCadastro != null ? dataDeCadastro.toString ( ) : "não informada",
                quantidadeEmEstoque,
                categoria != null ? categoria.getNome ( ) : "sem categoria"
        );
    }

    @Builder
    public Produto(Long id, String nome, String descricao, BigDecimal preco, Categoria categoria, Integer quantidadeEmEstoque) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
        this.quantidadeEmEstoque = quantidadeEmEstoque != null ? quantidadeEmEstoque : 0;
    }
