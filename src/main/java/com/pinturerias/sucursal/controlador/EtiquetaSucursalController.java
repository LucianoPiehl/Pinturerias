package com.pinturerias.sucursal.controlador;

import com.pinturerias.compartidos.dto.etiqueta.EtiquetaCreateDTO;
import com.pinturerias.compartidos.dto.etiqueta.EtiquetaDTO;
import com.pinturerias.compartidos.servicio.EtiquetaOrquestadorService;
import com.pinturerias.configuracion.tenant.TenantContext;
import com.pinturerias.sucursal.servicio.EtiquetaSucursalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursal/etiquetas")
public class EtiquetaSucursalController {

    private final EtiquetaSucursalService service;
    private final EtiquetaOrquestadorService etiquetaOrquestadorService;

    public EtiquetaSucursalController(EtiquetaSucursalService service,
                                      EtiquetaOrquestadorService etiquetaOrquestadorService) {
        this.service = service;
        this.etiquetaOrquestadorService = etiquetaOrquestadorService;
    }

    @GetMapping
    public List<EtiquetaDTO> listarLocales() {
        return service.listarLocales();
    }

    @GetMapping("/disponibles")
    public List<EtiquetaDTO> listarDisponibles() {
        return etiquetaOrquestadorService.listarEtiquetasDisponibles(TenantContext.getTenantId());
    }


    @PostMapping
    public EtiquetaDTO crear(@RequestBody EtiquetaCreateDTO dto) {

        return service.crear(dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
