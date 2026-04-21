CREATE TABLE etiqueta_general (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    valor VARCHAR(120) NOT NULL,
    CONSTRAINT uk_etiqueta_general_valor UNIQUE (valor)
);

CREATE TABLE producto_otro_general_etiqueta (
    producto_id BIGINT NOT NULL,
    etiqueta_id BIGINT NOT NULL,
    PRIMARY KEY (producto_id, etiqueta_id),
    CONSTRAINT fk_poge_producto
        FOREIGN KEY (producto_id) REFERENCES producto_otro_general(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_poge_etiqueta
        FOREIGN KEY (etiqueta_id) REFERENCES etiqueta_general(id)
        ON DELETE CASCADE
);

CREATE TABLE producto_pintura_general_etiqueta (
    producto_id BIGINT NOT NULL,
    etiqueta_id BIGINT NOT NULL,
    PRIMARY KEY (producto_id, etiqueta_id),
    CONSTRAINT fk_ppge_producto
        FOREIGN KEY (producto_id) REFERENCES producto_pintura_general(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_ppge_etiqueta
        FOREIGN KEY (etiqueta_id) REFERENCES etiqueta_general(id)
        ON DELETE CASCADE
);

-- MIGRACION DESDE EL VIEJO TEXT etiquetas
INSERT IGNORE INTO etiqueta_general(valor)
SELECT DISTINCT TRIM(jt.valor)
FROM producto_otro_general p
JOIN JSON_TABLE(
    COALESCE(p.etiquetas, '[]'),
    '$[*]' COLUMNS (
        valor VARCHAR(120) PATH '$'
    )
) jt
WHERE jt.valor IS NOT NULL
  AND TRIM(jt.valor) <> ''

UNION

SELECT DISTINCT TRIM(jt.valor)
FROM producto_pintura_general p
JOIN JSON_TABLE(
    COALESCE(p.etiquetas, '[]'),
    '$[*]' COLUMNS (
        valor VARCHAR(120) PATH '$'
    )
) jt
WHERE jt.valor IS NOT NULL
  AND TRIM(jt.valor) <> '';

INSERT IGNORE INTO producto_otro_general_etiqueta(producto_id, etiqueta_id)
SELECT p.id, e.id
FROM producto_otro_general p
JOIN JSON_TABLE(
    COALESCE(p.etiquetas, '[]'),
    '$[*]' COLUMNS (
        valor VARCHAR(120) PATH '$'
    )
) jt
JOIN etiqueta_general e
    ON LOWER(e.valor) = LOWER(TRIM(jt.valor))
WHERE jt.valor IS NOT NULL
  AND TRIM(jt.valor) <> '';

INSERT IGNORE INTO producto_pintura_general_etiqueta(producto_id, etiqueta_id)
SELECT p.id, e.id
FROM producto_pintura_general p
JOIN JSON_TABLE(
    COALESCE(p.etiquetas, '[]'),
    '$[*]' COLUMNS (
        valor VARCHAR(120) PATH '$'
    )
) jt
JOIN etiqueta_general e
    ON LOWER(e.valor) = LOWER(TRIM(jt.valor))
WHERE jt.valor IS NOT NULL
  AND TRIM(jt.valor) <> '';