package com.pinturerias.compartidos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EtiquetasProductoDTO {
    private List<Long> etiquetasGeneralesIds = new ArrayList<>();
    private List<Long> etiquetasSucursalIds = new ArrayList<>();
    private List<String> etiquetas = new ArrayList<>();
}
