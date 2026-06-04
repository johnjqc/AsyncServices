
INSERT INTO cuenta (numero_cuenta, tipo_cuenta, saldo_inicial, estado, cliente_id)
VALUES ('478758', 'AHORROS', 2000, TRUE, 1);

INSERT INTO cuenta (numero_cuenta, tipo_cuenta, saldo_inicial, estado, cliente_id)
VALUES ('225487', 'CORRIENTE', 100, TRUE, 2);

INSERT INTO cuenta (numero_cuenta, tipo_cuenta, saldo_inicial, estado, cliente_id)
VALUES ('495878', 'AHORROS', 0, TRUE, 3);

INSERT INTO cuenta (numero_cuenta, tipo_cuenta, saldo_inicial, estado, cliente_id)
VALUES ('496825', 'AHORROS', 540, TRUE, 2);

INSERT INTO cuenta (numero_cuenta, tipo_cuenta, saldo_inicial, estado, cliente_id)
VALUES ('585545', 'CORRIENTE', 1000, TRUE, 1);


INSERT INTO movimiento (cuenta_id, fecha, tipo_movimiento, valor, saldo_anterior, saldo_disponible, descripcion)
VALUES (
           1,
           '2022-02-10 10:00:00',
           'RETIRO',
           -575,
           2000,
           1425,
           'Retiro de 575'
       );

INSERT INTO movimiento (cuenta_id, fecha, tipo_movimiento, valor, saldo_anterior, saldo_disponible, descripcion)
VALUES (
           2,
           '2022-02-10 11:00:00',
           'DEPOSITO',
           600,
           100,
           700,
           'Depósito de 600'
       );

INSERT INTO movimiento (cuenta_id, fecha, tipo_movimiento, valor, saldo_anterior, saldo_disponible, descripcion)
VALUES (
           3,
           '2022-02-08 09:00:00',
           'DEPOSITO',
           150,
           0,
           150,
           'Depósito de 150'
       );

INSERT INTO movimiento (cuenta_id, fecha, tipo_movimiento, valor, saldo_anterior, saldo_disponible, descripcion)
VALUES (
           4,
           '2022-02-08 12:00:00',
           'RETIRO',
           -540,
           540,
           0,
           'Retiro de 540'
       );