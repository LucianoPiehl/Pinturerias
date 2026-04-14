package com.pinturerias.com.pinturerias.sucursal.service;

import com.pinturerias.com.pinturerias.sucursal.entity.ProductoPrecioStock;
import com.pinturerias.com.pinturerias.sucursal.repository.ProductoPrecioStockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoPrecioStockService {
    private final ProductoPrecioStockRepository repo;

    public ProductoPrecioStockService(ProductoPrecioStockRepository repo) {
        this.repo = repo;
    }

    public ProductoPrecioStock save(Long productoGeneralId, Double precio, Integer stock) {
        ProductoPrecioStock pps = repo.findByProductoId(productoGeneralId)
                .orElse(new ProductoPrecioStock(productoGeneralId, precio, stock));

        // actualizar valores
        pps.setPrecio(precio);
        pps.setStock(stock);

        return repo.save(pps);
    }

    public ProductoPrecioStock getProductogeneralId (Long productoGeneralId) {
        return repo.findByProductoId(productoGeneralId)
                .orElseThrow(() -> new RuntimeException("No hay control local para el producto general ID: " + productoGeneralId));
    }

    public List<ProductoPrecioStock> getAll() {
        return repo.findAll();
    }

    public void delete(Long productoGeneralId) {
        ProductoPrecioStock pps = getProductogeneralId(productoGeneralId);
        repo.delete(pps);
    }
}