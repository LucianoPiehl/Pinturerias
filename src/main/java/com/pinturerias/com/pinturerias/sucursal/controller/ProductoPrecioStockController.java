package com.pinturerias.com.pinturerias.sucursal.controller;

import com.pinturerias.com.pinturerias.sucursal.entity.ProductoPrecioStock;
import com.pinturerias.com.pinturerias.sucursal.service.ProductoPrecioStockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursal/{sucursalId}/productos-general")
public class ProductoPrecioStockController {

        private final ProductoPrecioStockService service;

        public ProductoPrecioStockController(ProductoPrecioStockService service) {
            this.service = service;
        }

        @PostMapping("/{productoGeneralId}")
        public ProductoPrecioStock crearOActualizar(
                @PathVariable Long sucursalId,
                @PathVariable Long productoGeneralId,
                @RequestParam Double precio,
                @RequestParam Integer stock) {

            return service.guardar(productoGeneralId, precio, stock);
        }

        @GetMapping("/{productoGeneralId}")
        public ProductoPrecioStock obtener(
                @PathVariable Long sucursalId,
                @PathVariable Long productoGeneralId) {

            return service.obtener(productoGeneralId);
        }

        @GetMapping
        public List<ProductoPrecioStock> listar(@PathVariable Long sucursalId) {
            return service.listar();
        }

        @DeleteMapping("/{productoGeneralId}")
        public void eliminar(
                @PathVariable Long sucursalId,
                @PathVariable Long productoGeneralId) {

            service.eliminar(productoGeneralId);}
}

