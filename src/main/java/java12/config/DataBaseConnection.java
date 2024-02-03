package java12.config;

import jakarta.persistence.EntityManagerFactory;
import java12.entities.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class DataBaseConnection {
    public static EntityManagerFactory getEntityManagerFactory(){
        Properties properties = new Properties();
        properties.put(Environment.JAKARTA_JDBC_DRIVER, "org.postgresql.Driver");
        properties.put(Environment.JAKARTA_JDBC_URL, "jdbc:postgresql://localhost:5432/min_airbnb");
        properties.put(Environment.JAKARTA_JDBC_USER, "postgres");
        properties.put(Environment.JAKARTA_JDBC_PASSWORD, "rootroot");
        properties.put(Environment.HBM2DDL_AUTO, "update");
        properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        properties.put(Environment.SHOW_SQL, "true");
        Configuration configuration = new Configuration();
        configuration.addProperties(properties);
        configuration.addAnnotatedClass(Address.class);
        configuration.addAnnotatedClass(Agency.class);
        configuration.addAnnotatedClass(Customer.class);
        configuration.addAnnotatedClass(House.class);
        configuration.addAnnotatedClass(Owner.class);
        configuration.addAnnotatedClass(RentInfo.class);
        return configuration.buildSessionFactory().unwrap(EntityManagerFactory.class);
    }
}
