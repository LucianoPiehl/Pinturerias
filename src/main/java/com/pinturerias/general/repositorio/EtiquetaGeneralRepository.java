package com.pinturerias.general.repositorio;

import com.pinturerias.compartidos.entidad.shared.Etiqueta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EtiquetaGeneralRepository extends JpaRepository<Etiqueta, Long> {

    Optional<Etiqueta> findByClaveNormalizada(String claveNormalizada);
    boolean existsByClaveNormalizada(String claveNormalizada);
}
