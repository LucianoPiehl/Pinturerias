package com.pinturerias.sucursal.controlador;

import com.pinturerias.compartidos.dto.PedidoDTO;
import com.pinturerias.compartidos.enumeracion.EstadoPedido;
import com.pinturerias.sucursal.servicio.PedidoSucursalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sucursal/{sucursalId}/pedidos")
public class PedidoSucursalController {

    private final PedidoSucursalService servicio;

    public PedidoSucursalController(PedidoSucursalService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<PedidoDTO> listar(@PathVariable Long sucursalId) {
        return servicio.listar();
    }

    @GetMapping("/{id}")
    public PedidoDTO obtener(@PathVariable Long sucursalId, @PathVariable Long id) {
        return servicio.obtener(id);
    }

    @PostMapping
    public PedidoDTO crear(@PathVariable Long sucursalId, @RequestBody PedidoDTO dto) {
        return servicio.crear(dto);
    }

    @PutMapping("/{id}/estado")
    public PedidoDTO actualizarEstado(
            @PathVariable Long sucursalId,
            @PathVariable Long id,
            @RequestParam EstadoPedido estado
    ) {
        return servicio.actualizarEstado(id, estado);
    }
}



