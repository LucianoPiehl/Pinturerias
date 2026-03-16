package com.pinturerias.com.pinturerias.compartidos.dto;
public class ProductoOtroDTO extends ProductoDTO{
    public String  getTamanoEnv(){
        return "Esto rompe SOLID, hay que cambiarlo :D";
    }
    public String getTipoPintura(){
        return "Esto rompe SOLID, hay que cambiarlo :D";
    }
    public String getColor(){
        return "Esto rompe SOLID, hay que cambiarlo :D";
    }
    public ProductoOtroDTO() {
    }
}