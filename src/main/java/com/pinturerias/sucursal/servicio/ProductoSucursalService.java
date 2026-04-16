package com.pinturerias.sucursal.servicio;

import com.pinturerias.compartidos.director.ProductoDirector;
import com.pinturerias.compartidos.dto.ProductoDTO;
import com.pinturerias.compartidos.entidad.Producto;
import com.pinturerias.compartidos.entidad.sucursal.ProductoOtroSucursal;
import com.pinturerias.sucursal.repositorio.ProductoOtroSucursalRepository;
import com.pinturerias.excepciones.ExcepcionApi;
import com.pinturerias.excepciones.RecursoNoEncontradoException;

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

    public Double obtenerPrecioVenta(Long productoId) {
        return obtenerProducto(productoId).getPrecioFinal();
    }

    public void descontarStock(Long productoId, Integer cantidad) {
        ProductoOtroSucursal producto = obtenerProducto(productoId);

        if (producto.getStock() < cantidad) {
            throw new ExcepcionApi(400, "Stock insuficiente para el producto de sucursal " + productoId);
        }

        producto.setStock(producto.getStock() - cantidad);
        repoOtro.save(producto);
    }

    private ProductoOtroSucursal obtenerProducto(Long productoId) {
        return repoOtro.findById(productoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto de sucursal no encontrado"));
    }
}




