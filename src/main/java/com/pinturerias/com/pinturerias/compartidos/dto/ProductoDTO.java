package com.pinturerias.com.pinturerias.compartidos.dto;

import java.util.List;

import com.pinturerias.com.pinturerias.compartidos.enumerate.Contexto;
import com.pinturerias.com.pinturerias.compartidos.enumerate.Tipo;
import com.pinturerias.com.pinturerias.general.entity.ColorBase;
import com.pinturerias.com.pinturerias.general.entity.TamanoEnvase;
import com.pinturerias.com.pinturerias.general.entity.TipoPintura;

public abstract class ProductoDTO {
    private Long idProducto;
    private String nombre;
    private String descripcion;
    private Double precioFinal;
    private String marca;
    private List<String> etiquetas; //nombre : color
    private Tipo tipo;
    private Contexto contexto;
    private int stock;

    

    

    public ProductoDTO() {
        // constructor vacío necesario para herencia y frameworks
    }

    public ProductoDTO( Long idProducto, String nombre, String descripcion, Double precioFinal, String marca, List<String> etiquetas, Tipo tipo, Contexto contexto) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioFinal = precioFinal;
        this.marca = marca;
        this.etiquetas = etiquetas;
        this.tipo = tipo;
        this.contexto = contexto;
    }
//#########################
    public void setIdProducto(Long id){
        this.idProducto = id;
    }

    public Long getId(){
        return this.idProducto;
    }
    public abstract TamanoEnvase getTamanoEnv();
    public abstract TipoPintura getTipoPintura();
    public abstract ColorBase getColor();
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecioFinal() {
        return precioFinal;
    }
    public List<String> getEtiquetas(){
        return etiquetas;
    }

    public void setPrecioFinal(Double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public int getStock() {
        return stock;
    }

    public Contexto getContexto() {
        return contexto;
    }

    public void setContexto(Contexto contexto) {
        this.contexto = contexto;
    }

    public void setEtiqueta(List<String> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
