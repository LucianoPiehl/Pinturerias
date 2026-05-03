package com.pinturerias.compartidos.servicio;

import com.pinturerias.compartidos.dto.EtiquetaDTO;
import com.pinturerias.configuracion.TenantExecutor;
import com.pinturerias.general.servicio.EtiquetaGeneralService;
import com.pinturerias.sucursal.servicio.EtiquetaSucursalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CatalogoEtiquetaService {

    private final EtiquetaGeneralService etiquetaGeneralService;
    private final EtiquetaSucursalService etiquetaSucursalService;
    private final TenantExecutor tenantExecutor;


    public List<EtiquetaDTO> listarEtiquetasDisponibles(String tenantId) {

        List<EtiquetaDTO> generales = tenantExecutor.ejecutarEnTenant(null,
                etiquetaGeneralService::listar);

        List<EtiquetaDTO> locales = tenantExecutor.ejecutarEnTenant(tenantId,
                etiquetaSucursalService::listarLocales);


        // Evitar NPE por si algún service devuelve null
        return Stream.concat(
                generales == null ? Stream.empty() : generales.stream(),
                locales == null ? Stream.empty() : locales.stream()
        ).toList();
    }
}