package com.pinturerias.com.pinturerias.general.service;

import com.pinturerias.com.pinturerias.config.TenantRegistry;
import com.pinturerias.com.pinturerias.general.entity.Sucursal;
import com.pinturerias.com.pinturerias.general.repository.SucursalRepository;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SucursalService {

    private final SucursalRepository repo;
    private final TenantRegistry tenantRegistry;

    public SucursalService(SucursalRepository repo, TenantRegistry tenantRegistry) {
        this.repo = repo;
        this.tenantRegistry = tenantRegistry;
    }

    public List<Sucursal> listar() {
        return repo.findAll();
    }

    @Transactional
    public Sucursal registrar(Sucursal s) {
        if (repo.findByCodigo(s.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("Código de sucursal ya existente");
        }

        String tenantDbName = s.getCodigo();             // ej: "suc-001"

        String jdbcUrl = "jdbc:mysql://localhost:3306/"
                + tenantDbName
                + "?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC";

        s.setJdbcUrl(jdbcUrl);

        // Guarda la sucursal en la BD general
        Sucursal guardada = repo.save(s);

        // 1️⃣ Ejecuta Flyway sobre la base nueva de la sucursal
        inicializarBaseSucursal(jdbcUrl, s.getUsername(), s.getPassword());

        // 2️⃣ Registra dinámicamente el tenant en memoria
        tenantRegistry.addTenant(
                s.getCodigo(),   // tenantId = suc-001
                jdbcUrl,
                s.getUsername(),
                s.getPassword()
        );

        System.out.println("✅ Sucursal registrada y migrada: " + s.getCodigo());
        return guardada;
    }

    public void eliminar(Long id) {
        Sucursal s = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sucursal no encontrada"));
        repo.delete(s);
        tenantRegistry.removeTenant(s.getCodigo());
        System.out.println("❌ Sucursal eliminada: " + s.getCodigo());
    }

    // 🔹 Ejecuta las migraciones en la base de datos de la sucursal
    private void inicializarBaseSucursal(String jdbcUrl, String user, String pass) {
        System.out.println("⚙️ Ejecutando migración Flyway en BD: " + jdbcUrl);

        Flyway flyway = Flyway.configure()
                .dataSource(jdbcUrl, user, pass)
                .locations("classpath:db/migration/tenant") // 🔹 ruta de migraciones tenant
                .baselineOnMigrate(true)
                .load();

        flyway.migrate();
    }
}
