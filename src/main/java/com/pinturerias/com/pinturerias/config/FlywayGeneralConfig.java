package com.pinturerias.com.pinturerias.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class FlywayGeneralConfig {

    @Bean(initMethod = "migrate")
    public Flyway flyway(Environment env) {
        return Flyway.configure()
                .dataSource(
                        env.getProperty("spring.datasource.url"),
                        env.getProperty("spring.datasource.username"),
                        env.getProperty("spring.datasource.password")
                )
                .locations("classpath:db/migration/general")
                .baselineOnMigrate(true)
                .load();
    }
}