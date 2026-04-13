package com.pinturerias.com.pinturerias.compartidos.builder.general;


import java.util.List;

import org.springframework.stereotype.Component;

import com.pinturerias.com.pinturerias.compartidos.builder.base.ProductoBuilderBase;
import com.pinturerias.com.pinturerias.compartidos.entity.general.ProductoOtroGeneral;
import com.pinturerias.com.pinturerias.general.entity.ColorBase;
import com.pinturerias.com.pinturerias.general.entity.TamanoEnvase;
import com.pinturerias.com.pinturerias.general.entity.TipoPintura;

@Component
public class ProductoOtroBuilder implements ProductoBuilderBase  {
    private ProductoOtroGeneral producto;

    public ProductoOtroBuilder() {
        reset();
    }

    @Override
    public String tipo() {
        return "OTRO_GENERAL";
    }

    @Override
    public void reset() {
        producto = new ProductoOtroGeneral();
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
    public void setPrecioFinal(Double precioFinal) {
        // Falta implementar la logica real de como se setea el precio.
        producto.setPrecioFinal(precioFinal);
    }

    @Override
    public void setEtiqueta(List<String> etiquetas){
        producto.setEtiquetas(etiquetas);
    }

    
    @Override
    public ProductoOtroGeneral build() {
        return producto;
    }


    @Override
    public void setTamanoEnvase(TamanoEnvase tamanoEnvase) {
       //ESTO ROMPE SOLID :D
    }

    @Override
    public void setColor(ColorBase colorBase) {
       //ESTO ROMPE SOLID :D
    }

    @Override
    public void setTipoPintura(TipoPintura tipoPintura) {
        //ESTO ROMPE SOLID :D
    }
}
 