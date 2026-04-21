package com.pinturerias.general.servicio;

import com.pinturerias.compartidos.director.ProductoDirector;
import com.pinturerias.compartidos.dto.ProductoOtroDTO;
import com.pinturerias.compartidos.dto.ProductoPinturaDTO;
import com.pinturerias.compartidos.entidad.Producto;
import com.pinturerias.compartidos.entidad.general.ProductoOtroGeneral;
import com.pinturerias.compartidos.entidad.general.ProductoPinturaGeneral;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.compartidos.servicio.PrecioProductoService;
import com.pinturerias.excepciones.RecursoNoEncontradoException;
import com.pinturerias.general.entidad.EtiquetaGeneral;
import com.pinturerias.general.repositorio.EtiquetaGeneralRepository;
import com.pinturerias.general.repositorio.ProductoOtroGeneralRepository;
import com.pinturerias.general.repositorio.ProductoPinturaGeneralRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductoGeneralService {

    private final ProductoOtroGeneralRepository repoOtro;
    private final ProductoPinturaGeneralRepository repoPintura;
    private final EtiquetaGeneralRepository etiquetaGeneralRepository;
    private final ProductoDirector director;
    private final PrecioProductoService precioProductoService;

    public ProductoGeneralService(ProductoDirector director,
                                  ProductoOtroGeneralRepository repoOtro,
                                  ProductoPinturaGeneralRepository repoPintura,
                                  EtiquetaGeneralRepository etiquetaGeneralRepository,
                                  PrecioProductoService precioProductoService) {
        this.repoOtro = repoOtro;
        this.repoPintura = repoPintura;
        this.etiquetaGeneralRepository = etiquetaGeneralRepository;
        this.director = director;
        this.precioProductoService = precioProductoService;
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

    public ProductoOtroDTO createProductoOtro(ProductoOtroDTO dto) {
        Producto producto = director.construirProducto(dto);
        ProductoOtroGeneral productoOtro = (ProductoOtroGeneral) producto;
        productoOtro.setEtiquetas(resolverEtiquetasGenerales(dto.getEtiquetasGeneralesIds()));

        ProductoOtroGeneral guardado = repoOtro.save(productoOtro);
        return toOtroDTO(guardado);
    }

    public ProductoPinturaDTO createProductoPintura(ProductoPinturaDTO dto) {
        Producto producto = director.construirProducto(dto);
        ProductoPinturaGeneral productoPintura = (ProductoPinturaGeneral) producto;
        productoPintura.setEtiquetas(resolverEtiquetasGenerales(dto.getEtiquetasGeneralesIds()));

        actualizarPrecioBasePintura(productoPintura);
        ProductoPinturaGeneral guardado = repoPintura.save(productoPintura);
        return toPinturaDTO(guardado);
    }

    public void deleteProductoOtro(Long id) {
        repoOtro.deleteById(id);
    }

    public void deleteProductoPintura(Long id) {
        repoPintura.deleteById(id);
    }

    public ProductoPinturaDTO updateProductoPintura(Long id, ProductoPinturaDTO dto) {
        ProductoPinturaGeneral producto = repoPintura.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto pintura no encontrado"));

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setMarca(dto.getMarca());
        producto.setTipoPintura(dto.getTipoPintura());
        producto.setColorBase(dto.getColorBase());
        producto.setTamanoEnv(dto.getTamanoEnv());
        producto.setEtiquetas(resolverEtiquetasGenerales(dto.getEtiquetasGeneralesIds()));

        actualizarPrecioBasePintura(producto);
        return toPinturaDTO(repoPintura.save(producto));
    }

    public ProductoOtroDTO updateProductoOtro(Long id, ProductoOtroDTO dto) {
        ProductoOtroGeneral producto = repoOtro.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto otro no encontrado"));

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setMarca(dto.getMarca());
        producto.setPrecioFinal(dto.getPrecioFinal());
        producto.setEtiquetas(resolverEtiquetasGenerales(dto.getEtiquetasGeneralesIds()));

        return toOtroDTO(repoOtro.save(producto));
    }

    private Set<EtiquetaGeneral> resolverEtiquetasGenerales(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new HashSet<>();
        }

        Set<Long> idsUnicos = ids.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        List<EtiquetaGeneral> encontradas = etiquetaGeneralRepository.findAllById(idsUnicos);

        if (encontradas.size() != idsUnicos.size()) {
            throw new RecursoNoEncontradoException("Una o más etiquetas generales no existen");
        }

        return new HashSet<>(encontradas);
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

        List<Long> ids = producto.getEtiquetas().stream()
                .map(EtiquetaGeneral::getId)
                .toList();

        List<String> nombres = producto.getEtiquetas().stream()
                .map(EtiquetaGeneral::getValor)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList();

        dto.setEtiquetasGeneralesIds(ids);
        dto.setEtiquetas(nombres);
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

        List<Long> ids = producto.getEtiquetas().stream()
                .map(EtiquetaGeneral::getId)
                .toList();

        List<String> nombres = producto.getEtiquetas().stream()
                .map(EtiquetaGeneral::getValor)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList();

        dto.setEtiquetasGeneralesIds(ids);
        dto.setEtiquetas(nombres);
        dto.setEtiquetasSucursalIds(new ArrayList<>());
        return dto;
    }
}