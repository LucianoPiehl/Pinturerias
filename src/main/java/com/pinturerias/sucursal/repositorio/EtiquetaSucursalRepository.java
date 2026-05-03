package com.pinturerias.sucursal.repositorio;

import com.pinturerias.compartidos.entidad.shared.Etiqueta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EtiquetaSucursalRepository extends JpaRepository<Etiqueta, Long> {

    Optional<Etiqueta> findByClaveNormalizada(String claveNormalizada);
    boolean existsByClaveNormalizada(String claveNormalizada);
}
