package com.pinturerias.com.pinturerias.general.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import com.pinturerias.com.pinturerias.general.dto.ColorBaseDTO;

import com.pinturerias.com.pinturerias.general.service.ColorBaseService;

@RestController
@RequestMapping("/api/general/color-base")
@RequiredArgsConstructor
public class ColorBaseController {

    private final ColorBaseService service;

    @GetMapping
    public List<ColorBaseDTO> getAll() {
        return service.findAll();
    }
    // OBTENER POR ID
    @GetMapping("/{id}")
    public ColorBaseDTO getById(@PathVariable Long id) {
        return service.findById(id);
    }
    @PostMapping
    public ColorBaseDTO create(@RequestBody ColorBaseDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public ColorBaseDTO update(@PathVariable Long id, @RequestBody ColorBaseDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}