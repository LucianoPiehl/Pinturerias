package com.pinturerias.general.repositorio;

import com.pinturerias.compartidos.entidad.shared.Etiqueta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EtiquetaGeneralRepository extends JpaRepository<Etiqueta, Long> {

    List<Etiqueta> findByHabilitadoTrue();
    Optional<Etiqueta> findByClaveNormalizada(String claveNormalizada);
    boolean existsByClaveNormalizada(String claveNormalizada);

}
