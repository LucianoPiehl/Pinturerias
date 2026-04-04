package com.pinturerias.com.pinturerias.compartidos.entity;

import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String nombre;
    protected String descripcion;
    protected String marca;
    protected int precioFinal;
    protected List<String> etiquetas;
    protected String idCategoria;

    public Long getId(){return id;}

    public List<String> getEtiquetas(){
        return etiquetas;
    }

    public void setEtiqueta(List<String> etiquetas){
        this.etiquetas = etiquetas;
    }

    public void agregarEtiqueta(String etiqueta){
        etiquetas.add(etiqueta);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(int precioFinal) {
        this.precioFinal = precioFinal;
    }

    public void setCategoria(String idCategoria){
        this.idCategoria = idCategoria;
    };

    public String getCategoria(){
        return idCategoria;
    }

}