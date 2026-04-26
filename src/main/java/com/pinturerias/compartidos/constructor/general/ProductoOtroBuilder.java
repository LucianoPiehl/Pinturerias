package com.pinturerias.compartidos.constructor.general;

import com.pinturerias.compartidos.dto.ProductoDTO;
import com.pinturerias.compartidos.dto.ProductoOtroDTO;
import com.pinturerias.compartidos.dto.ProductoPinturaDTO;
import com.pinturerias.compartidos.entidad.Producto;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import org.springframework.stereotype.Component;
import com.pinturerias.compartidos.constructor.base.ProductoBuilderBase;
import com.pinturerias.compartidos.entidad.general.ProductoOtroGeneral;

@Component
public class ProductoOtroBuilder implements ProductoBuilderBase<ProductoOtroDTO> {
    @Override
    public boolean supports(ProductoDTO dto) {
        boolean dtoMatch = getDtoClass().isAssignableFrom(dto.getClass());
        boolean tipoMatch = getTipo() == dto.getTipo();
        boolean contextoMatch = getContexto() == dto.getContexto();

        boolean result = dtoMatch && tipoMatch && contextoMatch;

        return result;
    }


    @Override
    public Class<ProductoOtroDTO> getDtoClass() {
        return ProductoOtroDTO.class;
    }

    @Override
    public Tipo getTipo() {
        return Tipo.OTRO;
    }

    @Override
    public Contexto getContexto() {
        return Contexto.GENERAL;
    }

    @Override
    public Producto build(ProductoOtroDTO dto) {

        if (dto == null) {
            throw new IllegalArgumentException("El DTO no puede ser null");
        }

        ProductoOtroGeneral producto = new ProductoOtroGeneral();

        // comunes
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setMarca(dto.getMarca());
        producto.setPrecioFinal(dto.getPrecioFinal());


        return producto;
    }
}