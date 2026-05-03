CREATE TABLE producto_otro_sucursal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255),
    marca VARCHAR(255),
    precio_final DOUBLE NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    etiquetas TEXT,
    stock INT NOT NULL
);

CREATE TABLE producto_precio_stock (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    producto_id BIGINT NOT NULL,
    tipo_producto VARCHAR(20) NOT NULL,
    porcentaje_ajuste DOUBLE NOT NULL,
    stock INT NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    INDEX idx_prod_id (producto_id),
    UNIQUE INDEX uk_producto_precio_stock (producto_id, tipo_producto)
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
    CONSTRAINT fk_pedido_producto_pedido_sucursal
        FOREIGN KEY (pedido_id) REFERENCES pedido(id)
);
