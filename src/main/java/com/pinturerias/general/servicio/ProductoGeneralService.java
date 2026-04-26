package com.pinturerias.general.servicio;

import com.pinturerias.compartidos.director.ProductoDirector;
import com.pinturerias.compartidos.dto.EtiquetasProductoDTO;
import com.pinturerias.compartidos.dto.ProductoOtroDTO;
import com.pinturerias.compartidos.dto.ProductoPinturaDTO;
import com.pinturerias.compartidos.entidad.Producto;
import com.pinturerias.compartidos.entidad.general.ProductoOtroGeneral;
import com.pinturerias.compartidos.entidad.general.ProductoPinturaGeneral;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.compartidos.servicio.PrecioProductoService;
import com.pinturerias.excepciones.RecursoNoEncontradoException;
import com.pinturerias.general.repositorio.ProductoOtroGeneralRepository;
import com.pinturerias.general.repositorio.ProductoPinturaGeneralRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoGeneralService {

    private final ProductoOtroGeneralRepository repoOtro;
    private final ProductoPinturaGeneralRepository repoPintura;
    private final ProductoDirector director;
    private final PrecioProductoService precioProductoService;
    private final ProductoEtiquetaGeneralService productoEtiquetaGeneralService;

    public ProductoGeneralService(ProductoDirector director,
                                  ProductoOtroGeneralRepository repoOtro,
                                  ProductoPinturaGeneralRepository repoPintura,
                                  PrecioProductoService precioProductoService,
                                  ProductoEtiquetaGeneralService productoEtiquetaGeneralService) {
        this.repoOtro = repoOtro;
        this.repoPintura = repoPintura;
        this.director = director;
        this.precioProductoService = precioProductoService;
        this.productoEtiquetaGeneralService = productoEtiquetaGeneralService;
    }

    public List<ProductoOtroDTO> getAllProductosOtro() {
        return repoOtro.findAll().stream()
                .map(this::toOtroDTO)
                .toList();
    }

    public List<ProductoPinturaDTO> getAllProductosPintura() {
        return repoPintura.findAll().stream()
                .map(this::actualizarPrecioBasePintura)
                .map(this::toPinturaDTO)
                .toList();
    }

    @Transactional("generalTransactionManager")
    public ProductoOtroDTO createProductoOtro(ProductoOtroDTO dto) {
        ProductoOtroGeneral productoOtro = (ProductoOtroGeneral) director.construir(dto);
        ProductoOtroGeneral guardado = repoOtro.save(productoOtro);
        productoEtiquetaGeneralService.sincronizar(guardado.getId(), Tipo.OTRO, dto.getEtiquetasGeneralesIds());
        return toOtroDTO(guardado);
    }

    @Transactional("generalTransactionManager")
    public ProductoPinturaDTO createProductoPintura(ProductoPinturaDTO dto) {
        ProductoPinturaGeneral productoPintura = (ProductoPinturaGeneral) director.construir(dto);
        actualizarPrecioBasePintura(productoPintura);
        ProductoPinturaGeneral guardado = repoPintura.save(productoPintura);
        productoEtiquetaGeneralService.sincronizar(guardado.getId(), Tipo.PINTURA, dto.getEtiquetasGeneralesIds());
        return toPinturaDTO(guardado);
    }

    @Transactional("generalTransactionManager")
    public void deleteProductoOtro(Long id) {
        productoEtiquetaGeneralService.eliminar(id, Tipo.OTRO);
        repoOtro.deleteById(id);
    }

    @Transactional("generalTransactionManager")
    public void deleteProductoPintura(Long id) {
        productoEtiquetaGeneralService.eliminar(id, Tipo.PINTURA);
        repoPintura.deleteById(id);
    }

    @Transactional("generalTransactionManager")
    public ProductoPinturaDTO updateProductoPintura(Long id, ProductoPinturaDTO dto) {
        ProductoPinturaGeneral producto = repoPintura.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto pintura no encontrado"));

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setMarca(dto.getMarca());
        producto.setTipoPintura(dto.getTipoPintura());
        producto.setColorBase(dto.getColorBase());
        producto.setTamanoEnv(dto.getTamanoEnv());

        actualizarPrecioBasePintura(producto);
        ProductoPinturaGeneral guardado = repoPintura.save(producto);
        productoEtiquetaGeneralService.sincronizar(id, Tipo.PINTURA, dto.getEtiquetasGeneralesIds());
        return toPinturaDTO(guardado);
    }

    @Transactional("generalTransactionManager")
    public ProductoOtroDTO updateProductoOtro(Long id, ProductoOtroDTO dto) {
        ProductoOtroGeneral producto = repoOtro.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto otro no encontrado"));

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setMarca(dto.getMarca());
        producto.setPrecioFinal(dto.getPrecioFinal());

        ProductoOtroGeneral guardado = repoOtro.save(producto);
        productoEtiquetaGeneralService.sincronizar(id, Tipo.OTRO, dto.getEtiquetasGeneralesIds());
        return toOtroDTO(guardado);
    }

    private ProductoPinturaGeneral actualizarPrecioBasePintura(ProductoPinturaGeneral producto) {
        producto.setPrecioBase(precioProductoService.calcularPrecioRecomendadoGeneral(producto));
        return producto;
    }

    private ProductoOtroDTO toOtroDTO(ProductoOtroGeneral producto) {
        ProductoOtroDTO dto = new ProductoOtroDTO();
        dto.setIdProducto(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecioFinal(producto.getPrecioFinal());
        dto.setMarca(producto.getMarca());
        dto.setTipo(Tipo.OTRO);
        dto.setContexto(Contexto.GENERAL);
        dto.setStock(0);

        EtiquetasProductoDTO etiquetas = productoEtiquetaGeneralService.obtener(producto.getId(), Tipo.OTRO);
        dto.setEtiquetas(etiquetas.getEtiquetas());
        dto.setEtiquetasGeneralesIds(etiquetas.getEtiquetasGeneralesIds());
        dto.setEtiquetasSucursalIds(new ArrayList<>());
        return dto;
    }

    private ProductoPinturaDTO toPinturaDTO(ProductoPinturaGeneral producto) {
        ProductoPinturaDTO dto = new ProductoPinturaDTO();
        dto.setIdProducto(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecioFinal(producto.getPrecioFinal());
        dto.setMarca(producto.getMarca());
        dto.setTipo(Tipo.PINTURA);
        dto.setContexto(Contexto.GENERAL);
        dto.setStock(0);
        dto.setTipoPintura(producto.getTipoPintura());
        dto.setColorBase(producto.getColorBase());
        dto.setTamanoEnv(producto.getTamanoEnv());

        EtiquetasProductoDTO etiquetas = productoEtiquetaGeneralService.obtener(producto.getId(), Tipo.PINTURA);
        dto.setEtiquetas(etiquetas.getEtiquetas());
        dto.setEtiquetasGeneralesIds(etiquetas.getEtiquetasGeneralesIds());
        dto.setEtiquetasSucursalIds(new ArrayList<>());
        return dto;
    }
}
