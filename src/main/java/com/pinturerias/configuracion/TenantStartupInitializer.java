package com.pinturerias.configuracion;

import com.pinturerias.general.repositorio.SucursalRepository;
import org.flywaydb.core.Flyway;
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

    @jakarta.annotation.PostConstruct
    public void initTenants() {
        sucursalRepo.findAll().forEach(s -> {
            migrarTenant(s.getJdbcUrl(), s.getUsername(), s.getPassword());
            tenantRegistry.addTenant(
                    s.getCodigo(),
                    s.getJdbcUrl(),
                    s.getUsername(),
                    s.getPassword()
            );
        });
    }

    private void migrarTenant(String jdbcUrl, String user, String pass) {
        Flyway flyway = Flyway.configure()
                .dataSource(jdbcUrl, user, pass)
                .locations("classpath:db/migration/sucursal")
                .load();

        flyway.migrate();
    }
}



