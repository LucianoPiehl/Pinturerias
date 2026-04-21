package com.pinturerias.general.repositorio;

import com.pinturerias.general.entidad.EtiquetaGeneral;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EtiquetaGeneralRepository extends JpaRepository<EtiquetaGeneral, Long> {
    Optional<EtiquetaGeneral> findByValorIgnoreCase(String valor);
    boolean existsByValorIgnoreCase(String valor);
    Optional<EtiquetaGeneral> findByClaveNormalizada(String claveNormalizada);
    boolean existsByClaveNormalizada(String claveNormalizada);
}
