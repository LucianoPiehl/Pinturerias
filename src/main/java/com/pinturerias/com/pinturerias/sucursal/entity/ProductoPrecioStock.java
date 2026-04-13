package com.pinturerias.com.pinturerias.sucursal.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Producto_precio_stock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoPrecioStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productoId;

    private int stock;

    private Double precio; // Precio que el dueño o gerente quiere tener


    public ProductoPrecioStock(Long productoGeneralId, Double precio, Integer stock) {
    }
}