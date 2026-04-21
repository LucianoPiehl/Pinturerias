package com.pinturerias.compartidos.entidad.general;

import com.pinturerias.compartidos.entidad.Producto;
import com.pinturerias.general.entidad.ColorBase;
import com.pinturerias.general.entidad.EtiquetaGeneral;
import com.pinturerias.general.entidad.TamanoEnvase;
import com.pinturerias.general.entidad.TipoPintura;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "producto_pintura_general")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoPinturaGeneral extends Producto {

    @ManyToOne
    @JoinColumn(name = "tipo_pintura_id")
    private TipoPintura tipoPintura;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private ColorBase colorBase;

    @ManyToOne
    @JoinColumn(name = "tam_env_id")
    private TamanoEnvase tamanoEnv;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "producto_pintura_general_etiqueta",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "etiqueta_id")
    )
    private Set<EtiquetaGeneral> etiquetas = new HashSet<>();
}