# Cuenta Service

Microservicio responsable de la gestión de cuentas, movimientos y generación de reportes de estado de cuenta.

Este servicio forma parte de una solución basada en microservicios y se comunica de forma asíncrona con el servicio de clientes mediante eventos Kafka.

---

# Arquitectura

La solución está implementada utilizando una arquitectura por capas:

```text
src/main/java
├── controller
├── service
├── repository
├── entity
├── dto
├── mapper
├── exception
├── config
├── consumer
└── producer
```

## Capas

### Controller

Expone los endpoints REST definidos en OpenAPI.

### Service

Implementa la lógica de negocio.

### Repository

Acceso a datos mediante Spring Data JPA.

### Entity

Entidades persistidas en la base de datos.

### DTO

Objetos utilizados para intercambio de información entre capas.

### Mapper

Transformación entre entidades, DTOs y modelos OpenAPI.

### Consumer

Consumidores Kafka para sincronización de información proveniente del servicio de clientes.

---

# Tecnologías

* Java 21
* Spring Boot 3
* Spring Data JPA
* Spring Kafka
* PostgreSQL
* Docker
* OpenAPI 3
* Gradle
* Lombok
* JUnit 5

---

# Comunicación entre Microservicios

La comunicación entre el servicio de clientes y el servicio de cuentas es asíncrona mediante Kafka.

## Eventos Consumidos

### client-created

Permite registrar un snapshot del cliente.

```json
{
  "clientId": 1,
  "name": "Jose Lema",
  "identification": "1234",
  "status": true
}
```

### client-updated

Actualiza la información del snapshot del cliente.

```json
{
  "clientId": 1,
  "name": "Jose Lema",
  "identification": "1234",
  "status": true
}
```

### client-deleted

Desactiva o elimina el snapshot del cliente.

```json
{
  "clientId": 1
}
```

---

# Base de Datos

## Tablas principales

### client_snapshot

Replica local del cliente para desacoplar el servicio de cuentas.

### cuenta

Información de cuentas bancarias.

### movimiento

Registro histórico de movimientos.

---

# Endpoints

## Accounts

### Crear cuenta

```http
POST /api/v1/accounts
```

### Consultar cuentas

```http
GET /api/v1/accounts
```

Parámetros:

```text
clientId (opcional)
page
size
```

### Consultar cuenta por número

```http
GET /api/v1/accounts/{accountNumber}
```

### Actualizar cuenta

```http
PUT /api/v1/accounts/{accountNumber}
```

---

## Transactions

### Registrar movimiento

```http
POST /api/v1/transactions
```

Soporta:

* DEPOSIT
* WITHDRAWAL

### Consultar movimientos por cuenta

```http
GET /api/v1/transactions/account/{accountNumber}
```

Parámetros:

```text
page
size
```

---

## Reports

### Estado de Cuenta

```http
GET /api/v1/reports/account-statement
```

Parámetros:

```text
clientId
fromDate
toDate
```

Ejemplo:

```http
GET /api/v1/reports/account-statement?clientId=2&fromDate=2022-02-01&toDate=2022-02-28
```

---

# Ejemplo de Respuesta del Reporte

```json
{
  "clientId": 2,
  "clientName": "Marianela Montalvo",
  "identification": "5678",
  "fromDate": "2022-02-01",
  "toDate": "2022-02-28",
  "reportGeneratedAt": "2026-06-04T22:30:00Z",
  "accounts": [
    {
      "accountId": 2,
      "accountNumber": "225487",
      "accountType": "CHECKING",
      "status": true,
      "openingBalance": 100,
      "closingBalance": 700,
      "transactions": [
        {
          "transactionId": 1,
          "date": "2022-02-10T11:00:00Z",
          "transactionType": "DEPOSIT",
          "amount": 600,
          "initialBalance": 100,
          "availableBalance": 700
        }
      ]
    }
  ]
}
```

---

# Reglas de Negocio

## Registro de Movimientos

### Depósito

Incrementa el saldo disponible de la cuenta.

### Retiro

Disminuye el saldo disponible de la cuenta.

## Validación de Saldo

Si el retiro supera el saldo disponible se genera la excepción:

```text
Saldo no disponible
```

Retornando:

```http
HTTP 400 Bad Request
```

## Validación de Cliente

Antes de crear una cuenta se valida la existencia del cliente mediante la tabla:

```text
client_snapshot
```

La información es mantenida mediante eventos Kafka.

---

# Ejecución Local

## Requisitos

* Java 21
* Docker
* Docker Compose

## Compilar

```bash
./gradlew clean build
```

## Ejecutar

```bash
./gradlew bootRun
```

---

# Docker

## Construir Imagen

```bash
docker build -t cuenta-service .
```

## Ejecutar Contenedor

```bash
docker run -p 8080:8080 cuenta-service
```

---

# Docker Compose

La solución puede ejecutarse junto con:

* PostgreSQL
* Kafka
* Zookeeper
* Cliente Service

mediante Docker Compose.

```bash
docker-compose up -d
```

---

# Pruebas

## Unitarias

```bash
./gradlew test
```

## Cobertura mínima implementada

* Account Service
* Transaction Service
* Report Service

---

# OpenAPI

La especificación se encuentra en:

```text
src/main/resources/openapi/account-api.yaml
```

La generación de código se realiza mediante OpenAPI Generator durante el proceso de build.

---

# Consideraciones de Escalabilidad

## Rendimiento

* Paginación para consultas de cuentas y movimientos.
* Índices sobre claves de búsqueda frecuentes.
* Consultas optimizadas mediante Spring Data JPA.

## Escalabilidad

* Separación en microservicios.
* Comunicación asíncrona mediante Kafka.
* Independencia de despliegue entre servicios.

## Resiliencia

* Desacoplamiento mediante eventos.
* Uso de snapshots locales para evitar dependencias síncronas.
* Persistencia independiente por microservicio.
* Recuperación automática de eventos mediante Kafka Consumer Groups.
