CREATE TABLE usuario (
    id BIGINT PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    telefono VARCHAR(40),
    habilitado BOOLEAN NOT NULL DEFAULT TRUE,
    rol VARCHAR(50) NOT NULL
);

CREATE TABLE usuario_indice (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(120) NOT NULL,
    password VARCHAR(255) NOT NULL,
    sucursal_id VARCHAR(255),
    habilitado BOOLEAN DEFAULT TRUE,
    rol VARCHAR(50) NOT NULL,
    contexto VARCHAR(50) NOT NULL,

    CONSTRAINT uk_usuario_indice_username
            UNIQUE (username)
);