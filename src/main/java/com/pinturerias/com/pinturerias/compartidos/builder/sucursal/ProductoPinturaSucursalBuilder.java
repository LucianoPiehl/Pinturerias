package com.pinturerias.com.pinturerias.compartidos.builder.sucursal;

import java.util.List;

import org.springframework.stereotype.Component;

import com.pinturerias.com.pinturerias.compartidos.builder.base.ProductoBuilderBase;
import com.pinturerias.com.pinturerias.compartidos.entity.sucursal.ProductoPinturaSucursal;

@Component
public class ProductoPinturaSucursalBuilder implements ProductoBuilderBase {
    private ProductoPinturaSucursal producto;

    @Override
    public String tipo() {
        return "PINTURA_SUCURSAL";
    }

    public ProductoPinturaSucursalBuilder() {
        reset();
    }

    @Override
    public void reset() {
        producto = new ProductoPinturaSucursal();
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
        // ejemplo: pintura sucursal = recargo 10% + manejo sucursal 5%
        Double precioFinal =  precioBase * 1.15;
        producto.setPrecioFinal(precioFinal);
    }

    @Override
    public void setEtiqueta(List<String> etiqueta){
        producto.setEtiqueta(etiqueta);
    }

    @Override
    public void setTamanoEnvase(String tamanoEnvase) {
        producto.setTamEnv(tamanoEnvase);
    }


    @Override
    public void setTipoPintura(String tipoPintura) {
        producto.setTipoPintura(tipoPintura);
    }


    @Override
    public void setColor(String color) {
        producto.setColor(color);
    }

    @Override
    public void setStock(int stock) {
        producto.setStock(stock);
    }

    @Override
    public ProductoPinturaSucursal build() {
        return producto;
    }

    @Override
    public void setCategoria(String idCategoria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
