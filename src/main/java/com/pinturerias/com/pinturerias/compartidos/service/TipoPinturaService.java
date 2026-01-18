package com.pinturerias.com.pinturerias.compartidos.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class TipoPinturaService<T> {
    protected final JpaRepository<T, Long> repository;

    protected TipoPinturaService(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public T findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public T save(T entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}

