package com.pinturerias.sucursal.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "etiqueta_sucursal",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_etiqueta_sucursal_valor", columnNames = "valor")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EtiquetaSucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String valor;
}