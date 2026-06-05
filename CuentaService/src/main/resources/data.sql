
INSERT INTO cuenta (numero_cuenta, tipo_cuenta, saldo, estado, cliente_id)
VALUES ('478758', 'AHORROS', 1425, TRUE, 1);

INSERT INTO cuenta (numero_cuenta, tipo_cuenta, saldo, estado, cliente_id)
VALUES ('225487', 'CORRIENTE', 700, TRUE, 2);

INSERT INTO cuenta (numero_cuenta, tipo_cuenta, saldo, estado, cliente_id)
VALUES ('495878', 'AHORROS', 150, TRUE, 3);

INSERT INTO cuenta (numero_cuenta, tipo_cuenta, saldo, estado, cliente_id)
VALUES ('496825', 'AHORROS', 0, TRUE, 2);

INSERT INTO cuenta (numero_cuenta, tipo_cuenta, saldo, estado, cliente_id)
VALUES ('585545', 'CORRIENTE', 1000, TRUE, 1);

INSERT INTO movimiento (cuenta_id, fecha, tipo_movimiento, valor, estado, saldo_inicial, saldo_disponible)
VALUES (
    1,
           '2022-02-10 10:00:00',
           'WITHDRAWAL',
    -575.00,
           TRUE,
           2000.00,
           1425.00
       );

INSERT INTO movimiento (cuenta_id, fecha, tipo_movimiento, valor, estado, saldo_inicial, saldo_disponible)
VALUES (
           2,
           '2022-02-10 11:00:00',
           'DEPOSIT',
           600.00,
           TRUE,
           100.00,
           700.00
       );

INSERT INTO movimiento (cuenta_id, fecha, tipo_movimiento, valor, estado, saldo_inicial, saldo_disponible)
VALUES (
           3,
           '2022-02-08 09:00:00',
           'DEPOSIT',
           150.00,
           TRUE,
           0.00,
           150.00
       );

INSERT INTO movimiento (cuenta_id, fecha, tipo_movimiento, valor, estado, saldo_inicial, saldo_disponible)
VALUES (
           4,
           '2022-02-08 12:00:00',
           'WITHDRAWAL',
           -540.00,
           TRUE,
           540.00,
           0.00
       );

INSERT INTO client_snapshot (client_id, name, identification)
VALUES (1, 'Jose Lema', '1024467001');

INSERT INTO client_snapshot (client_id, name, identification)
VALUES (2, 'Marianela Montalvo', '1024467002');

INSERT INTO client_snapshot (client_id, name, identification)
VALUES (3, 'Juan Osorio', '1024467003');