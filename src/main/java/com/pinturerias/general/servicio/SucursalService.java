package com.pinturerias.general.servicio;

import com.pinturerias.configuracion.TenantRegistry;
import com.pinturerias.general.entidad.Sucursal;
import com.pinturerias.general.repositorio.SucursalRepository;
import com.pinturerias.excepciones.RecursoNoEncontradoException;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SucursalService {

    private final SucursalRepository repoSucursal;
    private final TenantRegistry tenantRegistry;

    public SucursalService(SucursalRepository repo, TenantRegistry tenantRegistry) {
        this.repoSucursal = repo;
        this.tenantRegistry = tenantRegistry;
    }

    public List<Sucursal> listar() {
        return repoSucursal.findAll();
    }

    @Transactional
    public Sucursal registrar(Sucursal s) {
        if (repoSucursal.findByCodigo(s.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("Codigo de sucursal ya existente");
        }

        String tenantDbName = s.getCodigo();
        String jdbcUrl = "jdbc:mysql://localhost:3306/"
                + tenantDbName
                + "?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC";

        s.setJdbcUrl(jdbcUrl);

        Sucursal guardada = repoSucursal.save(s);

        inicializarBaseSucursal(jdbcUrl, s.getUsername(), s.getPassword());

        tenantRegistry.addTenant(
                s.getCodigo(),
                jdbcUrl,
                s.getUsername(),
                s.getPassword()
        );

        return guardada;
    }

    public void eliminar(Long id) {
        Sucursal s = repoSucursal.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sucursal no encontrada"));
        repoSucursal.delete(s);
        tenantRegistry.removeTenant(s.getCodigo());
    }

    private void inicializarBaseSucursal(String jdbcUrl, String user, String pass) {
        Flyway flyway = Flyway.configure()
                .dataSource(jdbcUrl, user, pass)
                .locations("classpath:db/migration/sucursal")
                .load();

        flyway.migrate();
    }

    public Sucursal actualizarSucursal(Long id, Sucursal s) {
        Sucursal sucursalVieja = repoSucursal.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Sucursal no encontrada"));
        sucursalVieja.setCodigo(s.getCodigo());
        sucursalVieja.setNombre(s.getNombre());
        sucursalVieja.setJdbcUrl(s.getJdbcUrl());
        sucursalVieja.setUsername(s.getUsername());
        sucursalVieja.setPassword(s.getPassword());
        sucursalVieja.setHabilitada(s.isHabilitada());

        return repoSucursal.save(sucursalVieja);
    }
}




