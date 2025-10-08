package br.com.fiap.produtos;

import br.com.fiap.produtos.config.FlywayConfig;
import br.com.fiap.produtos.model.Produto;
import br.com.fiap.produtos.service.ProdutoService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class SistemaCadastroProdutos {
    private static final ProdutoService produtoService = new ProdutoService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            // Inicializar Flyway para migrações de banco de dados
            FlywayConfig.migrate();
            
            // Iniciar a aplicação
            exibirMenuPrincipal();
        } catch (Exception e) {
            System.err.println("Erro ao iniciar a aplicação: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void exibirMenuPrincipal() {
        int opcao;
        do {
            System.out.println("\n=== SISTEMA DE CADASTRO DE PRODUTOS ===");
            System.out.println("1. Cadastrar novo produto");
            System.out.println("2. Listar todos os produtos");
            System.out.println("3. Buscar produto por ID");
            System.out.println("4. Buscar produtos por categoria");
            System.out.println("5. Buscar produtos por preço máximo");
            System.out.println("6. Atualizar produto");
            System.out.println("7. Remover produto");
            System.out.println("0. Sair");
            System.out.print("\nEscolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                System.out.println();
                
                switch (opcao) {
                    case 1 -> cadastrarProduto();
                    case 2 -> listarProdutos();
                    case 3 -> buscarProdutoPorId();
                    case 4 -> buscarProdutosPorCategoria();
                    case 5 -> buscarProdutosPorPrecoMaximo();
                    case 6 -> atualizarProduto();
                    case 7 -> removerProduto();
                    case 0 -> System.out.println("Saindo do sistema...");
                    default -> System.out.println("Opção inválida! Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nErro: Por favor, digite um número válido.");
                opcao = -1;
            } catch (Exception e) {
                System.out.println("\nOcorreu um erro: " + e.getMessage());
                opcao = -1;
            }
            
        } while (opcao != 0);
        
        scanner.close();
    }

    private static void cadastrarProduto() {
        System.out.println("=== CADASTRAR NOVO PRODUTO ===");
        
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();
        
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        
        BigDecimal preco = null;
        while (preco == null) {
            try {
                System.out.print("Preço: R$ ");
                preco = new BigDecimal(scanner.nextLine().replace(",", "."));
                if (preco.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("O preço deve ser maior que zero!");
                    preco = null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um valor numérico válido.");
            }
        }
        
        System.out.print("Categoria: ");
        String categoria = scanner.nextLine();
        
        try {
            Produto produto = produtoService.cadastrarProduto(nome, descricao, preco, categoria);
            System.out.println("\nProduto cadastrado com sucesso!");
            System.out.println(produto);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    private static void listarProdutos() {
        System.out.println("=== LISTA DE PRODUTOS ===");
        List<Produto> produtos = produtoService.listarTodosProdutos();
        
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        } else {
            for (int i = 0; i < produtos.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, produtos.get(i));
            }
        }
    }

    private static void buscarProdutoPorId() {
        System.out.println("=== BUSCAR PRODUTO POR ID ===");
        System.out.print("Digite o ID do produto: ");
        
        try {
            Long id = Long.parseLong(scanner.nextLine());
            produtoService.buscarProdutoPorId(id).ifPresentOrElse(
                produto -> {
                    System.out.println("\nProduto encontrado:");
                    System.out.println(produto);
                },
                () -> System.out.println("\nProduto não encontrado com o ID: " + id)
            );
        } catch (NumberFormatException e) {
            System.out.println("ID inválido! Digite um número.");
        }
    }

    private static void buscarProdutosPorCategoria() {
        System.out.println("=== BUSCAR PRODUTOS POR CATEGORIA ===");
        System.out.print("Digite o nome da categoria: ");
        String categoria = scanner.nextLine();
        
        List<Produto> produtos = produtoService.buscarProdutosPorCategoria(categoria);
        
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado na categoria: " + categoria);
        } else {
            System.out.printf("\nProdutos na categoria '%s':%n", categoria);
            for (int i = 0; i < produtos.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, produtos.get(i));
            }
        }
    }

    private static void buscarProdutosPorPrecoMaximo() {
        System.out.println("=== BUSCAR PRODUTOS ATÉ UM PREÇO MÁXIMO ===");
        System.out.print("Digite o preço máximo: R$ ");
        
        try {
            BigDecimal precoMaximo = new BigDecimal(scanner.nextLine().replace(",", "."));
            List<Produto> produtos = produtoService.buscarProdutosPorPrecoMaximo(precoMaximo);
            
            if (produtos.isEmpty()) {
                System.out.println("Nenhum produto encontrado com preço até R$ " + precoMaximo);
            } else {
                System.out.printf("\nProdutos com preço até R$ %.2f:%n", precoMaximo);
                for (int i = 0; i < produtos.size(); i++) {
                    System.out.printf("%d. %s%n", i + 1, produtos.get(i));
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Preço inválido! Digite um valor numérico.");
        }
    }

    private static void atualizarProduto() {
        System.out.println("=== ATUALIZAR PRODUTO ===");
        System.out.print("Digite o ID do produto que deseja atualizar: ");
        
        try {
            Long id = Long.parseLong(scanner.nextLine());
            
            produtoService.buscarProdutoPorId(id).ifPresentOrElse(
                produto -> {
                    System.out.println("Produto atual: " + produto);
                    System.out.println("\nDeixe em branco os campos que não deseja alterar.");
                    
                    System.out.print("Novo nome: ");
                    String nome = scanner.nextLine();
                    
                    System.out.print("Nova descrição: ");
                    String descricao = scanner.nextLine();
                    
                    BigDecimal preco = null;
                    System.out.print("Novo preço: R$ ");
                    String precoStr = scanner.nextLine();
                    if (!precoStr.isBlank()) {
                        try {
                            preco = new BigDecimal(precoStr.replace(",", "."));
                            if (preco.compareTo(BigDecimal.ZERO) <= 0) {
                                System.out.println("O preço deve ser maior que zero!");
                                return;
                            }
                        } catch (NumberFormatException e) {
                            return;
                        }
                    }
                    
                    System.out.print("Nova categoria (deixe em branco para não alterar): ");
                    String novaCategoria = scanner.nextLine();
                    
                    try {
                        Produto produtoAtualizado = produtoService.atualizarProduto(
                            id,
                            nome.isBlank() ? null : nome,
                            descricao.isBlank() ? null : descricao,
                            preco,
                            novaCategoria.isBlank() ? null : novaCategoria
                        );
                        
                        System.out.println("\nProduto atualizado com sucesso!");
                        System.out.println(produtoAtualizado);
                    } catch (Exception e) {
                        System.out.println("Erro ao atualizar produto: " + e.getMessage());
                    }
                },
                () -> System.out.println("Produto não encontrado com o ID: " + id)
            );
        } catch (NumberFormatException e) {
            System.out.println("ID inválido! Digite um número.");
        }
    }

    private static void removerProduto() {
        System.out.println("=== REMOVER PRODUTO ===");
        System.out.print("Digite o ID do produto que deseja remover: ");
        
        try {
            Long id = Long.parseLong(scanner.nextLine());
            
            produtoService.buscarProdutoPorId(id).ifPresentOrElse(
                produto -> {
                    System.out.println("Tem certeza que deseja remover o produto abaixo? (s/n)");
                    System.out.println(produto);
                    System.out.print("Confirmação: ");
                    
                    if (scanner.nextLine().equalsIgnoreCase("s")) {
                        try {
                            produtoService.removerProduto(id);
                            System.out.println("Produto removido com sucesso!");
                        } catch (Exception e) {
                            System.out.println("Erro ao remover produto: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Operação cancelada.");
                    }
                },
                () -> System.out.println("Produto não encontrado com o ID: " + id)
            );
        } catch (NumberFormatException e) {
            System.out.println("ID inválido! Digite um número.");
        }
    }
}