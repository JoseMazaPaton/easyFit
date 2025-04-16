-- Creación de la base de datos
CREATE DATABASE IF NOT EXISTS easyfit_db;
USE easyfit_db;

-- Tabla de roles
CREATE TABLE roles (
    id_rol INT PRIMARY KEY,
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
    id_rol INT DEFAULT 1,
    fecha_registro DATE,
    FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
);

-- Tabla de valores nutricionales (por usuario)
CREATE TABLE valores_nutricionales (
    id_valores INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE, 
    kcal_objetivo INT NOT NULL,
    proteinas DECIMAL(5,2) NOT NULL,         -- gramos
    carbohidratos DECIMAL(5,2) NOT NULL,     -- gramos
    grasas DECIMAL(5,2) NOT NULL,            -- gramos
    porcentaje_proteinas DECIMAL(5,2) NOT NULL DEFAULT 50,
    porcentaje_carbohidratos DECIMAL(5,2) NOT NULL DEFAULT 30,
    porcentaje_grasas DECIMAL(5,2) NOT NULL DEFAULT 20,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	FOREIGN KEY (email) REFERENCES usuarios(email) ON DELETE CASCADE
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

-- Tabla de objetivos (sin kcal ni macros)
CREATE TABLE objetivos (
    id_objetivo INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE, 
    peso_actual DECIMAL(5,2) NOT NULL,
    peso_objetivo DECIMAL(5,2) NOT NULL,
    objetivo_usuario ENUM('PERDERPESO', 'MANTENER', 'GANARPESO') NOT NULL,
    opcion_peso ENUM('LIGERO', 'MODERADO', 'INTENSO', 'MANTENER'),
    actividad ENUM('SEDENTARIO', 'LIGERO', 'MODERADO', 'ACTIVO', 'MUYACTIVO') NOT NULL,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (email) REFERENCES usuarios(email) ON DELETE CASCADE
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
        INSERT INTO progresos (email, peso)
        VALUES (NEW.email, NEW.peso_actual);
    END IF;
END$$

DELIMITER ;
DELIMITER $$

-- Trigger que se ejecuta antes de insertar un nuevo registro en la tabla valores_nutricionales
CREATE TRIGGER before_valores_insert
BEFORE INSERT ON valores_nutricionales
FOR EACH ROW
BEGIN
    -- Si la suma de los tres porcentajes es mayor que 100, lanza un error y no permite la inserción
    IF NEW.porcentaje_proteinas + NEW.porcentaje_carbohidratos + NEW.porcentaje_grasas > 100 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La suma de los porcentajes no puede superar el 100%';
    END IF;

    -- Calcula los gramos de cada macronutriente en base a las kcal y el porcentaje indicado
    SET NEW.proteinas = ROUND((NEW.kcal_objetivo * (NEW.porcentaje_proteinas / 100)) / 4, 2);
    SET NEW.carbohidratos = ROUND((NEW.kcal_objetivo * (NEW.porcentaje_carbohidratos / 100)) / 4, 2);
    SET NEW.grasas = ROUND((NEW.kcal_objetivo * (NEW.porcentaje_grasas / 100)) / 9, 2);
END$$



-- Trigger que se ejecuta antes de actualizar un registro en la tabla valores_nutricionales
CREATE TRIGGER before_valores_update
BEFORE UPDATE ON valores_nutricionales
FOR EACH ROW
BEGIN
    -- Si la suma de los tres porcentajes es mayor que 100, lanza un error y no permite la actualización
    IF NEW.porcentaje_proteinas + NEW.porcentaje_carbohidratos + NEW.porcentaje_grasas > 100 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La suma de los porcentajes no puede superar el 100%';
    END IF;

    -- Calcula los gramos de cada macronutriente en base a las kcal y el porcentaje indicado
    SET NEW.proteinas = ROUND((NEW.kcal_objetivo * (NEW.porcentaje_proteinas / 100)) / 4, 2);
    SET NEW.carbohidratos = ROUND((NEW.kcal_objetivo * (NEW.porcentaje_carbohidratos / 100)) / 4, 2);
    SET NEW.grasas = ROUND((NEW.kcal_objetivo * (NEW.porcentaje_grasas / 100)) / 9, 2);
END$$

DELIMITER ;
