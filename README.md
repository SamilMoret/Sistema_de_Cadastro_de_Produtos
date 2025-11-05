# Sistema de Gerenciamento de Produtos

Sistema de gerenciamento de produtos desenvolvido em Java com Hibernate, MySQL e Flyway para o controle de estoque e cadastro de produtos.

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Maven** - Gerenciamento de dependências
- **Hibernate ORM** - Mapeamento objeto-relacional
- **MySQL** - Banco de dados
- **Flyway** - Controle de migrações de banco de dados
- **H2 Database** - Banco de dados em memória para testes
- **Lombok** - Redução de código boilerplate
- **JUnit 5** - Testes unitários
- **HikariCP** - Pool de conexões
- **Logback** - Logging

## 📋 Pré-requisitos

- Java 21 ou superior
- Maven 3.8 ou superior
- MySQL 8.0 ou superior
- Git (opcional, para controle de versão)

## 🛠️ Configuração do Ambiente

1. **Clone o repositório**
   ```bash
   git clone [URL_DO_REPOSITÓRIO]
   cd nano-fiap-produtos
   ```

2. **Configure o banco de dados**
   - Crie um banco de dados MySQL
   - Atualize as configurações de conexão no arquivo `src/main/resources/application.properties`

3. **Execute as migrações do Flyway**
   ```bash
   mvn flyway:migrate
   ```

4. **Compile o projeto**
   ```bash
   mvn clean install
   ```

## 🚀 Executando o Projeto

Execute o seguinte comando para iniciar a aplicação:

```bash
mvn spring-boot:run
```

Ou execute o arquivo `iniciar-sistema.bat` no Windows.

## 🧪 Executando Testes

Para executar os testes unitários:

```bash
mvn test
```

## 🏗️ Estrutura do Projeto

```
nano-fiap-produtos/
├── src/
│   ├── main/
│   │   ├── java/br/com/fiap/produtos/
│   │   │   ├── model/       # Entidades do domínio
│   │   │   ├── repository/  # Repositórios de dados
│   │   │   ├── service/     # Lógica de negócios
│   │   │   └── Main.java    # Classe principal
│   │   └── resources/       # Arquivos de configuração
│   └── test/                # Testes unitários e de integração
└── pom.xml                  # Configuração do Maven
```

## 📝 Licença

Este projeto está licenciado sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

## 🤝 Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir uma issue ou enviar um pull request.

---

Desenvolvido por [Seu Nome] - FIAP © 2025
