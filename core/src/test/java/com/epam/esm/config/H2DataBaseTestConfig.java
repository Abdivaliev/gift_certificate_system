package com.epam.esm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;

@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:application-test.properties")
@Profile("test")
public class H2DataBaseTestConfig {
    @Value("${db.user}")
    String user;
    @Value("${db.password}")
    String password;
    @Value("${db.url}")
    String connectionUrl;

    @Bean
    public DataSource dataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setURL(connectionUrl);

        Resource initData = new ClassPathResource("sql/init-ddl.sql");
        Resource fillData = new ClassPathResource("sql/init-dml.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initData, fillData);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
