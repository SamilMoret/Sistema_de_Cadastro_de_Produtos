-- Desactivar verificación de claves foráneas temporalmente
SET FOREIGN_KEY_CHECKS = 0;

-- Vaciar las tablas
TRUNCATE TABLE produtos;
TRUNCATE TABLE categorias;

-- Reactivar verificación de claves foráneas
SET FOREIGN_KEY_CHECKS = 1;

-- Insertar categorías iniciales
INSERT INTO categorias (id, nome) VALUES 
    (1, 'Eletrônicos'),
    (2, 'Roupas'),
    (3, 'Alimentos'),
    (4, 'Livros'),
    (5, 'Casa e Decoração');

-- Insertar algunos productos de ejemplo
INSERT INTO produtos (nome, descricao, preco, categoria_id) VALUES 
    ('Smartphone XYZ', 'Último modelo com câmera de alta resolução', 2999.90, 1),
    ('Notebook Ultra Slim', 'Leve e potente, perfeito para trabalho', 4999.90, 1),
    ('Camiseta Básica', 'Algodão 100%, disponível em várias cores', 79.90, 2),
    ('Arroz Integral', 'Pacote 5kg, grãos selecionados', 25.90, 3),
    ('O Senhor dos Anéis', 'Edição de colecionador', 129.90, 4);

-- Actualizar las fechas de creación y actualización para que sean consistentes
UPDATE categorias SET data_criacao = NOW(), data_atualizacao = NOW();
UPDATE produtos SET data_cadastro = NOW(), data_atualizacao = NOW();
