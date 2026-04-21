CREATE TABLE etiqueta_sucursal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    valor VARCHAR(120) NOT NULL,
    clave_normalizada VARCHAR(120) NOT NULL,
    CONSTRAINT uk_etiqueta_sucursal_valor UNIQUE (valor),
    CONSTRAINT uk_etiqueta_sucursal_clave UNIQUE (clave_normalizada)
);

CREATE TABLE producto_etiqueta_sucursal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    producto_id BIGINT NOT NULL,
    contexto_producto VARCHAR(20) NOT NULL,
    tipo_producto VARCHAR(20) NOT NULL,
    CONSTRAINT uk_producto_etiqueta_sucursal UNIQUE (producto_id, contexto_producto, tipo_producto)
);

CREATE TABLE producto_etiqueta_sucursal_general (
    producto_etiqueta_sucursal_id BIGINT NOT NULL,
    etiqueta_general_id BIGINT NOT NULL,
    PRIMARY KEY (producto_etiqueta_sucursal_id, etiqueta_general_id),
    CONSTRAINT fk_pesg_asignacion
        FOREIGN KEY (producto_etiqueta_sucursal_id) REFERENCES producto_etiqueta_sucursal(id)
        ON DELETE CASCADE
);

CREATE TABLE producto_etiqueta_sucursal_local (
    producto_etiqueta_sucursal_id BIGINT NOT NULL,
    etiqueta_sucursal_id BIGINT NOT NULL,
    PRIMARY KEY (producto_etiqueta_sucursal_id, etiqueta_sucursal_id),
    CONSTRAINT fk_pesl_asignacion
        FOREIGN KEY (producto_etiqueta_sucursal_id) REFERENCES producto_etiqueta_sucursal(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_pesl_etiqueta
        FOREIGN KEY (etiqueta_sucursal_id) REFERENCES etiqueta_sucursal(id)
        ON DELETE CASCADE
);
