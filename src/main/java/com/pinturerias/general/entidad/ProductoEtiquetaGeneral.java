package com.pinturerias.general.entidad;

import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "producto_etiqueta_general")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoEtiquetaGeneral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productoId;

    @Column(nullable = false)
    private Long etiquetaId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Contexto contexto; // siempre GENERAL

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tipo tipo;
}
