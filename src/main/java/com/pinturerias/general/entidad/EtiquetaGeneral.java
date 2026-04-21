package com.pinturerias.general.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "etiqueta_general",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_etiqueta_general_valor", columnNames = "valor")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EtiquetaGeneral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String valor;

    @Column(name = "clave_normalizada", nullable = false, length = 120, unique = true)
    private String claveNormalizada;
}
