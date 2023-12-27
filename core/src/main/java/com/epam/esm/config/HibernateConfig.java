package com.epam.esm.config;

import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@RequiredArgsConstructor
public class HibernateConfig {

    private final Environment environment;

    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        sessionFactory.setPackagesToScan("com.epam.esm.entity");
        sessionFactory.setHibernateProperties(getAdditionalProperties());
        return sessionFactory;
    }

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager getTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(getSessionFactory().getObject());

        return transactionManager;
    }

    @Bean
    public static PersistenceExceptionTranslationPostProcessor getExceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties getAdditionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.show_sql", "true");

        properties.setProperty("hibernate.ejb.event.post-insert", "org.hibernate.envers.event.spi.EnversPostInsertEventListenerImpl");
        properties.setProperty("hibernate.ejb.event.post-update", "org.hibernate.envers.event.spi.EnversPostUpdateEventListenerImpl");
        properties.setProperty("hibernate.ejb.event.post-delete", "org.hibernate.envers.event.spi.EnversPostDeleteEventListenerImpl");
        properties.setProperty("hibernate.ejb.event.pre-collection-update", "org.hibernate.envers.event.spi.EnversPreCollectionUpdateEventListenerImpl");
        properties.setProperty("hibernate.ejb.event.pre-collection-remove", "org.hibernate.envers.event.spi.EnversPreCollectionRemoveEventListenerImpl");
        properties.setProperty("hibernate.ejb.event.post-collection-recreate", "org.hibernate.envers.event.spi.EnversPostCollectionRecreateEventListenerImpl");

        return properties;
    }
}