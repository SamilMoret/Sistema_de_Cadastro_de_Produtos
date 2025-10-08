package br.com.fiap.produtos.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Properties;

public class HibernateUtil {

    private static final String PERSISTENCE_UNIT = "produtos-pu";
    private static EntityManagerFactory factory;
    
    static {
        try {
            // Configura o sistema de logging
            try {
                java.net.URL loggingConfig = HibernateUtil.class.getClassLoader().getResource("logging.properties");
                if (loggingConfig != null) {
                    System.setProperty("java.util.logging.config.file", loggingConfig.getFile());
                    System.setProperty("org.jboss.logging.provider", "slf4j");
                }
            } catch (Exception e) {
                System.err.println("Aviso: Não foi possível configurar o sistema de logging: " + e.getMessage());
                // Continua a execução mesmo se houver falha na configuração de log
            }
            // Carrega as propriedades do persistence.xml
            Properties props = new Properties();
            
            // Configurações de conexão
            props.put("jakarta.persistence.jdbc.url", "jdbc:mysql://localhost:3306/produtos_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC");
            props.put("jakarta.persistence.jdbc.user", "root");
            props.put("jakarta.persistence.jdbc.password", "123456");

            // Configurações do HikariCP
            props.put("hibernate.connection.provider", "org.hibernate.hikaricp.internal.HikariCPConnectionProvider");
            props.put("hibernate.hikari.dataSourceClassName", "com.mysql.cj.jdbc.MysqlDataSource");
            props.put("hibernate.hikari.dataSource.url", "jdbc:mysql://localhost:3306/produtos_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC");
            props.put("hibernate.hikari.dataSource.user", "root");
            props.put("hibernate.hikari.dataSource.password", "123456");
            props.put("hibernate.hikari.maximumPoolSize", "20");
            props.put("hibernate.hikari.minimumIdle", "5");
            props.put("hibernate.hikari.idleTimeout", "300000");
            props.put("hibernate.hikari.maxLifetime", "1200000");
            props.put("hibernate.hikari.connectionTimeout", "30000");
            props.put("hibernate.hikari.autoCommit", "false");
            
            // Configurações de logging
            props.put("hibernate.show_sql", "true");
            props.put("hibernate.format_sql", "true");
            props.put("hibernate.use_sql_comments", "true");
            
            // Cria a fábrica de EntityManager com as propriedades
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT, props);
            
            // Adiciona um shutdown hook para fechar a fábrica ao desligar a aplicação
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (factory != null && factory.isOpen()) {
                    factory.close();
                }
            }));
            
        } catch (Exception e) {
            System.err.println("Erro ao criar EntityManagerFactory: " + e.getMessage());
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }
    
    public static EntityManager getEntityManager() {
        if (factory == null) {
            throw new IllegalStateException("EntityManagerFactory não foi inicializado corretamente.");
        }
        return factory.createEntityManager();
    }
    
    public static void close() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }
}
