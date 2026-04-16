package com.pinturerias.compartidos.dto;

import com.pinturerias.compartidos.enumeracion.EstadoPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private Long id;
    private String mail;
    private String telefono;
    private String nombre;
    private EstadoPedido estado;
    private LocalDate fecha;
    private LocalTime hora;
    private String nombreCliente;
    private String nombreVendedor;
    private String medioPago;
    private Double descuento;
    private String observaciones;
    private Long sucursalDestinoId;
    private List<PedidoProductoDTO> productos;
}



