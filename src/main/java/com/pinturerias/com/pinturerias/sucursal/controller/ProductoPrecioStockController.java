package com.pinturerias.com.pinturerias.sucursal.controller;

import com.pinturerias.com.pinturerias.sucursal.entity.ProductoPrecioStock;
import com.pinturerias.com.pinturerias.sucursal.service.ProductoPrecioStockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursal/{sucursalId}/productos-general")
public class ProductoPrecioStockController {

    private final ProductoPrecioStockService service;

    @GetMapping
    public List<ProductoPrecioStock> getAll(@PathVariable Long sucursalId) {
        return service.getAll();
    }

    @GetMapping("/{productoGeneralId}")
    public ProductoPrecioStock getProductoGeneralId(
            @PathVariable Long sucursalId,
            @PathVariable Long productoGeneralId) {

        return service.getProductogeneralId(productoGeneralId);
    }

    @PostMapping("/{productoGeneralId}")
    public ProductoPrecioStock crearOActualizar(
            @PathVariable Long sucursalId,
            @PathVariable Long productoGeneralId,
            @RequestParam Double precio,
            @RequestParam Integer stock) {

        return service.save(productoGeneralId, precio, stock);
    }

    @DeleteMapping("/{productoGeneralId}")
    public void delete(
            @PathVariable Long sucursalId,
            @PathVariable Long productoGeneralId) {

        service.delete(productoGeneralId);}

    public ProductoPrecioStockController(ProductoPrecioStockService service) {
        this.service = service;
    }
}

