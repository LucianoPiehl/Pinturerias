package com.pinturerias.sucursal.controlador;

import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.sucursal.entidad.ProductoPrecioStock;
import com.pinturerias.sucursal.servicio.ProductoPrecioStockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursal/{sucursalId}/productos-general")
public class ProductoPrecioStockController {

    private final ProductoPrecioStockService servicio;

    @GetMapping
    public List<ProductoPrecioStock> getAll(@PathVariable Long sucursalId) {
        return servicio.getAll();
    }

    @GetMapping("/{productoGeneralId}")
    public ProductoPrecioStock getProductoGeneralId(
            @PathVariable Long sucursalId,
            @PathVariable Long productoGeneralId,
            @RequestParam Tipo tipoProducto) {

        return servicio.getProductoGeneralId(productoGeneralId, tipoProducto);
    }

    @PostMapping("/{productoGeneralId}")
    public ProductoPrecioStock crearOActualizar(
            @PathVariable Long sucursalId,
            @PathVariable Long productoGeneralId,
            @RequestParam Tipo tipoProducto,
            @RequestParam(required = false) Double porcentajeAjuste,
            @RequestParam(name = "precio", required = false) Double precioFinalSucursal,
            @RequestParam Integer stock) {

        if (porcentajeAjuste != null) {
            return servicio.save(productoGeneralId, tipoProducto, porcentajeAjuste, stock);
        }

        if (precioFinalSucursal != null) {
            return servicio.saveDesdePrecioFinal(productoGeneralId, tipoProducto, precioFinalSucursal, stock);
        }

        return servicio.save(productoGeneralId, tipoProducto, 0D, stock);
    }

    @DeleteMapping("/{productoGeneralId}")
    public void delete(
            @PathVariable Long sucursalId,
            @PathVariable Long productoGeneralId,
            @RequestParam Tipo tipoProducto) {

        servicio.delete(productoGeneralId, tipoProducto);
    }

    public ProductoPrecioStockController(ProductoPrecioStockService servicio) {
        this.servicio = servicio;
    }
}




