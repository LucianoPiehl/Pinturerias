package com.pinturerias.general.repositorio;

import com.pinturerias.general.entidad.UsuarioIndice;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioIndiceRepository extends JpaRepository<UsuarioIndice, Long> {
    boolean existsByUsername(String username);
}
