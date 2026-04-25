package com.pinturerias.compartidos.director;

import com.pinturerias.compartidos.constructor.ProductoBuilderRegistry;
import com.pinturerias.compartidos.constructor.base.ProductoBuilderBase;
import com.pinturerias.compartidos.dto.ProductoDTO;
import com.pinturerias.compartidos.entidad.Producto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductoDirector {

    private final ProductoBuilderRegistry registry;

    public Producto construir(ProductoDTO dto) {

        if (dto == null) {
            throw new IllegalArgumentException("El DTO no puede ser null");
        }

        ProductoBuilderBase builder = registry.getBuilder(dto);

        // cast seguro porque supports ya validó
        return buildSafe(builder, dto);
    }

    @SuppressWarnings("unchecked")
    private <T extends ProductoDTO> Producto buildSafe(
            ProductoBuilderBase<T> builder,
            ProductoDTO dto
    ) {
        return builder.build((T) dto);
    }
}