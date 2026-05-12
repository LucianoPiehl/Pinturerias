package com.pinturerias.general.repositorio;

import com.pinturerias.compartidos.entidad.shared.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioGeneralRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findById(Long id);
}
