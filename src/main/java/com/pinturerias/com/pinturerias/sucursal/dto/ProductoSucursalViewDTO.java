package com.pinturerias.com.pinturerias.sucursal.dto;

public class ProductoSucursalViewDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String marca;

    private Double precio;
    private Integer stock;

    private String origen; // GENERAL, GENERAL_OVERRIDE, SUCURSAL

    public ProductoSucursalViewDTO(Long id, String nombre, String descripcion,
                                   String marca, Double precio, Integer stock, String origen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.marca = marca;
        this.precio = precio;
        this.stock = stock;
        this.origen = origen;
    }
    }