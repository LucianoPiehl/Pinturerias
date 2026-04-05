package com.pinturerias.com.pinturerias.compartidos.director;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.pinturerias.com.pinturerias.compartidos.builder.base.ProductoBuilderBase;
import com.pinturerias.com.pinturerias.compartidos.dto.ProductoDTO;
import com.pinturerias.com.pinturerias.compartidos.entity.Producto;

@Component
public class ProductoDirector {

    private ProductoBuilderBase builder;

    private final Map<String, ProductoBuilderBase> builders = new HashMap<>();


    // Spring inyecta automáticamente TODOS los builders
    public ProductoDirector(List<ProductoBuilderBase> builderList) {
        for (ProductoBuilderBase b : builderList) {
            builders.put(b.tipo(), b);
        }
    }


    // CONSTRUIR PRODUCTO PARA BD GENERAL
    public Producto construirProducto(ProductoDTO dto) { 
        // antes se pasaba esto como parametro, lo quite, no le vi la utilidad
        // Map<String, Object> objetosResueltos

        //clave para indentificar que builder usar
        String clave = dto.getTipo() + "_" + dto.getContexto().name();

        //obtener el builder mediante la clave
        ProductoBuilderBase builder = builders.get(clave);
        //validar si existe
        if (builder == null) {
            throw new IllegalArgumentException("No existe builder para: " + clave);
        }

        builder.reset();

        // ----------- SET GENÉRICO --------------
        builder.setNombre(dto.getNombre());
        builder.setDescripcion(dto.getDescripcion());
        builder.setMarca(dto.getMarca());
        builder.setPrecioFinal(dto.getPrecioFinal());
        builder.setEtiqueta(dto.getEtiquetas());

        // ----------- Entidades Pintura --------------
    
        builder.setTamanoEnvase(dto.getTamanoEnv());
        builder.setTipoPintura(dto.getTipoPintura());
        builder.setColor(dto.getColor());

        // ----------- STOCK SOLO EN SUCURSAL --------------
        builder.setStock(dto.getStock());

        return builder.build();
    }

    
}

