@echo off
echo ===================================
echo  Iniciando Sistema de Cadastro de Produtos
echo ===================================

mvn clean compile exec:java -Dexec.mainClass="br.com.fiap.produtos.SistemaCadastroProdutos"

pause
