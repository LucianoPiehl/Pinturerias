package com.pinturerias.compartidos.entidad.general;

import com.pinturerias.compartidos.entidad.Producto;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "producto_otro_general")
@Getter
@Setter
@NoArgsConstructor
public class ProductoOtroGeneral extends Producto {
}
