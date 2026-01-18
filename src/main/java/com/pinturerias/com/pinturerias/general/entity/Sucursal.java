package com.pinturerias.com.pinturerias.general.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sucursal")
@Data
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=50)
    private String codigo; // Ej: "suc-001"

    @Column(nullable=false, length=100)
    private String nombre;

    @Column(nullable=false, length=300)
    private String jdbcUrl;

    @Column(nullable=false, length=100)
    private String username;

    @Column(nullable=false, length=100)
    private String password;

    private boolean habilitada = true;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}