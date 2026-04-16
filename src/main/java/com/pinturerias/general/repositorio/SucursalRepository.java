package com.pinturerias.general.repositorio;

import com.pinturerias.general.entidad.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SucursalRepository extends JpaRepository<Sucursal, Long> {
    Optional<Sucursal> findByCodigo(String codigo);
}



