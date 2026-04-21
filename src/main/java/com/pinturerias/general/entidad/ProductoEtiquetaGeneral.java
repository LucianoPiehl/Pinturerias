package com.pinturerias.general.entidad;

import com.pinturerias.compartidos.enumeracion.Tipo;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "producto_etiqueta_general",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_producto_etiqueta_general",
                columnNames = {"producto_id", "tipo_producto"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoEtiquetaGeneral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "producto_id", nullable = false)
    private Long productoId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_producto", nullable = false, length = 20)
    private Tipo tipoProducto;

    @ElementCollection(fetch = jakarta.persistence.FetchType.EAGER)
    @CollectionTable(
            name = "producto_etiqueta_general_item",
            joinColumns = @JoinColumn(name = "producto_etiqueta_general_id")
    )
    @Column(name = "etiqueta_general_id", nullable = false)
    private Set<Long> etiquetasGeneralesIds = new HashSet<>();
}
