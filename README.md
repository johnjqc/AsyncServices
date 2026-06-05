# Async Services - Technical Challenge

## Descripción

Solución basada en arquitectura de microservicios desarrollada con Spring Boot 3 y Java 21.

El sistema está compuesto por dos dominios independientes:

* **Persona Service**: Gestión de clientes.
* **Cuenta Service**: Gestión de cuentas y movimientos.

La comunicación entre ambos servicios se realiza mediante eventos asincrónicos utilizando Apache Kafka.

---

## Arquitectura

### Microservicios

#### Persona Service

Responsable de:

* Crear clientes
* Consultar clientes
* Actualizar clientes
* Eliminar clientes

Cuando un cliente es creado exitosamente, publica un evento en Kafka para notificar a otros dominios.

---

#### Cuenta Service

Responsable de:

* Crear cuentas bancarias
* Registrar movimientos
* Consultar movimientos
* Generar reportes

Consume eventos emitidos por Persona Service para mantener una proyección local de clientes.

---

## Comunicación Asíncrona

La integración entre dominios se realiza mediante Apache Kafka.

### Evento publicado

Topic:

```text
client-created
```

Payload:

```json
{
  "clientId": 1,
  "name": "Jose Lema",
  "identification": "1234567890"
}
```

### Flujo

1. Persona Service crea un cliente.
2. Persona Service publica evento ClientCreated.
3. Kafka almacena el evento.
4. Cuenta Service consume el evento.
5. Cuenta Service actualiza su proyección local de clientes.

Este diseño elimina el acoplamiento síncrono entre microservicios.

---

## Arquitectura del Repositorio

```text
AsyncServices/
│
├── docker-compose.yml
│
├── docker/
│   └── kafka/
│       └── init-topics.sh
│
├── PersonaService/
│   ├── src/
│   ├── build.gradle
│   └── Dockerfile
│
├── CuentaService/
│   ├── src/
│   ├── build.gradle
│   └── Dockerfile
│
└── README.md
```
---

## Tecnologías Utilizadas

* Java 21
* Spring Boot 3.2
* Spring Data JPA
* Spring Kafka
* Apache Kafka
* Zookeeper
* H2 Database
* Gradle
* Docker
* Docker Compose
* OpenAPI
* MapStruct
* JUnit 5
* Mockito
* Testcontainers

---

## Levantar la Solución

### Requisitos

* Docker
* Docker Compose

Verificar instalación:

```bash
docker --version
docker-compose --version
```

---

### Construcción

Desde la raíz del proyecto:

```bash
docker-compose build
```

---

### Ejecución

```bash
docker-compose up
```

o

```bash
docker-compose up --build
```

---

## Servicios Disponibles

| Servicio        | URL                              |
| --------------- | -------------------------------- |
| Persona Service | http://localhost:8081            |
| Cuenta Service  | http://localhost:8082            |
| Kafka UI        | http://localhost:8090            |
| H2 Persona      | http://localhost:8081/h2-console |
| H2 Cuenta       | http://localhost:8082/h2-console |

---

## Configuración Kafka

### Broker interno

```text
kafka:9092
```

### Broker externo

```text
localhost:29092
```

### Topics

```text
client-created
```

Los topics son creados automáticamente mediante el contenedor:

```text
kafka-init
```

---

## Documentación OpenAPI

### Persona Service

```text
http://localhost:8081/swagger-ui.html
```

o

```text
http://localhost:8081/swagger-ui/index.html
```

---

### Cuenta Service

```text
http://localhost:8082/swagger-ui.html
```

o

```text
http://localhost:8082/swagger-ui/index.html
```

---

## Bases de Datos

Actualmente ambos servicios utilizan H2.

### Persona Service

```text
jdbc:h2:mem:test
```

### Cuenta Service

```text
jdbc:h2:mem:test
```

Usuario:

```text
sa
```

Password:

```text
(vacío)
```

---

## Consideraciones de Escalabilidad

La solución contempla:

### Escalabilidad

* Servicios desacoplados mediante eventos.
* Kafka permite múltiples consumidores.
* Posibilidad de particionar topics.

### Rendimiento

* Comunicación asíncrona.
* Eliminación de dependencias síncronas entre dominios.

### Resiliencia

* Persistencia de eventos en Kafka.
* Reintentos automáticos de consumidores.
* Recuperación ante reinicio de servicios.

---

## Pruebas

Ejecutar pruebas unitarias:

```bash
./gradlew test
```

o

```bash
gradle test
```

