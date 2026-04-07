package com.pinturerias.com.pinturerias.general.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import com.pinturerias.com.pinturerias.general.dto.TipoPinturaDTO;
import com.pinturerias.com.pinturerias.general.service.TipoPinturaService;

@RestController
@RequestMapping("/api/general/tipo-pintura")
@RequiredArgsConstructor
public class TipoPinturaController {

    private final TipoPinturaService service;

    // LISTAR
    @GetMapping
    public List<TipoPinturaDTO> getAll() {
        return service.findAll();
    }

    // OBTENER POR ID
    @GetMapping("/{id}")
    public TipoPinturaDTO getById(@PathVariable Long id) {
        return service.findById(id);
    }

    // CREAR
    @PostMapping
    public TipoPinturaDTO create(@RequestBody TipoPinturaDTO dto) {
        return service.save(dto);
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public TipoPinturaDTO update(@PathVariable Long id,
                                 @RequestBody TipoPinturaDTO dto) {
        return service.update(id, dto);
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}