package com.pinturerias.com.pinturerias.compartidos.builder.base;
import java.util.List;

import com.pinturerias.com.pinturerias.compartidos.entity.Producto;

// Interfaz de el builder de producto
public interface ProductoBuilderBase  {
    String tipo();
    void reset();

    void setNombre(String nombre);
    void setDescripcion(String descripcion);
    void setMarca(String marca);
    void setPrecioFinal(Double precioFinal);
    void setCategoria(String idCategoria);
    void setEtiqueta(List<String> lista);
    abstract void setTamanoEnvase(String idTamanoEnvase);
    abstract void setColor(String idColor);
    abstract void setTipoPintura(String idTipoPintura);
    default void setStock(int stock) {}
   
    Producto build();
}
