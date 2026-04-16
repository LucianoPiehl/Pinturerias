
package com.pinturerias.compartidos.constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.pinturerias.compartidos.constructor.base.ProductoBuilderBase;

@Component
public class ProductoBuilderRegistry {

    private final Map<String, ProductoBuilderBase> builders = new HashMap<>();

    public ProductoBuilderRegistry(List<ProductoBuilderBase> buildersList) {
        for (ProductoBuilderBase b : buildersList) {
            builders.put(b.tipo(), b);
        }
    }

    public ProductoBuilderBase getBuilder(String tipo, String contexto) {
        String key = tipo + "_" + contexto;
        return builders.get(key);
    }
}



