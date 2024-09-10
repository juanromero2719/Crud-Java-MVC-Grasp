CREATE SCHEMA IF EXISTS Prueba;

CREATE TABLE Prueba.PAIS(
    id INT PRIMARY KEY,
    nombre VARCHAR(255)
);

CREATE TABLE Prueba.CARGO (
    id INT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL  
);

CREATE TABLE Prueba.DEPARTAMENTO (
    id INT PRIMARY KEY,
    nombre VARCHAR(255),
    pais_id INT,
    CONSTRAINT fk_pais FOREIGN KEY (pais_id) REFERENCES Prueba.PAIS(id) ON DELETE CASCADE
);

CREATE TABLE Prueba.MUNICIPIO (
    id INT PRIMARY KEY,
    nombre VARCHAR(255),
    departamento_id INT,
    CONSTRAINT fk_departamento FOREIGN KEY (departamento_id) REFERENCES Prueba.DEPARTAMENTO(id) ON DELETE CASCADE
);

CREATE TABLE Prueba.DIRECCION (
    id INT PRIMARY KEY,
    municipio_id INT,
    calle VARCHAR(255),
    carrera VARCHAR(255),
    coordenada VARCHAR(255),
    descripcion TEXT,
    CONSTRAINT fk_municipio_direccion FOREIGN KEY (municipio_id) REFERENCES Prueba.MUNICIPIO(id) ON DELETE CASCADE,
    
);

CREATE TABLE Prueba.PERSONA (
    id INT PRIMARY KEY,
    nombres VARCHAR(255) NOT NULL,
    apellidos VARCHAR(255) NOT NULL,
    direccion_id INT,
    FOREIGN KEY (direccion_id) REFERENCES Prueba.DIRECCION(id) ON DELETE CASCADE
);

CREATE TABLE Prueba.EMPLEADO (
    id INT PRIMARY KEY,
    salario DOUBLE,
    cargo_id INT,
    persona_id INT,
    FOREIGN KEY (persona_id) REFERENCES Prueba.PERSONA(id) ON DELETE CASCADE,
    FOREIGN KEY (cargo_id) REFERENCES Prueba.CARGO(id)
);

CREATE TABLE Prueba.Estudiante (
    id INT PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE,
    programa VARCHAR(100),
    promedio DOUBLE,
    persona_id INT,
    FOREIGN KEY (persona_id) REFERENCES Prueba.PERSONA(id) ON DELETE CASCADE
);


