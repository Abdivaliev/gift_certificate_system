package com.epam.esm.config.db;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Configuration class for setting up the database connection.
 * This class reads the database configuration from the application properties file.
 * It sets up the data source based on the active profile - 'dev' or 'prod'.
 */
@Configuration
@PropertySource(value = "classpath:application-${spring.profiles.active}.properties", ignoreResourceNotFound = true)
@Profile({"prod", "dev"})
public class DBConfig {
    @Value("${db.user}")
    String user;
    @Value("${db.password}")
    String password;
    @Value("${db.driver}")
    String className;
    @Value("${db.url}")
    String connectionUrl;
    @Value("${db.connections}")
    Integer connections;

    @Bean
    @Profile("dev")
    public DataSource getDataSourceDev() {

        return getBasicDataSource();
    }

    @Bean
    @Profile("prod")
    public DataSource getDataSourceProd() {

        return getBasicDataSource();
    }

    @Bean
    public JdbcTemplate getJDBCTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    private BasicDataSource getBasicDataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(password);
        basicDataSource.setDriverClassName(className);
        basicDataSource.setUrl(connectionUrl);
        basicDataSource.setMaxActive(connections);
        return basicDataSource;
    }
}
