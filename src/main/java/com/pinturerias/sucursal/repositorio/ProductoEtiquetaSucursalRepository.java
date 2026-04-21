package com.pinturerias.sucursal.repositorio;

import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.sucursal.entidad.ProductoEtiquetaSucursal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductoEtiquetaSucursalRepository extends JpaRepository<ProductoEtiquetaSucursal, Long> {
    Optional<ProductoEtiquetaSucursal> findByProductoIdAndContextoProductoAndTipoProducto(
            Long productoId,
            Contexto contextoProducto,
            Tipo tipoProducto
    );
}
