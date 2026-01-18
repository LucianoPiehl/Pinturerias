package com.pinturerias.com.pinturerias.sucursal.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Producto_precio_stock")
@Data
public class ProductoPrecioStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productoId;
    private int stock;
    private Double precio;
    // constructor vacío
    public ProductoPrecioStock() {}

    public ProductoPrecioStock(Long productoId, Double precio, Integer stock) {
        this.productoId = productoId;
        this.precio = precio;
        this.stock = stock;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getPrecio(){return precio;}

    public Long getProductoId(){return productoId;}

    public int getStock() {
        return stock;
    }
}
