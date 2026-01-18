package com.pinturerias.com.pinturerias.compartidos.entity.general;
import com.pinturerias.com.pinturerias.compartidos.entity.Producto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "producto_pintura")
public class ProductoPinturaGeneral extends Producto {
    protected String tipoPintura;
    protected String color;
    protected String tamEnv;
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
