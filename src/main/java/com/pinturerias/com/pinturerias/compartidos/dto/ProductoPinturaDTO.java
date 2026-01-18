package com.pinturerias.com.pinturerias.compartidos.dto;

public class ProductoPinturaDTO extends ProductoDTO{
    private String tipoPinturaId;
    private String colorId;
    private String tamanoEnvId;

    public ProductoPinturaDTO() {
        }

    public ProductoPinturaDTO(String tipoPinturaId, String colorId, String tamanoEnvId) {
        this.tipoPinturaId = tipoPinturaId;
        this.tamanoEnvId = tamanoEnvId;
        this.colorId = colorId;
    }
    public void setTipoPintura(String tipoPintura){
        this.tipoPinturaId = tipoPintura;
    }
    public void setColor(String color){
        this.colorId = color;
    }
    public void setTamanoEnv(String tamanoEnv){
        this.tamanoEnvId = tamanoEnv;
    }
    
    public String getTipoPintura(){
        return this.tipoPinturaId;
    }
    public String getColor(){
        return this.colorId;
    }
    public String getTamanoEnv(){
        return this.tamanoEnvId;
    }
}
