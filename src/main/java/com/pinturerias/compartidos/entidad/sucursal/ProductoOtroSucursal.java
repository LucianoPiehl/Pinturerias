package com.pinturerias.compartidos.entidad.sucursal;

import com.pinturerias.compartidos.entidad.Producto;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "producto_otro_sucursal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoOtroSucursal extends Producto {

    private int stock;

}



