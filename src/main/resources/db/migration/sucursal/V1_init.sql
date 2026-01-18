-- ================================
-- TABLAS DE CATÁLOGOS (SUCURSAL)
-- ================================

CREATE TABLE categoria_sucursal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

CREATE TABLE familia_sucursal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

CREATE TABLE tamano_envase_sucursal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

CREATE TABLE base_pintura_sucursal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

CREATE TABLE color_sucursal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    formula VARCHAR(255)
);

CREATE TABLE tipo_pintura_sucursal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    precio_pintura DOUBLE,
    rendimiento_mt2 DOUBLE,
    base_id BIGINT NULL,
    CONSTRAINT fk_tipo_pintura_base_sucursal
        FOREIGN KEY (base_id) REFERENCES base_pintura_sucursal(id)
);

-- ===========================================
-- TABLAS DE PRODUCTOS SUCURSAL
-- ===========================================

CREATE TABLE producto_otro_sucursal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255),
    marca VARCHAR(150),
    precio_final INT NOT NULL,
    stock INT NOT NULL,
    categoria_id BIGINT,
    familia_id BIGINT,
    tam_env_id BIGINT,
    CONSTRAINT fk_otro_cat_sucursal FOREIGN KEY (categoria_id) REFERENCES categoria_sucursal(id),
    CONSTRAINT fk_otro_fam_sucursal FOREIGN KEY (familia_id) REFERENCES familia_sucursal(id),
    CONSTRAINT fk_otro_tam_sucursal FOREIGN KEY (tam_env_id) REFERENCES tamano_envase_sucursal(id)
);

CREATE TABLE producto_pintura_sucursal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255),
    marca VARCHAR(150),
    precio_final INT NOT NULL,
    stock INT NOT NULL,
    categoria_id BIGINT,
    familia_id BIGINT,
    tam_env_id BIGINT,
    tipo_pintura_id BIGINT,
    color_id BIGINT,
    CONSTRAINT fk_pint_cat_sucursal FOREIGN KEY (categoria_id) REFERENCES categoria_sucursal(id),
    CONSTRAINT fk_pint_fam_sucursal FOREIGN KEY (familia_id) REFERENCES familia_sucursal(id),
    CONSTRAINT fk_pint_tam_sucursal FOREIGN KEY (tam_env_id) REFERENCES tamano_envase_sucursal(id),
    CONSTRAINT fk_pint_tipo_sucursal FOREIGN KEY (tipo_pintura_id) REFERENCES tipo_pintura_sucursal(id),
    CONSTRAINT fk_pint_color_sucursal FOREIGN KEY (color_id) REFERENCES color_sucursal(id)
);

-- =======================================
-- PRODUCTOS GENERALES CON CONTROL LOCAL
-- =======================================


CREATE TABLE producto_precio_stock (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    -- ID de producto en la base de datos GENERAL
    producto_general_id BIGINT NOT NULL,

    precio DOUBLE NOT NULL,
    stock INT NOT NULL

    -- índice recomendado
        INDEX idx_prod_general_id (producto_general_id)
);