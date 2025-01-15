CREATE DATABASE EVENTOS_BBDD;
USE EVENTOS_BBDD;

-- alter table usuarios modify column password varchar(200);

CREATE TABLE TIPOS
(ID_TIPO INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
NOMBRE VARCHAR(45) NOT NULL,
DESCRIPCION VARCHAR(200)
);


CREATE TABLE USUARIOS
(
USERNAME VARCHAR(45) NOT NULL PRIMARY KEY,
PASSWORD VARCHAR(200) NOT NULL,
EMAIL VARCHAR(100) NOT NULL UNIQUE,
NOMBRE VARCHAR(30),
APELLIDOS VARCHAR(45),
DIRECCION VARCHAR(100),
ENABLED INT NOT NULL DEFAULT 1,
FECHA_REGISTRO DATE
);



CREATE TABLE PERFILES
(ID_PERFIL INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
NOMBRE VARCHAR(45) NOT NULL
);

CREATE TABLE USUARIO_PERFILES
(USERNAME VARCHAR(45),
ID_PERFIL INT NOT NULL,
PRIMARY KEY(USERNAME, ID_PERFIL),
FOREIGN KEY(USERNAME) REFERENCES USUARIOS(USERNAME),
FOREIGN KEY(ID_PERFIL) REFERENCES PERFILES(ID_PERFIL)
);

CREATE TABLE EVENTOS
(ID_EVENTO INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
NOMBRE VARCHAR(50) NOT NULL,
DESCRIPCION VARCHAR(200),
FECHA_INICIO DATE,
DURACION INT,
DIRECCION VARCHAR(100),
ESTADO ENUM('ACEPTADO','TERMINADO','CANCELADO'),
DESTACADO ENUM('S','N'),
AFORO_MAXIMO INT,
MINIMO_ASISTENCIA INT,
PRECIO DEC(9,2),
ID_TIPO INT NOT NULL,
FOREIGN KEY(ID_TIPO) REFERENCES TIPOS(ID_TIPO)

);



CREATE TABLE RESERVAS
(ID_RESERVA INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
ID_EVENTO INT NOT NULL,
USERNAME VARCHAR(45)  NOT NULL,
PRECIO_VENTA DEC(9,2),
OBSERVACIONES VARCHAR(200),
CANTIDAD INT,
FOREIGN KEY(USERNAME) REFERENCES USUARIOS(USERNAME),
FOREIGN KEY(ID_EVENTO) REFERENCES EVENTOS(ID_EVENTO),
check(cantidad between 1 and 10),
UNIQUE(ID_EVENTO,USERNAME)
);

-- Alta de Tipos de evento
insert into tipos values(1,'Boda','Bodas grandes, pequeñas y medianas'),
						(2,'Conciertos','Espacios abiertos o cerrado, nacionales e internacionales');
-- Alta de perfiles
insert into perfiles values
(1, 'ROLE_ADMON'), (2,'ROLE_CLIENTE');
-- Alta de Usuarios                        
insert into usuarios values
('tomas', '{noop}tomasin', 'tomas@fp.com','Tomas', 'Profesor', 'Madrid',1,'2019-11-06'),
('eva', '{noop}evita', 'eva@fp.com','Eva', 'Goma', 'Sevilla',1,'2020-07-07');

-- Alata de Usuario_Perfiles
insert into usuario_perfiles values
('tomas', 1),('eva', 2);

-- Alta de eventos
insert into eventos values
(1, 'Boda de Blas', 'No entra ni una persoan de más', '2025-05-15', 3, 'Madrid', 'ACEPTADO', 'S', 100,20,200,1),
(2, 'Boda de Epi', 'Absolutamente informal, ven como quieras', '2025-06-15', 3, 'Madrid', 'ACEPTADO', 'S', 15,5,150,1),
(3, 'We will of rock', 'Te lo vas a pasar pipa', '2025-03-15', 1, 'Madrid', 'ACEPTADO', 'S', 100,20,200,2);

-- Alta de reservas
insert into reservas values(1,2,'eva',150,'Hay plazas de garaje, o autobus', 10);