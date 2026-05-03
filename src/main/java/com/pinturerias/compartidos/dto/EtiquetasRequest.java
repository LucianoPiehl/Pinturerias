package com.pinturerias.compartidos.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EtiquetasRequest {

    private List<Long> generales;
    private List<Long> locales;
}
