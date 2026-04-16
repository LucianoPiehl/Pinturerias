package com.pinturerias.compartidos.entidad.shared;

import com.pinturerias.compartidos.enumeracion.EstadoPedido;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 120)
    private String mail;

    @Column(length = 50)
    private String telefono;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoPedido estado = EstadoPedido.INICIADO;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(length = 120)
    private String nombreCliente;

    @Column(length = 120)
    private String nombreVendedor;

    @Column(length = 60)
    private String medioPago;

    private Double descuento;

    @Column(length = 500)
    private String observaciones;

    private Long sucursalDestinoId;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PedidoProducto> productos = new ArrayList<>();

    public void setProductos(List<PedidoProducto> productos) {
        this.productos.clear();
        if (productos == null) {
            return;
        }

        for (PedidoProducto producto : productos) {
            agregarProducto(producto);
        }
    }

    public void agregarProducto(PedidoProducto producto) {
        producto.setPedido(this);
        this.productos.add(producto);
    }
}




