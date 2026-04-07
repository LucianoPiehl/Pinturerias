package com.pinturerias.com.pinturerias.general.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pinturerias.com.pinturerias.general.entity.TipoPintura;

public interface TipoPinturaRepository extends JpaRepository<TipoPintura, Long> {
}