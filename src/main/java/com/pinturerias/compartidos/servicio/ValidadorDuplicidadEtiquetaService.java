package com.pinturerias.compartidos.servicio;

import com.pinturerias.configuracion.TenantExecutor;
import com.pinturerias.excepciones.ExcepcionApi;
import com.pinturerias.general.repositorio.SucursalRepository;
import com.pinturerias.general.repositorio.EtiquetaGeneralRepository;
import com.pinturerias.sucursal.repositorio.EtiquetaSucursalRepository;
import org.springframework.stereotype.Service;

@Service
public class ValidadorDuplicidadEtiquetaService {

    private final EtiquetaGeneralRepository etiquetaGeneralRepository;
    private final EtiquetaSucursalRepository etiquetaSucursalRepository;
    private final SucursalRepository sucursalRepository;
    private final TenantExecutor tenantExecutor;

    public ValidadorDuplicidadEtiquetaService(EtiquetaGeneralRepository etiquetaGeneralRepository,
                                              EtiquetaSucursalRepository etiquetaSucursalRepository,
                                              SucursalRepository sucursalRepository,
                                              TenantExecutor tenantExecutor) {
        this.etiquetaGeneralRepository = etiquetaGeneralRepository;
        this.etiquetaSucursalRepository = etiquetaSucursalRepository;
        this.sucursalRepository = sucursalRepository;
        this.tenantExecutor = tenantExecutor;
    }

    public void validarClaveLibreEnTodoElSistema(String claveNormalizada) {
        boolean existeGeneral = etiquetaGeneralRepository.existsByClaveNormalizada(claveNormalizada);
        if (existeGeneral) {
            throw new ExcepcionApi(400, "Ya existe una etiqueta equivalente en el sistema");
        }

        boolean existeEnSucursal = sucursalRepository.findAll().stream()
                .anyMatch(sucursal -> tenantExecutor.ejecutarEnTenant(
                        sucursal.getCodigo(),
                        () -> etiquetaSucursalRepository.existsByClaveNormalizada(claveNormalizada)
                ));

        if (existeEnSucursal) {
            throw new ExcepcionApi(400, "Ya existe una etiqueta equivalente en el sistema");
        }
    }
}
