package com.pinturerias.sucursal.controlador;

import com.pinturerias.compartidos.dto.ProductoOtroDTO;
import com.pinturerias.compartidos.dto.ProductoPinturaDTO;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.compartidos.servicio.CatalogoPinturaService;
import com.pinturerias.configuracion.TenantContext;
import com.pinturerias.sucursal.servicio.ProductoSucursalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursal/productos")
public class ProductoSucursalController {

    private final ProductoSucursalService servicio;
    private final CatalogoPinturaService catalogoPinturaService;

    public ProductoSucursalController(ProductoSucursalService servicio,
                                      CatalogoPinturaService catalogoPinturaService) {
        this.servicio = servicio;
        this.catalogoPinturaService = catalogoPinturaService;
    }

    @GetMapping("/otro")
    public List<ProductoOtroDTO> getAllProductoOtro() {
        return servicio.listarProductosOtro();
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
}