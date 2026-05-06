package com.pinturerias.sucursal.repositorio;

import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.sucursal.entidad.ProductoEtiquetaSucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProductoEtiquetaSucursalRepository extends JpaRepository<ProductoEtiquetaSucursal, Long> {

    List<ProductoEtiquetaSucursal> findByProductoIdAndTipo(Long productoId,Tipo tipo);

    boolean existsByProductoIdAndEtiquetaIdAndContexto(
            Long productoId,
            Long etiquetaId,
            Contexto contexto
    );

    ProductoEtiquetaSucursal findByProductoIdAndEtiquetaId(Long id, Long etiquetaId);

    @Modifying
    @Query("""
    DELETE FROM ProductoEtiquetaSucursal p 
    WHERE p.etiquetaId = :id 
    AND p.contexto = :contexto
""")
    void deleteByEtiquetaIdAndContexto(
            @Param("id") Long id,
            @Param("contexto") Contexto contexto
    );
}
