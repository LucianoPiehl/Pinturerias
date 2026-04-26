package com.pinturerias.compartidos.constructor;

import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;

public record BuilderKey(
        Class<?> dtoClass,
        Tipo tipo,
        Contexto contexto
) {}
/*
Representa UNA combinación válida de builder.
Ejemplo:
ProductoOtroDTO + OTRO + GENERAL
*/