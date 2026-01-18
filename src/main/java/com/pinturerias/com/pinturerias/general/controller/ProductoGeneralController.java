package com.pinturerias.com.pinturerias.general.controller;

import com.pinturerias.com.pinturerias.compartidos.dto.ProductoDTO;
import com.pinturerias.com.pinturerias.compartidos.entity.Producto;
import com.pinturerias.com.pinturerias.compartidos.enumerate.Contexto;
import com.pinturerias.com.pinturerias.general.service.ProductoGeneralService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.pinturerias.com.pinturerias.compartidos.entity.general.ProductoOtroGeneral;
import com.pinturerias.com.pinturerias.compartidos.entity.general.ProductoPinturaGeneral;


@RestController
@RequestMapping("/api/general/productos")
public class ProductoGeneralController {

    private final ProductoGeneralService service;

    public ProductoGeneralController(ProductoGeneralService service) {
        this.service = service;
    }

    // LISTAR
    @GetMapping("/otro")
    public List<ProductoOtroGeneral> listarProductosOtro() {
        return service.listarProductosOtro();
    }

    @GetMapping("/pintura")
    public List<ProductoPinturaGeneral> listarProductosPintura() {
        return service.listarProductosPintura();
    }

    // CREAR
    @PostMapping("/otro")
    public Producto crearOtro(@RequestBody ProductoDTO dto) {
        dto.setContexto(Contexto.GENERAL);
        return service.crearOtro(dto);
    }

    @PostMapping("/pintura")
    public Producto crearPintura(@RequestBody ProductoDTO dto) {
        dto.setContexto(Contexto.GENERAL);
        return service.crearPintura(dto);
    }

    // ELIMINAR
    @DeleteMapping("/otro/{id}")
    public void eliminarOtro(@PathVariable Long id) {
        service.eliminarOtro(id);
    }

    @DeleteMapping("/pintura/{id}")
    public void eliminarPintura(@PathVariable Long id) {
        service.eliminarPintura(id);
    }
}
