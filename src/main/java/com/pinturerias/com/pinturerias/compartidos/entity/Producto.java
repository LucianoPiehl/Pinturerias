package com.pinturerias.com.pinturerias.compartidos.entity;

import java.util.List;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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
    protected List<String> etiquetas;


    public void agregarEtiqueta(String etiqueta) {
        if (this.etiquetas != null) {
            this.etiquetas.add(etiqueta);
        }
    }
}