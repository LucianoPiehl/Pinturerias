package com.pinturerias.compartidos.entidad.shared;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "etiqueta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Etiqueta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String valor;

    @Column(nullable = false, unique = true)
    private String claveNormalizada;
}