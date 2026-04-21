package com.pinturerias.sucursal.controlador;

import com.pinturerias.compartidos.dto.EtiquetaCreateDTO;
import com.pinturerias.compartidos.dto.EtiquetaDTO;
import com.pinturerias.compartidos.servicio.CatalogoEtiquetaService;
import com.pinturerias.sucursal.servicio.EtiquetaSucursalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursal/{sucursalId}/etiquetas")
public class EtiquetaSucursalController {

    private final EtiquetaSucursalService service;
    private final CatalogoEtiquetaService catalogoEtiquetaService;

    public EtiquetaSucursalController(EtiquetaSucursalService service,
                                      CatalogoEtiquetaService catalogoEtiquetaService) {
        this.service = service;
        this.catalogoEtiquetaService = catalogoEtiquetaService;
    }

    @GetMapping
    public List<EtiquetaDTO> listarLocales() {
        return service.listarLocales();
    }

    @GetMapping("/disponibles")
    public List<EtiquetaDTO> listarDisponibles(@PathVariable String sucursalId) {
        return catalogoEtiquetaService.listarEtiquetasDisponibles(sucursalId);
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