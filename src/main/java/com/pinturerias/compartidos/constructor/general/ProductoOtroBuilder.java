package com.pinturerias.compartidos.constructor.general;


import java.util.List;

import org.springframework.stereotype.Component;

import com.pinturerias.compartidos.constructor.base.ProductoBuilderBase;
import com.pinturerias.compartidos.entidad.general.ProductoOtroGeneral;
import com.pinturerias.general.entidad.ColorBase;
import com.pinturerias.general.entidad.TamanoEnvase;
import com.pinturerias.general.entidad.TipoPintura;

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
    public void setPrecioFinal(Double precioBase) {
        producto.setPrecioBase(precioBase);
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
 


