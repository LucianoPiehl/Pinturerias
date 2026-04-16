package com.pinturerias.general.servicio;

import com.pinturerias.compartidos.dto.PedidoDTO;
import com.pinturerias.compartidos.entidad.shared.Pedido;
import com.pinturerias.compartidos.entidad.shared.PedidoProducto;
import com.pinturerias.compartidos.enumeracion.EstadoPedido;
import com.pinturerias.compartidos.servicio.PedidoMapper;
import com.pinturerias.compartidos.servicio.PedidoValidationService;
import com.pinturerias.compartidos.servicio.PrecioProductoService;
import com.pinturerias.configuracion.TenantExecutor;
import com.pinturerias.general.entidad.Sucursal;
import com.pinturerias.general.repositorio.PedidoGeneralRepository;
import com.pinturerias.general.repositorio.SucursalRepository;
import com.pinturerias.excepciones.ExcepcionApi;
import com.pinturerias.excepciones.RecursoNoEncontradoException;
import com.pinturerias.sucursal.servicio.ProductoPrecioStockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoGeneralService {

    private final PedidoGeneralRepository pedidoRepository;
    private final PedidoMapper mapper;
    private final PedidoValidationService validationService;
    private final SucursalRepository sucursalRepository;
    private final TenantExecutor tenantExecutor;
    private final ProductoPrecioStockService productoPrecioStockService;
    private final PrecioProductoService precioProductoService;

    public PedidoGeneralService(
            PedidoGeneralRepository pedidoRepository,
            PedidoMapper mapper,
            PedidoValidationService validationService,
            SucursalRepository sucursalRepository,
            TenantExecutor tenantExecutor,
            ProductoPrecioStockService productoPrecioStockService,
            PrecioProductoService precioProductoService
    ) {
        this.pedidoRepository = pedidoRepository;
        this.mapper = mapper;
        this.validationService = validationService;
        this.sucursalRepository = sucursalRepository;
        this.tenantExecutor = tenantExecutor;
        this.productoPrecioStockService = productoPrecioStockService;
        this.precioProductoService = precioProductoService;
    }

    public List<PedidoDTO> listar() {
        return pedidoRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public PedidoDTO obtener(Long id) {
        return mapper.toDto(obtenerEntidad(id));
    }

    @Transactional("generalTransactionManager")
    public PedidoDTO crear(PedidoDTO dto) {
        Pedido pedido = mapper.toEntity(dto);
        if (pedido.getEstado() == null) {
            pedido.setEstado(EstadoPedido.INICIADO);
        }
        normalizarPrecios(pedido);
        validationService.validarPedido(pedido, true);
        return mapper.toDto(pedidoRepository.save(pedido));
    }

    @Transactional("generalTransactionManager")
    public PedidoDTO actualizarEstado(Long id, EstadoPedido estado) {
        Pedido pedido = obtenerEntidad(id);

        if (pedido.getEstado() == EstadoPedido.FINALIZADO) {
            throw new ExcepcionApi(400, "El pedido ya fue finalizado");
        }

        pedido.setEstado(estado);
        Pedido guardado = pedidoRepository.save(pedido);

        if (estado == EstadoPedido.FINALIZADO) {
            finalizarPedido(guardado);
        }

        return mapper.toDto(guardado);
    }

    private void finalizarPedido(Pedido pedido) {
        Sucursal sucursal = sucursalRepository.findById(pedido.getSucursalDestinoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Sucursal no encontrada"));

        for (PedidoProducto producto : pedido.getProductos()) {
            validationService.validarProductoGeneral(producto);

            tenantExecutor.ejecutarEnTenant(sucursal.getCodigo(), () -> {
                productoPrecioStockService.acreditarStock(
                        producto.getIdProducto(),
                        producto.getTipoProducto(),
                        producto.getCantidad()
                );
                return null;
            });
        }
    }

    private void normalizarPrecios(Pedido pedido) {
        for (PedidoProducto producto : pedido.getProductos()) {
            validationService.validarProductoGeneral(producto);
            producto.setPrecioUnitario(
                    precioProductoService.calcularPrecioRecomendadoGeneral(
                            producto.getIdProducto(),
                            producto.getTipoProducto()
                    )
            );
        }
    }

    private Pedido obtenerEntidad(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado"));
    }
}




