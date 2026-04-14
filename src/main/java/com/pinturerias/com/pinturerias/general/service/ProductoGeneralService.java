package com.pinturerias.com.pinturerias.general.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pinturerias.com.pinturerias.compartidos.director.ProductoDirector;
import com.pinturerias.com.pinturerias.compartidos.dto.ProductoDTO;
import com.pinturerias.com.pinturerias.compartidos.dto.ProductoPinturaDTO;
import com.pinturerias.com.pinturerias.compartidos.entity.Producto;
import com.pinturerias.com.pinturerias.compartidos.entity.general.ProductoOtroGeneral;
import com.pinturerias.com.pinturerias.compartidos.entity.general.ProductoPinturaGeneral;
import com.pinturerias.com.pinturerias.general.repository.ProductoOtroGeneralRepository;
import com.pinturerias.com.pinturerias.general.repository.ProductoPinturaGeneralRepository;
import com.pinturerias.excepciones.RecursoNoEncontradoException;

@Service
public class ProductoGeneralService {
    private final ProductoOtroGeneralRepository repoOtro;
    private final ProductoPinturaGeneralRepository repoPintura;
    private final ProductoDirector director;

    public ProductoGeneralService(ProductoDirector director,ProductoOtroGeneralRepository repoOtro,ProductoPinturaGeneralRepository repoPintura) {
        this.repoOtro = repoOtro;
        this.repoPintura = repoPintura;
        this.director=director;

    }
   
    public List<ProductoOtroGeneral> getAllProductosOtro() {
        return repoOtro.findAll();
    }
    public List<ProductoPinturaGeneral> getAllProductosPintura() {
        return repoPintura.findAll();
    }

    public Producto createProductoOtro(ProductoDTO dto) {
        Producto producto = director.construirProducto(dto);
        ProductoOtroGeneral p = (ProductoOtroGeneral) producto;
        return repoOtro.save(p);
    }

    public ProductoPinturaGeneral createProductoPintura(ProductoPinturaDTO dto) {
        Producto producto = director.construirProducto(dto);
        ProductoPinturaGeneral p = (ProductoPinturaGeneral) producto;
        return repoPintura.save(p);
    }

    public void deleteProductoOtro(Long id) {
        repoOtro.deleteById(id);
    }
    public void deleteProductoPintura(Long id) {
        repoPintura.deleteById(id);
    }

    public ProductoPinturaGeneral updateProductoPintura(Long id, ProductoPinturaDTO dto) {

        ProductoPinturaGeneral producto = repoPintura.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto pintura no encontrado"));

        // campos base
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setMarca(dto.getMarca());
        producto.setPrecioFinal(dto.getPrecioFinal());
        producto.setEtiquetas(dto.getEtiquetas());

        // campos específicos
        producto.setTipoPintura(dto.getTipoPintura());
        producto.setColorBase(dto.getColorBase());
        producto.setTamanoEnv(dto.getTamanoEnv());

        return repoPintura.save(producto);
    }

    public ProductoOtroGeneral updateProductoOtro(Long id, ProductoDTO dto) {

    ProductoOtroGeneral producto = repoOtro.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Producto pintura no encontrado"));

    producto.setNombre(dto.getNombre());
    producto.setDescripcion(dto.getDescripcion());
    producto.setMarca(dto.getMarca());
    producto.setPrecioFinal(dto.getPrecioFinal());
    producto.setEtiquetas(dto.getEtiquetas());

    return repoOtro.save(producto);
}
}
