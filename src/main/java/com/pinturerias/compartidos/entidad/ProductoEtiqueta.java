package com.pinturerias.compartidos.entidad;

import com.pinturerias.compartidos.enumeracion.Contexto;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ProductoEtiqueta {

    protected Long productoId;
    protected Long etiquetaId;
    protected Contexto contexto;
}