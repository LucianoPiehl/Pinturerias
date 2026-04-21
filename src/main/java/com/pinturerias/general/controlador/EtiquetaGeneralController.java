package com.pinturerias.general.controlador;

import com.pinturerias.compartidos.dto.EtiquetaCreateDTO;
import com.pinturerias.compartidos.dto.EtiquetaDTO;
import com.pinturerias.general.servicio.EtiquetaGeneralService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/general/etiquetas")
public class EtiquetaGeneralController {

    private final EtiquetaGeneralService service;

    public EtiquetaGeneralController(EtiquetaGeneralService service) {
        this.service = service;
    }

    @GetMapping
    public List<EtiquetaDTO> listar() {
        return service.listar();
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