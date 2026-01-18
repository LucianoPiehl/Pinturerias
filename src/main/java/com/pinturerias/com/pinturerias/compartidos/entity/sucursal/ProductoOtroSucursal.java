package com.pinturerias.com.pinturerias.compartidos.entity.sucursal;

import com.pinturerias.com.pinturerias.compartidos.entity.Producto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "producto_otro_sucursal")

public class ProductoOtroSucursal extends Producto {
    private int stock;
    public void setStock(int stock) {
        this.stock = stock;
    }
    public int getStock(){return stock;}

}