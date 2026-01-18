package com.pinturerias.com.pinturerias.compartidos.entity;

import com.pinturerias.com.pinturerias.compartidos.enumerate.Contexto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class TamanoEnvase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private double capacidad;
    private Contexto contexto;
}
