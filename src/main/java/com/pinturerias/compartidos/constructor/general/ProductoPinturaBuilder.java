package com.pinturerias.compartidos.constructor.general;

import java.util.List;

import org.springframework.stereotype.Component;

import com.pinturerias.compartidos.constructor.base.ProductoBuilderBase;
import com.pinturerias.compartidos.entidad.general.ProductoPinturaGeneral;
import com.pinturerias.general.entidad.ColorBase;
import com.pinturerias.general.entidad.TamanoEnvase;
import com.pinturerias.general.entidad.TipoPintura;


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
        // El precio de pintura se calcula desde sus atributos relacionados.
    }

    @Override
    public void setTamanoEnvase(TamanoEnvase tamanoEnvase) {

        producto.setTamanoEnv(tamanoEnvase);
    }

    @Override
    public void setTipoPintura(TipoPintura tipoPintura) {
        producto.setTipoPintura(tipoPintura);
    }

    @Override
    public void setColor(ColorBase color) {

        producto.setColorBase(color);
    }

    @Override
    public void setEtiqueta(List<String> etiquetas) {
        producto.setEtiquetas(etiquetas);
    }

    @Override
    public ProductoPinturaGeneral build() {
        return producto;
    }
}



