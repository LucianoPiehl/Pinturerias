package com.pinturerias.sucursal.servicio;

import com.pinturerias.compartidos.director.ProductoDirector;
import com.pinturerias.compartidos.dto.ProductoOtroDTO;
import com.pinturerias.compartidos.entidad.Producto;
import com.pinturerias.compartidos.entidad.sucursal.ProductoOtroSucursal;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.configuracion.TenantExecutor;
import com.pinturerias.excepciones.ExcepcionApi;
import com.pinturerias.excepciones.RecursoNoEncontradoException;
import com.pinturerias.general.entidad.EtiquetaGeneral;
import com.pinturerias.general.repositorio.EtiquetaGeneralRepository;
import com.pinturerias.sucursal.entidad.EtiquetaSucursal;
import com.pinturerias.sucursal.repositorio.EtiquetaSucursalRepository;
import com.pinturerias.sucursal.repositorio.ProductoOtroSucursalRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductoSucursalService {

    private final ProductoDirector director;
    private final ProductoOtroSucursalRepository repoOtro;
    private final EtiquetaSucursalRepository etiquetaSucursalRepository;
    private final EtiquetaGeneralRepository etiquetaGeneralRepository;
    private final TenantExecutor tenantExecutor;

    public ProductoSucursalService(
            ProductoDirector director,
            ProductoOtroSucursalRepository otroRepo,
            EtiquetaSucursalRepository etiquetaSucursalRepository,
            EtiquetaGeneralRepository etiquetaGeneralRepository,
            TenantExecutor tenantExecutor
    ) {
        this.director = director;
        this.repoOtro = otroRepo;
        this.etiquetaSucursalRepository = etiquetaSucursalRepository;
        this.etiquetaGeneralRepository = etiquetaGeneralRepository;
        this.tenantExecutor = tenantExecutor;
    }

    public ProductoOtroDTO crearOtro(ProductoOtroDTO dto) {
        Producto producto = director.construirProducto(dto);
        ProductoOtroSucursal productoSucursal = (ProductoOtroSucursal) producto;

        productoSucursal.setEtiquetasSucursal(resolverEtiquetasSucursal(dto.getEtiquetasSucursalIds()));
        productoSucursal.setEtiquetasGeneralesIds(resolverEtiquetasGeneralesIds(dto.getEtiquetasGeneralesIds()));

        ProductoOtroSucursal guardado = repoOtro.save(productoSucursal);
        return toDTO(guardado);
    }

    public ProductoOtroDTO actualizarOtro(Long id, ProductoOtroDTO dto) {
        ProductoOtroSucursal producto = repoOtro.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto de sucursal no encontrado"));

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setMarca(dto.getMarca());
        producto.setPrecioFinal(dto.getPrecioFinal());
        producto.setStock(dto.getStock());

        producto.setEtiquetasSucursal(resolverEtiquetasSucursal(dto.getEtiquetasSucursalIds()));
        producto.setEtiquetasGeneralesIds(resolverEtiquetasGeneralesIds(dto.getEtiquetasGeneralesIds()));

        return toDTO(repoOtro.save(producto));
    }

    public List<ProductoOtroDTO> listarProductosOtro() {
        return repoOtro.findAll().stream()
                .map(this::toDTO)
                .toList();
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

    private Set<EtiquetaSucursal> resolverEtiquetasSucursal(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new HashSet<>();
        }

        Set<Long> idsUnicos = ids.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        List<EtiquetaSucursal> encontradas = etiquetaSucursalRepository.findAllById(idsUnicos);

        if (encontradas.size() != idsUnicos.size()) {
            throw new RecursoNoEncontradoException("Una o más etiquetas de sucursal no existen");
        }

        return new HashSet<>(encontradas);
    }

    private Set<Long> resolverEtiquetasGeneralesIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new HashSet<>();
        }

        Set<Long> idsUnicos = ids.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        return tenantExecutor.ejecutarEnTenant(null, () -> {
            List<EtiquetaGeneral> encontradas = etiquetaGeneralRepository.findAllById(idsUnicos);

            if (encontradas.size() != idsUnicos.size()) {
                throw new RecursoNoEncontradoException("Una o más etiquetas generales no existen");
            }

            return encontradas.stream()
                    .map(EtiquetaGeneral::getId)
                    .collect(Collectors.toSet());
        });
    }

    private ProductoOtroDTO toDTO(ProductoOtroSucursal producto) {
        ProductoOtroDTO dto = new ProductoOtroDTO();
        dto.setIdProducto(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecioFinal(producto.getPrecioFinal());
        dto.setMarca(producto.getMarca());
        dto.setTipo(Tipo.OTRO);
        dto.setContexto(Contexto.SUCURSAL);
        dto.setStock(producto.getStock());

        List<Long> idsSucursal = producto.getEtiquetasSucursal().stream()
                .map(EtiquetaSucursal::getId)
                .toList();

        List<String> nombresSucursal = producto.getEtiquetasSucursal().stream()
                .map(EtiquetaSucursal::getValor)
                .toList();

        List<Long> idsGenerales = new ArrayList<>(producto.getEtiquetasGeneralesIds());

        List<String> nombresGenerales = tenantExecutor.ejecutarEnTenant(null, () ->
                etiquetaGeneralRepository.findAllById(producto.getEtiquetasGeneralesIds()).stream()
                        .map(EtiquetaGeneral::getValor)
                        .toList()
        );

        List<String> todas = new ArrayList<>();
        todas.addAll(nombresGenerales);
        todas.addAll(nombresSucursal);
        todas.sort(String.CASE_INSENSITIVE_ORDER);

        dto.setEtiquetas(todas);
        dto.setEtiquetasGeneralesIds(idsGenerales);
        dto.setEtiquetasSucursalIds(idsSucursal);

        return dto;
    }
}