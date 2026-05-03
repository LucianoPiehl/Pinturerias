package com.pinturerias.compartidos.dto;

import com.pinturerias.compartidos.enumeracion.Contexto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EtiquetaRefDTO {
    private Long id;
    private Contexto contexto;

}
