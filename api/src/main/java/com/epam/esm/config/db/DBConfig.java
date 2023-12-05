package com.epam.esm.config.db;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

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
    public DataSource dataSourceDev() {
        BasicDataSource basicDataSource = getBasicDataSource();

        Resource initData = new ClassPathResource("sql/init-ddl.sql");
        Resource fillData = new ClassPathResource("sql/init-dml.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initData, fillData);
        DatabasePopulatorUtils.execute(databasePopulator, basicDataSource);
        return basicDataSource;
    }

    @Bean
    @Profile("prod")
    public DataSource dataSourceProd() {
        BasicDataSource basicDataSource = getBasicDataSource();

        Resource initData = new ClassPathResource("sql/init-ddl.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initData);
        DatabasePopulatorUtils.execute(databasePopulator, basicDataSource);
        return basicDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
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
