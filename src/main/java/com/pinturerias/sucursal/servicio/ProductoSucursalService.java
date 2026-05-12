package com.pinturerias.sucursal.servicio;

import com.pinturerias.compartidos.director.ProductoDirector;
import com.pinturerias.compartidos.dto.producto.ProductoOtroDTO;
import com.pinturerias.compartidos.entidad.sucursal.ProductoOtroSucursal;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.compartidos.servicio.ProductoEtiquetaOrquestadorService;
import com.pinturerias.excepciones.ExcepcionApi;
import com.pinturerias.excepciones.RecursoNoEncontradoException;
import com.pinturerias.sucursal.repositorio.PedidoSucursalRepository;
import com.pinturerias.sucursal.repositorio.ProductoOtroSucursalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductoSucursalService {

    private final ProductoDirector director;
    private final ProductoOtroSucursalRepository repoOtro;
    private final ProductoEtiquetaSucursalService productoEtiquetaSucursalService;
    private final ProductoEtiquetaOrquestadorService productoEtiquetaQueryService;
    private final PedidoSucursalRepository pedidoSucursalRepository;


    @Transactional("tenantTransactionManager")
    public ProductoOtroDTO crearOtro(ProductoOtroDTO dto) {

        ProductoOtroSucursal producto = (ProductoOtroSucursal) director.construir(dto);
        ProductoOtroSucursal guardado = repoOtro.save(producto);

        productoEtiquetaSucursalService.sincronizar(
                guardado.getId(),
                Contexto.SUCURSAL,
                Tipo.OTRO,
                dto.getEtiquetasGeneralesIds(),
                dto.getEtiquetasSucursalIds()
        );

        return toDTO(guardado);
    }

    @Transactional("tenantTransactionManager")
    public ProductoOtroDTO actualizarOtro(Long id, ProductoOtroDTO dto) {

        ProductoOtroSucursal producto = repoOtro.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto de sucursal no encontrado"));

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setMarca(dto.getMarca());
        producto.setPrecioFinal(dto.getPrecioFinal());
        producto.setStock(dto.getStock());

        ProductoOtroSucursal guardado = repoOtro.save(producto);

        productoEtiquetaSucursalService.sincronizar(
                id,
                Contexto.SUCURSAL,
                Tipo.OTRO,
                dto.getEtiquetasGeneralesIds(),
                dto.getEtiquetasSucursalIds()
        );

        return toDTO(guardado);
    }

    public List<ProductoOtroDTO> listarProductosOtro() {

        return repoOtro.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional("tenantTransactionManager")
    public void eliminarProductoOtro(Long id) {

        ProductoOtroSucursal producto = repoOtro.findById(id).orElseThrow();

        boolean existeEnPedidos = pedidoSucursalRepository.existsByProductos_IdProducto(id);
        productoEtiquetaSucursalService.sincronizar(id,Contexto.SUCURSAL,Tipo.OTRO,new ArrayList<Long>(),new ArrayList<Long>());
        if (existeEnPedidos) {
            producto.setHabilitado(false);
            repoOtro.save(producto);
        } else {
            repoOtro.delete(producto);
        }
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

        dto.setEtiquetas(
                productoEtiquetaQueryService.obtenerEtiquetasSucursal(producto.getId(), Tipo.OTRO)
        );

        return dto;
    }
}
