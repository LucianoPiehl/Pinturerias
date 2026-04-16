package com.pinturerias.compartidos.constructor.base;
import java.util.List;

import com.pinturerias.compartidos.entidad.Producto;
import com.pinturerias.general.entidad.ColorBase;
import com.pinturerias.general.entidad.TamanoEnvase;
import com.pinturerias.general.entidad.TipoPintura;

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



