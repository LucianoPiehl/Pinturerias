package com.pinturerias.com.pinturerias.compartidos.dto;

import com.pinturerias.com.pinturerias.general.entity.ColorBase;
import com.pinturerias.com.pinturerias.general.entity.TamanoEnvase;
import com.pinturerias.com.pinturerias.general.entity.TipoPintura;

public class ProductoOtroDTO extends ProductoDTO{
    public TamanoEnvase getTamanoEnv(){
        return new TamanoEnvase();
    }
    public TipoPintura getTipoPintura(){
        return new TipoPintura();
    }
    public ColorBase getColor(){
        return new ColorBase();
    }
    public ProductoOtroDTO() {
    }
}