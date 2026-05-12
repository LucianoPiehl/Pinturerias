package com.pinturerias.compartidos.constructor.base;

import com.pinturerias.compartidos.dto.producto.ProductoDTO;
import com.pinturerias.compartidos.entidad.Producto;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;

public interface ProductoBuilderBase<T extends ProductoDTO> {

    Class<T> getDtoClass();

    Tipo getTipo();

    Contexto getContexto();

    Producto build(T dto);

}
