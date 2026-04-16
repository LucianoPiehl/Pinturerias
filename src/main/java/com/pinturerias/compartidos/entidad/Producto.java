package com.pinturerias.compartidos.entidad;

import com.pinturerias.compartidos.persistencia.StringListJsonConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String nombre;
    protected String descripcion;
    protected String marca;
    protected Double precioFinal;

    @Convert(converter = StringListJsonConverter.class)
    @Column(columnDefinition = "TEXT")
    protected List<String> etiquetas;


    public void agregarEtiqueta(String etiqueta) {
        if (this.etiquetas != null) {
            this.etiquetas.add(etiqueta);
        }
    }

    public Double getPrecioBase() {
        return this.precioFinal;
    }

    public void setPrecioBase(Double precioBase) {
        this.precioFinal = precioBase;
    }
}




