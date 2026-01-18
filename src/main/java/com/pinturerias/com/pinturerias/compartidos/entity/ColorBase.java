package com.pinturerias.com.pinturerias.compartidos.entity;

import com.pinturerias.com.pinturerias.compartidos.enumerate.Contexto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class ColorBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String formula;
    private Contexto contexto;
}
