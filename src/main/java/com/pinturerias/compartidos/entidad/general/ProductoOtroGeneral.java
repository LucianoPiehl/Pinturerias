package com.pinturerias.compartidos.entidad.general;

import com.pinturerias.compartidos.entidad.Producto;
import com.pinturerias.general.entidad.EtiquetaGeneral;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "producto_otro_general")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoOtroGeneral extends Producto {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "producto_otro_general_etiqueta",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "etiqueta_id")
    )
    private Set<EtiquetaGeneral> etiquetas = new HashSet<>();
}