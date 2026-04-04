package com.pinturerias.com.pinturerias.sucursal.controller;

import com.pinturerias.com.pinturerias.compartidos.dto.ProductoDTO;
import com.pinturerias.com.pinturerias.compartidos.dto.ProductoPinturaDTO;
import com.pinturerias.com.pinturerias.compartidos.entity.Producto;
import com.pinturerias.com.pinturerias.compartidos.entity.sucursal.ProductoOtroSucursal;
import com.pinturerias.com.pinturerias.compartidos.entity.sucursal.ProductoPinturaSucursal;
import com.pinturerias.com.pinturerias.compartidos.enumerate.Contexto;
//import com.pinturerias.com.pinturerias.sucursal.dto.ProductoSucursalViewDTO;
import com.pinturerias.com.pinturerias.compartidos.service.CatalogoPinturaService;
import com.pinturerias.com.pinturerias.config.TenantContext;
import com.pinturerias.com.pinturerias.sucursal.service.ProductoSucursalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/sucursal/{sucursalId}/productos")
public class ProductoSucursalController {

    private final ProductoSucursalService service;
    private final CatalogoPinturaService catalogoPinturaService; //Servicio utilizado como punto en comun de ambos contexto (general y sucursal) para poder obtener las listas pertenecienteas a estos

    public ProductoSucursalController(ProductoSucursalService service, CatalogoPinturaService catalogoPinturaService) {
        this.service = service;
        this.catalogoPinturaService = catalogoPinturaService;
    }

    // GET
    @GetMapping("/otro")
    public List<ProductoOtroSucursal> listarProductoOtro() {
        return service.listarProductosOtro();
    }

    @GetMapping("/pintura")
    public List<ProductoPinturaDTO> listarProductoPintura() {
        String tenantId = TenantContext.getTenantId(); //obtenemos el id del contexto (id de sucursal)
        return catalogoPinturaService.listarProductosPintura(tenantId); // pedimos al service todas las pinturas las globales y las propias de sucursal
    }

    // POST
    @PostMapping("/otro")
    public Producto crearOtro(
            @PathVariable Long sucursalId,
            @RequestBody ProductoDTO dto
    ) {
        dto.setContexto(Contexto.SUCURSAL);
        return service.crearOtro(dto);
    }

    @PostMapping("/pintura")
    public Producto crearPintura(
            @PathVariable Long sucursalId,
            @RequestBody ProductoDTO dto
    ) {
        dto.setContexto(Contexto.SUCURSAL);
        return service.crearPintura(dto);
    }

    // DELETE
    @DeleteMapping("/otro/{id}")
    public void eliminarOtro(@PathVariable Long id) {
        service.eliminarProductoOtro(id);
    }

    @DeleteMapping("/pintura/{id}")
    public void eliminarPintura(@PathVariable Long id) {
        service.eliminarProductoPintura(id);
    }
}


    // Para un futuro todos los endpoints estaran optimizados asi 
    // @DeleteMapping("/{id}/{tipo}")
    // public void eliminar(
    //         @PathVariable Long id,
    //         @PathVariable String tipo
    // ) {
    //     service.eliminar(id, tipo);
    // }
