package com.pinturerias.sucursal.controlador;

import com.pinturerias.compartidos.dto.EtiquetaDTO;
import com.pinturerias.compartidos.dto.ProductoOtroDTO;
import com.pinturerias.compartidos.dto.ProductoPinturaDTO;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.compartidos.servicio.CatalogoOtroService;
import com.pinturerias.compartidos.servicio.CatalogoPinturaService;
import com.pinturerias.compartidos.servicio.ProductoEtiquetaService;
import com.pinturerias.configuracion.TenantContext;
import com.pinturerias.sucursal.servicio.ProductoSucursalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursal/productos")
public class ProductoSucursalController {

    private final ProductoSucursalService servicio;
    private final CatalogoPinturaService catalogoPinturaService;
    private final CatalogoOtroService catalogoOtroService;
    private final ProductoEtiquetaService productoEtiquetaService;

    public ProductoSucursalController(ProductoSucursalService servicio,
                                      CatalogoPinturaService catalogoPinturaService, CatalogoOtroService catalogoOtroService, ProductoEtiquetaService productoEtiquetaService) {
        this.servicio = servicio;
        this.catalogoPinturaService = catalogoPinturaService;
        this.catalogoOtroService = catalogoOtroService;
        this.productoEtiquetaService = productoEtiquetaService;
    }

    @GetMapping("/otro")
    public List<ProductoOtroDTO> getAllProductoOtro() {
        String tenantId = TenantContext.getTenantId();
        return catalogoOtroService.listarProductosOtro(tenantId);

    }

    @GetMapping("/pintura")
    public List<ProductoPinturaDTO> getAllProductoPintura() {
        String tenantId = TenantContext.getTenantId();
        return catalogoPinturaService.listarProductosPintura(tenantId);
    }

    @PostMapping("/otro")
    public ProductoOtroDTO createProductoOtro(
            @RequestBody ProductoOtroDTO dto
    ) {
        dto.setContexto(Contexto.SUCURSAL);
        dto.setTipo(Tipo.OTRO);
        return servicio.crearOtro(dto);
    }

    @PutMapping("/otro/{id}")
    public ProductoOtroDTO updateProductoOtro(
            @PathVariable Long id,
            @RequestBody ProductoOtroDTO dto
    ) {
        dto.setContexto(Contexto.SUCURSAL);
        dto.setTipo(Tipo.OTRO);
        return servicio.actualizarOtro(id, dto);
    }

    @DeleteMapping("/otro/{id}")
    public void deleteProductoOtro(@PathVariable Long id) {
        servicio.eliminarProductoOtro(id);
    }

    @GetMapping("/otro/{id}/etiquetas")
    public List<EtiquetaDTO> obtenerEtiquetasProductoOtro(@PathVariable Long id){
        return productoEtiquetaService.obtenerEtiquetasSucursal(id, Tipo.OTRO);
    }

    @GetMapping("/pintura/{id}/etiquetas")
    public List<EtiquetaDTO> obtenerEtiquetasProductoPintura(@PathVariable Long id){
        return productoEtiquetaService.obtenerEtiquetasSucursal(id, Tipo.PINTURA);
    }
}