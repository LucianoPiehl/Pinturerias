package com.pinturerias.configuracion;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    // ---------- BD GENERAL ----------
    @Primary
    @Bean(name = "generalDataSource")
    public DataSource generalDataSource(org.springframework.core.env.Environment env) {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(env.getProperty("spring.datasource.url"));
        ds.setUsername(env.getProperty("spring.datasource.username"));
        ds.setPassword(env.getProperty("spring.datasource.password"));
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setMaximumPoolSize(10);
        return ds;
    }

    @Primary
    @Bean(name = "generalEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean generalEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("generalDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.pinturerias.general.entidad",
                "com.pinturerias.compartidos.entidad.general",
                "com.pinturerias.compartidos.entidad.shared")
                .persistenceUnit("generalPU")
                .build();
    }

    @Primary
    @Bean(name = "generalTransactionManager")
    public PlatformTransactionManager generalTransactionManager(
            @Qualifier("generalEntityManagerFactory") LocalContainerEntityManagerFactoryBean emf) {
        return new JpaTransactionManager(emf.getObject());
    }

    @EnableJpaRepositories(
            basePackages = "com.pinturerias.general.repositorio",
            entityManagerFactoryRef = "generalEntityManagerFactory",
            transactionManagerRef = "generalTransactionManager"
    )
    @Configuration
    static class GeneralJpaConfig {}

    // ---------- BD TENANTS ----------
    @Bean(name = "tenantRoutingDataSource")
    public AbstractRoutingDataSource tenantRoutingDataSource(
            @Qualifier("generalDataSource") DataSource generalDataSource) {

        TenantRoutingDataSource routing = new TenantRoutingDataSource();

        // al inicio, no hay tenants cargados
        routing.setTargetDataSources(new HashMap<>());

        // establecemos un DataSource por defecto (la BD general)
        // esto evita que Hibernate tire "lookup key [null]"
        routing.setDefaultTargetDataSource(generalDataSource);

        routing.afterPropertiesSet();
        return routing;
    }

    @Bean(name = "tenantEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean tenantEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("tenantRoutingDataSource") DataSource routingDataSource) {

        // ConfiguraciÃ³n especÃ­fica del contexto tenant
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "none"); // âŒ no validar ni crear tablas al arrancar
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

        return builder
                .dataSource(routingDataSource)
                .packages("com.pinturerias.sucursal.entidad",
                "com.pinturerias.compartidos.entidad.sucursal",
                "com.pinturerias.compartidos.entidad.shared")

                .persistenceUnit("tenantPU")
                .properties(properties)
                .build();
    }

    @Bean(name = "tenantTransactionManager")
    public PlatformTransactionManager tenantTransactionManager(
            @Qualifier("tenantEntityManagerFactory") LocalContainerEntityManagerFactoryBean emf) {
        return new JpaTransactionManager(emf.getObject());
    }

    @EnableJpaRepositories(
            basePackages = "com.pinturerias.sucursal.repositorio",
            entityManagerFactoryRef = "tenantEntityManagerFactory",
            transactionManagerRef = "tenantTransactionManager"
    )
    @Configuration
    static class TenantJpaConfig {}
}




