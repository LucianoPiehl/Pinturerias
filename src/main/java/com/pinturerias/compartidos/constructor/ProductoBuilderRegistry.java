package com.pinturerias.compartidos.constructor;

import java.util.List;

import com.pinturerias.compartidos.dto.ProductoDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.pinturerias.compartidos.constructor.base.ProductoBuilderBase;

@Component
public class ProductoBuilderRegistry {

    private final List<ProductoBuilderBase<?>> builders;

    public ProductoBuilderRegistry(List<ProductoBuilderBase<?>> builders) {
        this.builders = builders;
    }

    public ProductoBuilderBase<?> getBuilder(ProductoDTO dto) {

        return builders.stream()
                .filter(b -> b.supports(dto))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe builder para: tipo=" + dto.getTipo() +
                                ", contexto=" + dto.getContexto() +
                                ", dto=" + dto.getClass().getSimpleName()
                ));
    }
    @PostConstruct
    public void debugBuilders() {
        System.out.println("=== BUILDERS REGISTRADOS ===");
        builders.forEach(b -> System.out.println(
                b.getClass().getSimpleName() + " | DTO=" +
                        b.getDtoClass().getSimpleName() + " | " +
                        b.getTipo() + " | " +
                        b.getContexto()
        ));
    }
}