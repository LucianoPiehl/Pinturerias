package com.pinturerias.general.controlador;

import com.pinturerias.compartidos.dto.PedidoDTO;
import com.pinturerias.compartidos.enumeracion.EstadoPedido;
import com.pinturerias.general.servicio.PedidoGeneralService;
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
@RequestMapping("/api/general/pedidos")
public class PedidoGeneralController {

    private final PedidoGeneralService servicio;

    public PedidoGeneralController(PedidoGeneralService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<PedidoDTO> listar() {
        return servicio.listar();
    }

    @GetMapping("/{id}")
    public PedidoDTO obtener(@PathVariable Long id) {
        return servicio.obtener(id);
    }

    @PostMapping
    public PedidoDTO crear(@RequestBody PedidoDTO dto) {
        return servicio.crear(dto);
    }

    @PutMapping("/{id}/estado")
    public PedidoDTO actualizarEstado(@PathVariable Long id, @RequestParam EstadoPedido estado) {
        return servicio.actualizarEstado(id, estado);
    }
}



