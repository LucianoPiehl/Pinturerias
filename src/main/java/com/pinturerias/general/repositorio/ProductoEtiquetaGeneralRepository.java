package com.pinturerias.general.repositorio;

import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.general.entidad.ProductoEtiquetaGeneral;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoEtiquetaGeneralRepository extends JpaRepository<ProductoEtiquetaGeneral, Long> {

    boolean existsByProductoIdAndEtiquetaIdAndContexto(Long productoId, Long id, Contexto contexto);

    List<ProductoEtiquetaGeneral> findByProductoIdAndTipo(Long productoId, Tipo tipo);
}
