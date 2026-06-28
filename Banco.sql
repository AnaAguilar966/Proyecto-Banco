CREATE DATABASE Banco;
USE Banco;

-- Tabla 1
CREATE TABLE cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    correo VARCHAR(100)
);

-- Tabla 2
CREATE TABLE cuenta_ahorro (
    id_cuenta INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT,
    numero_cuenta VARCHAR(20) UNIQUE NOT NULL,
    saldo DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente) ON DELETE CASCADE
);

-- Tabla 3
CREATE TABLE credito (
    id_credito INT AUTO_INCREMENT PRIMARY KEY,
    id_cuenta INT,
    monto DECIMAL(10, 2) NOT NULL,
    saldo_pendiente DECIMAL(10, 2) NOT NULL,
    estado VARCHAR(20),
    FOREIGN KEY (id_cuenta) REFERENCES cuenta_ahorro(id_cuenta) ON DELETE CASCADE
);

-- Tabla 4
CREATE TABLE movimientos (
    id_movimiento INT AUTO_INCREMENT PRIMARY KEY,
    id_cuenta INT,
    tipo VARCHAR(50) NOT NULL,
    monto DECIMAL(10, 2) NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_cuenta) REFERENCES cuenta_ahorro(id_cuenta) ON DELETE CASCADE
);