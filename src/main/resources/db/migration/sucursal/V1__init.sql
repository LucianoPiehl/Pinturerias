
-- ===========================================
-- TABLAS DE PRODUCTOS SUCURSAL
-- ===========================================

CREATE TABLE producto_otro_sucursal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255),
    marca VARCHAR(150),
    precio_final DOUBLE NOT NULL,
    stock INT NOT NULL
);


-- =======================================
-- PRODUCTOS GENERALES CON CONTROL LOCAL
-- =======================================


CREATE TABLE producto_precio_stock (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    -- ID de producto en la base de datos GENERAL
    producto_general_id BIGINT NOT NULL,
    precio DOUBLE NOT NULL,
    stock INT NOT NULL,
    INDEX idx_prod_general_id (producto_general_id)
);