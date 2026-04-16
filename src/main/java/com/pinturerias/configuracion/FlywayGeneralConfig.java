package com.pinturerias.configuracion;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class FlywayGeneralConfig {

    @Bean
    public Flyway flyway(Environment env) {
        Flyway flyway = Flyway.configure()
                .dataSource(
                        env.getProperty("spring.datasource.url"),
                        env.getProperty("spring.datasource.username"),
                        env.getProperty("spring.datasource.password")
                )
                .locations("classpath:db/migration/general")
                .load();

        flyway.migrate();
        return flyway;
    }
}



