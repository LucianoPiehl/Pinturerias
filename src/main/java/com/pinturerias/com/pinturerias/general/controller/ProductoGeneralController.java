package com.pinturerias.com.pinturerias.general.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pinturerias.com.pinturerias.compartidos.dto.ProductoDTO;
import com.pinturerias.com.pinturerias.compartidos.dto.ProductoPinturaDTO;
import com.pinturerias.com.pinturerias.compartidos.entity.Producto;
import com.pinturerias.com.pinturerias.compartidos.entity.general.ProductoOtroGeneral;
import com.pinturerias.com.pinturerias.compartidos.entity.general.ProductoPinturaGeneral;
import com.pinturerias.com.pinturerias.compartidos.enumerate.Contexto;
import com.pinturerias.com.pinturerias.general.service.ProductoGeneralService;


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
    public Producto crearPintura(@RequestBody ProductoPinturaDTO dto) {
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

    @PutMapping("/pintura/{id}")
    public ProductoPinturaGeneral actualizarPintura(
            @PathVariable Long id,
            @RequestBody ProductoPinturaDTO dto) {

        return service.actualizarPintura(id, dto);
    }

    @PutMapping("/otro/{id}")
    public ProductoOtroGeneral actualizarOtro(
            @PathVariable Long id,
            @RequestBody ProductoDTO dto) {

        return service.actualizarOtro(id, dto);
    }
    
}
