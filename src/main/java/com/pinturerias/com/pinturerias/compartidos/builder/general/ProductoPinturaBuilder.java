package com.pinturerias.com.pinturerias.compartidos.builder.general;

import java.util.List;

import org.springframework.stereotype.Component;

import com.pinturerias.com.pinturerias.compartidos.builder.base.ProductoBuilderBase;
import com.pinturerias.com.pinturerias.compartidos.entity.general.ProductoPinturaGeneral;
import com.pinturerias.com.pinturerias.general.entity.ColorBase;
import com.pinturerias.com.pinturerias.general.entity.TamanoEnvase;
import com.pinturerias.com.pinturerias.general.entity.TipoPintura;


@Component
public class ProductoPinturaBuilder implements ProductoBuilderBase {
    private ProductoPinturaGeneral producto;

    @Override
    public String tipo() {
        return "PINTURA_GENERAL";
    }

    public ProductoPinturaBuilder() {
        reset();
    }

    @Override
    public void reset() {
        producto = new ProductoPinturaGeneral();
    }

    @Override
    public void setNombre(String nombre) {
        producto.setNombre(nombre);
    }

    @Override
    public void setDescripcion(String descripcion) {
        producto.setDescripcion(descripcion);
    }

    @Override
    public void setMarca(String marca) {
        producto.setMarca(marca);
    }

    @Override
    public void setPrecioFinal(Double precioBase) {
        // ejemplo: pinturas tienen recargo 10%
        Double precioFinal =  precioBase * 1.10;
        producto.setPrecioFinal(precioFinal);
    }


    @Override
    public void setTamanoEnvase(TamanoEnvase tamanoEnvase) {
        producto.setTamEnv(tamanoEnvase);
    }


    @Override
    public void setTipoPintura(TipoPintura tipoPintura) {
        producto.setTipoPintura(tipoPintura);
    }


    @Override
    public void setColor(ColorBase color) {
        producto.setColor(color);
    }

    @Override
    public void setEtiqueta(List<String> etiquetas){
        producto.setEtiqueta(etiquetas);
    }

    @Override
    public ProductoPinturaGeneral build() {
        return producto;
    }



    

    
}
