# Async Services - Technical Challenge

Solución desarrollada utilizando una arquitectura basada en microservicios con Java 21, Spring Boot y Apache Kafka.

## Arquitectura General

La solución está compuesta por dos microservicios independientes:

| Servicio | README                                                                                          | Responsabilidad                            |
|-----------|-------------------------------------------------------------------------------------------------|--------------------------------------------|
| Persona Service | [PersonaService/README.md](./PersonaService/README.md)                                          | Gestión de Personas y Clientes             |
| Cuenta Service | [CuentaService/README.md](./CuentaService/README.md) | Gestión de Cuentas, Movimientos y Reportes |

La comunicación entre servicios se realiza de forma asíncrona mediante Apache Kafka.

```text
+------------------+
|  Persona Service |
+------------------+
          |
          | client.created
          | client.updated
          v
+------------------+
|      Kafka       |
+------------------+
          |
          v
+------------------+
|  Cuenta Service  |
+------------------+
```

---

## Tecnologías

- Java 21
- Spring Boot 3
- Spring Data JPA
- Spring Validation
- Spring Kafka
- OpenAPI Generator
- Lombok
- Docker
- Docker Compose
- H2 Database
- JUnit 5
- MockMvc

---

## Estructura del Proyecto

```text
AsyncServices
│
├── PersonaService
│   └── README.md
│
├── CuentaService
│   └── README.md
│
├── docker-compose.yml
│
├── Customer Service API.postman_collection.json
│
├── Account Service API.postman_collection.json
│
└── README.md
```

---

## Funcionalidades Implementadas


---

### Cuenta Service

- Gestión de cuentas
- Registro de movimientos
- Validación de saldo disponible
- Reporte de estado de cuenta
- Consumo de eventos Kafka

Ver documentación detallada:


---

## Comunicación Asíncrona

El servicio de cuentas no realiza llamadas HTTP hacia Persona Service.

Para obtener información del cliente se implementó un patrón de:

```text
Local Read Model
```

mediante una tabla:

```text
client_snapshot
```

actualizada a través de eventos Kafka.

Esto permite:

- Menor acoplamiento
- Mayor disponibilidad
- Mejor rendimiento
- Escalabilidad independiente

---

## Ejecución Local

### Requisitos

- Docker
- Docker Compose

---

### Levantar toda la plataforma

```bash
docker compose up --build
```

---

## Servicios Disponibles

| Servicio | URL |
|-----------|-----------|
| Persona Service | http://localhost:8081 |
| Cuenta Service | http://localhost:8082 |
| Kafka UI | http://localhost:8090 |

---

## Colecciones Postman

Incluidas en la raíz del proyecto:

```text
Customer Service API.postman_collection.json
Account Service API.postman_collection.json
```

---

## Pruebas

Se incluyen en PersonaService:

- Prueba unitaria
- Prueba de integración