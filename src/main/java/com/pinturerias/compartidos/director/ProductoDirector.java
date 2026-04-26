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

        ProductoBuilderBase builder = registry.getBuilder(dto);

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