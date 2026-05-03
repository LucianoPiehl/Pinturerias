package com.pinturerias.sucursal.repositorio;

import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.sucursal.entidad.ProductoEtiquetaSucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ProductoEtiquetaSucursalRepository extends JpaRepository<ProductoEtiquetaSucursal, Long> {

    List<ProductoEtiquetaSucursal> findByProductoIdAndTipo(Long productoId,Tipo tipo);

    boolean existsByProductoIdAndEtiquetaIdAndContexto(
            Long productoId,
            Long etiquetaId,
            Contexto contexto
    );

    ProductoEtiquetaSucursal findByProductoIdAndEtiquetaId(Long id, Long etiquetaId);
}
