INSERT INTO personas (
    nombre,
    genero,
    edad,
    identificacion,
    direccion,
    telefono
)
VALUES
    ('Jose Lema', 'MALE', 30, '1024467001', 'CR 35 54 60', '+573013680001'),
    ('Marianela Montalvo', 'FEMALE', 28, '1024467002', 'CR 7 99 10', '573013680002'),
    ('Juan Osorio', 'MALE', 35, '1024467003', 'CL 74 15 66', '573013680003');

INSERT INTO clientes (
    persona_id,
    contrasena,
    estado
)
VALUES
    (1, '1234', TRUE),
    (2, '5678', TRUE),
    (3, '1245', TRUE);