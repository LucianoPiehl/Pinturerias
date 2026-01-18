package com.pinturerias.com.pinturerias.compartidos.entity.sucursal;

import com.pinturerias.com.pinturerias.compartidos.entity.Producto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "producto_pintura_sucursal")
public class ProductoPinturaSucursal extends Producto{
    private int stock;
    protected String tipoPintura;
    protected String color;
    protected String tamEnv;

    public void setStock(int stock) {
        this.stock = stock;
    }
    public int getStock(){
        return stock;
    }
    public String getTipoPintura(){
        return this.tipoPintura;
    }
    public String getColor(){
        return this.color;
    }  
    public String getTamEnv(){
        return this.tamEnv;
    }
    public void setTipoPintura(String tipo) {
        this.tipoPintura = tipo;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public void setTamEnv(String tamEnv){
        this.tamEnv = tamEnv;
    }

}
