package com.pinturerias.general.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pinturerias.compartidos.dto.ProductoOtroDTO;
import com.pinturerias.compartidos.dto.ProductoPinturaDTO;
import com.pinturerias.compartidos.entidad.Producto;
import com.pinturerias.compartidos.entidad.general.ProductoOtroGeneral;
import com.pinturerias.compartidos.entidad.general.ProductoPinturaGeneral;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.general.servicio.ProductoGeneralService;


@RestController
@RequestMapping("/api/general/productos")
public class ProductoGeneralController {

    private final ProductoGeneralService servicio;

    // LISTAR
    @GetMapping("/otro")
    public List<ProductoOtroGeneral> getAllProductosOtro() {
        return servicio.getAllProductosOtro();
    }

    @GetMapping("/pintura")
    public List<ProductoPinturaGeneral> getAllProductosPintura() {
        return servicio.getAllProductosPintura();
    }

    // CREAR
    @PostMapping("/otro")
    public Producto createProductoOtro(@RequestBody ProductoOtroDTO dto) {
        dto.setContexto(Contexto.GENERAL);
        dto.setTipo(Tipo.OTRO);
        return servicio.createProductoOtro(dto);
    }

    @PostMapping("/pintura")
    public Producto createProductoPintura(@RequestBody ProductoPinturaDTO dto) {
        dto.setContexto(Contexto.GENERAL);
        dto.setTipo(Tipo.PINTURA);
        return servicio.createProductoPintura(dto);
    }

    @PutMapping("/pintura/{id}")
    public ProductoPinturaGeneral updateProductoPintura(
            @PathVariable Long id,
            @RequestBody ProductoPinturaDTO dto) {

        return servicio.updateProductoPintura(id, dto);
    }

    @PutMapping("/otro/{id}")
    public ProductoOtroGeneral updateProductoOtro(
            @PathVariable Long id,
            @RequestBody ProductoOtroDTO dto) {

        return servicio.updateProductoOtro(id, dto);
    }

    @DeleteMapping("/otro/{id}")
    public void deleteProductoOtro(@PathVariable Long id) {
        servicio.deleteProductoOtro(id);
    }

    @DeleteMapping("/pintura/{id}")
    public void deleteProductoPintura(@PathVariable Long id) {
        servicio.deleteProductoPintura(id);
    }


    public ProductoGeneralController(ProductoGeneralService servicio) {
        this.servicio = servicio;
    }
    
}



