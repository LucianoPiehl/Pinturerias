package com.pinturerias.com.pinturerias.general.controller;

import com.pinturerias.com.pinturerias.general.entity.Sucursal;
import com.pinturerias.com.pinturerias.general.service.SucursalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/general/sucursales")
public class AdminSucursalController {

    private final SucursalService service;

    public AdminSucursalController(SucursalService service) {
        this.service = service;
    }

    @GetMapping
    public List<Sucursal> listar() {
        return service.listar();
    }

    @PostMapping
    public Sucursal crear(@RequestBody Sucursal s) {
        return service.registrar(s);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

}