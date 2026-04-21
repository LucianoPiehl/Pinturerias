package com.pinturerias.compartidos.dto;

import com.pinturerias.compartidos.enumeracion.Contexto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EtiquetaDTO {
    private Long id;
    private String valor;
    private Contexto contexto;
}