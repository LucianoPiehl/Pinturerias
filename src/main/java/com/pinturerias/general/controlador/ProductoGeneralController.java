package com.pinturerias.general.controlador;

import com.pinturerias.compartidos.dto.EtiquetaDTO;
import com.pinturerias.compartidos.dto.ProductoOtroDTO;
import com.pinturerias.compartidos.dto.ProductoPinturaDTO;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.compartidos.servicio.ProductoEtiquetaService;
import com.pinturerias.general.servicio.ProductoGeneralService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/general/productos")
public class ProductoGeneralController {

    private final ProductoGeneralService servicio;
    private final ProductoEtiquetaService productoEtiquetaService;

    public ProductoGeneralController(ProductoGeneralService servicio, ProductoEtiquetaService productoEtiquetaService) {
        this.servicio = servicio;
        this.productoEtiquetaService = productoEtiquetaService;
    }

    @GetMapping("/otro")
    public List<ProductoOtroDTO> getAllProductosOtro() {
        return servicio.getAllProductosOtro();
    }

    @GetMapping("/pintura")
    public List<ProductoPinturaDTO> getAllProductosPintura() {
        return servicio.getAllProductosPintura();
    }

    @PostMapping("/otro")
    public ProductoOtroDTO createProductoOtro(@RequestBody ProductoOtroDTO dto) {
        dto.setContexto(Contexto.GENERAL);
        dto.setTipo(Tipo.OTRO);
        dto.setEtiquetasSucursalIds(List.of());
        return servicio.createProductoOtro(dto);
    }

    @PostMapping("/pintura")
    public ProductoPinturaDTO createProductoPintura(@RequestBody ProductoPinturaDTO dto) {
        dto.setContexto(Contexto.GENERAL);
        dto.setTipo(Tipo.PINTURA);
        dto.setEtiquetasSucursalIds(List.of());
        return servicio.createProductoPintura(dto);
    }

    @PutMapping("/pintura/{id}")
    public ProductoPinturaDTO updateProductoPintura(
            @PathVariable Long id,
            @RequestBody ProductoPinturaDTO dto
    ) {
        dto.setContexto(Contexto.GENERAL);
        dto.setTipo(Tipo.PINTURA);
        dto.setEtiquetasSucursalIds(List.of());
        return servicio.updateProductoPintura(id, dto);
    }

    @PutMapping("/otro/{id}")
    public ProductoOtroDTO updateProductoOtro(
            @PathVariable Long id,
            @RequestBody ProductoOtroDTO dto
    ) {
        dto.setContexto(Contexto.GENERAL);
        dto.setTipo(Tipo.OTRO);
        dto.setEtiquetasSucursalIds(List.of());
        return servicio.updateProductoOtro(id, dto);
    }

    @DeleteMapping("/otro/{id}")
    public void deleteProductoOtro(@PathVariable Long id) {
        servicio.deleteProductoOtro(id);
    }

    @DeleteMapping("/pintura/{id}")
    public void deleteProductoPintura(@PathVariable Long id) {
        servicio.deleteProductoPintura(id);
    }

    @GetMapping("/otro/{id}/etiquetas")
    public List<EtiquetaDTO> obtenerEtiquetasProductoOtro(@PathVariable Long id){
        return productoEtiquetaService.obtenerEtiquetasGeneral(id, Tipo.OTRO);
    }

    @GetMapping("/pintura/{id}/etiquetas")
    public List<EtiquetaDTO> obtenerEtiquetasProductoPintura(@PathVariable Long id){
        return productoEtiquetaService.obtenerEtiquetasGeneral(id, Tipo.PINTURA);
    }
}