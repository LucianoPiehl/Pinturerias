package com.pinturerias.sucursal.controlador;

import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.sucursal.entidad.ProductoPrecioStock;
import com.pinturerias.sucursal.servicio.ProductoPrecioStockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursal/productos-general")
public class ProductoPrecioStockController {

    private final ProductoPrecioStockService servicio;

    public ProductoPrecioStockController(ProductoPrecioStockService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<ProductoPrecioStock> getAll(@PathVariable Long sucursalId) {
        return servicio.getAll();
    }

    @GetMapping("/{productoGeneralId}")
    public ProductoPrecioStock getProductoGeneralId(
            @PathVariable Long productoGeneralId,
            @RequestParam Tipo tipoProducto) {

        return servicio.getProductoGeneralId(productoGeneralId, tipoProducto);
    }

    @PostMapping("/{productoGeneralId}")
    public ProductoPrecioStock crearOActualizar(
            @PathVariable Long productoGeneralId,
            @RequestParam Tipo tipoProducto,
            @RequestParam(required = false) Double porcentajeAjuste,
            @RequestParam(name = "precio", required = false) Double precioFinalSucursal,
            @RequestParam Integer stock,
            @RequestParam Boolean habilitado) {

        if (porcentajeAjuste != null) {
            return servicio.save(productoGeneralId, tipoProducto, porcentajeAjuste, stock, habilitado);
        }

        if (precioFinalSucursal != null) {
            return servicio.saveDesdePrecioFinal(productoGeneralId, tipoProducto, precioFinalSucursal, stock, habilitado);
        }

        return servicio.save(productoGeneralId, tipoProducto, 0D, stock, habilitado);
    }

    @DeleteMapping("/{productoGeneralId}")
    public void delete(
            @PathVariable Long productoGeneralId,
            @RequestParam Tipo tipoProducto) {

        servicio.delete(productoGeneralId, tipoProducto);
    }


}




