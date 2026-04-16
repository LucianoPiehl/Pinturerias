package com.pinturerias.general.servicio;

import com.pinturerias.compartidos.director.ProductoDirector;
import com.pinturerias.compartidos.dto.ProductoDTO;
import com.pinturerias.compartidos.dto.ProductoPinturaDTO;
import com.pinturerias.compartidos.entidad.Producto;
import com.pinturerias.compartidos.entidad.general.ProductoOtroGeneral;
import com.pinturerias.compartidos.entidad.general.ProductoPinturaGeneral;
import com.pinturerias.compartidos.servicio.PrecioProductoService;
import com.pinturerias.excepciones.RecursoNoEncontradoException;
import com.pinturerias.general.repositorio.ProductoOtroGeneralRepository;
import com.pinturerias.general.repositorio.ProductoPinturaGeneralRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoGeneralService {
    private final ProductoOtroGeneralRepository repoOtro;
    private final ProductoPinturaGeneralRepository repoPintura;
    private final ProductoDirector director;
    private final PrecioProductoService precioProductoService;

    public ProductoGeneralService(ProductoDirector director,
                                  ProductoOtroGeneralRepository repoOtro,
                                  ProductoPinturaGeneralRepository repoPintura,
                                  PrecioProductoService precioProductoService) {
        this.repoOtro = repoOtro;
        this.repoPintura = repoPintura;
        this.director = director;
        this.precioProductoService = precioProductoService;
    }

    public List<ProductoOtroGeneral> getAllProductosOtro() {
        return repoOtro.findAll();
    }

    public List<ProductoPinturaGeneral> getAllProductosPintura() {
        return repoPintura.findAll().stream()
                .map(this::actualizarPrecioBasePintura)
                .toList();
    }

    public Producto createProductoOtro(ProductoDTO dto) {
        Producto producto = director.construirProducto(dto);
        ProductoOtroGeneral productoOtro = (ProductoOtroGeneral) producto;
        return repoOtro.save(productoOtro);
    }

    public ProductoPinturaGeneral createProductoPintura(ProductoPinturaDTO dto) {
        Producto producto = director.construirProducto(dto);
        ProductoPinturaGeneral productoPintura = (ProductoPinturaGeneral) producto;
        actualizarPrecioBasePintura(productoPintura);
        return repoPintura.save(productoPintura);
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

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setMarca(dto.getMarca());
        producto.setEtiquetas(dto.getEtiquetas());
        producto.setTipoPintura(dto.getTipoPintura());
        producto.setColorBase(dto.getColorBase());
        producto.setTamanoEnv(dto.getTamanoEnv());

        actualizarPrecioBasePintura(producto);
        return repoPintura.save(producto);
    }

    public ProductoOtroGeneral updateProductoOtro(Long id, ProductoDTO dto) {
        ProductoOtroGeneral producto = repoOtro.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto otro no encontrado"));

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setMarca(dto.getMarca());
        producto.setPrecioFinal(dto.getPrecioFinal());
        producto.setEtiquetas(dto.getEtiquetas());

        return repoOtro.save(producto);
    }

    private ProductoPinturaGeneral actualizarPrecioBasePintura(ProductoPinturaGeneral producto) {
        producto.setPrecioBase(precioProductoService.calcularPrecioRecomendadoGeneral(producto));
        return producto;
    }
}
