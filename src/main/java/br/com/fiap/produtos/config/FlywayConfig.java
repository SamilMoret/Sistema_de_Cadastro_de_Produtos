package br.com.fiap.produtos.config;

import org.flywaydb.core.Flyway;
import java.util.Properties;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FlywayConfig {
    
    private static final String CONFIG_FILE = "src/main/resources/application.properties";

    public static void migrate() {
        try {
            // Carregar as propriedades do arquivo de configuração
            Properties props = new Properties();
            try (InputStream in = Files.newInputStream(Paths.get(CONFIG_FILE))) {
                props.load(in);
            }
            
            // Configurar e executar o Flyway
            Flyway flyway = Flyway.configure()
                    .dataSource(
                            props.getProperty("jakarta.persistence.jdbc.url"),
                            props.getProperty("jakarta.persistence.jdbc.user"),
                            props.getProperty("jakarta.persistence.jdbc.password")
                    )
                    .baselineOnMigrate(true)
                    .outOfOrder(false)
                    .locations("classpath:db/migration")
                    .load();
            
            // Executar as migrações
            flyway.migrate();
            
            System.out.println("Migrações do Flyway executadas com sucesso!");
            
        } catch (Exception e) {
            System.err.println("Erro ao executar migrações do Flyway: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Falha ao executar migrações do banco de dados", e);
        }
    }
}
