package com.pinturerias.compartidos.servicio;

import com.pinturerias.compartidos.entidad.shared.Pedido;
import com.pinturerias.compartidos.entidad.shared.PedidoProducto;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.excepciones.ExcepcionApi;
import org.springframework.stereotype.Service;

@Service
public class PedidoValidationService {

    public void validarPedido(Pedido pedido, boolean requiereSucursalDestino) {
        if (pedido.getNombre() == null || pedido.getNombre().isBlank()) {
            throw new ExcepcionApi(400, "El pedido debe tener un nombre");
        }
        if (pedido.getProductos() == null || pedido.getProductos().isEmpty()) {
            throw new ExcepcionApi(400, "El pedido debe tener al menos un producto");
        }
        if (requiereSucursalDestino && pedido.getSucursalDestinoId() == null) {
            throw new ExcepcionApi(400, "El pedido debe indicar sucursalDestinoId");
        }

        for (PedidoProducto producto : pedido.getProductos()) {
            if (producto.getIdProducto() == null) {
                throw new ExcepcionApi(400, "Cada item debe indicar idProducto");
            }
            if (producto.getContextoProducto() == null) {
                throw new ExcepcionApi(400, "Cada item debe indicar contextoProducto");
            }
            if (producto.getContextoProducto() == Contexto.GENERAL && producto.getTipoProducto() == null) {
                throw new ExcepcionApi(400, "Cada item GENERAL debe indicar tipoProducto");
            }
            if (producto.getCantidad() == null || producto.getCantidad() <= 0) {
                throw new ExcepcionApi(400, "Cada item debe tener una cantidad mayor a cero");
            }
            if (producto.getPrecioUnitario() == null || producto.getPrecioUnitario() < 0) {
                throw new ExcepcionApi(400, "Cada item debe tener un precioUnitario valido");
            }
        }
    }

    public void validarProductoSucursal(PedidoProducto producto) {
        if (producto.getContextoProducto() != Contexto.SUCURSAL) {
            throw new ExcepcionApi(400, "El producto debe pertenecer al contexto SUCURSAL");
        }
        if (producto.getTipoProducto() != null && producto.getTipoProducto() != Tipo.OTRO) {
            throw new ExcepcionApi(400, "Actualmente solo existen productos propios de sucursal del tipo OTRO");
        }
    }

    public void validarProductoGeneral(PedidoProducto producto) {
        if (producto.getContextoProducto() != Contexto.GENERAL) {
            throw new ExcepcionApi(400, "El producto debe pertenecer al contexto GENERAL");
        }
        if (producto.getTipoProducto() == null) {
            throw new ExcepcionApi(400, "El producto general debe indicar tipoProducto");
        }
    }
}




