package com.pinturerias.compartidos.constructor.base;

import com.pinturerias.compartidos.entidad.Producto;
import com.pinturerias.general.entidad.ColorBase;
import com.pinturerias.general.entidad.TamanoEnvase;
import com.pinturerias.general.entidad.TipoPintura;

public interface ProductoBuilderBase {

    String tipo();

    void reset();

    void setNombre(String nombre);

    void setDescripcion(String descripcion);

    void setMarca(String marca);

    void setPrecioFinal(Double precioFinal);

    void setTamanoEnvase(TamanoEnvase tamanoEnvase);

    void setColor(ColorBase color);

    void setTipoPintura(TipoPintura tipoPintura);

    default void setStock(int stock) {}

    Producto build();
}