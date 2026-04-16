package com.pinturerias.compartidos.servicio;

import com.pinturerias.compartidos.dto.PedidoDTO;
import com.pinturerias.compartidos.dto.PedidoProductoDTO;
import com.pinturerias.compartidos.entidad.shared.Pedido;
import com.pinturerias.compartidos.entidad.shared.PedidoProducto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class PedidoMapper {

    public Pedido toEntity(PedidoDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setId(dto.getId());
        pedido.setMail(dto.getMail());
        pedido.setTelefono(dto.getTelefono());
        pedido.setNombre(dto.getNombre());
        pedido.setEstado(dto.getEstado());
        pedido.setFecha(dto.getFecha() != null ? dto.getFecha() : LocalDate.now());
        pedido.setHora(dto.getHora() != null ? dto.getHora() : LocalTime.now());
        pedido.setNombreCliente(dto.getNombreCliente());
        pedido.setNombreVendedor(dto.getNombreVendedor());
        pedido.setMedioPago(dto.getMedioPago());
        pedido.setDescuento(dto.getDescuento());
        pedido.setObservaciones(dto.getObservaciones());
        pedido.setSucursalDestinoId(dto.getSucursalDestinoId());

        List<PedidoProducto> productos = new ArrayList<>();
        if (dto.getProductos() != null) {
            for (PedidoProductoDTO itemDto : dto.getProductos()) {
                PedidoProducto item = new PedidoProducto();
                item.setIdProducto(itemDto.getIdProducto());
                item.setContextoProducto(itemDto.getContextoProducto());
                item.setTipoProducto(itemDto.getTipoProducto());
                item.setCantidad(itemDto.getCantidad());
                item.setPrecioUnitario(itemDto.getPrecioUnitario());
                productos.add(item);
            }
        }
        pedido.setProductos(productos);
        return pedido;
    }

    public PedidoDTO toDto(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setMail(pedido.getMail());
        dto.setTelefono(pedido.getTelefono());
        dto.setNombre(pedido.getNombre());
        dto.setEstado(pedido.getEstado());
        dto.setFecha(pedido.getFecha());
        dto.setHora(pedido.getHora());
        dto.setNombreCliente(pedido.getNombreCliente());
        dto.setNombreVendedor(pedido.getNombreVendedor());
        dto.setMedioPago(pedido.getMedioPago());
        dto.setDescuento(pedido.getDescuento());
        dto.setObservaciones(pedido.getObservaciones());
        dto.setSucursalDestinoId(pedido.getSucursalDestinoId());
        dto.setProductos(pedido.getProductos().stream().map(this::toDto).toList());
        return dto;
    }

    private PedidoProductoDTO toDto(PedidoProducto producto) {
        return new PedidoProductoDTO(
                producto.getIdProducto(),
                producto.getContextoProducto(),
                producto.getTipoProducto(),
                producto.getCantidad(),
                producto.getPrecioUnitario()
        );
    }
}



