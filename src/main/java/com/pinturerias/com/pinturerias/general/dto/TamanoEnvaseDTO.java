package com.pinturerias.com.pinturerias.general.dto;

import lombok.Data;

@Data
public class TamanoEnvaseDTO {
    private Long id;
    private String nombre;
    private Double capacidad;
    private Double porcentajeAumento;
}