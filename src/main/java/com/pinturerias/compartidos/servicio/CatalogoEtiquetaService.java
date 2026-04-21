package com.pinturerias.compartidos.servicio;

import com.pinturerias.compartidos.dto.EtiquetaDTO;
import com.pinturerias.configuracion.TenantExecutor;
import com.pinturerias.general.servicio.EtiquetaGeneralService;
import com.pinturerias.sucursal.servicio.EtiquetaSucursalService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogoEtiquetaService {

    private final EtiquetaGeneralService etiquetaGeneralService;
    private final EtiquetaSucursalService etiquetaSucursalService;
    private final TenantExecutor tenantExecutor;

    public CatalogoEtiquetaService(
            EtiquetaGeneralService etiquetaGeneralService,
            EtiquetaSucursalService etiquetaSucursalService,
            TenantExecutor tenantExecutor
    ) {
        this.etiquetaGeneralService = etiquetaGeneralService;
        this.etiquetaSucursalService = etiquetaSucursalService;
        this.tenantExecutor = tenantExecutor;
    }

    public List<EtiquetaDTO> listarEtiquetasDisponibles(String tenantId) {
        List<EtiquetaDTO> resultado = new ArrayList<>();

        List<EtiquetaDTO> generales = tenantExecutor.ejecutarEnTenant(null,
                etiquetaGeneralService::listar);

        List<EtiquetaDTO> locales = tenantExecutor.ejecutarEnTenant(tenantId,
                etiquetaSucursalService::listarLocales);

        resultado.addAll(generales);
        resultado.addAll(locales);

        return resultado;
    }
}