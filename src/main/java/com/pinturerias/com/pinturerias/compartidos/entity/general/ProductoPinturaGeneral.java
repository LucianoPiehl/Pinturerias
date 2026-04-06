package com.pinturerias.com.pinturerias.compartidos.entity.general;
import com.pinturerias.com.pinturerias.compartidos.entity.Producto;
import com.pinturerias.com.pinturerias.general.entity.ColorBase;
import com.pinturerias.com.pinturerias.general.entity.TamanoEnvase;
import com.pinturerias.com.pinturerias.general.entity.TipoPintura;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "producto_pintura")
public class ProductoPinturaGeneral extends Producto {

    @ManyToOne
    @JoinColumn(name = "tipo_pintura_id")
    private TipoPintura tipoPintura;

    @ManyToOne
    @JoinColumn(name = "color_base_id")
    private ColorBase color;

    @ManyToOne
    @JoinColumn(name = "tamano_envase_id")
    private TamanoEnvase tamEnv;

    // getters y setters

    public void setTipoPintura(TipoPintura tipo) {
        this.tipoPintura = tipo;
    }
    public void setColor(ColorBase color) {
        this.color = color;
    }
    public void setTamEnv(TamanoEnvase tamEnv){
        this.tamEnv = tamEnv;
    }

    public ColorBase getColor() {
        return  color;
    }

    public TamanoEnvase getTamanoEnv() {
        return tamEnv;
    }

    public TipoPintura getTipoPintura() {
        return tipoPintura;
    }
}
