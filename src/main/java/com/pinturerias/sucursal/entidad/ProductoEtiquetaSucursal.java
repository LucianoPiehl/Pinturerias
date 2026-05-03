package com.pinturerias.sucursal.entidad;

import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "producto_etiqueta_sucursal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoEtiquetaSucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productoId;

    @Column(nullable = false)
    private Long etiquetaId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Contexto contexto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tipo tipo;
}

