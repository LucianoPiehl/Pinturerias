package com.pinturerias.sucursal.entidad;

import com.pinturerias.compartidos.enumeracion.Tipo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "producto_precio_stock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoPrecioStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "producto_id")
    private Long productoId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_producto", nullable = false, length = 20)
    private Tipo tipoProducto;

    private int stock;

    @Column(name = "porcentaje_ajuste", nullable = false)
    private Double porcentajeAjuste;

    @Transient
    private Double precioRecomendadoGeneral;

    @Transient
    private Double precioFinalSucursal;

    public ProductoPrecioStock(Long productoGeneralId, Tipo tipoProducto, Double porcentajeAjuste, Integer stock) {
        this.productoId = productoGeneralId;
        this.tipoProducto = tipoProducto;
        this.porcentajeAjuste = porcentajeAjuste != null ? porcentajeAjuste : 0D;
        this.stock = stock != null ? stock : 0;
    }
}




