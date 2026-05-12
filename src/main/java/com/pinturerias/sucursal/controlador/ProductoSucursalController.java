package com.pinturerias.sucursal.controlador;

import com.pinturerias.compartidos.dto.etiqueta.EtiquetaDTO;
import com.pinturerias.compartidos.dto.producto.ProductoOtroDTO;
import com.pinturerias.compartidos.dto.producto.ProductoPinturaDTO;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.compartidos.servicio.OtroOrquestadorService;
import com.pinturerias.compartidos.servicio.PinturaOrquestadorService;
import com.pinturerias.compartidos.servicio.ProductoEtiquetaOrquestadorService;
import com.pinturerias.configuracion.tenant.TenantContext;
import com.pinturerias.sucursal.servicio.ProductoSucursalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursal/productos")
public class ProductoSucursalController {

    private final ProductoSucursalService servicio;
    private final PinturaOrquestadorService pinturaOrquestadorService;
    private final OtroOrquestadorService otroOrquestadorService;
    private final ProductoEtiquetaOrquestadorService productoEtiquetaOrquestadorService;

    public ProductoSucursalController(ProductoSucursalService servicio,
                                      PinturaOrquestadorService pinturaOrquestadorService, OtroOrquestadorService otroOrquestadorService, ProductoEtiquetaOrquestadorService productoEtiquetaOrquestadorService) {
        this.servicio = servicio;
        this.pinturaOrquestadorService = pinturaOrquestadorService;
        this.otroOrquestadorService = otroOrquestadorService;
        this.productoEtiquetaOrquestadorService = productoEtiquetaOrquestadorService;
    }

    @GetMapping("/otro")
    public List<ProductoOtroDTO> getAllProductoOtro() {
        String tenantId = TenantContext.getTenantId();
        return otroOrquestadorService.listarProductosOtro(tenantId);

    }

    @GetMapping("/pintura")
    public List<ProductoPinturaDTO> getAllProductoPintura() {
        String tenantId = TenantContext.getTenantId();
        return pinturaOrquestadorService.listarProductosPintura(tenantId);
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
        return productoEtiquetaOrquestadorService.obtenerEtiquetasSucursal(id, Tipo.OTRO);
    }

    @GetMapping("/pintura/{id}/etiquetas")
    public List<EtiquetaDTO> obtenerEtiquetasProductoPintura(@PathVariable Long id){
        return productoEtiquetaOrquestadorService.obtenerEtiquetasSucursal(id, Tipo.PINTURA);
    }
}