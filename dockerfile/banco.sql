-- Conectar como el usuario SYS
CONNECT SYS/mysecretpassword AS SYSDBA;


ALTER SESSION SET "_ORACLE_SCRIPT"=true;

-- Crear el usuario BANCO
CREATE USER BANCO IDENTIFIED BY mysecretpassword;

-- Otorgar privilegios al usuario BANCO
GRANT CONNECT, RESOURCE, DBA TO BANCO;

-- Conectar como el usuario BANCO
CONNECT BANCO/mysecretpassword;

-- Crear la secuencia para cliente_id
CREATE SEQUENCE seq_cliente_id
    START WITH 100000000
    INCREMENT BY 1;

-- Crear la secuencia para numero_cuenta
CREATE SEQUENCE seq_numero_cuenta
    START WITH 100000000000
    INCREMENT BY 1;

-- Crear la tabla Cliente
CREATE TABLE Cliente (
                         cliente_id NUMBER(9) DEFAULT seq_cliente_id.NEXTVAL PRIMARY KEY,
                         contrasena VARCHAR2(60) NOT NULL,
                         estado NUMBER(1) NOT NULL,
                         tipo_identificacion VARCHAR2(20) NOT NULL,
                         identificacion VARCHAR2(20) NOT NULL,
                         nombre VARCHAR2(100) NOT NULL,
                         genero VARCHAR2(1) NOT NULL,
                         edad NUMBER(3) NOT NULL,
                         direccion VARCHAR2(200) NOT NULL,
                         telefono VARCHAR2(15) NOT NULL,
                         email VARCHAR2(100),
                         fecha_alta TIMESTAMP WITH TIME ZONE NOT NULL,
                         usuario_alta VARCHAR2(10) NOT NULL,
                         fecha_modificacion TIMESTAMP WITH TIME ZONE,
                         usuario_modificacion VARCHAR2(50)
);

-- Modificar la tabla Cliente para agregar la restricción UNIQUE
ALTER TABLE Cliente
    ADD CONSTRAINT unique_identificacion UNIQUE (tipo_identificacion, identificacion);

-- Crear la tabla Cuenta
CREATE TABLE Cuenta (
                        numero_cuenta NUMBER(12) DEFAULT seq_numero_cuenta.NEXTVAL PRIMARY KEY,
                        tipo_cuenta VARCHAR2(20) NOT NULL,
                        saldo NUMBER(10, 2) NOT NULL,
                        estado NUMBER(1) NOT NULL,
                        cliente_id NUMBER NOT NULL,
                        fecha_alta TIMESTAMP WITH TIME ZONE NOT NULL,
                        usuario_alta VARCHAR2(10) NOT NULL,
                        fecha_modificacion TIMESTAMP WITH TIME ZONE,
                        usuario_modificacion VARCHAR2(10),
                        FOREIGN KEY (cliente_id) REFERENCES Cliente(cliente_id)
);

-- Crear la tabla Movimiento
CREATE TABLE Movimiento (
                            movimiento_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                            fecha TIMESTAMP WITH TIME ZONE NOT NULL,
                            tipo_movimiento VARCHAR2(20) NOT NULL,
                            operacion_credito_debito VARCHAR2(1) NOT NULL,
                            valor NUMBER(10, 2) NOT NULL,
                            saldo NUMBER(10, 2) NOT NULL,
                            numero_cuenta NUMBER NOT NULL,
                            FOREIGN KEY (numero_cuenta) REFERENCES Cuenta(numero_cuenta)
);



-- Insertar datos

-- Insertar datos en la tabla Cliente
INSERT INTO Cliente (
    contrasena, estado, tipo_identificacion, identificacion, nombre, genero, edad, direccion, telefono, email, fecha_alta, usuario_alta
) VALUES (
             'contrasena1', 1, 'DNI', '12345678', 'Juan Perez', 'M', 30, 'Calle Falsa 123', '1234567890', 'juan.perez@example.com', CURRENT_TIMESTAMP, 'usuario1'
         );

-- Insertar datos en la tabla Cuenta
INSERT INTO Cuenta (
    tipo_cuenta, saldo, estado, cliente_id, fecha_alta, usuario_alta
) VALUES (
             'Ahorro', 2000.00, 1, seq_cliente_id.CURRVAL, CURRENT_TIMESTAMP, 'usuario1'
         );

-- Insertar datos en la tabla Movimiento
INSERT INTO Movimiento (
    fecha, tipo_movimiento, operacion_credito_debito, valor, saldo, numero_cuenta
) VALUES (
            CURRENT_TIMESTAMP, 'Deposito','-', 1000.00, 1000.00, seq_numero_cuenta.CURRVAL
         );
COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error al insertar datos: ' || SQLERRM);-END;