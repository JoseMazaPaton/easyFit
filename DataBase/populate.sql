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
('laura_martinez94@gmail.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Laura Martínez Ruiz', 28, 'MUJER', 165.2, FALSE, 2,'2025-03-31'),-- CONTRASEÑA: Usuario1234

-- USUARIOS FAKE PARA RELLENAR
('marco.diaz87@gmail.com','$2a$12$3K1zFvZ/8SK2N3EzLgCkN.g9/P9bWZZ6ulB4qvqpr4VjHohY5bqgO','Marco Díaz Fernández',37,'HOMBRE',178.4,FALSE,2,'2025-03-10'),
('reyesjurado@bru.net', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Sandra Rosado Tena', 22, 'MUJER', 181.9, FALSE, 2, '2025-04-12'),
('pvalls@yahoo.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'José Navarro Escamilla', 53, 'MUJER', 155.3, FALSE, 1, '2024-11-19'),
('jose-antonio47@gmail.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Inmaculada Salamanca', 43, 'MUJER', 165.2, FALSE, 1, '2025-01-05'),
('egordillo@hotmail.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Baudelio Morante Pina', 23, 'HOMBRE', 158.8, FALSE, 1, '2024-10-17'),
('fmendez@solis.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Nélida Gras', 23, 'MUJER', 169.2, FALSE, 2, '2025-01-10'),
('piamanzano@pera.es', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Julieta Garay Chaparro', 18, 'MUJER', 167.4, FALSE, 1, '2024-10-03'),
('pedreroelpidio@hotmail.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Encarna Prats Suarez', 20, 'MUJER', 165.3, FALSE, 2, '2024-12-28'),
('muryolanda@varela-barreda.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Máximo Tomé Carmona', 21, 'HOMBRE', 180.1, FALSE, 2, '2025-03-08'),
('adanjaen@gmail.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Adela Orozco Pizarro', 38, 'MUJER', 175.7, FALSE, 1, '2024-10-01'),
('acerojuan-luis@andrade.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Consuelo Criado Alvarado', 48, 'MUJER', 185.7, FALSE, 2, '2024-12-26'),
('albertaparicio@hidalgo-anguita.es', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Eusebia Martina Calzada Barco', 51, 'MUJER', 191.6, FALSE, 2, '2025-03-07'),
('evapelaez@llorens.org', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Ruth Fabra Camino', 41, 'MUJER', 173.4, FALSE, 1, '2025-01-15'),
('jose-manuel25@solera.es', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Amelia Dulce Matas Mayol', 46, 'MUJER', 174.2, FALSE, 2, '2025-02-01'),
('amayanogues@gmail.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Porfirio Cristian Ribas Gomis', 51, 'HOMBRE', 167.5, FALSE, 1, '2024-10-03'),
('teodosio22@ariza.net', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Mar Bermudez Cámara', 22, 'MUJER', 190.4, FALSE, 2, '2025-01-16'),
('estefaniaaguilar@bayo.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Albina Leiva Salvà', 38, 'MUJER', 177.3, FALSE, 2, '2024-10-20'),
('renatatomas@gmail.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Berto Pagès Bernat', 51, 'HOMBRE', 162.1, FALSE, 2, '2024-10-23'),
('donatoperales@gmail.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Julio Ballester Pascual', 39, 'HOMBRE', 151.1, FALSE, 2, '2024-11-15'),
('solsonaadela@villegas.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Rosa Fernandez-Quevedo', 60, 'MUJER', 173.9, FALSE, 2, '2025-02-24'),
('acamino@hotmail.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Omar Castelló Valbuena', 24, 'HOMBRE', 177.2, FALSE, 1, '2025-02-10'),
('ainaraparra@gmail.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Azeneth Céspedes', 48, 'MUJER', 189.2, FALSE, 1, '2025-02-22'),
('garias@vazquez-maza.es', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Darío Baena', 56, 'HOMBRE', 150.6, FALSE, 1, '2024-12-16'),
('uiniesta@yahoo.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Abigaíl Alfaro Jover', 54, 'MUJER', 187.8, FALSE, 1, '2025-02-13'),
('squero@gmail.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Morena Arenas Cruz', 37, 'MUJER', 160.6, FALSE, 1, '2025-01-20'),
('pastorjulian@yahoo.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Loreto Escobar Quevedo', 60, 'MUJER', 167.9, FALSE, 2, '2025-01-24'),
('vparra@enriquez.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Cebrián Pastor Cuesta', 37, 'HOMBRE', 161.0, FALSE, 1, '2024-12-07'),
('luisdiaz@bayo.org', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Delia Leyre Alcalá Gallo', 38, 'MUJER', 187.6, FALSE, 1, '2024-11-02'),
('felipainiesta@yahoo.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Zacarías Jover Valls', 24, 'HOMBRE', 192.6, FALSE, 2, '2025-03-09'),
('domitila01@yahoo.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Ricardo Casanova', 34, 'HOMBRE', 186.5, FALSE, 2, '2024-12-16'),
('bpla@gonzalez-benito.org', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Otilia Chico Narváez', 45, 'MUJER', 194.0, FALSE, 1, '2025-01-24'),
('maciasrufina@yahoo.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Juan José Barroso Salcedo', 47, 'HOMBRE', 155.9, FALSE, 2, '2024-10-01'),
('bautistaprudencio@galvez.es', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Amaro Moya', 32, 'HOMBRE', 150.9, FALSE, 2, '2024-11-21'),
('maestreroberta@galindo.es', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Arcelia Rivera Ribas', 60, 'MUJER', 191.9, FALSE, 1, '2024-10-21'),
('nidiaferrando@benet.org', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Lalo Sanz', 42, 'HOMBRE', 161.8, FALSE, 2, '2024-12-16'),
('flujan@poza.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Socorro Chacón Solé', 25, 'MUJER', 189.8, FALSE, 2, '2024-11-11'),
('fidel89@gmail.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Ruben Baños Ballesteros', 59, 'HOMBRE', 179.4, FALSE, 1, '2025-01-25'),
('miguelnatanael@aranda-perez.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Pancho Morán Hernandez', 18, 'HOMBRE', 154.1, FALSE, 2, '2025-02-11'),
('atienzaagata@gmail.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Cipriano Olivé Cánovas', 44, 'HOMBRE', 192.5, FALSE, 2, '2025-01-01'),
('anselmacasanova@lago.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Nicodemo Nicodemo Larrañaga Batalla', 50, 'HOMBRE', 171.9, FALSE, 1, '2024-11-09'),
('cornejolola@yahoo.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Inmaculada Ángeles Vergara Zabala', 19, 'MUJER', 184.7, FALSE, 2, '2025-01-02'),
('anadiez@ortega.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Sandalio Ayala Cueto', 45, 'HOMBRE', 184.4, FALSE, 2, '2025-01-27'),
('mariareguera@yahoo.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Leonardo Losada Gascón', 45, 'HOMBRE', 176.4, FALSE, 1, '2024-11-01'),
('robertaalmeida@alvarez-olivares.es', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Maxi Barrena Bermejo', 43, 'HOMBRE', 178.7, FALSE, 1, '2025-02-11'),
('azahara39@barrena-torrecilla.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Erasmo Miranda Castellanos', 51, 'HOMBRE', 168.8, FALSE, 1, '2025-01-13'),
('rbello@rubio.net', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'María Teresa Ordóñez Hoyos', 36, 'MUJER', 176.4, FALSE, 1, '2025-02-02'),
('ilorenzo@carballo.es', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Cosme Cózar Manrique', 42, 'HOMBRE', 161.5, FALSE, 2, '2025-02-10'),
('isaias29@yahoo.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Griselda Maestre', 40, 'MUJER', 155.7, FALSE, 2, '2025-03-07'),
('casandraberrocal@bermudez-ortega.es', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Inés Tomás Arteaga', 59, 'MUJER', 177.7, FALSE, 1, '2025-04-02'),
('adolfodavila@gmail.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Leocadio Trujillo Tovar', 18, 'HOMBRE', 150.2, FALSE, 2, '2025-03-27'),
('halcalde@yahoo.com', '$2a$12$Yy6ttVkjaBPqdyRBkcSO2OJTltRU.y6Hlved0k44HRChxcEqc1Naa', 'Salomón Pallarès Agudo', 18, 'HOMBRE', 192.6, FALSE, 2, '2025-02-13');
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

-- Comidas para el 14 de abril
('laura_martinez94@gmail.com', 'Desayuno', 1, '2025-04-14'),
('laura_martinez94@gmail.com', 'Comida', 2, '2025-04-14'),
('laura_martinez94@gmail.com', 'Cena', 3, '2025-04-14'),

-- Comidas para el 15 de abril
('laura_martinez94@gmail.com', 'Desayuno', 1, '2025-04-15'),
('laura_martinez94@gmail.com', 'Comida', 2, '2025-04-15'),
('laura_martinez94@gmail.com', 'Cena', 3, '2025-04-15'),

-- Comidas para el 16 de abril
('laura_martinez94@gmail.com', 'Desayuno', 1, '2025-04-16'),
('laura_martinez94@gmail.com', 'Comida', 2, '2025-04-16'),
('laura_martinez94@gmail.com', 'Cena', 3, '2025-04-16'),

-- Comidas para el 17 de abril
('laura_martinez94@gmail.com', 'Desayuno', 1, '2025-04-17'),
('laura_martinez94@gmail.com', 'Comida', 2, '2025-04-17'),
('laura_martinez94@gmail.com', 'Cena', 3, '2025-04-17'),

-- Comidas para el 18 de abril
('laura_martinez94@gmail.com', 'Desayuno', 1, '2025-04-18'),
('laura_martinez94@gmail.com', 'Comida', 2, '2025-04-18'),
('laura_martinez94@gmail.com', 'Cena', 3, '2025-04-18'),

-- Comidas para el 19 de abril
('laura_martinez94@gmail.com', 'Desayuno', 1, '2025-04-19'),
('laura_martinez94@gmail.com', 'Comida', 2, '2025-04-19'),
('laura_martinez94@gmail.com', 'Cena', 3, '2025-04-19'),

-- Comidas para el 20 de abril
('laura_martinez94@gmail.com', 'Desayuno', 1, '2025-04-20'),
('laura_martinez94@gmail.com', 'Comida', 2, '2025-04-20'),
('laura_martinez94@gmail.com', 'Cena', 3, '2025-04-20');



-- -------------------------------------------------------------------------------------------------------------------------------------------

-- Inserción de comidas_alimentos
-- Desayuno: 14/04/2025
INSERT INTO comidas_alimentos (id_comida, id_alimento, cantidad) VALUES
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-14' AND email = 'laura_martinez94@gmail.com'), 3, 1.0),   -- Naranja
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-14' AND email = 'laura_martinez94@gmail.com'), 4, 1.0),   -- Fresas
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-14' AND email = 'laura_martinez94@gmail.com'), 14, 0.5),  -- Avena
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-14' AND email = 'laura_martinez94@gmail.com'), 38, 1.0),  -- Leche semidesnatada
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-14' AND email = 'laura_martinez94@gmail.com'), 40, 1.0);  -- Queso fresco batido 0%

-- Comida: 14/04/2025
INSERT INTO comidas_alimentos (id_comida, id_alimento, cantidad) VALUES
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-14' AND email = 'laura_martinez94@gmail.com'), 11, 1.0),  -- Lentejas cocidas
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-14' AND email = 'laura_martinez94@gmail.com'), 23, 1.0),  -- Pechuga de pollo
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-14' AND email = 'laura_martinez94@gmail.com'), 6, 1.0),   -- Brócoli
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-14' AND email = 'laura_martinez94@gmail.com'), 8, 1.0),   -- Zanahoria
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-14' AND email = 'laura_martinez94@gmail.com'), 16, 1.0);  -- Arroz blanco

-- Cena: 14/04/2025
INSERT INTO comidas_alimentos (id_comida, id_alimento, cantidad) VALUES
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-14' AND email = 'laura_martinez94@gmail.com'), 9, 1.0),    -- Tomate
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-14' AND email = 'laura_martinez94@gmail.com'), 10, 1.0),   -- Calabacín
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-14' AND email = 'laura_martinez94@gmail.com'), 19, 1.0),   -- Nueces
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-14' AND email = 'laura_martinez94@gmail.com'), 25, 1.0),   -- Zumo de naranja
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-14' AND email = 'laura_martinez94@gmail.com'), 39, 1.0);   -- Yogur natural

-- Desayuno: 15/04/2025
INSERT INTO comidas_alimentos (id_comida, id_alimento, cantidad) VALUES
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-15' AND email = 'laura_martinez94@gmail.com'), 1, 1.0),   -- Manzana
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-15' AND email = 'laura_martinez94@gmail.com'), 2, 1.0),   -- Plátano
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-15' AND email = 'laura_martinez94@gmail.com'), 14, 0.5),  -- Avena
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-15' AND email = 'laura_martinez94@gmail.com'), 15, 1.0),  -- Pan integral
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-15' AND email = 'laura_martinez94@gmail.com'), 38, 1.0);  -- Leche semidesnatada

-- Comida: 15/04/2025
INSERT INTO comidas_alimentos (id_comida, id_alimento, cantidad) VALUES
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-15' AND email = 'laura_martinez94@gmail.com'), 11, 1.0),  -- Lentejas cocidas
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-15' AND email = 'laura_martinez94@gmail.com'), 20, 0.5),  -- Ternera magra
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-15' AND email = 'laura_martinez94@gmail.com'), 6, 1.0),   -- Brócoli
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-15' AND email = 'laura_martinez94@gmail.com'), 8, 1.0),   -- Zanahoria
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-15' AND email = 'laura_martinez94@gmail.com'), 16, 1.0);  -- Arroz blanco

-- Cena: 15/04/2025
INSERT INTO comidas_alimentos (id_comida, id_alimento, cantidad) VALUES
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-15' AND email = 'laura_martinez94@gmail.com'), 9, 1.0),    -- Tomate
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-15' AND email = 'laura_martinez94@gmail.com'), 10, 1.0),   -- Calabacín
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-15' AND email = 'laura_martinez94@gmail.com'), 39, 1.0),   -- Yogur natural
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-15' AND email = 'laura_martinez94@gmail.com'), 19, 1.0),   -- Nueces
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-15' AND email = 'laura_martinez94@gmail.com'), 25, 1.0);   -- Zumo de naranja


-- Desayuno: 16/04/2025
INSERT INTO comidas_alimentos (id_comida, id_alimento, cantidad) VALUES
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-16' AND email = 'laura_martinez94@gmail.com'), 2, 1.0),   -- Plátano
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-16' AND email = 'laura_martinez94@gmail.com'), 14, 0.5),  -- Avena
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-16' AND email = 'laura_martinez94@gmail.com'), 15, 1.0),  -- Pan integral
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-16' AND email = 'laura_martinez94@gmail.com'), 38, 1.0),  -- Leche semidesnatada
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-16' AND email = 'laura_martinez94@gmail.com'), 18, 0.5);  -- Almendras

-- Comida: 16/04/2025
INSERT INTO comidas_alimentos (id_comida, id_alimento, cantidad) VALUES
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-16' AND email = 'laura_martinez94@gmail.com'), 11, 1.0),  -- Lentejas cocidas
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-16' AND email = 'laura_martinez94@gmail.com'), 24, 1.0),  -- Judías blancas
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-16' AND email = 'laura_martinez94@gmail.com'), 6, 1.0),   -- Brócoli
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-16' AND email = 'laura_martinez94@gmail.com'), 20, 1.0),  -- Ternera magra
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-16' AND email = 'laura_martinez94@gmail.com'), 34, 1.0);  -- Patatas fritas

-- Cena: 16/04/2025
INSERT INTO comidas_alimentos (id_comida, id_alimento, cantidad) VALUES
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-16' AND email = 'laura_martinez94@gmail.com'), 9, 1.0),    -- Tomate
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-16' AND email = 'laura_martinez94@gmail.com'), 10, 1.0),   -- Calabacín
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-16' AND email = 'laura_martinez94@gmail.com'), 19, 1.0),   -- Nueces
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-16' AND email = 'laura_martinez94@gmail.com'), 37, 1.0),   -- Huevo cocido
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-16' AND email = 'laura_martinez94@gmail.com'), 25, 1.0);   -- Zumo de naranja


-- Desayuno: 17/04/2025
INSERT INTO comidas_alimentos (id_comida, id_alimento, cantidad) VALUES
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-17' AND email = 'laura_martinez94@gmail.com'), 3, 1.0),   -- Naranja
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-17' AND email = 'laura_martinez94@gmail.com'), 5, 1.0),   -- Kiwi
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-17' AND email = 'laura_martinez94@gmail.com'), 14, 0.5),  -- Avena
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-17' AND email = 'laura_martinez94@gmail.com'), 38, 1.0),  -- Leche semidesnatada
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-17' AND email = 'laura_martinez94@gmail.com'), 18, 0.5);  -- Almendras

-- Comida: 17/04/2025
INSERT INTO comidas_alimentos (id_comida, id_alimento, cantidad) VALUES
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-17' AND email = 'laura_martinez94@gmail.com'), 11, 1.0),  -- Lentejas cocidas
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-17' AND email = 'laura_martinez94@gmail.com'), 16, 1.0),  -- Arroz blanco
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-17' AND email = 'laura_martinez94@gmail.com'), 23, 1.0),  -- Pechuga de pollo
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-17' AND email = 'laura_martinez94@gmail.com'), 6, 1.0),   -- Brócoli
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-17' AND email = 'laura_martinez94@gmail.com'), 20, 0.5);  -- Ternera magra

-- Cena: 17/04/2025
INSERT INTO comidas_alimentos (id_comida, id_alimento, cantidad) VALUES
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-17' AND email = 'laura_martinez94@gmail.com'), 9, 1.0),    -- Tomate
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-17' AND email = 'laura_martinez94@gmail.com'), 10, 1.0),   -- Calabacín
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-17' AND email = 'laura_martinez94@gmail.com'), 39, 1.0),   -- Yogur natural
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-17' AND email = 'laura_martinez94@gmail.com'), 19, 1.0),   -- Nueces
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-17' AND email = 'laura_martinez94@gmail.com'), 25, 1.0);   -- Zumo de naranja


-- Desayuno: 18/04/2025
INSERT INTO comidas_alimentos (id_comida, id_alimento, cantidad) VALUES
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-18' AND email = 'laura_martinez94@gmail.com'), 1, 1.0),   -- Manzana
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-18' AND email = 'laura_martinez94@gmail.com'), 2, 1.0),   -- Plátano
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-18' AND email = 'laura_martinez94@gmail.com'), 14, 0.5),  -- Avena
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-18' AND email = 'laura_martinez94@gmail.com'), 15, 1.0),  -- Pan integral
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-18' AND email = 'laura_martinez94@gmail.com'), 38, 1.0);  -- Leche semidesnatada

-- Comida: 18/04/2025
INSERT INTO comidas_alimentos (id_comida, id_alimento, cantidad) VALUES
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-18' AND email = 'laura_martinez94@gmail.com'), 16, 1.0),  -- Arroz blanco
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-18' AND email = 'laura_martinez94@gmail.com'), 23, 1.0),  -- Pechuga de pollo
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-18' AND email = 'laura_martinez94@gmail.com'), 6, 1.0),   -- Brócoli
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-18' AND email = 'laura_martinez94@gmail.com'), 8, 1.0),   -- Zanahoria
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-18' AND email = 'laura_martinez94@gmail.com'), 20, 0.5);  -- Ternera magra

-- Cena: 18/04/2025
INSERT INTO comidas_alimentos (id_comida, id_alimento, cantidad) VALUES
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-18' AND email = 'laura_martinez94@gmail.com'), 37, 1.0),  -- Huevo cocido
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-18' AND email = 'laura_martinez94@gmail.com'), 9, 1.0),   -- Tomate
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-18' AND email = 'laura_martinez94@gmail.com'), 39, 1.0),  -- Yogur natural
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-18' AND email = 'laura_martinez94@gmail.com'), 18, 0.5),  -- Almendras
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-18' AND email = 'laura_martinez94@gmail.com'), 25, 0.5);  -- Zumo de naranja

-- Desayuno: 19/04/2025
INSERT INTO comidas_alimentos (id_comida, id_alimento, cantidad) VALUES
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-19' AND email = 'laura_martinez94@gmail.com'), 1, 1.0),  -- Manzana
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-19' AND email = 'laura_martinez94@gmail.com'), 14, 0.5); -- Avena

-- Comida: 19/04/2025
INSERT INTO comidas_alimentos (id_comida, id_alimento, cantidad) VALUES
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-19' AND email = 'laura_martinez94@gmail.com'), 16, 1.0),  -- Arroz blanco
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-19' AND email = 'laura_martinez94@gmail.com'), 23, 1.0); -- Pechuga de pollo

-- Cena: 19/04/2025
INSERT INTO comidas_alimentos (id_comida, id_alimento, cantidad) VALUES
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-19' AND email = 'laura_martinez94@gmail.com'), 39, 1.0),  -- Yogur natural
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-19' AND email = 'laura_martinez94@gmail.com'), 18, 0.5),  -- Almendras
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-19' AND email = 'laura_martinez94@gmail.com'), 6, 1.0);   -- Brócoli

-- Desayuno:20/04/2025
INSERT INTO comidas_alimentos (id_comida, id_alimento, cantidad) VALUES
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-20' AND email = 'laura_martinez94@gmail.com'), 2, 1.0),  -- Plátano
((SELECT id_comida FROM comidas WHERE nombre = 'Desayuno' AND fecha = '2025-04-20' AND email = 'laura_martinez94@gmail.com'), 38, 1.5); -- Leche semidesnatada

-- Comida: 20/04/2025
INSERT INTO comidas_alimentos (id_comida, id_alimento, cantidad) VALUES
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-20' AND email = 'laura_martinez94@gmail.com'), 11, 1.0),  -- Lentejas cocidas
((SELECT id_comida FROM comidas WHERE nombre = 'Comida' AND fecha = '2025-04-20' AND email = 'laura_martinez94@gmail.com'), 8, 1.0);   -- Zanahoria

-- Cena: 20/04/2025
INSERT INTO comidas_alimentos (id_comida, id_alimento, cantidad) VALUES
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-20' AND email = 'laura_martinez94@gmail.com'), 37, 1.0),  -- Huevo cocido
((SELECT id_comida FROM comidas WHERE nombre = 'Cena' AND fecha = '2025-04-20' AND email = 'laura_martinez94@gmail.com'), 9, 1.0);   -- Tomate


-- -------------------------------------------------------------------------------------------------------------------------------------------
-- Inserción de objetivos
INSERT INTO objetivos (email, peso_actual, peso_objetivo, objetivo_usuario, opcion_peso, actividad) VALUES 
('laura_martinez94@gmail.com', 67.0, 60.0, 'PERDERPESO', 'LIGERO', 'MODERADO');

-- -------------------------------------------------------------------------------------------------------------------------------------------
-- Inserción de valores_nutricionales
INSERT INTO valores_nutricionales (email, kcal_objetivo, proteinas, carbohidratos, grasas, porcentaje_proteinas, porcentaje_carbohidratos, porcentaje_grasas) VALUES 
('laura_martinez94@gmail.com', 1800, 120.0, 150.0, 50.0, 26.7, 33.3, 40.0);


-- -------------------------------------------------------------------------------------------------------------------------------------------


-- Inserción de favoritos
INSERT INTO favoritos (email, id_alimento) VALUES 
('laura_martinez94@gmail.com', 2), 
('laura_martinez94@gmail.com', 21), 
('laura_martinez94@gmail.com', 31);

-- -------------------------------------------------------------------------------------------------------------------------------------------

-- Progresos de laura_martinez94@gmail.com
INSERT INTO progresos (peso, fecha_cambio, email) VALUES
(70.0, '2025-03-27 08:00:00', 'laura_martinez94@gmail.com'), 
(69.0, '2025-03-30 08:00:00', 'laura_martinez94@gmail.com'),  
(68.0, '2025-04-02 08:00:00', 'laura_martinez94@gmail.com'),  
(67.0, '2025-04-05 08:00:00', 'laura_martinez94@gmail.com');  


-- =========================
-- FIN DEL SCRIPT
-- =========================
