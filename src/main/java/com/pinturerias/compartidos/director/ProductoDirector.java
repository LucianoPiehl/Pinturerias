package com.pinturerias.compartidos.director;

import com.pinturerias.compartidos.constructor.ProductoBuilderRegistry;
import com.pinturerias.compartidos.constructor.base.ProductoBuilderBase;
import com.pinturerias.compartidos.dto.ProductoDTO;
import com.pinturerias.compartidos.entidad.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoDirector {

    private final ProductoBuilderRegistry registroConstructores;

    public ProductoDirector(ProductoBuilderRegistry registroConstructores) {
        this.registroConstructores = registroConstructores;
    }

    public Producto construirProducto(ProductoDTO dto) {
        ProductoBuilderBase constructor = registroConstructores.getBuilder(
                dto.getTipo().name(),
                dto.getContexto().name()
        );

        if (constructor == null) {
            throw new IllegalArgumentException(
                    "No existe constructor para tipo " + dto.getTipo() + " y contexto " + dto.getContexto()
            );
        }

        constructor.reset();
        constructor.setNombre(dto.getNombre());
        constructor.setDescripcion(dto.getDescripcion());
        constructor.setMarca(dto.getMarca());
        constructor.setPrecioFinal(dto.getPrecioFinal());
        constructor.setEtiqueta(dto.getEtiquetas());
        constructor.setTamanoEnvase(dto.getTamanoEnv());
        constructor.setTipoPintura(dto.getTipoPintura());
        constructor.setColor(dto.getColorBase());
        constructor.setStock(dto.getStock());

        return constructor.build();
    }
}

