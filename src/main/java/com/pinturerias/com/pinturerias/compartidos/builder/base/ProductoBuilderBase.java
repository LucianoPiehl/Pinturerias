package com.pinturerias.com.pinturerias.compartidos.builder.base;
import java.util.List;

import com.pinturerias.com.pinturerias.compartidos.entity.Producto;
import com.pinturerias.com.pinturerias.general.entity.ColorBase;
import com.pinturerias.com.pinturerias.general.entity.TamanoEnvase;
import com.pinturerias.com.pinturerias.general.entity.TipoPintura;

// Interfaz de el builder de producto
public interface ProductoBuilderBase  {
    String tipo();
    void reset();

    void setNombre(String nombre);
    void setDescripcion(String descripcion);
    void setMarca(String marca);
    void setPrecioFinal(Double precioFinal);
    void setEtiqueta(List<String> lista);
    abstract void setTamanoEnvase(TamanoEnvase TamanoEnvase);
    abstract void setColor(ColorBase Color);
    abstract void setTipoPintura(TipoPintura TipoPintura);
    default void setStock(int stock) {}
   
    Producto build();
}
