package com.pinturerias.sucursal.servicio;

import com.pinturerias.compartidos.dto.PedidoDTO;
import com.pinturerias.compartidos.entidad.shared.Pedido;
import com.pinturerias.compartidos.entidad.shared.PedidoProducto;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.EstadoPedido;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.compartidos.servicio.PedidoMapper;
import com.pinturerias.compartidos.servicio.PrecioProductoService;
import com.pinturerias.compartidos.servicio.PedidoValidationService;
import com.pinturerias.sucursal.repositorio.PedidoSucursalRepository;
import com.pinturerias.excepciones.ExcepcionApi;
import com.pinturerias.excepciones.RecursoNoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoSucursalService {

    private final PedidoSucursalRepository repositorio;
    private final PedidoMapper mapper;
    private final PedidoValidationService validationService;
    private final ProductoPrecioStockService productoPrecioStockService;
    private final ProductoSucursalService productoSucursalService;
    private final PrecioProductoService precioProductoService;

    public PedidoSucursalService(
            PedidoSucursalRepository repositorio,
            PedidoMapper mapper,
            PedidoValidationService validationService,
            ProductoPrecioStockService productoPrecioStockService,
            ProductoSucursalService productoSucursalService,
            PrecioProductoService precioProductoService
    ) {
        this.repositorio = repositorio;
        this.mapper = mapper;
        this.validationService = validationService;
        this.productoPrecioStockService = productoPrecioStockService;
        this.productoSucursalService = productoSucursalService;
        this.precioProductoService = precioProductoService;
    }

    public List<PedidoDTO> listar() {
        return repositorio.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public PedidoDTO obtener(Long id) {
        return mapper.toDto(obtenerEntidad(id));
    }

    @Transactional("tenantTransactionManager")
    public PedidoDTO crear(PedidoDTO dto) {
        Pedido pedido = mapper.toEntity(dto);
        if (pedido.getEstado() == null) {
            pedido.setEstado(EstadoPedido.INICIADO);
        }
        normalizarPrecios(pedido);
        validationService.validarPedido(pedido, false);
        return mapper.toDto(repositorio.save(pedido));
    }

    @Transactional("tenantTransactionManager")
    public PedidoDTO actualizarEstado(Long id, EstadoPedido estado) {
        Pedido pedido = obtenerEntidad(id);

        if (pedido.getEstado() == EstadoPedido.FINALIZADO) {
            throw new ExcepcionApi(400, "El pedido ya fue finalizado");
        }

        pedido.setEstado(estado);
        Pedido guardado = repositorio.save(pedido);

        if (estado == EstadoPedido.FINALIZADO) {
            finalizarPedido(guardado);
        }

        return mapper.toDto(guardado);
    }

    private void finalizarPedido(Pedido pedido) {
        for (PedidoProducto producto : pedido.getProductos()) {
            if (producto.getContextoProducto() == Contexto.GENERAL) {
                productoPrecioStockService.descontarStock(
                        producto.getIdProducto(),
                        producto.getTipoProducto(),
                        producto.getCantidad()
                );
                continue;
            }

            validationService.validarProductoSucursal(producto);
            productoSucursalService.descontarStock(producto.getIdProducto(), producto.getCantidad());
        }
    }

    private void normalizarPrecios(Pedido pedido) {
        for (PedidoProducto producto : pedido.getProductos()) {
            if (producto.getContextoProducto() == Contexto.GENERAL) {
                validationService.validarProductoGeneral(producto);
                producto.setPrecioUnitario(
                        precioProductoService.calcularPrecioFinalSucursal(
                                producto.getIdProducto(),
                                producto.getTipoProducto()
                        )
                );
                continue;
            }

            producto.setTipoProducto(Tipo.OTRO);
            validationService.validarProductoSucursal(producto);
            producto.setPrecioUnitario(
                    precioProductoService.obtenerPrecioProductoSucursal(producto.getIdProducto())
            );
        }
    }

    private Pedido obtenerEntidad(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado"));
    }
}




