package com.pinturerias.compartidos.dto;

import com.pinturerias.general.entidad.ColorBase;
import com.pinturerias.general.entidad.TamanoEnvase;
import com.pinturerias.general.entidad.TipoPintura;

public class ProductoOtroDTO extends ProductoDTO{
    public TamanoEnvase getTamanoEnv(){
        return new TamanoEnvase();
    }
    public TipoPintura getTipoPintura(){
        return new TipoPintura();
    }
    public ColorBase getColorBase(){
        return new ColorBase();
    }
    public ProductoOtroDTO() {
    }
}


