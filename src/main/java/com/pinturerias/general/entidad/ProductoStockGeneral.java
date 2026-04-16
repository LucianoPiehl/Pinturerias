package com.pinturerias.general.entidad;

import com.pinturerias.compartidos.enumeracion.Tipo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "producto_stock_general")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoStockGeneral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productoId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Tipo tipoProducto;

    @Column(nullable = false)
    private Integer stock;

    public ProductoStockGeneral(Long productoId, Tipo tipoProducto, Integer stock) {
        this.productoId = productoId;
        this.tipoProducto = tipoProducto;
        this.stock = stock != null ? stock : 0;
    }
}




