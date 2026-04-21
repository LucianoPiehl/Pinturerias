package com.pinturerias.general.repositorio;

import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.general.entidad.ProductoEtiquetaGeneral;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductoEtiquetaGeneralRepository extends JpaRepository<ProductoEtiquetaGeneral, Long> {
    Optional<ProductoEtiquetaGeneral> findByProductoIdAndTipoProducto(Long productoId, Tipo tipoProducto);
}
