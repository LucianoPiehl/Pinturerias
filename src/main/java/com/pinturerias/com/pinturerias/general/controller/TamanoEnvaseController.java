package com.pinturerias.com.pinturerias.general.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import com.pinturerias.com.pinturerias.general.dto.TamanoEnvaseDTO;
import com.pinturerias.com.pinturerias.general.service.TamanoEnvaseService;

@RestController
@RequestMapping("/api/general/tamano-envase")
@RequiredArgsConstructor
public class TamanoEnvaseController {

    private final TamanoEnvaseService service;

    // LISTAR
    @GetMapping
    public List<TamanoEnvaseDTO> getAll() {
        return service.findAll();
    }

    // OBTENER POR ID
    @GetMapping("/{id}")
    public TamanoEnvaseDTO getById(@PathVariable Long id) {
        return service.findById(id);
    }

    // CREAR
    @PostMapping
    public TamanoEnvaseDTO create(@RequestBody TamanoEnvaseDTO dto) {
        return service.save(dto);
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public TamanoEnvaseDTO update(@PathVariable Long id,
                                 @RequestBody TamanoEnvaseDTO dto) {
        return service.update(id, dto);
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}