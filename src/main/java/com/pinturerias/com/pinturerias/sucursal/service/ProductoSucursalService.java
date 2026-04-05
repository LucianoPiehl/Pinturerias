package com.pinturerias.com.pinturerias.sucursal.service;

import com.pinturerias.com.pinturerias.compartidos.director.ProductoDirector;
import com.pinturerias.com.pinturerias.compartidos.dto.ProductoDTO;
import com.pinturerias.com.pinturerias.compartidos.entity.Producto;
import com.pinturerias.com.pinturerias.compartidos.entity.sucursal.ProductoOtroSucursal;
import com.pinturerias.com.pinturerias.sucursal.repository.ProductoOtroSucursalRepository;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductoSucursalService {
    private final ProductoDirector director;
    private final ProductoOtroSucursalRepository repoOtro;

    public ProductoSucursalService(
            ProductoDirector director,
            ProductoOtroSucursalRepository otroRepo
    ) {
        this.director = director;
        this.repoOtro = otroRepo;
    }

    ;

    public Producto crearOtro(ProductoDTO dto) {

        Producto producto = director.construirProducto(dto);
        return repoOtro.save((ProductoOtroSucursal) producto);
    }

    public List<ProductoOtroSucursal> listarProductosOtro() {
        return repoOtro.findAll();
    }

    public void eliminarProductoOtro(Long id) {
        repoOtro.deleteById(id);
    }
}
