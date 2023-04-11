package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class ApplicationConfig {

    @Value("${spring.datasource.url}")
    private String DB_URL;
    @Value("${spring.datasource.username}")
    private String DB_USERNAME;
    @Value("${spring.datasource.password}")
    private String DB_PASSWORD;
    @Value("${spring.datasource.driver-class-name}")
    private String DB_DRIVER;
    @Value("${spring.datasource.dialect}")
    private String DB_DIALECT;
    @Value("${spring.jpa.show-sql}")
    private String SHOW_SQL;
    @Value("${spring.jpa.format-sql}")
    private String FORMAT_SQL;
    @Value("${spring.jpa.packages-scan}")
    private String PACKAGES_SCAN;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String HIBERNATE_DDL;


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        var factoryBean = new LocalContainerEntityManagerFactoryBean();
        var vendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setPackagesToScan(PACKAGES_SCAN);
        factoryBean.setDataSource(dataSource);
        factoryBean.setJpaProperties(hibernateProperties());
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        return factoryBean;
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        var dataSource = new DriverManagerDataSource();
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setDriverClassName(DB_DRIVER);
        return dataSource;
    }

    private Properties hibernateProperties() {
        var properties = new Properties();
        properties.put("hibernate.dialect", DB_DIALECT);
        properties.put("hibernate.show_sql", SHOW_SQL);
        properties.put("hibernate.format_sql", FORMAT_SQL);
        properties.put("hibernate.hbm2ddl.auto", HIBERNATE_DDL);
        return properties;
    }

    @Bean
    public JpaTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        var transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean.getObject());
        return transactionManager;
    }
}