package com.pinturerias.com.pinturerias.compartidos.dto;

import com.pinturerias.com.pinturerias.general.entity.ColorBase;
import com.pinturerias.com.pinturerias.general.entity.TamanoEnvase;
import com.pinturerias.com.pinturerias.general.entity.TipoPintura;

public class ProductoPinturaDTO extends ProductoDTO{
    private TipoPintura tipoPintura;
    private ColorBase colorBase;
    private TamanoEnvase tamanoEnv;

    public ProductoPinturaDTO() {
        }

    public ProductoPinturaDTO(TipoPintura tipoPintura, ColorBase colorBase, TamanoEnvase tamanoEnv) {
        this.tipoPintura = tipoPintura;
        this.tamanoEnv = tamanoEnv;
        this.colorBase = colorBase;
    }
    public void setTipoPintura(TipoPintura tipoPintura){
        this.tipoPintura = tipoPintura;
    }
    public void setColor(ColorBase color){
        this.colorBase = color;
    }
    public void setTamanoEnv(TamanoEnvase tamanoEnv){
        this.tamanoEnv = tamanoEnv;
    }
    
    public TipoPintura getTipoPintura(){
        return this.tipoPintura;
    }
    public ColorBase getColor(){
        return this.colorBase;
    }
    public TamanoEnvase getTamanoEnv(){
        return this.tamanoEnv;
    }

}
