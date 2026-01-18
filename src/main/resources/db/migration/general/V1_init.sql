-- ================================
-- TABLAS DE CATÁLOGOS (GENERALES)
-- ================================

CREATE TABLE categoria_general (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

CREATE TABLE familia_general (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

CREATE TABLE tamano_envase_general (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

CREATE TABLE base_pintura_general (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

CREATE TABLE color_general (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    formula VARCHAR(255)
);

CREATE TABLE tipo_pintura_general (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    precio_pintura DOUBLE,
    rendimiento_mt2 DOUBLE,
    base_id BIGINT NULL,
    CONSTRAINT fk_tipo_pintura_base_general
        FOREIGN KEY (base_id) REFERENCES base_pintura_general(id)
);

-- ===========================================
-- TABLAS DE PRODUCTOS GENERALES
-- ===========================================

CREATE TABLE producto_otro_general (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255),
    marca VARCHAR(150),
    precio_final INT NOT NULL,

    categoria_id BIGINT,
    familia_id BIGINT,
    tam_env_id BIGINT,

    CONSTRAINT fk_otro_cat_general FOREIGN KEY (categoria_id) REFERENCES categoria_general(id),
    CONSTRAINT fk_otro_fam_general FOREIGN KEY (familia_id) REFERENCES familia_general(id),
    CONSTRAINT fk_otro_tam_general FOREIGN KEY (tam_env_id) REFERENCES tamano_envase_general(id)
);

CREATE TABLE producto_pintura_general (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255),
    marca VARCHAR(150),
    precio_final INT NOT NULL,

    categoria_id BIGINT,
    familia_id BIGINT,
    tam_env_id BIGINT,
    tipo_pintura_id BIGINT,
    color_id BIGINT,

    CONSTRAINT fk_pint_cat_general FOREIGN KEY (categoria_id) REFERENCES categoria_general(id),
    CONSTRAINT fk_pint_fam_general FOREIGN KEY (familia_id) REFERENCES familia_general(id),
    CONSTRAINT fk_pint_tam_general FOREIGN KEY (tam_env_id) REFERENCES tamano_envase_general(id),
    CONSTRAINT fk_pint_tipo_general FOREIGN KEY (tipo_pintura_id) REFERENCES tipo_pintura_general(id),
    CONSTRAINT fk_pint_color_general FOREIGN KEY (color_id) REFERENCES color_general(id)
);