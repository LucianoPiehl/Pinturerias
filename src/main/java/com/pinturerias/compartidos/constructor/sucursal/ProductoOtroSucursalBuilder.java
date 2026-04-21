package com.pinturerias.compartidos.constructor.sucursal;

import com.pinturerias.compartidos.constructor.base.ProductoBuilderBase;
import com.pinturerias.compartidos.entidad.sucursal.ProductoOtroSucursal;
import com.pinturerias.general.entidad.ColorBase;
import com.pinturerias.general.entidad.TamanoEnvase;
import com.pinturerias.general.entidad.TipoPintura;
import org.springframework.stereotype.Component;

@Component
public class ProductoOtroSucursalBuilder implements ProductoBuilderBase {

    private ProductoOtroSucursal producto;

    public ProductoOtroSucursalBuilder() {
        reset();
    }

    @Override
    public String tipo() {
        return "OTRO_SUCURSAL";
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
    public void setTamanoEnvase(TamanoEnvase tamanoEnvase) {
    }

    @Override
    public void setColor(ColorBase color) {
    }

    @Override
    public void setTipoPintura(TipoPintura tipoPintura) {
    }

    @Override
    public void setStock(int stock) {
        producto.setStock(stock);
    }

    @Override
    public ProductoOtroSucursal build() {
        return producto;
    }
}