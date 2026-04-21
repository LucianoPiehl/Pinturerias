CREATE TABLE etiqueta_general (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    valor VARCHAR(120) NOT NULL,
    clave_normalizada VARCHAR(120) NOT NULL,
    CONSTRAINT uk_etiqueta_general_valor UNIQUE (valor),
    CONSTRAINT uk_etiqueta_general_clave UNIQUE (clave_normalizada)
);

CREATE TABLE producto_etiqueta_general (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    producto_id BIGINT NOT NULL,
    tipo_producto VARCHAR(20) NOT NULL,
    CONSTRAINT uk_producto_etiqueta_general UNIQUE (producto_id, tipo_producto)
);

CREATE TABLE producto_etiqueta_general_item (
    producto_etiqueta_general_id BIGINT NOT NULL,
    etiqueta_general_id BIGINT NOT NULL,
    PRIMARY KEY (producto_etiqueta_general_id, etiqueta_general_id),
    CONSTRAINT fk_peg_item_asignacion
        FOREIGN KEY (producto_etiqueta_general_id) REFERENCES producto_etiqueta_general(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_peg_item_etiqueta
        FOREIGN KEY (etiqueta_general_id) REFERENCES etiqueta_general(id)
        ON DELETE CASCADE
);
