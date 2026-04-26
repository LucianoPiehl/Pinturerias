package com.pinturerias.compartidos.constructor.sucursal;

import com.pinturerias.compartidos.constructor.base.ProductoBuilderBase;
import com.pinturerias.compartidos.dto.ProductoDTO;
import com.pinturerias.compartidos.dto.ProductoOtroDTO;
import com.pinturerias.compartidos.dto.ProductoPinturaDTO;
import com.pinturerias.compartidos.entidad.Producto;
import com.pinturerias.compartidos.entidad.sucursal.ProductoOtroSucursal;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import org.springframework.stereotype.Component;

@Component
public class ProductoOtroSucursalBuilder implements ProductoBuilderBase<ProductoOtroDTO> {

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
        return Contexto.SUCURSAL;
    }

    @Override
    public Producto build(ProductoOtroDTO dto) {

        if (dto == null) {
            throw new IllegalArgumentException("El DTO no puede ser null");
        }

        ProductoOtroSucursal producto = new ProductoOtroSucursal();

        // comunes
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setMarca(dto.getMarca());
        producto.setPrecioFinal(dto.getPrecioFinal());
        producto.setStock(dto.getStock());

        return producto;
    }
}