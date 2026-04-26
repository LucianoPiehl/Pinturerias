package com.pinturerias.compartidos.constructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pinturerias.compartidos.dto.ProductoDTO;
import org.springframework.stereotype.Component;
import com.pinturerias.compartidos.constructor.base.ProductoBuilderBase;

@Component
public class ProductoBuilderRegistry {

    private final Map<BuilderKey, ProductoBuilderBase<?>> registry = new HashMap<>();

    public ProductoBuilderRegistry(List<ProductoBuilderBase<?>> builders) {

        for (ProductoBuilderBase<?> builder : builders) {

            BuilderKey key = new BuilderKey(
                    builder.getDtoClass(),
                    builder.getTipo(),
                    builder.getContexto()
            );

            if (registry.containsKey(key)) {
                throw new IllegalStateException(
                        "Builder duplicado para: " + key
                );
            }

            registry.put(key, builder);
        }
    }

    public ProductoBuilderBase<?> getBuilder(ProductoDTO dto) {

        BuilderKey key = new BuilderKey(
                dto.getClass(),
                dto.getTipo(),
                dto.getContexto()
        );

        ProductoBuilderBase<?> builder = registry.get(key);

        if (builder == null) {
            throw new IllegalArgumentException(
                    "No existe builder para: " + key
            );
        }

        return builder;
    }
}