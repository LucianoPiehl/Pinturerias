package com.pinturerias.com.pinturerias.general.service;

import java.util.List;
import java.util.Optional;

import com.pinturerias.com.pinturerias.compartidos.dto.ProductoPinturaDTO;
import org.springframework.stereotype.Service;

import com.pinturerias.com.pinturerias.compartidos.director.ProductoDirector;
import com.pinturerias.com.pinturerias.compartidos.dto.ProductoDTO;
import com.pinturerias.com.pinturerias.compartidos.entity.Producto;
import com.pinturerias.com.pinturerias.compartidos.entity.general.ProductoOtroGeneral;
import com.pinturerias.com.pinturerias.compartidos.entity.general.ProductoPinturaGeneral;
import com.pinturerias.com.pinturerias.general.repository.ProductoOtroGeneralRepository;
import com.pinturerias.com.pinturerias.general.repository.ProductoPinturaGeneralRepository;

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
   
    public List<ProductoOtroGeneral> listarProductosOtro() {
        return repoOtro.findAll();
    }
    public List<ProductoPinturaGeneral> listarProductosPintura() {
        return repoPintura.findAll();
    }

    public Producto crearOtro(ProductoDTO dto) {
        Producto producto = director.construirProducto(dto);
        ProductoOtroGeneral p = (ProductoOtroGeneral) producto;
        return repoOtro.save(p);
    }

    public ProductoPinturaGeneral crearPintura(ProductoPinturaDTO dto) {
        Producto producto = director.construirProducto(dto);
        ProductoPinturaGeneral p = (ProductoPinturaGeneral) producto;
        return repoPintura.save(p);
    }

    public void eliminarOtro(Long id) {
        repoOtro.deleteById(id);
    }
    public void eliminarPintura(Long id) {
        repoPintura.deleteById(id);
    }

    public void actualizarPintura(ProductoPinturaDTO dto)
    {
        Optional<ProductoPinturaGeneral> opt = repoPintura.findById(dto.getId());
        // validar el optional, y en caso de que el producto este vacio o no se encuentre arrojar error http 505
        throw new UnsupportedOperationException("Este método aún no está implementado");
    }
}
