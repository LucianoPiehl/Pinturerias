package com.pinturerias.compartidos.dto;

import com.pinturerias.general.entidad.ColorBase;
import com.pinturerias.general.entidad.TamanoEnvase;
import com.pinturerias.general.entidad.TipoPintura;
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