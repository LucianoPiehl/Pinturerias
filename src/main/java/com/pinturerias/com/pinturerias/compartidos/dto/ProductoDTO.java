package com.pinturerias.com.pinturerias.compartidos.dto;

import java.util.List;

import com.pinturerias.com.pinturerias.compartidos.enumerate.Contexto;
import com.pinturerias.com.pinturerias.compartidos.enumerate.Tipo;

public abstract class ProductoDTO {
    private Long idProducto;
    private String nombre;
    private String descripcion;
    private int precioFinal;
    private String marca;
    private List<String> etiquetas; //nombre : color
    private Tipo tipo;
    private Contexto contexto;
    private int stock;
    //Id de entidades (Entero + String [Que determina a que bd pertenece])
    private String idCategoria;
    

    

    public ProductoDTO() {
        // constructor vacío necesario para herencia y frameworks
    }

    public ProductoDTO( Long idProducto, String nombre, String descripcion, int precioFinal, String marca, List<String> etiquetas, Tipo tipo, Contexto contexto) {
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
    public abstract String getTamanoEnv();
    public abstract String getTipoPintura();
    public abstract String getColor();
    
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

    public int getPrecioFinal() {
        return precioFinal;
    }
    public List<String> getEtiquetas(){
        return etiquetas;
    }

    public void setPrecioFinal(int precioFinal) {
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

    public String getIdCategoria() {
        return idCategoria;
    }
}
