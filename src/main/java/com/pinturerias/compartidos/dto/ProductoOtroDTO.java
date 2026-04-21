package com.pinturerias.compartidos.dto;

import com.pinturerias.general.entidad.ColorBase;
import com.pinturerias.general.entidad.TamanoEnvase;
import com.pinturerias.general.entidad.TipoPintura;

public class ProductoOtroDTO extends ProductoDTO {

    @Override
    public TamanoEnvase getTamanoEnv() {
        return null;
    }

    @Override
    public TipoPintura getTipoPintura() {
        return null;
    }

    @Override
    public ColorBase getColorBase() {
        return null;
    }

    public ProductoOtroDTO() {
        super();
    }
}