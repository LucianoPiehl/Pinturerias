package com.pinturerias.compartidos.servicio;

import com.pinturerias.configuracion.TenantContext;
import com.pinturerias.configuracion.TenantExecutor;
import com.pinturerias.excepciones.ExcepcionApi;
import com.pinturerias.general.repositorio.SucursalRepository;
import com.pinturerias.general.repositorio.EtiquetaGeneralRepository;
import com.pinturerias.sucursal.repositorio.EtiquetaSucursalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidadorDuplicidadEtiquetaService {

    private final EtiquetaGeneralRepository etiquetaGeneralRepository;
    private final EtiquetaSucursalRepository etiquetaSucursalRepository;
    private final TenantExecutor tenantExecutor;

    public void validarClaveLibreGeneral(String claveNormalizada) {

        boolean existeGeneral = tenantExecutor.ejecutarEnTenant(
                null, () -> etiquetaGeneralRepository.existsByClaveNormalizada(claveNormalizada)
        );

        if (existeGeneral) {
            throw new ExcepcionApi(400,
                    "Ya existe una etiqueta GLOBAL");
        }
    }

    public void validarClaveLibreSucursal(String claveNormalizada) {

        // 1. Validar contra GENERAL
        boolean existeGeneral = tenantExecutor.ejecutarEnTenant(
                null,
                () -> etiquetaGeneralRepository.existsByClaveNormalizada(claveNormalizada)
        );

        if (existeGeneral) {
            throw new ExcepcionApi(400,
                    "Ya existe una etiqueta GLOBAL, debe reutilizarla");
        }

        // 2. Validar en SUCURSAL actual
        boolean existeSucursal = tenantExecutor.ejecutarEnTenant(
                TenantContext.getTenantId(),
                () -> etiquetaSucursalRepository.existsByClaveNormalizada(claveNormalizada)
        );

        if (existeSucursal) {
            throw new ExcepcionApi(400,
                    "Ya existe una etiqueta en esta sucursal");
        }
    }
}
