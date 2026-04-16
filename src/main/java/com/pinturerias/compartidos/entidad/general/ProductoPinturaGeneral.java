package com.pinturerias.compartidos.entidad.general;

import com.pinturerias.compartidos.entidad.Producto;
import com.pinturerias.general.entidad.ColorBase;
import com.pinturerias.general.entidad.TamanoEnvase;
import com.pinturerias.general.entidad.TipoPintura;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}




