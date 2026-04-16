CREATE TABLE sucursal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    jdbc_url VARCHAR(300) NOT NULL,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    habilitada BIT NOT NULL DEFAULT b'1'
);

CREATE TABLE color_general (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    formula VARCHAR(255),
    porcentaje_aumento DOUBLE
);

CREATE TABLE tipo_pintura_general (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    porcentaje_aumento DOUBLE,
    rendimientomt2 DOUBLE
);

CREATE TABLE tamano_envase_general (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    capacidad DOUBLE,
    porcentaje_aumento DOUBLE
);

CREATE TABLE producto_otro_general (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255),
    marca VARCHAR(255),
    precio_final DOUBLE,
    etiquetas TEXT
);

CREATE TABLE producto_pintura_general (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255),
    marca VARCHAR(255),
    precio_final DOUBLE,
    etiquetas TEXT,
    tipo_pintura_id BIGINT,
    color_id BIGINT,
    tam_env_id BIGINT,
    CONSTRAINT fk_producto_pintura_general_tipo
        FOREIGN KEY (tipo_pintura_id) REFERENCES tipo_pintura_general(id),
    CONSTRAINT fk_producto_pintura_general_color
        FOREIGN KEY (color_id) REFERENCES color_general(id),
    CONSTRAINT fk_producto_pintura_general_tamano
        FOREIGN KEY (tam_env_id) REFERENCES tamano_envase_general(id)
);

CREATE TABLE producto_stock_general (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    producto_id BIGINT NOT NULL,
    tipo_producto VARCHAR(20) NOT NULL,
    stock INT NOT NULL
);

CREATE TABLE pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mail VARCHAR(120),
    telefono VARCHAR(50),
    nombre VARCHAR(150) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    nombre_cliente VARCHAR(120),
    nombre_vendedor VARCHAR(120),
    medio_pago VARCHAR(60),
    descuento DOUBLE,
    observaciones VARCHAR(500),
    sucursal_destino_id BIGINT
);

CREATE TABLE pedido_producto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_producto BIGINT NOT NULL,
    contexto_producto VARCHAR(20) NOT NULL,
    tipo_producto VARCHAR(20) NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DOUBLE NOT NULL,
    pedido_id BIGINT NOT NULL,
    CONSTRAINT fk_pedido_producto_pedido_general
        FOREIGN KEY (pedido_id) REFERENCES pedido(id)
);

CREATE UNIQUE INDEX uk_producto_stock_general
    ON producto_stock_general (producto_id, tipo_producto);
