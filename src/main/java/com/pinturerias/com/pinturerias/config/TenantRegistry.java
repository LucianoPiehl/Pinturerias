package com.pinturerias.com.pinturerias.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TenantRegistry {

    private final DataSourceFactory factory;
    private final AbstractRoutingDataSource routingDataSource;
    private final Map<Object, Object> tenantDataSources = new ConcurrentHashMap<>();

    public TenantRegistry(DataSourceFactory factory,
                          @Qualifier("tenantRoutingDataSource") AbstractRoutingDataSource routingDataSource) {
        this.factory = factory;
        this.routingDataSource = routingDataSource;
    }

    public synchronized void addTenant(String tenantId, String url, String user, String pass) {
        DataSource ds = factory.createDataSource(url, user, pass);
        tenantDataSources.put(tenantId, ds);
        routingDataSource.setTargetDataSources(new ConcurrentHashMap<>(tenantDataSources));
        routingDataSource.afterPropertiesSet();
        System.out.println("✅ Tenant agregado: " + tenantId);
    }

    public synchronized void removeTenant(String tenantId) {
        Object ds = tenantDataSources.remove(tenantId);
        if (ds instanceof AutoCloseable ac) {
            try { ac.close(); } catch (Exception ignored) {}
        }
        routingDataSource.setTargetDataSources(new ConcurrentHashMap<>(tenantDataSources));
        routingDataSource.afterPropertiesSet();
        System.out.println("❌ Tenant removido: " + tenantId);
    }

    public Map<Object, Object> currentTenants() {
        return Map.copyOf(tenantDataSources);
    }
}