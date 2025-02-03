package org.edu.main.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    private final Dotenv dotenv = Dotenv.load();

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
//                DB
                .url(dotenv.get("DB_URL"))
                .username(dotenv.get("DB_USERNAME"))
                .password(dotenv.get("DB_PASSWORD"))
                .driverClassName("com.mysql.cj.jdbc.Driver")
//                SET UP
                .build();
    }
}
