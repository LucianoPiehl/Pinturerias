package com.pinturerias.com.pinturerias.sucursal.controller;

import com.pinturerias.com.pinturerias.compartidos.dto.ProductoDTO;
import com.pinturerias.com.pinturerias.compartidos.dto.ProductoPinturaDTO;
import com.pinturerias.com.pinturerias.compartidos.entity.Producto;
import com.pinturerias.com.pinturerias.compartidos.entity.sucursal.ProductoOtroSucursal;
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


    // GET
    @GetMapping("/otro")
    public List<ProductoOtroSucursal> getAllProductoOtro() {
        return service.listarProductosOtro();
    }

    @GetMapping("/pintura")
    public List<ProductoPinturaDTO> getAllProductoPintura() {
        String tenantId = TenantContext.getTenantId(); //obtenemos el id del contexto (id de sucursal)
        return catalogoPinturaService.listarProductosPintura(tenantId); // pedimos al service todas las pinturas las globales y las propias de sucursal
    }

    // POST
    @PostMapping("/otro")
    public Producto CreateProductoOtro(
            @PathVariable Long sucursalId,
            @RequestBody ProductoDTO dto
    ) {
        dto.setContexto(Contexto.SUCURSAL);
        return service.crearOtro(dto);
    }

    // DELETE
    @DeleteMapping("/otro/{id}")
    public void deleteProductoOtro(@PathVariable Long id) {
        service.eliminarProductoOtro(id);
    }

    public ProductoSucursalController(ProductoSucursalService service, CatalogoPinturaService catalogoPinturaService) {
        this.service = service;
        this.catalogoPinturaService = catalogoPinturaService;
    }

}

