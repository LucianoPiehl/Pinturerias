package com.pinturerias.sucursal.repositorio;

import com.pinturerias.compartidos.entidad.shared.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioSucursalRepository extends JpaRepository<Usuario, Long> {

}
