package com.pinturerias.com.pinturerias.compartidos.controller;

import com.pinturerias.com.pinturerias.compartidos.service.TipoPinturaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class TipoPinturaController<T> {
    protected final TipoPinturaService<T> service;

    protected TipoPinturaController(TipoPinturaService<T> service) {
        this.service = service;
    }

    @GetMapping
    public List<T> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public T getOne(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public T create(@RequestBody T entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public T update(
            @PathVariable Long id,
            @RequestBody T entity
    ) {
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
