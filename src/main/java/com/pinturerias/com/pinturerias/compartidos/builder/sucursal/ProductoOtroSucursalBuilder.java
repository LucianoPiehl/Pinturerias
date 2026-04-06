package com.pinturerias.com.pinturerias.compartidos.builder.sucursal;

import java.util.List;

import org.springframework.stereotype.Component;

import com.pinturerias.com.pinturerias.compartidos.builder.base.ProductoBuilderBase;
import com.pinturerias.com.pinturerias.compartidos.entity.sucursal.ProductoOtroSucursal;
import com.pinturerias.com.pinturerias.general.entity.ColorBase;
import com.pinturerias.com.pinturerias.general.entity.TamanoEnvase;
import com.pinturerias.com.pinturerias.general.entity.TipoPintura;

@Component
public class ProductoOtroSucursalBuilder implements ProductoBuilderBase {

    private ProductoOtroSucursal producto;

    @Override
    public String tipo() {
        return "OTRO_SUCURSAL";
    }

    public ProductoOtroSucursalBuilder() {
        reset();
    }

    @Override
    public void reset() {
        producto = new ProductoOtroSucursal();
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
        producto.setPrecioFinal(precioFinal);
    }
    
    @Override
    public void setEtiqueta(List<String> etiqueta){
        producto.setEtiqueta(etiqueta);
    }


    @Override
    public void setStock(int stock) {
        producto.setStock(stock);
    }


    @Override
    public ProductoOtroSucursal build() {
        return producto;
    }


    @Override
    public void setTamanoEnvase(TamanoEnvase tamanoEnvase) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setColor(ColorBase color) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setTipoPintura(TipoPintura tipoPintura) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
