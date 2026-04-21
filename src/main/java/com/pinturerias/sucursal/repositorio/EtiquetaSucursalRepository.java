package com.pinturerias.sucursal.repositorio;

import com.pinturerias.sucursal.entidad.EtiquetaSucursal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EtiquetaSucursalRepository extends JpaRepository<EtiquetaSucursal, Long> {
    Optional<EtiquetaSucursal> findByValorIgnoreCase(String valor);
    boolean existsByValorIgnoreCase(String valor);
}