package com.pinturerias.com.pinturerias.sucursal.repository;

import com.pinturerias.com.pinturerias.sucursal.entity.ProductoPrecioStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductoPrecioStockRepository extends JpaRepository<ProductoPrecioStock, Long> {
    Optional<ProductoPrecioStock> findByProductoId(Long productoGeneralId);
}
