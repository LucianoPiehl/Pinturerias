package com.pinturerias.compartidos.dto;

import java.util.List;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
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
public abstract class ProductoDTO {

    private Long idProducto;
    private String nombre;
    private String descripcion;
    private Double precioFinal;
    private String marca;
    private List<String> etiquetas; //nombre : color
    private Tipo tipo;
    private Contexto contexto;
    private int stock;

    public abstract TamanoEnvase getTamanoEnv();
    public abstract TipoPintura getTipoPintura();
    public abstract ColorBase getColorBase();

}


