package com.pinturerias.com.pinturerias.compartidos.entity;

import com.pinturerias.com.pinturerias.compartidos.enumerate.Contexto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


public class TipoPintura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double precioPintura;
    private Double RendimientoMT2;
    private Contexto contexto;
    @ManyToOne(optional = true)
    @JoinColumn(name = "base_pintura_id")
    private String basepintura;
}
