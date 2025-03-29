-- Creación de la base de datos
CREATE DATABASE IF NOT EXISTS easyfit_db;
USE easyfit_db;

-- Tabla de roles
CREATE TABLE roles (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(15) NOT NULL
    CHECK(nombre IN ('ROL_ADMIN', 'ROL_USUARIO'))
);

-- Tabla de usuarios (email como PRIMARY KEY)
CREATE TABLE usuarios (
    email VARCHAR(100) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    edad INT NOT NULL,
    sexo ENUM ('HOMBRE', 'MUJER') NOT NULL,
    altura DECIMAL(5,2),
    suspendida BOOLEAN,
    id_rol INT,
    fecha_registro DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
);

-- Tabla de categorías de alimentos
CREATE TABLE categorias (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- Tabla de alimentos (creado_por → email del usuario)
CREATE TABLE alimentos (
    id_alimento INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    marca VARCHAR(100) NOT NULL,
    kcal INT NOT NULL,
    proteinas DECIMAL(5,2) NOT NULL,
    carbohidratos DECIMAL(5,2) NOT NULL,
    grasas DECIMAL(5,2) NOT NULL,
    unidad_medida VARCHAR(50) NOT NULL,
    id_categoria INT,
    creado_por VARCHAR(100),
    FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria),
    FOREIGN KEY (creado_por) REFERENCES usuarios(email) ON DELETE CASCADE
);

-- Tabla de comidas
CREATE TABLE comidas (
    id_comida INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    orden INT NOT NULL CHECK (orden BETWEEN 1 AND 6),
    fecha DATE NOT NULL,
    FOREIGN KEY (email) REFERENCES usuarios(email),
    UNIQUE (email, orden, fecha)
);

-- Relación comidas ↔ alimentos
CREATE TABLE comidas_alimentos (
    id_comida_alimento INT AUTO_INCREMENT PRIMARY KEY,
    id_comida INT NOT NULL,
    id_alimento INT NOT NULL,
    cantidad DECIMAL(5,2) NOT NULL,
    FOREIGN KEY (id_comida) REFERENCES comidas(id_comida) ON DELETE CASCADE,
    FOREIGN KEY (id_alimento) REFERENCES alimentos(id_alimento) ON DELETE CASCADE
);

-- Tabla de consumo diario
CREATE TABLE consumo_diario (
    id_consumo INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    fecha DATE NOT NULL,
    kcal_consumidas INT NOT NULL,
    proteinas DECIMAL(5,2) NOT NULL,
    carbohidratos DECIMAL(5,2) NOT NULL,
    grasas DECIMAL(5,2) NOT NULL,
    FOREIGN KEY (email) REFERENCES usuarios(email)
);

-- Tabla de progreso
CREATE TABLE progresos (
    id_progreso INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    fecha_cambio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    peso DECIMAL(5,2) NOT NULL,
    FOREIGN KEY (email) REFERENCES usuarios(email)
);

-- Tabla de objetivos
CREATE TABLE objetivos (
    id_objetivo INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    peso_actual DECIMAL(5,2) NOT NULL,
    peso_objetivo DECIMAL(5,2) NOT NULL,
    objetivo_usuario ENUM('PERDERPESO', 'MANTENER', 'GANARPESO') NOT NULL,
    opcion_peso ENUM('KG_025', 'KG_050', 'KG_075', 'KG_100'),
    actividad ENUM('SEDENTARIO', 'LIGERO', 'MODERADO', 'ACTIVO', 'MUYACTIVO') NOT NULL,
    kcal_objetivo INT NOT NULL,
    proteinas DECIMAL(5,2) NOT NULL,
    carbohidratos DECIMAL(5,2) NOT NULL,
    grasas DECIMAL(5,2) NOT NULL,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (email) REFERENCES usuarios(email)
);


-- Tabla de favoritos
CREATE TABLE favoritos (
    email VARCHAR(100) NOT NULL,
    id_alimento INT NOT NULL,
    PRIMARY KEY (email, id_alimento),
    FOREIGN KEY (email) REFERENCES usuarios(email),
    FOREIGN KEY (id_alimento) REFERENCES alimentos(id_alimento)
);

-- Triggers
DELIMITER $$

CREATE TRIGGER after_comida_alimento_insert
AFTER INSERT ON comidas_alimentos
FOR EACH ROW
BEGIN
    REPLACE INTO consumo_diario (email, fecha, kcal_consumidas, proteinas, carbohidratos, grasas)
    SELECT c.email, c.fecha,
           COALESCE(SUM(a.kcal * ca.cantidad), 0),
           COALESCE(SUM(a.proteinas * ca.cantidad), 0),
           COALESCE(SUM(a.carbohidratos * ca.cantidad), 0),
           COALESCE(SUM(a.grasas * ca.cantidad), 0)
    FROM comidas c
    LEFT JOIN comidas_alimentos ca ON c.id_comida = ca.id_comida
    LEFT JOIN alimentos a ON ca.id_alimento = a.id_alimento
    WHERE c.id_comida = NEW.id_comida
    GROUP BY c.email, c.fecha;
END$$

CREATE TRIGGER after_comida_alimento_delete
AFTER DELETE ON comidas_alimentos
FOR EACH ROW
BEGIN
    REPLACE INTO consumo_diario (email, fecha, kcal_consumidas, proteinas, carbohidratos, grasas)
    SELECT c.email, c.fecha,
           COALESCE(SUM(a.kcal * ca.cantidad), 0),
           COALESCE(SUM(a.proteinas * ca.cantidad), 0),
           COALESCE(SUM(a.carbohidratos * ca.cantidad), 0),
           COALESCE(SUM(a.grasas * ca.cantidad), 0)
    FROM comidas c
    LEFT JOIN comidas_alimentos ca ON c.id_comida = ca.id_comida
    LEFT JOIN alimentos a ON ca.id_alimento = a.id_alimento
    WHERE c.id_comida = OLD.id_comida
    GROUP BY c.email, c.fecha;
END$$

CREATE TRIGGER after_comida_alimento_update
AFTER UPDATE ON comidas_alimentos
FOR EACH ROW
BEGIN
    REPLACE INTO consumo_diario (email, fecha, kcal_consumidas, proteinas, carbohidratos, grasas)
    SELECT c.email, c.fecha,
           COALESCE(SUM(a.kcal * ca.cantidad), 0),
           COALESCE(SUM(a.proteinas * ca.cantidad), 0),
           COALESCE(SUM(a.carbohidratos * ca.cantidad), 0),
           COALESCE(SUM(a.grasas * ca.cantidad), 0)
    FROM comidas c
    LEFT JOIN comidas_alimentos ca ON c.id_comida = ca.id_comida
    LEFT JOIN alimentos a ON ca.id_alimento = a.id_alimento
    WHERE c.id_comida = NEW.id_comida
    GROUP BY c.email, c.fecha;
END$$

CREATE TRIGGER after_peso_update
AFTER UPDATE ON objetivos
FOR EACH ROW
BEGIN
    IF NEW.peso_actual <> OLD.peso_actual THEN
        INSERT INTO progreso (email, fecha_cambio, peso)
        VALUES (NEW.email, NOW(), NEW.peso_actual);
    END IF;
END$$

DELIMITER ;
