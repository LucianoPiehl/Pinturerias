package com.pinturerias.com.pinturerias.compartidos.builder.sucursal;

import java.util.List;

import org.springframework.stereotype.Component;

import com.pinturerias.com.pinturerias.compartidos.builder.base.ProductoBuilderBase;
import com.pinturerias.com.pinturerias.compartidos.entity.sucursal.ProductoOtroSucursal;

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
    public void setPrecioFinal(int precioFinal) {
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
