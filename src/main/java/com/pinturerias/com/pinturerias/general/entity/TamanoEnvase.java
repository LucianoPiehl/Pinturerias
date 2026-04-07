package com.pinturerias.com.pinturerias.general.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tamano_envase")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TamanoEnvase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Double capacidad;
    private Double porcentajeAumento;

}