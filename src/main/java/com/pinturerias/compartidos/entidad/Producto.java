package com.pinturerias.compartidos.entidad;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String nombre;
    protected String descripcion;
    protected String marca;
    protected Double precioFinal;

    public Double getPrecioBase() {
        return this.precioFinal;
    }

    public void setPrecioBase(Double precioBase) {
        this.precioFinal = precioBase;
    }


}