package com.pinturerias.general.entidad;

import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Rol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario_indice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioIndice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String sucursalId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    @Column
    private Boolean habilitado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Contexto contexto;
}
