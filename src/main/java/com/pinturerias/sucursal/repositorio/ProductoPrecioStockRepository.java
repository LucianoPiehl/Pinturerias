package com.pinturerias.sucursal.repositorio;

import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.sucursal.entidad.ProductoPrecioStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoPrecioStockRepository extends JpaRepository<ProductoPrecioStock, Long> {
    Optional<ProductoPrecioStock> findByProductoIdAndTipoProducto(Long productoGeneralId, Tipo tipoProducto);
    List<ProductoPrecioStock> findAllByTipoProducto(Tipo tipoProducto);
}




