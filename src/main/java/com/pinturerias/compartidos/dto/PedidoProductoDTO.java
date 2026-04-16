package com.pinturerias.compartidos.dto;

import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoProductoDTO {
    private Long idProducto;
    private Contexto contextoProducto;
    private Tipo tipoProducto;
    private Integer cantidad;
    private Double precioUnitario;
}



