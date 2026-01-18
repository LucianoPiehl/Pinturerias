package com.pinturerias.com.pinturerias.compartidos.builder.general;


import java.util.List;

import org.springframework.stereotype.Component;

import com.pinturerias.com.pinturerias.compartidos.builder.base.ProductoBuilderBase;
import com.pinturerias.com.pinturerias.compartidos.entity.general.ProductoOtroGeneral;

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
    public void setPrecioFinal(int precioFinal) {
        // Falta implementar la logica real de como se setea el precio.
        producto.setPrecioFinal(precioFinal);
    }

    @Override
    public void setEtiqueta(List<String> etiquetas){
        producto.setEtiqueta(etiquetas);
    }

    
    @Override
    public ProductoOtroGeneral build() {
        return producto;
    }

    @Override
    public void setCategoria(String idCategoria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setTamanoEnvase(String idTamanoEnvase) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setColor(String idColor) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setTipoPintura(String idTipoPintura) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
 