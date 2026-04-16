package com.pinturerias.general.repositorio;

import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.general.entidad.ProductoStockGeneral;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductoStockGeneralRepository extends JpaRepository<ProductoStockGeneral, Long> {
    Optional<ProductoStockGeneral> findByProductoIdAndTipoProducto(Long productoId, Tipo tipoProducto);
}




