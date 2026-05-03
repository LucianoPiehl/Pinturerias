CREATE TABLE etiqueta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    valor VARCHAR(120) NOT NULL,
    clave_normalizada VARCHAR(120) NOT NULL,
    CONSTRAINT uk_etiqueta_general_valor UNIQUE (valor),
    CONSTRAINT uk_etiqueta_general_clave UNIQUE (clave_normalizada)
);

CREATE TABLE producto_etiqueta_general (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    producto_id BIGINT NOT NULL,
    contexto VARCHAR(20) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    etiqueta_id BIGINT NOT NULL,
    CONSTRAINT uk_producto_etiqueta_general UNIQUE (producto_id, etiqueta_id, contexto)
);

