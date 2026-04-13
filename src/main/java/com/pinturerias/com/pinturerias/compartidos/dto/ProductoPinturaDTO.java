package com.pinturerias.com.pinturerias.compartidos.dto;

import com.pinturerias.com.pinturerias.general.entity.ColorBase;
import com.pinturerias.com.pinturerias.general.entity.TamanoEnvase;
import com.pinturerias.com.pinturerias.general.entity.TipoPintura;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoPinturaDTO extends ProductoDTO {

    private TipoPintura tipoPintura;
    private ColorBase colorBase;
    private TamanoEnvase tamanoEnv;


}