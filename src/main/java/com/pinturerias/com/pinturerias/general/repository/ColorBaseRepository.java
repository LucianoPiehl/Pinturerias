package com.pinturerias.com.pinturerias.general.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pinturerias.com.pinturerias.general.entity.ColorBase;

public interface ColorBaseRepository extends JpaRepository<ColorBase, Long> {
}