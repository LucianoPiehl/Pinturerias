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

import com.pinturerias.com.pinturerias.compartidos.dto.ProductoOtroDTO;
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

    // LISTAR
    @GetMapping("/otro")
    public List<ProductoOtroGeneral> getAllProductosOtro() {
        return service.getAllProductosOtro();
    }

    @GetMapping("/pintura")
    public List<ProductoPinturaGeneral> getAllProductosPintura() {
        return service.getAllProductosPintura();
    }

    // CREAR
    @PostMapping("/otro")
    public Producto createProductoOtro(@RequestBody ProductoOtroDTO dto) {
        dto.setContexto(Contexto.GENERAL);
        return service.createProductoOtro(dto);
    }

    @PostMapping("/pintura")
    public Producto createProductoPintura(@RequestBody ProductoPinturaDTO dto) {
        dto.setContexto(Contexto.GENERAL);
        return service.createProductoPintura(dto);
    }

    // ELIMINAR
    @DeleteMapping("/otro/{id}")
    public void deleteProductoOtro(@PathVariable Long id) {
        service.deleteProductoOtro(id);
    }


    @DeleteMapping("/pintura/{id}")
    public void deleteProductoPintura(@PathVariable Long id) {
        service.deleteProductoPintura(id);
    }

    @PutMapping("/pintura/{id}")
    public ProductoPinturaGeneral updateProductoPintura(
            @PathVariable Long id,
            @RequestBody ProductoPinturaDTO dto) {

        return service.updateProductoPintura(id, dto);
    }

    @PutMapping("/otro/{id}")
    public ProductoOtroGeneral updateProductoOtro(
            @PathVariable Long id,
            @RequestBody ProductoOtroDTO dto) {

        return service.updateProductoOtro(id, dto);
    }


    public ProductoGeneralController(ProductoGeneralService service) {
        this.service = service;
    }
    
}
