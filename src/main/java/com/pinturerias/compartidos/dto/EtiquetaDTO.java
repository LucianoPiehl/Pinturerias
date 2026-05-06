package com.pinturerias.compartidos.dto;

import com.pinturerias.compartidos.enumeracion.Contexto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EtiquetaDTO {
    private Long id;
    private String valor;
    private Contexto contexto;
    private boolean habilitado;
}