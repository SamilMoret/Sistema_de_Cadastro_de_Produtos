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
            // Carrega as propriedades do persistence.xml
            Properties props = new Properties();
            
            // Configurações de conexão
            props.put("jakarta.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
            props.put("jakarta.persistence.jdbc.url", "jdbc:mysql://localhost:3306/produtos_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC");
            props.put("jakarta.persistence.jdbc.user", "root");
            props.put("jakarta.persistence.jdbc.password", "123456");
            
            // Configurações do Hibernate
            props.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
            props.put("hibernate.hbm2ddl.auto", "validate");
            props.put("hibernate.show_sql", "true");
            props.put("hibernate.format_sql", "true");
            props.put("hibernate.use_sql_comments", "true");
            
            // Configurações de conexão adicionais
            props.put("hibernate.connection.provider_disables_autocommit", "true");
            props.put("hibernate.connection.characterEncoding", "utf8");
            props.put("hibernate.connection.useUnicode", "true");
            props.put("hibernate.connection.allowPublicKeyRetrieval", "true");
            
            // Cria a fábrica de EntityManager com as propriedades
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT, props);
            
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
