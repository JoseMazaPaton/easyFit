-- Uso de la base de datos
USE easyfit_db;

-- =========================
-- INSERTS
-- =========================

-- Inserción de roles
INSERT INTO roles (id_rol, nombre) VALUES 
(1, 'ROL_ADMIN'),
(2, 'ROL_USUARIO');

-- -------------------------------------------------------------------------------------------------------------------------------------------
-- Inserción de usuarios (admin y cliente)
INSERT INTO usuarios (email, password, nombre, edad, sexo, altura, suspendida, id_rol, fecha_registro) VALUES 
('admin@easyfit.com', '$2a$12$zOJTAIhfyPI7Eq9FPqYW2usvjQvgoHKWyLBmqLXcsdI36cfiaXKOK', 'Pablo Prieto López', 36, 'HOMBRE', 180.5, FALSE, 1,'2025-03-31'), -- CONTRASEÑA: Admin1234
('laura_martinez94@gmail.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Laura Martínez Ruiz', 28, 'MUJER', 165.2, FALSE, 2,'2025-03-31'); -- CONTRASEÑA: Usuario1234

-- -------------------------------------------------------------------------------------------------------------------------------------------
-- Inserción de categorias
INSERT INTO categorias (nombre) VALUES 
-- Alimentos básicos
('Frutas'),
('Verduras'),
('Legumbres'),
('Cereales'),
('Tubérculos'),
('Frutos secos y semillas'),

-- Productos de origen animal
('Carnes'),
('Pescados y mariscos'),
('Huevos'),
('Lácteos'),

-- Alimentos procesados
('Panadería y bollería'),
('Snacks y aperitivos'),
('Comida rápida'),
('Precocinados y congelados'),

-- Grasas y aceites
('Aceites y grasas'),

-- Bebidas
('Bebidas sin alcohol'),
('Bebidas alcohólicas'),
('Zumos y batidos'),
('Infusiones y cafés'),

-- Otros
('Salsas y condimentos'),
('Especias y hierbas'),
('Suplementos y proteínas'),
('Postres y dulces'),
('Comida internacional'),
('Alimentos infantiles'),
('Sustitutivos de comidas'),
('Dietéticos y light');
-- -------------------------------------------------------------------------------------------------------------------------------------------
-- Inserción de alimentos
INSERT INTO alimentos (nombre, marca, kcal, proteinas, carbohidratos, grasas, unidad_medida, id_categoria, creado_por) VALUES 
-- Frutas
('Manzana', 'Hacendado', 52, 0.3, 14.0, 0.2, '100g', 1, 'admin@easyfit.com'),
('Plátano', 'Dole', 89, 1.1, 23.0, 0.3, '100g', 1, 'admin@easyfit.com'),
('Naranja', 'Hacendado', 47, 0.9, 12.0, 0.1, '100g', 1, 'admin@easyfit.com'),
('Fresas', 'Huelva', 33, 0.7, 7.7, 0.3, '100g', 1, 'admin@easyfit.com'),
('Kiwi', 'Zespri', 61, 1.1, 15.0, 0.5, '100g', 1, 'admin@easyfit.com'),

-- Verduras
('Brócoli', 'Hacendado', 34, 2.8, 6.6, 0.4, '100g', 2, 'admin@easyfit.com'),
('Espinacas', 'Hacendado', 23, 2.9, 1.1, 0.4, '100g', 2, 'admin@easyfit.com'),
('Zanahoria', 'Campomar', 41, 0.9, 9.6, 0.2, '100g', 2, 'admin@easyfit.com'),
('Tomate', 'El Huerto', 18, 0.9, 3.9, 0.2, '100g', 2, 'admin@easyfit.com'),
('Calabacín', 'Eco', 17, 1.2, 3.1, 0.3, '100g', 2, 'admin@easyfit.com'),

-- Legumbres
('Lentejas cocidas', 'Carretilla', 116, 9.0, 20.0, 0.4, '100g', 3, 'admin@easyfit.com'),
('Garbanzos cocidos', 'Carretilla', 119, 7.3, 21.3, 1.9, '100g', 3, 'admin@easyfit.com'),
('Judías blancas', 'Luengo', 127, 8.3, 21.0, 0.7, '100g', 3, 'admin@easyfit.com'),

-- Cereales
('Avena', 'Quaker', 389, 13.0, 66.0, 6.9, '100g', 4, 'admin@easyfit.com'),
('Pan integral', 'Bimbo', 247, 8.4, 41.0, 3.4, '100g', 4, 'admin@easyfit.com'),
('Arroz blanco', 'SOS', 130, 2.4, 28.0, 0.3, '100g', 4, 'admin@easyfit.com'),
('Arroz integral', 'Nomen', 111, 2.6, 23.0, 0.9, '100g', 4, 'admin@easyfit.com'),
('Cuscús', 'Gallo', 112, 3.8, 23.2, 0.2, '100g', 4, 'admin@easyfit.com'),

-- Frutos secos y semillas
('Almendras', 'Alesto', 579, 21.2, 21.7, 49.9, '100g', 6, 'admin@easyfit.com'),
('Nueces', 'Alesto', 654, 15.2, 13.7, 65.2, '100g', 6, 'admin@easyfit.com'),
('Pistachos', 'Wonderful', 562, 20.3, 28.0, 45.0, '100g', 6, 'admin@easyfit.com'),
('Semillas de chía', 'NaturGreen', 486, 16.5, 42.1, 30.7, '100g', 6, 'admin@easyfit.com'),

-- Carnes
('Pechuga de pollo', 'Campofrío', 110, 23.0, 0.0, 1.5, '100g', 7, 'admin@easyfit.com'),
('Ternera magra', 'El Pozo', 124, 21.0, 0.0, 4.5, '100g', 7, 'admin@easyfit.com'),
('Jamón serrano', 'Navidul', 241, 30.0, 0.0, 14.0, '100g', 7, 'admin@easyfit.com'),

-- Pescados y mariscos
('Salmón', 'Noruego', 208, 20.0, 0.0, 13.0, '100g', 8, 'admin@easyfit.com'),
('Atún en aceite', 'Calvo', 198, 25.5, 0.0, 10.4, '100g', 8, 'admin@easyfit.com'),
('Gambas cocidas', 'Pescanova', 99, 19.0, 0.2, 1.5, '100g', 8, 'admin@easyfit.com'),

-- Huevos
('Huevo cocido', 'Mercadona', 155, 13.0, 1.1, 11.0, 'unidad (60g)', 9, 'admin@easyfit.com'),

-- Lácteos
('Leche semidesnatada', 'Pascual', 46, 3.1, 4.9, 1.6, '100ml', 10, 'admin@easyfit.com'),
('Yogur natural', 'Danone', 61, 3.5, 4.7, 3.3, '125g', 10, 'admin@easyfit.com'),
('Queso fresco batido 0%', 'Hacendado', 52, 8.5, 4.0, 0.2, '100g', 10, 'admin@easyfit.com'),

-- Snacks y procesados (id_categoria = 12)
('Barrita de cereales', 'Nestlé Fitness', 91, 1.4, 18.0, 1.2, 'unidad (23g)', 12, 'admin@easyfit.com'),
('Galletas María', 'Fontaneda', 477, 7.0, 72.0, 16.0, '100g', 12, 'admin@easyfit.com'),
('Patatas fritas', 'Lay\'s', 536, 6.0, 52.0, 34.0, '100g', 12, 'admin@easyfit.com'),
('Donut glaseado', 'Panrico', 452, 4.5, 51.0, 25.0, 'unidad (50g)', 12, 'admin@easyfit.com'),

-- Aceites y grasas (id_categoria = 15)
('Aceite de oliva virgen extra', 'Carbonell', 884, 0.0, 0.0, 100.0, '100ml', 15, 'admin@easyfit.com'),
('Mantequilla', 'President', 717, 0.9, 0.1, 81.0, '100g', 15, 'admin@easyfit.com'),
('Aceite de coco', 'NaturGreen', 892, 0.0, 0.0, 99.9, '100ml', 15, 'admin@easyfit.com'),

-- Bebidas sin alcohol (id_categoria = 16)
('Agua mineral', 'Font Vella', 0, 0.0, 0.0, 0.0, '500ml', 16, 'admin@easyfit.com'),
('Coca-Cola Zero', 'Coca-Cola', 1, 0.0, 0.0, 0.0, '330ml', 16, 'admin@easyfit.com'),
('Zumo de naranja', 'Don Simón', 42, 0.7, 9.6, 0.2, '200ml', 16, 'admin@easyfit.com'),
('Té verde', 'Hornimans', 1, 0.1, 0.2, 0.0, '200ml', 16, 'admin@easyfit.com'),

-- Salsas y condimentos (id_categoria = 20)
('Ketchup', 'Heinz', 112, 1.2, 26.0, 0.1, '100g', 20, 'admin@easyfit.com'),
('Mayonesa', 'Hellmann\'s', 680, 1.0, 1.0, 75.0, '100g', 20, 'admin@easyfit.com'),
('Mostaza', 'French\'s', 66, 4.0, 5.0, 3.5, '100g', 20, 'admin@easyfit.com'),

-- Postres y dulces (id_categoria = 23)
('Chocolate negro 70%', 'Lindt', 539, 9.0, 32.0, 42.0, '100g', 23, 'admin@easyfit.com'),
('Helado de vainilla', 'Häagen-Dazs', 207, 3.5, 24.0, 11.0, '100g', 23, 'admin@easyfit.com'),
('Natillas', 'Danet', 122, 2.9, 18.0, 4.1, 'unidad (125g)', 23, 'admin@easyfit.com'),

-- Suplementos y proteínas (id_categoria = 22)
('Proteína whey', 'MyProtein', 400, 80.0, 8.0, 7.0, '100g', 22, 'admin@easyfit.com'),
('Proteína whey', 'HSN', 400, 80.0, 8.0, 7.0, '100g', 22, 'admin@easyfit.com'),

-- Comida rápida (id_categoria = 13)
('Pizza 4 quesos', 'Casa Tarradellas', 260, 11.0, 26.0, 12.0, '100g', 13, 'admin@easyfit.com'),
('Hamburguesa clásica', 'McDonald\'s', 250, 13.0, 31.0, 9.0, 'unidad', 13, 'admin@easyfit.com');

-- -------------------------------------------------------------------------------------------------------------------------------------------

-- Inserción de comidas
INSERT INTO comidas (email, nombre, orden, fecha) VALUES 
('laura_martinez94@gmail.com', 'Desayuno', 1, '2025-03-27'),
('laura_martinez94@gmail.com', 'Almuerzo', 2, '2025-03-27'),
('laura_martinez94@gmail.com', 'Comida', 3, '2025-03-27'),
('laura_martinez94@gmail.com', 'Merienda', 4, '2025-03-27'),
('laura_martinez94@gmail.com', 'Cena', 5, '2025-03-27'),
('laura_martinez94@gmail.com', 'Snack nocturno', 6, '2025-03-27');

-- -------------------------------------------------------------------------------------------------------------------------------------------

-- Inserción de comidas_alimentos
-- Desayuno (id_comida = 1)
INSERT INTO comidas_alimentos (id_comida, id_alimento, cantidad) VALUES
(1, 1, 1.0),   -- 100g Manzana
(1, 14, 0.5);  -- 50g Avena

-- Almuerzo (id_comida = 2)
INSERT INTO comidas_alimentos (id_comida, id_alimento, cantidad) VALUES
(2, 20, 1.0),  -- 100g Almendras
(2, 31, 1.0);  -- 100g Yogur natural

-- Comida (id_comida = 3)
INSERT INTO comidas_alimentos (id_comida, id_alimento, cantidad) VALUES
(3, 18, 1.0),  -- 100g Arroz blanco
(3, 21, 1.5),  -- 150g Pechuga de pollo
(3, 6, 0.5);   -- 50g Brócoli

-- Merienda (id_comida = 4)
INSERT INTO comidas_alimentos (id_comida, id_alimento, cantidad) VALUES
(4, 12, 0.5),  -- 50g Garbanzos cocidos
(4, 2, 1.0);   -- 100g Plátano

-- Cena (id_comida = 5)
INSERT INTO comidas_alimentos (id_comida, id_alimento, cantidad) VALUES
(5, 23, 1.0),  -- 100g Salmón
(5, 10, 1.0);  -- 100g Calabacín

-- Snack nocturno (id_comida = 6)
INSERT INTO comidas_alimentos (id_comida, id_alimento, cantidad) VALUES
(6, 31, 1.0),  -- 100g Yogur natural
(6, 6, 0.25);  -- 25g Almendras

-- -------------------------------------------------------------------------------------------------------------------------------------------
-- Inserción corregida de objetivos
INSERT INTO objetivos (
    email, peso_actual, peso_objetivo, objetivo_usuario, opcion_peso, actividad, kcal_objetivo, proteinas, carbohidratos, grasas) VALUES 
    ('laura_martinez94@gmail.com', 63.0, 58.0, 'PERDERPESO', 'LIGERO', 'MODERADO', 1800, 120.0, 150.0, 50.0);
-- -------------------------------------------------------------------------------------------------------------------------------------------
-- Inserción de progresos
INSERT INTO progresos (email, peso) VALUES 
('laura_martinez94@gmail.com', 63.0);

-- -------------------------------------------------------------------------------------------------------------------------------------------
-- Inserción de favoritos
INSERT INTO favoritos (email, id_alimento) VALUES 
('laura_martinez94@gmail.com', 2), 
('laura_martinez94@gmail.com', 21), 
('laura_martinez94@gmail.com', 31);

-- -------------------------------------------------------------------------------------------------------------------------------------------
-- Inserción manual de prueba de consumo_diario
-- los triggers generan este dato automáticamente al añadir alimentos a comidas
INSERT INTO consumo_diario (email, fecha, kcal_consumidas, proteinas, carbohidratos, grasas) VALUES 
('laura_martinez94@gmail.com', '2025-03-27', 1750, 115.0, 145.0, 48.0);


-- =========================
-- FIN DEL SCRIPT
-- =========================
