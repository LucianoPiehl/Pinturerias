package com.pinturerias.com.pinturerias.general.repository;

import com.pinturerias.com.pinturerias.general.entity.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SucursalRepository extends JpaRepository<Sucursal, Long> {
    Optional<Sucursal> findByCodigo(String codigo);
}