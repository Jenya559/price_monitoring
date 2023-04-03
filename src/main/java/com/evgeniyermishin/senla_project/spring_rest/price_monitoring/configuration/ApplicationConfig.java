package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class ApplicationConfig {
    @Value("${spring.datasource.driver-class-name}")
    private String DB_DRIVER;
    @Value("${spring.datasource.url}")
    private String DB_URL;
    @Value("${spring.datasource.dialect}")
    private String DB_DIALECT;

    @Value("${spring.datasource.username}")
    private String DB_USERNAME;
    @Value("${spring.datasource.password}")
    private String DB_PASSWORD;

    @Value("${spring.hibernate.packages-scan}")
    private String PACKAGES_SCAN;

    @Value("${spring.jpa.properties.hibernate.default_schema}")
    private String DB_SCHEMA;

    @Value("${spring.jpa.format-sql}")
    private String FORMAT_SQL;

    @Bean(name = "entityManagerFactory")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(PACKAGES_SCAN);
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        return dataSource;
    }

    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", DB_DIALECT);
        hibernateProperties.setProperty("hibernate.default_schema", DB_SCHEMA);
        hibernateProperties.setProperty("hibernate.dialect", DB_DIALECT);
        hibernateProperties.setProperty("hibernate.format_sql", FORMAT_SQL);
        return hibernateProperties;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(sessionFactory().getObject());
        return transactionManager;
    }



}