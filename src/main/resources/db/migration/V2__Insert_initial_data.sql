-- Inserir categorias iniciais apenas se não existirem
INSERT IGNORE INTO categorias (id, nome) VALUES 
    (1, 'Eletrônicos'),
    (2, 'Roupas'),
    (3, 'Alimentos'),
    (4, 'Livros'),
    (5, 'Casa e Decoração');

-- Inserir produtos de exemplo apenas se não existirem
INSERT IGNORE INTO produtos (id, nome, descricao, preco, categoria_id) VALUES 
    (1, 'Smartphone XYZ', 'Último modelo com câmera de alta resolução', 2999.90, 1),
    (2, 'Notebook Ultra Slim', 'Leve e potente, perfeito para o trabalho', 4999.90, 1),
    (3, 'Camiseta Básica', 'Algodão 100%, disponível em várias cores', 79.90, 2),
    (4, 'Arroz Integral', 'Pacote 5kg, grãos selecionados', 25.90, 3),
    (5, 'O Senhor dos Anéis', 'Edição de colecionador', 129.90, 4);
