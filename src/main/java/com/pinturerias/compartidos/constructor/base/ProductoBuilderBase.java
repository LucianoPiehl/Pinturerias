package com.pinturerias.compartidos.constructor.base;

import com.pinturerias.compartidos.dto.ProductoDTO;
import com.pinturerias.compartidos.entidad.Producto;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.general.entidad.ColorBase;
import com.pinturerias.general.entidad.TamanoEnvase;
import com.pinturerias.general.entidad.TipoPintura;

public interface ProductoBuilderBase<T extends ProductoDTO> {

    Class<T> getDtoClass();

    Tipo getTipo();

    Contexto getContexto();

    Producto build(T dto);

    default boolean supports(ProductoDTO dto) {
        return getDtoClass().isInstance(dto)
                && getTipo() == dto.getTipo()
                && getContexto() == dto.getContexto();
    }
}
