package com.pinturerias.com.pinturerias.general.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "sucursal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String codigo; // Ej: "suc-001"

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 300)
    private String jdbcUrl;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    private boolean habilitada = true;

    /**
     * NOTA SOBRE BOOLEANS:
     * Lombok para el campo 'habilitada' generará:
     * - setHabilitada(boolean b)
     * - isHabilitada() <--- Es el estándar de Java para booleans (en lugar de getHabilitada)
     * * Si en tu código usabas .getHabilitada(), podés cambiarlo a .isHabilitada()
     * o dejar el método manual aquí abajo si no querés cambiar el resto del código.
     */
}