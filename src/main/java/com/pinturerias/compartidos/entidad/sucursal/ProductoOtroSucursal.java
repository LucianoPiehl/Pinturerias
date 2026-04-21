package com.pinturerias.compartidos.entidad.sucursal;

import com.pinturerias.compartidos.entidad.Producto;
import com.pinturerias.sucursal.entidad.EtiquetaSucursal;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "producto_otro_sucursal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoOtroSucursal extends Producto {

    private int stock;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "producto_otro_sucursal_etiqueta_sucursal",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "etiqueta_id")
    )
    private Set<EtiquetaSucursal> etiquetasSucursal = new HashSet<>();

    @ElementCollection
    @CollectionTable(
            name = "producto_otro_sucursal_etiqueta_general",
            joinColumns = @JoinColumn(name = "producto_id")
    )
    @Column(name = "etiqueta_general_id", nullable = false)
    private Set<Long> etiquetasGeneralesIds = new HashSet<>();
}