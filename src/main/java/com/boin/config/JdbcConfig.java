package com.boin.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {

    @Value("${spring.datasource.url}")
    private String URL;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driverClassName}")
    private String driver;

    @Bean
    public DataSource DataSource() {
        return getDataSource(URL, username , password);
    }

    @Bean
    public JdbcTemplate database(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    public DataSource getDataSource(String URL, String username, String password) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
