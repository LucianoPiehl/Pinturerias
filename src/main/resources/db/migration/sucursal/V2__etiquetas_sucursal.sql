CREATE TABLE etiqueta_sucursal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    valor VARCHAR(120) NOT NULL,
    CONSTRAINT uk_etiqueta_sucursal_valor UNIQUE (valor)
);

CREATE TABLE producto_otro_sucursal_etiqueta_sucursal (
    producto_id BIGINT NOT NULL,
    etiqueta_id BIGINT NOT NULL,
    PRIMARY KEY (producto_id, etiqueta_id),
    CONSTRAINT fk_poses_producto
        FOREIGN KEY (producto_id) REFERENCES producto_otro_sucursal(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_poses_etiqueta
        FOREIGN KEY (etiqueta_id) REFERENCES etiqueta_sucursal(id)
        ON DELETE CASCADE
);

CREATE TABLE producto_otro_sucursal_etiqueta_general (
    producto_id BIGINT NOT NULL,
    etiqueta_general_id BIGINT NOT NULL,
    PRIMARY KEY (producto_id, etiqueta_general_id),
    CONSTRAINT fk_poseg_producto
        FOREIGN KEY (producto_id) REFERENCES producto_otro_sucursal(id)
        ON DELETE CASCADE
);

-- MIGRACION DE LAS ETIQUETAS VIEJAS DE SUCURSAL:
-- TODO lo viejo de sucursal se migra como etiqueta local.
INSERT IGNORE INTO etiqueta_sucursal(valor)
SELECT DISTINCT TRIM(jt.valor)
FROM producto_otro_sucursal p
JOIN JSON_TABLE(
    COALESCE(p.etiquetas, '[]'),
    '$[*]' COLUMNS (
        valor VARCHAR(120) PATH '$'
    )
) jt
WHERE jt.valor IS NOT NULL
  AND TRIM(jt.valor) <> '';

INSERT IGNORE INTO producto_otro_sucursal_etiqueta_sucursal(producto_id, etiqueta_id)
SELECT p.id, e.id
FROM producto_otro_sucursal p
JOIN JSON_TABLE(
    COALESCE(p.etiquetas, '[]'),
    '$[*]' COLUMNS (
        valor VARCHAR(120) PATH '$'
    )
) jt
JOIN etiqueta_sucursal e
    ON LOWER(e.valor) = LOWER(TRIM(jt.valor))
WHERE jt.valor IS NOT NULL
  AND TRIM(jt.valor) <> '';