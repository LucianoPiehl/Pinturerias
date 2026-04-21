package com.pinturerias.compartidos.entidad.sucursal;

import com.pinturerias.compartidos.entidad.Producto;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "producto_otro_sucursal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoOtroSucursal extends Producto {

    private int stock;
}
