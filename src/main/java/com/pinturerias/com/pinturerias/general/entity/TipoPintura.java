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
@Table(name = "tipo_pintura")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoPintura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double porcentajeAumento;
    private Double rendimientoMT2;


}