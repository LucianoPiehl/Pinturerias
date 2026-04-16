package com.pinturerias.sucursal.controlador;

import com.pinturerias.compartidos.dto.ProductoDTO;
import com.pinturerias.compartidos.dto.ProductoOtroDTO;
import com.pinturerias.compartidos.dto.ProductoPinturaDTO;
import com.pinturerias.compartidos.entidad.Producto;
import com.pinturerias.compartidos.entidad.sucursal.ProductoOtroSucursal;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
//import com.pinturerias.sucursal.dto.ProductoSucursalViewDTO;
import com.pinturerias.compartidos.servicio.CatalogoPinturaService;
import com.pinturerias.configuracion.TenantContext;
import com.pinturerias.sucursal.servicio.ProductoSucursalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/sucursal/{sucursalId}/productos")
public class ProductoSucursalController {

    private final ProductoSucursalService servicio;
    private final CatalogoPinturaService catalogoPinturaService; //Servicio utilizado como punto en comun de ambos contexto (general y sucursal) para poder obtener las listas pertenecienteas a estos


    // GET
    @GetMapping("/otro")
    public List<ProductoOtroSucursal> getAllProductoOtro() {
        return servicio.listarProductosOtro();
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
            @RequestBody ProductoOtroDTO dto
    ) {
        dto.setContexto(Contexto.SUCURSAL);
        dto.setTipo(Tipo.OTRO);
        return servicio.crearOtro(dto);
    }

    // DELETE
    @DeleteMapping("/otro/{id}")
    public void deleteProductoOtro(@PathVariable Long id) {
        servicio.eliminarProductoOtro(id);
    }

    public ProductoSucursalController(ProductoSucursalService servicio, CatalogoPinturaService catalogoPinturaService) {
        this.servicio = servicio;
        this.catalogoPinturaService = catalogoPinturaService;
    }

}




