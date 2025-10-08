-- Adiciona a coluna quantidade_estoque na tabela produtos
ALTER TABLE produtos
    ADD COLUMN quantidade_estoque INT NOT NULL DEFAULT 0;

-- Atualiza os registros existentes para terem quantidade 10 por padrão
UPDATE produtos SET quantidade_estoque = 10;
