package com.pinturerias.compartidos.constructor.general;

import com.pinturerias.compartidos.dto.ProductoDTO;
import com.pinturerias.compartidos.dto.ProductoPinturaDTO;
import com.pinturerias.compartidos.entidad.Producto;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import org.springframework.stereotype.Component;
import com.pinturerias.compartidos.constructor.base.ProductoBuilderBase;
import com.pinturerias.compartidos.entidad.general.ProductoPinturaGeneral;
import com.pinturerias.general.entidad.ColorBase;
import com.pinturerias.general.entidad.TamanoEnvase;
import com.pinturerias.general.entidad.TipoPintura;

@Component
public class ProductoPinturaBuilder implements ProductoBuilderBase<ProductoPinturaDTO> {

    @Override
    public boolean supports(ProductoDTO dto) {
        boolean dtoMatch = getDtoClass().isAssignableFrom(dto.getClass());
        boolean tipoMatch = getTipo() == dto.getTipo();
        boolean contextoMatch = getContexto() == dto.getContexto();

        boolean result = dtoMatch && tipoMatch && contextoMatch;

        return result;
    }

    @Override
    public Class<ProductoPinturaDTO> getDtoClass() {
        return ProductoPinturaDTO.class;
    }

    @Override
    public Tipo getTipo() {
        return Tipo.PINTURA;
    }

    @Override
    public Contexto getContexto() {
        return Contexto.GENERAL;
    }

    @Override
    public Producto build(ProductoPinturaDTO dto) {

        if (dto == null) {
            throw new IllegalArgumentException("El DTO no puede ser null");
        }

        ProductoPinturaGeneral producto = new ProductoPinturaGeneral();

        // comunes
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setMarca(dto.getMarca());
        producto.setPrecioFinal(dto.getPrecioFinal());

        // específicos
        producto.setTamanoEnv(dto.getTamanoEnv());
        producto.setTipoPintura(dto.getTipoPintura());
        producto.setColorBase(dto.getColorBase());


        return producto;
    }

}