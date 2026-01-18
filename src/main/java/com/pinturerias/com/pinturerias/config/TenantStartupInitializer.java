package com.pinturerias.com.pinturerias.config;

import com.pinturerias.com.pinturerias.general.repository.SucursalRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class TenantStartupInitializer {

    private final SucursalRepository sucursalRepo;
    private final TenantRegistry tenantRegistry;

    public TenantStartupInitializer(SucursalRepository sucursalRepo,
                                    TenantRegistry tenantRegistry) {
        this.sucursalRepo = sucursalRepo;
        this.tenantRegistry = tenantRegistry;
    }

    @PostConstruct
    public void initTenants() {
        System.out.println("🔄 Cargando tenants desde la BD general...");

        sucursalRepo.findAll().forEach(s -> {
            tenantRegistry.addTenant(
                    s.getCodigo(),
                    s.getJdbcUrl(),
                    s.getUsername(),
                    s.getPassword()
            );
        });

        System.out.println("✅ Tenants cargados correctamente.");
    }
}