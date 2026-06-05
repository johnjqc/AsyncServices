# Persona Service

Microservicio encargado de la gestión de Personas y Clientes.

---

## Responsabilidades

- Crear clientes
- Consultar clientes
- Actualizar clientes
- Eliminar clientes
- Publicación de eventos Kafka
    - client.created
    - client.updated

---

## Modelo de Dominio

### Person

```text
Person
├── id
├── name
├── gender
├── age
├── identification
├── address
└── phone
```

### Client

```text
Client
├── clientId
├── password
├── active
└── person
```

---

## Eventos Publicados

### client.created

Publicado cuando un cliente es creado.

```json
{
  "clientId": 1,
  "name": "Jose Lema",
  "identification": "123456"
}
```

---

### client.updated

Publicado cuando un cliente es actualizado.

```json
{
  "clientId": 1,
  "name": "Jose Lema",
  "identification": "123456"
}
```

---

## API

```http
http://localhost:8081/swagger-ui.html
```

OpenAPI Especificación:

```text
src/main/resources/openapi/customer-api.yaml
```

---

## Base de Datos

Tablas:

```text
person
client
```

---

## Pruebas

Ejecutar:

```bash
./gradlew test
```

Incluye:

- Unit Test de Client
- Integration Test de API REST

---

## Docker

Construcción:

```bash
docker build -t persona-service .
```