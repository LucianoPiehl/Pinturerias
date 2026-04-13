package com.pinturerias.com.pinturerias.compartidos.entity.general;

import com.pinturerias.com.pinturerias.compartidos.entity.Producto;
import com.pinturerias.com.pinturerias.general.entity.ColorBase;
import com.pinturerias.com.pinturerias.general.entity.TamanoEnvase;
import com.pinturerias.com.pinturerias.general.entity.TipoPintura;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "producto_pintura")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoPinturaGeneral extends Producto {

    @ManyToOne
    @JoinColumn(name = "tipo_pintura_id")
    private TipoPintura tipoPintura;

    @ManyToOne
    @JoinColumn(name = "color_base_id")
    private ColorBase colorBase;

    @ManyToOne
    @JoinColumn(name = "tamano_envase_id")
    private TamanoEnvase tamanoEnv;

}